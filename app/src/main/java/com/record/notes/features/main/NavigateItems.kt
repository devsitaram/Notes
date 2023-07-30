package com.record.notes.features.main

import com.record.notes.R

sealed class ScreenItem(var route: String){
    object Main: ScreenItem("Main")
    object Update: ScreenItem("Update")
}

sealed class ButtonNavigationBar(var icon: Int, var route: String){
    object Home: ButtonNavigationBar(R.drawable.ic_home,"Home")
    object Record: ButtonNavigationBar(R.drawable.ic_edit_note,"Record")
}
