package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing
import com.gameonanil.tailorapp.data.entity.Measurement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TailorViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TailorRepository = TailorRepository(application)
    private var customerIdForCustomer: MutableLiveData<Int> = MutableLiveData()
    private var customerIdForMeasurement: MutableLiveData<Int> = MutableLiveData()

    var customerWithClothing: LiveData<CustomerWithClothing> =
        Transformations.switchMap(customerIdForCustomer) {
            repository.getCustomerWithClothing(it)
        }

    val measurement: LiveData<Measurement> = Transformations.switchMap(customerIdForMeasurement) {
        repository.getMeasurementByCustomerId(customerId = it)
    }

    val customerList: LiveData<List<Customer>> = repository.getAllCustomers()


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

    fun getCustomerWithClothing(customerId: Int) {
        customerIdForCustomer.value = customerId
    }

    fun getMeasurement(customerId: Int) {
        customerIdForMeasurement.value = customerId
    }

    fun getMeasurementById(customerId: Int): Measurement? {
        return repository.getMeasurementByCusId(customerId)
    }


    fun updateMeasurement(measurement: Measurement) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMeasurement(measurement)
        }
    }


}