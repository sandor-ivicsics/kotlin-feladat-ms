package hu.vanio.kotlin.feladat.ms

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class WeatherDataHourly(
    @JsonProperty("time")
    val time: Array<String>,
    @JsonProperty("temperature_2m")
    val temperature: Array<Double>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherDataHourly

        if (!time.contentEquals(other.time)) return false
        if (!temperature.contentEquals(other.temperature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = time.contentHashCode()
        result = 31 * result + temperature.contentHashCode()
        return result
    }
}

data class WeatherData(
    val hourly: WeatherDataHourly
)

data class WeatherDataLocalDateAndTemperature(
    val localDate: LocalDate,
    val temperature: Double
)
