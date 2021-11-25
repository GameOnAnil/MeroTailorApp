package com.gameonanil.tailorapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing

class TailorViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TailorRepository = TailorRepository(application)

    var customerWithClothing: LiveData<List<CustomerWithClothing>> =
        repository.getCustomerWithClothing(1)


    fun insertCustomer(customer: Customer) {
        repository.insertCustomer(customer)
    }

    fun insertClothing(clothing: Clothing) {
        repository.insertClothing(clothing)
    }

}