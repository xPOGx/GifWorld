package com.example.gifworld.ui.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import com.example.gifworld.data.GifBan
import com.example.gifworld.network.GifData
import com.example.gifworld.network.toGifDB
import com.example.gifworld.repository.GifNetworkRepository
import com.example.gifworld.repository.GifOfflineRepository
import com.example.gifworld.utils.CoilManager
import com.example.gifworld.utils.Constants
import com.example.gifworld.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val networkRepository: GifNetworkRepository,
    private val offlineRepository: GifOfflineRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SharedUiState())
    val uiState = _uiState.asStateFlow()

    fun initLoadGifs() {
        val searchQuery = _uiState.value.searchQuery.trim().lowercase()
        if (searchQuery.isBlank() || searchQuery == _uiState.value.lastQuery) return

        _uiState.update {
            it.copy(
                status = Status.Loading,
                pagerStatus = Status.Done,
                currentPage = 0,
                canBePaginate = false
            )
        }
        viewModelScope.launch {
            val banList =
                offlineRepository.getAllBanStream(searchQuery).first().map { it.id }
            if (!onlineLoad(searchQuery, banList)) offlineLoad(searchQuery, banList.size)
        }
    }

    fun loadMoreGifs() {
        if (_uiState.value.pagerStatus == Status.Loading) return

        _uiState.update {
            it.copy(
                pagerStatus = Status.Loading,
                canBePaginate = false
            )
        }
        val searchQuery = _uiState.value.searchQuery.trim().lowercase()
        viewModelScope.launch {
            val banList =
                offlineRepository.getAllBanStream(searchQuery).first().map { it.id }
            try {
                val gifResult =
                    networkRepository.getGifs(
                        searchQuery,
                        _uiState.value.currentPage * Constants.BASE_LIMIT
                    )
                if (gifResult.meta.status == 200) {
                    val gifsList = gifResult.data.filter { it.id !in banList }
                    updateDB(gifsList)
                    _uiState.update {
                        it.copy(
                            gifsList = it.gifsList.plus(gifsList),
                            pagerStatus = Status.Done,
                            currentPage = it.currentPage.inc(),
                            canBePaginate = true
                        )
                    }
                } else throw Exception(gifResult.meta.status.toString())
            } catch (e: Exception) {
                Log.e("OnlineLoad", e.message.toString())
                val list = offlineRepository.getAllGifsStream(searchQuery).first()
                if (_uiState.value.gifsList == list) _uiState.update {
                    it.copy(pagerStatus = Status.Error)
                }
                else _uiState.update {
                    it.copy(
                        gifsList = list,
                        pagerStatus = Status.Done,
                        currentPage = (list.size + banList.size) / Constants.BASE_LIMIT,
                        canBePaginate = true
                    )
                }
            }
        }
    }

    private suspend fun onlineLoad(
        searchQuery: String,
        banList: List<String>,
        offset: Int = 0
    ): Boolean {
        return try {
            val gifResult = networkRepository.getGifs(searchQuery, offset)
            if (gifResult.meta.status == 200) {
                val list = gifResult.data.filter { it.id !in banList }
                _uiState.update { it.copy(currentPage = it.currentPage.inc()) }
                if (list.isEmpty()) {
                    onlineLoad(
                        searchQuery,
                        banList,
                        _uiState.value.currentPage * Constants.BASE_LIMIT
                    )
                } else {
                    updateDB(list)
                    _uiState.update {
                        it.copy(
                            gifsList = list,
                            status = Status.Done,
                            lastQuery = searchQuery,
                            canBePaginate = true
                        )
                    }
                    true
                }
            } else throw Exception(gifResult.meta.status.toString())
        } catch (e: Exception) {
            Log.e("OnlineLoad", e.message.toString())
            false
        }
    }

    private suspend fun offlineLoad(searchQuery: String, banListSize: Int) {
        val list = offlineRepository.getAllGifsStream(searchQuery).first()
        if (list.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    gifsList = list,
                    status = Status.Done,
                    currentPage = (list.size + banListSize) / Constants.BASE_LIMIT,
                    lastQuery = searchQuery,
                    canBePaginate = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    gifsList = emptyList(),
                    status = Status.Error,
                    currentPage = 0,
                    canBePaginate = false
                )
            }
        }
    }

    private suspend fun updateDB(list: List<GifData>) =
        list.forEach { offlineRepository.insertGif(it.toGifDB(_uiState.value.searchQuery)) }

    fun updateBan(context: Context, gifData: GifData) {
        val state = _uiState.value
        clearCache(context, gifData.images.preview.url to gifData.images.original.webp)
        _uiState.update { it.copy(gifsList = state.gifsList.minus(gifData)) }
        viewModelScope.launch {
            offlineRepository.insertBan(GifBan(gifData.id, state.searchQuery.trim().lowercase()))
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    private fun clearCache(context: Context, keys: Pair<String, String>) {
        val imageLoader = CoilManager.getImageLoader(context)
        imageLoader.diskCache?.let {
            it.remove(keys.first)
            it.remove(keys.second)
        }
    }

    fun updateUiState(uiState: SharedUiState) = _uiState.update { uiState }
}

data class SharedUiState(
    val status: Status = Status.Done,
    val gifsList: List<GifData> = listOf(),
    val pagerStatus: Status = Status.Done,
    val index: Int = 0,
    val searchQuery: String = "",
    val lastQuery: String = "",
    val currentPage: Int = 0,
    val canBePaginate: Boolean = false
)