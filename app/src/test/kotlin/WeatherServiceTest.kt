import hu.vanio.kotlin.feladat.ms.WeatherConfiguration
import hu.vanio.kotlin.feladat.ms.WeatherService

import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class WeatherServiceTest {
    @Test
    fun calculateAverageDailyTemperatures() {
        val configuration = WeatherConfiguration()
        configuration.uri = URI("https://api.open-meteo.com/v1/forecast?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto")
        val service = WeatherService(configuration)
        val averageDailyTemperatures = service.calculateAverageDailyTemperatures()
        assertNotNull(averageDailyTemperatures)
        assertEquals(7, averageDailyTemperatures.size)
        averageDailyTemperatures.forEach {
            assertNotNull(it.localDate)
            assertNotNull(it.temperature)
        }
    }
}