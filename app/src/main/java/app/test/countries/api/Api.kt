package app.test.countries.api

import app.test.countries.api.models.CountriesResponse
import app.test.countries.api.models.CountryDetailsResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(".")
    fun getCountries(@Query("query") results: String): Flowable<CountriesResponse>

    @GET(".")
    fun getCountryDetails(@Query("query") results: String): Flowable<CountryDetailsResponse>
}