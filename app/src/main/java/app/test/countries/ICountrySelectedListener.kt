package app.test.countries

import app.test.countries.api.models.Countries

interface ICountrySelectedListener {
    fun onCountrySelected(countries: Countries)
}