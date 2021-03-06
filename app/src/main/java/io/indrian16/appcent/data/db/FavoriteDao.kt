package io.indrian16.appcent.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.indrian16.appcent.data.model.Favorite
import io.reactivex.Single

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(bookmark: Favorite)

    @Query("SELECT * FROM favorites ORDER BY saveTime DESC")
    fun getFavorites(): Single<List<Favorite>>

    @Query("DELETE FROM favorites WHERE slug = :url")
    fun deleteFavorite(url: String)

    @Query("SELECT * FROM favorites WHERE slug = :url")
    fun getFavoriteIsExist(url: String): Single<List<Favorite>>
}