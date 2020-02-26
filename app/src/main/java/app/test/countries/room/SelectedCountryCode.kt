package app.test.countries.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_country_code")
data class SelectedCountryCode(@PrimaryKey val code: String)