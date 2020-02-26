package app.test.countries.api.models

import com.google.gson.annotations.SerializedName

data class Languages (
	@SerializedName("code") val code : String,
	@SerializedName("name") val name : String
)