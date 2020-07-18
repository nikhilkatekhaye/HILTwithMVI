package com.org.hilt_android_demo.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.org.hilt_android_demo.model.Blog
import com.org.hilt_android_demo.repository.MainRepository
import com.org.hilt_android_demo.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val saveStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState : MutableLiveData<DataState <List<Blog>>> = MutableLiveData()

    val dataState : LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent)
    {
        viewModelScope.launch {

            when(mainStateEvent)
            {
                is MainStateEvent.GetBlogEvents -> {

                    mainRepository.getBlog().onEach {
                        dataState -> _dataState.value = dataState
                    }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None ->{

                    //Who cares
                }
            }
        }
    }
}

sealed class MainStateEvent {
    object GetBlogEvents : MainStateEvent()
    object None : MainStateEvent()
}