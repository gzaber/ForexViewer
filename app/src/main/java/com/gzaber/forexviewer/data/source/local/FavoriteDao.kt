package com.gzaber.forexviewer.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites WHERE symbol = :symbol")
    fun loadBySymbol(symbol: String): Flow<FavoriteEntity?>

    @Query("SELECT * FROM favorites")
    fun loadAll(): Flow<List<FavoriteEntity>>
}