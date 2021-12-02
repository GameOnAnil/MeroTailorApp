package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.Measurement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TailorViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TailorRepository = TailorRepository(application)
    private var mCustomerId: MutableLiveData<Int> = MutableLiveData()
    private var mClothingId: MutableLiveData<Int> = MutableLiveData()


    val measurement: LiveData<Measurement> = Transformations.switchMap(mCustomerId) {
        repository.getMeasurementByCustomerId(customerId = it)
    }
    val clothing: LiveData<Clothing> = Transformations.switchMap(mClothingId) {
        repository.getClothing(it)
    }

    /**INSERT**/
    fun insertCustomer(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCustomer(customer)
        }
    }

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

    /**UPDATE**/
    fun updateMeasurement(measurement: Measurement) {
        repository.updateMeasurement(measurement)
    }

    fun updateClothing(clothing: Clothing) {
        repository.updateClothing(clothing)
    }

    /**DELETE**/
    fun deleteClothing(clothing: Clothing) {
        repository.deleteClothing(clothing)
    }

    fun deleteCustomer(customer: Customer) {
        repository.deleteCustomer(customer)
    }

    fun deleteMeasurement(measurement: Measurement) {
        repository.deleteMeasurement(measurement)
    }

    fun getMeasurement(customerId: Int) {
        mCustomerId.value = customerId
    }

    fun getMeasurementById(customerId: Int): Measurement? {
        return repository.getMeasurementByCusId(customerId)
    }


    fun getClothingById(clothingId: Int): Clothing? {
        return repository.getClothingById(clothingId)
    }





}