package com.example.cyclistance.feature_mapping.presentation.components

sealed class MappingDrawerConstraintItem(val layoutId: String){

    object UpperSection: MappingDrawerConstraintItem(layoutId = "upper_section")
    object BottomSection: MappingDrawerConstraintItem(layoutId = "bottom_section")
}