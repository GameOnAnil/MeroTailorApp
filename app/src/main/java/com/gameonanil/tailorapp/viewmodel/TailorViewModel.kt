package com.gameonanil.tailorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TailorViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TailorRepository = TailorRepository(application)

    var customerWithClothing: LiveData<CustomerWithClothing> =
        repository.getCustomerWithClothing(1)


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

}