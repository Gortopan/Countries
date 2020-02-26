package app.test.countries.api.models

import com.google.gson.annotations.SerializedName

data class Data (
	@SerializedName("countries") val countries : List<Countries>
)