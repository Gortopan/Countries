package app.test.countries.api.models

import com.google.gson.annotations.SerializedName

data class CountryDetailsResponse (
	@SerializedName("data") val data : CountryDetailsData
)