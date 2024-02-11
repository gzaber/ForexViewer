package com.gzaber.forexviewer.di

import com.gzaber.forexviewer.data.repository.apikey.ApiKeyRepository
import com.gzaber.forexviewer.data.repository.apikey.DefaultApiKeyRepository
import com.gzaber.forexviewer.data.repository.favorites.DefaultFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.DefaultForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindForexDataRepository(repository: DefaultForexDataRepository): ForexDataRepository

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(repository: DefaultFavoritesRepository): FavoritesRepository

    @Singleton
    @Binds
    abstract fun bindApiKeyRepository(repository: DefaultApiKeyRepository): ApiKeyRepository
}