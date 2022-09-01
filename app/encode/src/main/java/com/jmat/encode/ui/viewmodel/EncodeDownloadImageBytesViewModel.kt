package com.jmat.encode.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.encode.data.EncodeRepository
import com.jmat.powertools.base.service.ApiError
import com.jmat.powertools.base.service.ApiSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EncodeDownloadImageBytesViewModel @Inject constructor(
    private val encodeRepository: EncodeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.default)
    val uiState: Flow<UiState> = _uiState

    fun downloadImageBytes(url: String) {
        viewModelScope.launch {
            when(val result = encodeRepository.getData(url)) {
                is ApiError -> {
                    _uiState.emit(
                        _uiState.value.copy(
                            data = result.error.toString()
                        )
                    )
                }
                is ApiSuccess -> {
                    _uiState.emit(
                        _uiState.value.copy(
                            data = result.value
                        )
                    )
                }
            }
        }
    }

    data class UiState(
        val data: String?
    ) {
        companion object {
            val default = UiState(
                data = null
            )
        }
    }
}