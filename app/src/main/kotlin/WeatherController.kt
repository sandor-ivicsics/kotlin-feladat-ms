package hu.vanio.kotlin.feladat.ms

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WeatherController(@Autowired private val service: WeatherService) {
    @GetMapping(path = ["/", "/index.html"])
    fun doGet(model: Model): String {
        val averageDailyTemperatures = service.calculateAverageDailyTemperatures()
            ?: return "error"
        model["averageDailyTemperatures"] = averageDailyTemperatures
        return "index"
    }
}