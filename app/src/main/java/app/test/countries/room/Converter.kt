package app.test.countries.room

import app.test.countries.api.models.Countries

class Converter {
    fun countryToSelected(serverCountry: Countries): SelectedCountryCode {
        return SelectedCountryCode(serverCountry.code)
    }
}