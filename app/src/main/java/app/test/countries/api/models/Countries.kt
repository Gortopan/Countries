package app.test.countries.api.models

import com.google.gson.annotations.SerializedName

data class Countries (
	@SerializedName("name") val name : String,
	@SerializedName("code") val code : String,
	@SerializedName("emoji") val emoji : String
)