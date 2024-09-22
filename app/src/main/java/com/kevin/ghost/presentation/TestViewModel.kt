package com.kevin.ghost.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.ghost.domain.TestGetRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TestViewModel @Inject constructor(
    private val testUseCase: TestGetRequestUseCase
) : ViewModel() {


    val _globalExecptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TestViewModel", "CoroutineExceptionHandler", throwable)
    }
    private val _uiState = MutableStateFlow(TestUIState())
    val uiState = _uiState
        .onStart { getTest() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TestUIState()
        )


    private fun getTest() {
        viewModelScope.launch(_globalExecptionHandler) {
            when (val result = testUseCase()) {
                is com.kevin.ghost.domain.Result.Success -> {
                    _uiState.update {
                        it.copy(
                            testUiModel = TestUIModel(
                                title = result.data.title,
                                body = result.data.body
                            )
                        )
                    }
                }

                is com.kevin.ghost.domain.Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.error.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }

}
