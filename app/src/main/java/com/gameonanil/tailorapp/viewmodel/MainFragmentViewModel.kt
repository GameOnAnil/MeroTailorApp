package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.data.relation.CustomerWithClothing

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    var repository: TailorRepository = TailorRepository(application.baseContext)

    val customerList: LiveData<List<Customer>> = repository.getAllCustomers()

    fun getCustomerWithClothing(customerId: Int): CustomerWithClothing {
        return repository.getCustomerWithClothing(customerId)
    }

    fun getMeasurement(customerId: Int): Measurement? {
        return repository.getMeasurementByCusId(customerId)
    }

    fun deleteCustomer(customer: Customer): Int? {
        return repository.deleteCustomer(customer)
    }

    fun deleteClothingList(clothingList: List<Clothing>): Int? {
        return repository.deleteClothingList(clothingList)
    }

    fun deleteMeasurement(measurement: Measurement): Int? {
        return repository.deleteMeasurement(measurement)
    }
}