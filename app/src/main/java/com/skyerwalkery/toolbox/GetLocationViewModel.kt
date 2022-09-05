package com.skyerwalkery.toolbox

import androidx.lifecycle.ViewModel



class GetLocationViewModel: ViewModel() {

    companion object{
        private val TAG: String = GetLocationViewModel::class.java.simpleName
    }

    var lastLocInfo: String? = null
}