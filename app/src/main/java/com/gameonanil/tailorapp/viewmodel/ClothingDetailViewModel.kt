package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.data.entity.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClothingDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TailorRepository = TailorRepository(application)

    fun insertClothing(clothing: Clothing) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertClothing(clothing)
        }
    }

    fun insertMeasurement(measurement: Measurement) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMeasurement(measurement)
        }
    }

    fun insertNotification(notificationEntity: NotificationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotification(notificationEntity)
        }
    }

    /**UPDATE**/
    fun updateMeasurement(measurement: Measurement) {
        repository.updateMeasurement(measurement)
    }

    fun updateClothing(clothing: Clothing) {
        repository.updateClothing(clothing)
    }

    fun getMeasurementById(customerId: Int): Measurement? {
        return repository.getMeasurementByCusId(customerId)
    }


    fun getClothingById(clothingId: Int): Clothing? {
        return repository.getClothingById(clothingId)
    }


    fun getNotificationId(customerId: Int, clothingId: Int): NotificationEntity? {
        return repository.getNotificationId(customerId, clothingId)
    }
}