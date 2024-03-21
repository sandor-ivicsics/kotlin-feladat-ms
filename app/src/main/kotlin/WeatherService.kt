package hu.vanio.kotlin.feladat.ms

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.time.LocalDate
import java.util.LinkedList

@Service
class WeatherService(private val configuration: WeatherConfiguration) {
    companion object {
        private val logger = LoggerFactory.getLogger(WeatherService::class.java)
    }

    private val restClient = RestClient.create()

    fun calculateAverageDailyTemperatures(): List<WeatherDataLocalDateAndTemperature>? {
        val responseData = restClient.get().uri(configuration.uri).retrieve().body(WeatherData::class.java)
        if (responseData == null) {
            logger.error("no response data")
            return null
        }
        logger.debug("time: {}", responseData.hourly.time)
        logger.debug("temperature_2m: {}", responseData.hourly.temperature)
        if (responseData.hourly.time.size != responseData.hourly.temperature.size) {
            logger.error("bad response data")
            return null
        }
        val tmpList = mutableListOf<WeatherDataLocalDateAndTemperature>()
        for (i in 0 until responseData.hourly.time.size) {
            val time = responseData.hourly.time[i]
            val localDate = LocalDate.parse(time.take(time.indexOf('T')))
            val temperature = responseData.hourly.temperature[i]
            tmpList.add(i, WeatherDataLocalDateAndTemperature(localDate, temperature))
        }
        logger.debug("{}", tmpList)
        val tmpMap = LinkedHashMap<LocalDate, LinkedList<Double>>()
        for (i in tmpList) {
            var tmpValue = tmpMap[i.localDate]
            if (tmpValue == null) {
                tmpValue = LinkedList<Double>()
                tmpMap[i.localDate] = tmpValue
            }
            tmpValue.add(i.temperature)
        }
        logger.debug("{}", tmpMap)
        val result = mutableListOf<WeatherDataLocalDateAndTemperature>()
        for ((key, value) in tmpMap) {
            result.add(WeatherDataLocalDateAndTemperature(key, value.average()))
        }
        logger.info("calculated average daily temperatures: {}", result)
        return result
    }
}