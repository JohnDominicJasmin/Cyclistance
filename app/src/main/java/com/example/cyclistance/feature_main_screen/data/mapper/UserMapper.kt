package com.example.cyclistance.feature_main_screen.data.mapper

import com.example.cyclistance.feature_main_screen.data.remote.dto.*
import com.example.cyclistance.feature_main_screen.data.remote.dto.RescueRequestDto
import com.example.cyclistance.feature_main_screen.domain.model.*

object UserMapper {

fun UserDto.toUser():User{
    return User(
        address = this.address,
        id = this.id,
        location = this.location,
        name = this.name,
        userNeededHelp = this.userNeededHelp,
        userAssistance = this.userAssistance,
        profilePictureUrl = this.profilePictureUrl,
        contactNumber = this.contactNumber
    )
}


fun RescueRequestDto.toRescueRequest():RescueRequest{
    return RescueRequest(
       rescueEventId = this.rescueEventId,
        respondents = this.respondents
    )
}


fun CancellationEventDto.toCancellationEvent():CancellationEvent{
    return CancellationEvent(
        cancellationReasons = this.cancellationReasons,
        clientId = this.clientId,
        id = this.id
    )
}




}