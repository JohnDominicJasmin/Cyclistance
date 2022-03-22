package com.example.cyclistance.feature_authentication.domain.use_case

import com.example.cyclistance.feature_authentication.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential

class SignOutUseCase(private val repository: AuthRepository<AuthCredential>) {

     operator fun invoke() = repository.signOut()
}