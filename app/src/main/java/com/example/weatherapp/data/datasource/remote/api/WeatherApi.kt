package com.example.weatherapp.data.datasource.remote.api

import com.example.weatherapp.data.datasource.remote.dto.WeatherInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class WeatherApi() {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = true
            })
        }
    }

    suspend fun getWeather(latitude: String, longitude: String): WeatherInfoDto {
        val response: HttpResponse = client.get {
            url("https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&daily=temperature_2m_max,precipitation_probability_max,temperature_2m_min,weather_code,uv_index_max&hourly=temperature_2m,weather_code,is_day&current=temperature_2m,wind_speed_10m,relative_humidity_2m,rain,weather_code,apparent_temperature,pressure_msl,is_day,precipitation")

        }
        return response.body<WeatherInfoDto>()


    }


}