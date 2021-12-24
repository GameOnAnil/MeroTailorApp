package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.data.entity.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddClothingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TailorRepository = TailorRepository(application)
    private var mCustomerId: MutableLiveData<Int> = MutableLiveData()

    val measurement: LiveData<Measurement> = Transformations.switchMap(mCustomerId) {
        repository.getMeasurementByCustomerId(customerId = it)
    }

    /**INSERT**/
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


    fun getLatestClothing(): Clothing? {
        return repository.getLatestClothing()
    }

    fun getMeasurement(customerId: Int) {
        mCustomerId.value = customerId
    }

    fun getMeasurementById(customerId: Int): Measurement? {
        return repository.getMeasurementByCusId(customerId)
    }

    fun getNotificationId(customerId: Int, clothingId: Int): NotificationEntity? {
        return repository.getNotificationId(customerId, clothingId)
    }


}