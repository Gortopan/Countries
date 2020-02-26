package app.test.countries.api.models

import com.google.gson.annotations.SerializedName

data class CountriesResponse (
	@SerializedName("data") val data : Data
)