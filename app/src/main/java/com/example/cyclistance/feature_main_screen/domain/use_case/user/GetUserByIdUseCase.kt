package com.example.cyclistance.feature_main_screen.domain.use_case.user

import com.example.cyclistance.feature_main_screen.domain.model.User
import com.example.cyclistance.feature_main_screen.domain.repository.MappingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserByIdUseCase(private val repository: MappingRepository) {
    suspend operator fun invoke(id: String):User {
        return repository.getUserById(id)
    }
}