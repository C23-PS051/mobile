package com.dicoding.c23ps051.caferecommenderapp.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    fun getLogin(): Flow<Login> {
        return dataStore.data.map { preferences ->
            Login(
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[PHOTO_KEY] ?: "",
                preferences[STATE_KEY] ?: false,
                preferences[NEW_USER_KEY] ?: false,
            )
        }
    }

    suspend fun saveLogin(login: Login) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = login.name
            preferences[EMAIL_KEY] = login.email
            preferences[TOKEN_KEY] = login.token
            preferences[PHOTO_KEY] = login.photoUrl
            preferences[STATE_KEY] = login.isLogin
            preferences[NEW_USER_KEY] = login.isNewUser
        }
    }

    suspend fun setNotNewUser() {
        dataStore.edit { preferences ->
            preferences[NEW_USER_KEY] = false
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[TOKEN_KEY] = ""
            preferences[PHOTO_KEY] = ""
            preferences[STATE_KEY] = false
            preferences[NEW_USER_KEY] = false
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    fun getIsNewUser(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[NEW_USER_KEY] ?: false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private var NAME_KEY = stringPreferencesKey("name")
        private var EMAIL_KEY = stringPreferencesKey("email")
        private var STATE_KEY = booleanPreferencesKey("state")
        private var PHOTO_KEY = stringPreferencesKey("photo")
        private var TOKEN_KEY = stringPreferencesKey("token")
        private var NEW_USER_KEY = booleanPreferencesKey("new_user")

        fun getInstance(dataStore: DataStore<Preferences>):UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}