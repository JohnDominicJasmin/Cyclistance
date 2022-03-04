package com.example.cyclistance.feature_mapping.presentation.components

sealed class MappingConstraintItem(val layoutId: String){

    object UpperSection: MappingConstraintItem(layoutId = "upper_section")
    object BottomSection: MappingConstraintItem(layoutId = "bottom_section")

}
