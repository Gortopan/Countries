package app.test.countries.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SelectedCountryCode::class],
    version = 1,
    exportSchema = false
)
abstract class CountriesDatabase : RoomDatabase() {

    abstract fun selectedCountriesDao(): SelectedCountryCodeDao

    companion object {

        @Volatile
        private var INSTANCE: CountriesDatabase? = null

        fun getInstance(context: Context): CountriesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CountriesDatabase::class.java,
                "CountriesDB"
            ).build()
    }
}