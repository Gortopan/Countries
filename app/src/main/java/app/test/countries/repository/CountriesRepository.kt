package app.test.countries.repository

import app.test.countries.api.RetrofitFactory
import app.test.countries.api.models.CountriesResponse
import app.test.countries.api.models.CountryDetailsResponse
import io.reactivex.Flowable

class CountriesRepository {

    private val api = RetrofitFactory().api

    fun getCountries(): Flowable<CountriesResponse> {
        val s = "{\n" +
                "  countries {\n" +
                "    name\n" +
                "    code\n" +
                "    emoji\n" +
                "  }\n" +
                "}"
        return api.getCountries(s)
    }

    fun getCountryDetails(code: String): Flowable<CountryDetailsResponse> {
        val s = "{\n" +
                "  country(code: \"" + code + "\") {\n" +
                "    name\n" +
                "    native\n" +
                "    emoji\n" +
                "    currency\n" +
                "    languages {\n" +
                "      code\n" +
                "      name\n" +
                "    }\n" +
                "  }\n" +
                "}"
        return api.getCountryDetails(s)
    }
}