package com.gzaber.forexviewer.di

import android.content.Context
import androidx.room.Room
import com.gzaber.forexviewer.data.source.local.FavoriteDao
import com.gzaber.forexviewer.data.source.local.FavoritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesDatabaseModule {

    private const val DATABASE_NAME: String = "favorites-db"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoritesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoritesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: FavoritesDatabase): FavoriteDao = database.favoriteDao()
}