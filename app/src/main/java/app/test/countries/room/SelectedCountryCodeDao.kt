package app.test.countries.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface SelectedCountryCodeDao {
    @Query("SELECT * FROM SELECTED_COUNTRY_CODE LIMIT 1")
    fun getSelectedCountryCode(): Flowable<SelectedCountryCode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSelectedCode(user: SelectedCountryCode): Completable

    @Query("DELETE FROM SELECTED_COUNTRY_CODE")
    fun deleteSelected(): Completable
}