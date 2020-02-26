package app.test.countries.api.models

import com.google.gson.annotations.SerializedName

data class CountryDetails (
	@SerializedName("name") val name : String,
	@SerializedName("native") val native : String,
	@SerializedName("emoji") val emoji : String,
	@SerializedName("currency") val currency : String,
	@SerializedName("languages") val languages : List<Languages>
)