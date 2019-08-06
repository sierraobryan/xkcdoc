package com.example.sierraobryan.xkcdocument.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    var isGrid : MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { postValue(false)}

    fun isGrid(isGrid:Boolean) {
        this.isGrid.apply { postValue(isGrid) }
    }


}