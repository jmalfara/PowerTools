package com.jmat.encode.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.encode.data.EncodeRepository
import com.jmat.encode.data.model.TinyUrlCreateRequest
import com.jmat.powertools.TinyUrl
import com.jmat.powertools.base.service.ApiError
import com.jmat.powertools.base.service.ApiSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EncodeTinyUrlCreateViewModel @Inject constructor(
    private val encodeRepository: EncodeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(loading = false, tinyUrls = listOf()))
    val uiState: Flow<UiState> = _uiState

    private val _event = MutableSharedFlow<Event>()
    val event: Flow<Event> = _event

    fun createTinyUrl(url: String) {
        viewModelScope.launch {
            emitLoading(true)

            val request = TinyUrlCreateRequest(
                url = url,
                domain = "tiny.one"
            )
            when (val response =encodeRepository.createTinyUrl(request)) {
                is ApiSuccess -> _event.emit(Event.UrlCreated)
                is ApiError -> _event.emit(Event.Error(response.error))
            }

            emitLoading(false)
        }
    }

    private suspend fun emitLoading(loading: Boolean) {
        _uiState.emit(
            _uiState.value.copy(loading = loading)
        )
    }

    data class UiState(
        val loading: Boolean,
        val tinyUrls: List<TinyUrl>
    )

    sealed interface Event {
        object UrlCreated : Event
        data class Error(val exception: Exception) : Event
    }
}