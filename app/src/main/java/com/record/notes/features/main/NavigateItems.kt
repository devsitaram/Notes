package com.record.notes.features.main

import com.record.notes.R

sealed class ScreenItem(var icon: Int, var route: String){
    object Home: ScreenItem(R.drawable.ic_home,"Home")
    object Data: ScreenItem(R.drawable.ic_edit_note,"Data")
}
