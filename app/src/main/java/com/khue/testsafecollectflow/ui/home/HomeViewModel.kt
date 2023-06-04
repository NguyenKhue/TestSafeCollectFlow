package com.khue.testsafecollectflow.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khue.testsafecollectflow.NetworkConnectivityService
import com.khue.testsafecollectflow.NetworkConnectivityServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    networkConnectivityServiceImpl: NetworkConnectivityService
) : ViewModel() {

    private val _testFlow = MutableStateFlow(0)
    val testFlow = _testFlow.asStateFlow()

    val x: Observable<Int> = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val testCallBackFlow: Flow<Int> = callbackFlow {
        val disposable = CompositeDisposable()
        disposable.add(
            x
                .flatMap({ Observable.just(it).delay(1000, TimeUnit.MILLISECONDS) }, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.e("HomeViewModel", "emit $it")
                    channel.trySend(it)
                }
        )
        awaitClose {
            disposable.dispose()
            Log.e("HomeViewModel", "TestCallFlow cancel")
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        0
    )

    val networkStatus = networkConnectivityServiceImpl.networkStatus.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        0
    )

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _testFlow.update { it + 1 }
            }
        }
    }
}