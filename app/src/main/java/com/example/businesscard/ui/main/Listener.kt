package com.example.businesscard.ui.main

import android.view.View

interface Listener {
    fun onClick(card: View)
    fun onLongClick(id: Int)
}