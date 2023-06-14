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
                preferences[PHOTO_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[STATE_KEY] ?: false,
                preferences[NEW_USER_KEY] ?: false,
                preferences[UID_KEY] ?: ""
            )
        }
    }

    suspend fun saveLogin(login: Login) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = login.name
            preferences[EMAIL_KEY] = login.email
            preferences[PHOTO_KEY] = login.photoUrl
            preferences[TOKEN_KEY] = login.token
            preferences[STATE_KEY] = login.isLogin
            preferences[NEW_USER_KEY] = login.isNewUser
            preferences[UID_KEY] = login.userId
        }
    }

    suspend fun setUserLocation(location: String) {
        dataStore.edit { preferences ->
            preferences[USER_LOCATION] = location
        }
    }

    fun getUserLocation(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_LOCATION] ?: ""
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
            preferences[PHOTO_KEY] = ""
            preferences[TOKEN_KEY] = ""
            preferences[STATE_KEY] = false
            preferences[NEW_USER_KEY] = false
            preferences[USER_LOCATION] = ""
            preferences[UID_KEY] = ""
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun editUser(name: String, photoUrl: String) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[PHOTO_KEY] = photoUrl
        }
    }

    fun getPhotoUrl(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PHOTO_KEY] ?: ""
        }
    }

    suspend fun setToken(idToken: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = idToken
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
        private var USER_LOCATION = stringPreferencesKey("location")
        private var UID_KEY = stringPreferencesKey("user_id")

        fun getInstance(dataStore: DataStore<Preferences>):UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}