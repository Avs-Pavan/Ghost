package com.kevin.ghost.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.ghost.domain.TestGetRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _uiState = MutableStateFlow(TestUIState())
    val uiState = _uiState
        .onStart { getTest() }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TestUIState()
    )


    private fun getTest() {
        viewModelScope.launch {
            testUseCase().let { testModel ->
                _uiState.update {
                    it.copy(
                        testUiModel = TestUIModel(
                            title = testModel.title,
                            body = testModel.body
                        )
                    )
                }
            }
        }
    }

}
