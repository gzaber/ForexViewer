package com.gzaber.forexviewer.data.repository.apikey

import com.gzaber.forexviewer.util.fake.source.FakeDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test


class DefaultApiKeyRepositoryTest {

    private lateinit var localDataSource: FakeDataStore
    private lateinit var repository: DefaultApiKeyRepository

    private fun createRepository(
        initialData: String? = null,
        shouldThrow: Boolean = false
    ) {
        localDataSource = FakeDataStore(initialData = initialData, shouldThrow = shouldThrow)
        repository = DefaultApiKeyRepository(dataStore = localDataSource)
    }

    @Test
    fun loadApiKey_dataNotFound_emitsDefaultValue() = runTest {
        createRepository()
        val result = repository.loadApiKey().first()
        assert(result == "demo")
    }

    @Test
    fun loadApiKey_emitsFromLocalDataSource() = runTest {
        createRepository(initialData = "123apikey")
        val result = repository.loadApiKey().first()
        assert(result == "123apikey")
    }

    @Test
    fun loadApiKey_localDataSourceThrows_emitsDefaultValue() = runTest {
        createRepository(initialData = "123apikey", shouldThrow = true)
        val result = repository.loadApiKey().first()
        assert(result == "demo")
    }

    @Test
    fun saveApiKey_savesToLocalDataSource() = runTest {
        createRepository()
        repository.saveApiKey("123apikey")
        val result = repository.loadApiKey().first()
        assert(result == "123apikey")
    }
}