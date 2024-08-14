package com.perfectyang.bookshop.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perfectyang.bookshop.repository.Repository
import com.perfectyang.bookshop.room.BankEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BankViewModel(val repository: Repository, category: String = "1", userViewModel: UserViewModel): ViewModel() {



    private var _bankList = MutableLiveData<List<BankEntity>>()
    val banksList: LiveData<List<BankEntity>> = _bankList

    val _c = category
    val _userViewModel = userViewModel





//    init {
//        fetchBankList()
//    }




    fun fetchBankList() {
        viewModelScope.launch {
            val userId = _userViewModel.getUserData().firstOrNull()
            userId.let {
                Log.d("进来2", "$it --- $_c")
                repository.getAllBanks(category = _c, userId = it ?: 0).collect { items ->
                    _bankList.value = items
                }
            }
        }
    }

    fun _search(title: String) {
        viewModelScope.launch {
            val userId = _userViewModel.getUserData().firstOrNull()
            userId.let {
                repository.searchBank(title, category = _c, userId = it ?: 0).collect { items ->
                    // 将从 Room 获取的数据更新到 MutableState 中
                    _bankList.value = items
                }
            }

        }
    }

    fun searchBank(title: String) {
        if (title.isEmpty()) {
            Log.d("进来", "init-$_c")
            fetchBankList()
        } else {
            _search(title)
            Log.d("初始化-search", "init-$title")
        }
    }


    fun deleteBank(id: Int, title: String) {
        viewModelScope.launch {
            repository.deleteBank(id)
//            run {
//                searchBank(title)
//            }
        }
    }

    fun updateBank(bank: BankEntity) {
        viewModelScope.launch {
            repository.updateBank(bank)
        }
    }

    fun addBank(bank: BankEntity, callback: () -> Unit) {
        viewModelScope.launch {
            repository.addBank(bank)
            fetchBankList()
            runBlocking {
                callback()
            }
        }
    }




}