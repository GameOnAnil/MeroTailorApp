package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer

class ClothingListViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TailorRepository = TailorRepository(application)

    fun getClothingList(customerId: Int): List<Clothing>? {
        return repository.getClothingListByCusId(customerId)
    }

    fun getCurrentCustomer(customerId: Int): Customer {
        return repository.getCurrentCustomer(customerId)
    }

}