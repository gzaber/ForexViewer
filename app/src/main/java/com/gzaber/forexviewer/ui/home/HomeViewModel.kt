package com.gzaber.forexviewer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.apikey.ApiKeyRepository
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.ui.util.model.UiFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val forexDataRepository: ForexDataRepository,
    favoritesRepository: FavoritesRepository,
    private val apiKeyRepository: ApiKeyRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _uiFavorites = MutableStateFlow<List<UiFavorite>>(listOf())
    private val _apiKeyText = MutableStateFlow("")
    private val _showDialog = MutableStateFlow(false)
    private val _failureMessage = MutableStateFlow<String?>(null)
    private var _favorites = listOf<Favorite>()
    private var _previousFavorites = listOf<Favorite>()

    val uiState = combine(
        _uiFavorites,
        _showDialog,
        _apiKeyText,
        _isLoading,
        _failureMessage
    ) { uiFavorites, showDialog, apiKeyText, isLoading, failureMessage ->
        HomeUiState(
            uiFavorites, showDialog, apiKeyText, isLoading, failureMessage
        )
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
            favoritesRepository.loadAllFavorites()
                .catch {
                    _failureMessage.value = it.message
                }
                .collect { favorites ->
                    _isLoading.value = false
                    _favorites = favorites

                    val uiFavoritesToRemove = _uiFavorites.value.filter { uiFavorite ->
                        favorites.none { it.symbol == uiFavorite.symbol }
                    }
                    val mutableUiFavorites = _uiFavorites.value.toMutableList()
                    mutableUiFavorites.removeAll(uiFavoritesToRemove)
                    _uiFavorites.update { mutableUiFavorites }

                    favorites.forEach { favorite ->
                        if (_previousFavorites.none { it.symbol == favorite.symbol }) {
                            collectUiFavorites(favorite)
                        }
                    }

                    _previousFavorites = favorites
                }
        }
    }

    private fun collectUiFavorites(favorite: Favorite) {
        viewModelScope.launch {
            forexDataRepository.fetchExchangeRate(favorite.symbol)
                .catch {
                    _failureMessage.value = it.message
                    emit(ExchangeRate(favorite.symbol, 0.0))
                }
                .cancellable()
                .collect { exchangeRate ->

                    if (_favorites.none { it.symbol == favorite.symbol }) {
                        this.cancel()
                    } else {
                        val uiFavorite = UiFavorite(
                            symbol = favorite.symbol,
                            base = favorite.base,
                            quote = favorite.quote,
                            exchangeRate = exchangeRate.rate
                        )

                        _uiFavorites.update { uiFavorites ->
                            val mutableUiFavorites = uiFavorites.toMutableList()
                            if (_uiFavorites.value.find { it.symbol == favorite.symbol } == null) {
                                mutableUiFavorites.add(uiFavorite)
                            } else {
                                val index =
                                    mutableUiFavorites.indexOfFirst { it.symbol == uiFavorite.symbol }
                                mutableUiFavorites[index] = uiFavorite
                            }
                            mutableUiFavorites
                        }
                    }
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