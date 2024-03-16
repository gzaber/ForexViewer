package com.gzaber.forexviewer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.apikey.ApiKeyRepository
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.ui.util.model.UiFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    forexDataRepository: ForexDataRepository,
    favoritesRepository: FavoritesRepository,
    private val apiKeyRepository: ApiKeyRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _uiFavorites = MutableStateFlow<List<UiFavorite>>(listOf())
    private val _apiKeyText = MutableStateFlow("")
    private val _showDialog = MutableStateFlow(false)
    private val _failureMessage = MutableStateFlow<String?>(null)

    val uiState = combine(
        _uiFavorites,
        _apiKeyText,
        _showDialog,
        _isLoading,
        _failureMessage,
    ) { uiFavorites, apiKeyText, showDialog, isLoading, failureMessage ->
        HomeUiState(
            uiFavorites, showDialog, apiKeyText, isLoading, failureMessage
        )
    }
        .catch {
            _failureMessage.value = it.message
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    init {
        viewModelScope.launch {
            apiKeyRepository.loadApiKey()
                .catch {
                    _failureMessage.value = it.message
                }
                .collect {
                    _apiKeyText.value = it
                }
        }

        viewModelScope.launch {
            try {
                favoritesRepository.loadAllFavorites()
                    .map { favorites ->
                        favorites.map { favorite ->
                            forexDataRepository.fetchExchangeRate(favorite.symbol)
                                .catch {
                                    _failureMessage.value = it.message
                                    emit(ExchangeRate(favorite.symbol, 0.0))
                                }
                                .map { exchangeRate ->
                                    UiFavorite(
                                        symbol = favorite.symbol,
                                        base = favorite.base,
                                        quote = favorite.quote,
                                        exchangeRate = exchangeRate.rate
                                    )
                                }.first()
                        }
                    }
                    .catch {
                        _failureMessage.value = it.message
                    }
                    .collect { uiFavorites ->
                        _isLoading.value = false
                        _uiFavorites.value = uiFavorites
                    }
            } catch (e: Exception) {
                _failureMessage.value = e.message
            }
        }
    }

    fun toggleShowingDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun onApiKeyTextChanged(text: String) {
        _apiKeyText.value = text
    }

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch {
            try {
                apiKeyRepository.saveApiKey(apiKey)
                toggleShowingDialog()
            } catch (e: Throwable) {
                _failureMessage.value = e.message
            }
        }
    }

    fun snackbarMessageShown() {
        _failureMessage.value = null
    }
}