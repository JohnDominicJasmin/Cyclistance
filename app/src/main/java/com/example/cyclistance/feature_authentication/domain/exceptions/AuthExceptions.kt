package com.example.cyclistance.feature_authentication.domain.exceptions

import com.example.cyclistance.feature_readable_displays.domain.exceptions.ReadableDisplaysExceptions

sealed class AuthExceptions {
    class InternetException(message: String) : RuntimeException(message)
    class UserAlreadyExistsException(val title: String, message: String) : RuntimeException(message)
    class EmailVerificationException(message: String) : RuntimeException(message)
    class TooManyRequestsException(val title: String, message: String) : RuntimeException(message)
    class ConflictFBTokenException(message: String) : RuntimeException(message)
    class PasswordException(message: String) : RuntimeException(message)
    class ConfirmPasswordException(message: String) : RuntimeException(message)
    class EmailException(message: String) : RuntimeException(message)
    class UnexpectedErrorException(message:String): RuntimeException(message)
}
