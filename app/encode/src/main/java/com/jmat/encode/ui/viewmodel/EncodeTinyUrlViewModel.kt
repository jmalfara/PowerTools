package com.jmat.encode.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.encode.data.EncodeRepository
import com.jmat.powertools.TinyUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EncodeTinyUrlViewModel @Inject constructor(
    private val encodeRepository: EncodeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(tinyUrls = listOf()))
    val uiState: Flow<UiState> = _uiState

    init {
        viewModelScope.launch {
            encodeRepository.tinyUrls.collect { tinyUrls ->
                _uiState.emit(
                    _uiState.value.copy(tinyUrls = tinyUrls)
                )
            }
        }
    }

    fun deleteTinyUrls(urls: List<TinyUrl>) {
        viewModelScope.launch {
            encodeRepository.deleteTinyUrls(urls)
        }
    }

    data class UiState(
        val tinyUrls: List<TinyUrl>
    )
}