package com.perfectyang.bookshop.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perfectyang.bookshop.repository.Repository
import com.perfectyang.bookshop.room.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

data class UserState(
    var username: String = "",
    var password: String = "",
)



private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class UserViewModel(val repository: Repository, context: Context): ViewModel() {
    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState


    val _userId = intPreferencesKey("user_id")
    val _username = stringPreferencesKey("user_name")


    val userId = context.dataStore.data.map { it[_userId] ?: 0 }


    val _context = context

    fun updateUser(name: String, password: String) {
        _userState.value = _userState.value.copy(
            username = name,
            password = password,
        )
    }


    suspend fun saveUser(userId: Int, username: String) {
        _context.dataStore.edit { user ->
            user[_userId] = userId
            user[_username] = username
        }
    }


    suspend fun clear() {
        _context.dataStore.edit { user ->
            user[_username] = ""
        }
    }

    fun getUserData (): Flow<Int> {
        return _context.dataStore.data.map { it[_userId] ?: 0 }
    }




    fun register(user: UserEntity, callback: () -> Unit) {
        viewModelScope.launch {
            val i  = repository.register(user)
            if (i > 0) {
                callback()
            }
        }
    }




    fun login(user: UserEntity, callback: (bool: Boolean) -> Unit) {
        viewModelScope.launch {
            val userInfo: UserEntity =  repository.login(user)
            if (userInfo !== null && userInfo.userId.toString().isNotEmpty()) {
                saveUser(userInfo.userId, userInfo.username)
                callback(true)
            } else {
                callback(false)
                Log.d("failure", "失败登录")
            }
        }
    }




    var userList: Flow<List<UserEntity>>  = repository.getAllUsers()


    fun searchUser (username: String) {
        val tmp = repository.searchUser(username)
        Log.d("dms", "$tmp")
    }




}