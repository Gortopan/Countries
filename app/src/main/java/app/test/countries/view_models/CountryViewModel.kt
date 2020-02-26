package app.test.countries.view_models

import androidx.lifecycle.MutableLiveData
import app.test.countries.api.models.CountryDetails
import app.test.countries.repository.CountriesRepository
import app.test.countries.repository.SelectedCountryRepository
import app.test.countries.room.SelectedCountryCode
import app.test.countries.schedulers.SchedulersProvider

class CountryViewModel : NetworkViewModel() {
    private var countriesRepository: CountriesRepository? = null
    private var selectedCountryRepository: SelectedCountryRepository? = null
    var selectedCountryCode: MutableLiveData<SelectedCountryCode> = MutableLiveData()
    var countryDetails: MutableLiveData<CountryDetails> = MutableLiveData()

    fun start(
        selectedCountryRepository: SelectedCountryRepository,
        countriesRepository: CountriesRepository,
        schedulersProvider: SchedulersProvider
    ) {
        this.selectedCountryRepository = selectedCountryRepository
        this.countriesRepository = countriesRepository

        compositeDisposable.add(
            selectedCountryRepository.getSelectedCountryCode()
                .subscribeOn(schedulersProvider.backgroundScheduler)
                .observeOn(schedulersProvider.mainScheduler)
                ?.subscribe({
                    isLoading.postValue(true)
                    this.selectedCountryCode.value = it
                    getCountryDetails(it.code, schedulersProvider)
                }, {
                    errorMessage.value = it.message
                    isLoading.value = false
                }) ?: return
        )
    }

    private fun getCountryDetails(code: String, schedulersProvider: SchedulersProvider) {
        compositeDisposable.add(countriesRepository?.getCountryDetails(code)
            ?.subscribeOn(schedulersProvider.backgroundScheduler)
            ?.observeOn(schedulersProvider.mainScheduler)
            ?.doOnTerminate {
                isLoading.postValue(false) }
            ?.subscribe({
                countryDetails.value = it.data.country
            }, {
                errorMessage.value = it.message
            }) ?: return
        )
    }
}
