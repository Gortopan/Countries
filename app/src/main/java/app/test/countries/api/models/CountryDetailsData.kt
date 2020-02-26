package app.test.countries.api.models

import com.google.gson.annotations.SerializedName

data class CountryDetailsData (
	@SerializedName("country") val country : CountryDetails
)