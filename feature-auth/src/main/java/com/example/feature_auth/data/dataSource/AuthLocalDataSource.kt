package com.example.feature_auth.data.dataSource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.feature_auth.data.response.UserProfile
import com.example.feature_auth.data.response.UserProfile.Companion.toDomain
import com.example.feature_auth.domain.response.UserProfileDO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

const val AUTH_DATA = "AUTH_DATA"

val Context.dataStore by preferencesDataStore(name = AUTH_DATA)

@Singleton
class AuthLocalDataSource @Inject constructor(
     val dataStore: DataStore<Preferences>
) {

    suspend fun saveAccessToken(value: String) {
        dataStore.edit { prefs -> prefs[ACCESS_TOKEN] = value }
    }

    suspend fun getAccessToken(): String {
        return dataStore.data.map { it[ACCESS_TOKEN] }.first() ?:""
    }

    suspend fun saveRefreshToken(value: String) {
        dataStore.edit { prefs -> prefs[REFRESH_TOKEN] = value }
    }

    suspend fun getRefreshToken(): String {
        return dataStore.data.map { it[REFRESH_TOKEN] }.first() ?:""
    }
    suspend fun saveUserName(value: String) {
        dataStore.edit { prefs -> prefs[USER_NAME] = value }
    }

    suspend fun getUserName(): String {
        return dataStore.data.map { it[USER_NAME] }.first() ?:""
    }
    suspend fun saveSurname(value: String) {
        dataStore.edit { prefs -> prefs[SURNAME] = value }
    }

    suspend fun getSurname(): String {
        return dataStore.data.map { it[SURNAME] }.first() ?:""
    }
    suspend fun saveEmail(value: String) {
        dataStore.edit { prefs -> prefs[EMAIL] = value }
    }

    suspend fun getEmail(): String {
        return dataStore.data.map { it[EMAIL] }.first() ?:""
    }
    suspend fun savePhone(value: String) {
        dataStore.edit { prefs -> prefs[PHONE_NUMBER] = value }
    }

    suspend fun getPhone(): String {
        return dataStore.data.map { it[PHONE_NUMBER] }.first() ?:""
    }


    suspend fun saveUserProfile(profile: UserProfile) {
        val jsonString = Json.encodeToString(UserProfile.serializer(), profile)
        dataStore.edit { prefs -> prefs[USER_PROFILE] = jsonString }
    }

    suspend fun getUserProfileDO(): UserProfileDO? {
        val jsonString = dataStore.data.map { it[USER_PROFILE] }.first() ?: return null
        return try {
            Json.decodeFromString(UserProfile.serializer(), jsonString).toDomain()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun clearAuthData() {
        dataStore.edit { prefs -> prefs.clear() }
    }

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val USER_PROFILE = stringPreferencesKey("USER_PROFILE")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val SURNAME = stringPreferencesKey("SURNAME")
        val EMAIL = stringPreferencesKey("EMAIL")
        val PHONE_NUMBER = stringPreferencesKey("PHONE_NUMBER")
    }
}