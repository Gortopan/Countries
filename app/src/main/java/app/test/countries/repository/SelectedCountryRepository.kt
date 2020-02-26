package app.test.countries.repository

import app.test.countries.api.models.Countries
import app.test.countries.room.Converter
import app.test.countries.room.SelectedCountryCode
import app.test.countries.room.SelectedCountryCodeDao
import io.reactivex.Completable
import io.reactivex.Flowable

class SelectedCountryRepository(private val selectedCountryCodeDao: SelectedCountryCodeDao) {

    fun selectCountryCode(countries: Countries): Completable {
        val selectedCountryCode = Converter().countryToSelected(countries)
        return selectedCountryCodeDao.deleteSelected()
            .andThen(selectedCountryCodeDao.insertSelectedCode(selectedCountryCode))
    }

    fun getSelectedCountryCode(): Flowable<SelectedCountryCode> {
        return selectedCountryCodeDao.getSelectedCountryCode()
    }
}