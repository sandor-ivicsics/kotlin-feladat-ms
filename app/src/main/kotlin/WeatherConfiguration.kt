package hu.vanio.kotlin.feladat.ms

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
@ConfigurationProperties(prefix = "hu.vanio.kotlin.feladat.ms")
class WeatherConfiguration {
    lateinit var uri: URI
}