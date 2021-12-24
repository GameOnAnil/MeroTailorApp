package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.NotificationEntity

class ClothingListViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TailorRepository = TailorRepository(application)

    fun getClothingList(customerId: Int): List<Clothing>? {
        return repository.getClothingListByCusId(customerId)
    }

    fun getCurrentCustomer(customerId: Int): Customer {
        return repository.getCurrentCustomer(customerId)
    }


    /**DELETE**/
    fun deleteClothing(clothing: Clothing) {
        repository.deleteClothing(clothing)
    }

    fun deleteNotification(customerId: Int, clothingId: Int) {
        repository.deleteNotification(customerId, clothingId)
    }

    fun getNotificationId(customerId: Int, clothingId: Int): NotificationEntity? {
        return repository.getNotificationId(customerId = customerId, clothingId = clothingId)
    }


}