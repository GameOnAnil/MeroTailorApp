package com.gameonanil.tailorapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.gameonanil.tailorapp.data.TailorDao
import com.gameonanil.tailorapp.data.TailorDatabase
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing


class TailorRepository(context: Context) {
    private var tailorDao: TailorDao = TailorDatabase.getInstance(context).tailorDao

    fun insertCustomer(customer: Customer) {
        tailorDao.insertCustomer(customer)
    }

    fun insertClothing(clothing: Clothing) {
        tailorDao.insertClothing(clothing)
    }

    fun getCustomerWithClothing(customerId: Int): LiveData<CustomerWithClothing> {
        return tailorDao.getCustomerWithClothing(customerId)
    }
}