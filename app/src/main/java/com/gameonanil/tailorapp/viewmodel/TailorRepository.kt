package com.gameonanil.tailorapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.gameonanil.tailorapp.data.TailorDao
import com.gameonanil.tailorapp.data.TailorDatabase
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing
import com.gameonanil.tailorapp.data.entity.Measurement


class TailorRepository(context: Context) {
    private var tailorDao: TailorDao = TailorDatabase.getInstance(context).tailorDao

    fun insertCustomer(customer: Customer) {
        tailorDao.insertCustomer(customer)
    }

    fun insertClothing(clothing: Clothing) {
        tailorDao.insertClothing(clothing)
    }

    fun insertMeasurement(measurement: Measurement) {
        tailorDao.insertMeasurement(measurement)
    }

    fun getCustomerWithClothing(customerId: Int): LiveData<CustomerWithClothing> {
        return tailorDao.getCustomerWithClothing(customerId)
    }

    fun getMeasurementByCustomerId(customerId: Int): LiveData<Measurement> {
        return tailorDao.getMeasurementByCustomerId(customerId)
    }

    fun getAllCustomers(): LiveData<List<Customer>> {
        return tailorDao.getAllCustomer()
    }

    fun getMeasurementByCusId(customerId: Int): Measurement? {
        return tailorDao.getMeasurementByCusId(customerId)
    }

    fun getClothing(clothingId: Int): LiveData<Clothing> {
        return tailorDao.getClothing(clothingId)
    }

    fun updateMeasurement(measurement: Measurement) {
        tailorDao.updateMeasurement(measurement)
    }


    fun getClothingByCusId(customerId: Int): Clothing? {
        return tailorDao.getClothingByCusId(customerId)
    }

    fun updateClothing(clothing: Clothing) {
        tailorDao.updateClothing(clothing)
    }
}