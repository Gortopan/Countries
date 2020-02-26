package app.test.countries.view_models

import androidx.lifecycle.MutableLiveData
import app.test.countries.api.models.Countries
import app.test.countries.repository.CountriesRepository
import app.test.countries.repository.SelectedCountryRepository
import app.test.countries.schedulers.SchedulersProvider


class CountriesListViewModel : NetworkViewModel() {
    var countries = MutableLiveData<List<Countries>>()
    private var countriesRepository: CountriesRepository? = null
    private var selectedCountryRepository: SelectedCountryRepository? = null

    fun start(
        selectedCountryRepository: SelectedCountryRepository,
        countriesRepository: CountriesRepository,
        schedulersProvider: SchedulersProvider
    ) {
        this.selectedCountryRepository = selectedCountryRepository
        this.countriesRepository = countriesRepository

        isLoading.value = true
        compositeDisposable.add(
            countriesRepository.getCountries()
                .subscribeOn(schedulersProvider.backgroundScheduler)
                .observeOn(schedulersProvider.mainScheduler)
                .doOnTerminate { isLoading.postValue(false) }
                .subscribe({
                    if (it.data.countries.isNotEmpty()) {
                        countries.value = it.data.countries
                    }
                }, {
                    errorMessage.value = it.message
                })
        )
    }

    fun setCountrySelected(countries: Countries, schedulersProvider: SchedulersProvider) {
        compositeDisposable.add(selectedCountryRepository?.selectCountryCode(countries)
            ?.subscribeOn(schedulersProvider.backgroundScheduler)
            ?.subscribe() ?: return)
    }
}
