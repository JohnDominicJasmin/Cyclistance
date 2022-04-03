package com.example.cyclistance.feature_authentication.domain.repository

import com.google.firebase.auth.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository< T> {
    suspend fun reloadEmail(): Boolean
    fun signOut()
    fun getEmail(): String?
    suspend fun sendEmailVerification(): Boolean
    fun getName(): String?
    fun isSignedInWithProvider(): Flow<Boolean>
    fun isEmailVerified(): Boolean?
    fun hasAccountSignedIn(): Boolean
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun signInWithCredentials(v: T): Boolean
}