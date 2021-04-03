package io.indrian16.appcent.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.indrian16.appcent.data.model.Article
import io.indrian16.appcent.data.model.Favorite
import io.indrian16.appcent.util.AppConstant

@Database(entities = [Article::class, Favorite::class], version = AppConstant.DB_VER, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    abstract fun favoriteDao(): FavoriteDao
}