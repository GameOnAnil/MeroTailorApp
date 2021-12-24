package com.gameonanil.tailorapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.gameonanil.tailorapp.data.TailorDao
import com.gameonanil.tailorapp.data.TailorDatabase
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.data.entity.NotificationEntity
import com.gameonanil.tailorapp.data.relation.CustomerWithClothing


class TailorRepository(context: Context) {
    private var tailorDao: TailorDao = TailorDatabase.getInstance(context).tailorDao

    /**INSERT**/
    fun insertCustomer(customer: Customer) {
        tailorDao.insertCustomer(customer)
    }

    fun insertClothing(clothing: Clothing) {
        tailorDao.insertClothing(clothing)
    }

    fun insertMeasurement(measurement: Measurement) {
        tailorDao.insertMeasurement(measurement)
    }

    fun insertNotification(notificationEntity: NotificationEntity) {
        tailorDao.insertNotification(notificationEntity)
    }

    /**UPDATE**/
    fun updateClothing(clothing: Clothing) {
        tailorDao.updateClothing(clothing)
    }

    fun updateMeasurement(measurement: Measurement) {
        tailorDao.updateMeasurement(measurement)
    }

    fun updateCustomer(customer: Customer) {
        tailorDao.updateCustomer(customer)
    }

    /**DELETE**/
    fun deleteClothing(clothing: Clothing) {
        tailorDao.deleteClothing(clothing)
    }

    fun deleteCustomer(customer: Customer): Int? {
        return tailorDao.deleteCustomer(customer)
    }

    fun deleteMeasurement(measurement: Measurement): Int? {
        return tailorDao.deleteMeasurement(measurement)
    }

    fun deleteClothingList(clothinglist: List<Clothing>): Int? {
        return tailorDao.deleteClothingList(clothinglist)
    }


    fun getCustomerWithClothing(customerId: Int): CustomerWithClothing {
        return tailorDao.getCustomerWithClothing(customerId)
    }

    fun getMeasurementByCustomerId(customerId: Int): LiveData<Measurement> {
        return tailorDao.getMeasurementByCustomerId(customerId)
    }

    fun getAllCustomers(): LiveData<List<Customer>> {
        return tailorDao.getAllCustomer()
    }

    fun getAllCustomerWithClothes(): LiveData<List<CustomerWithClothing>> {
        return tailorDao.getAllCustomerWithClothes()
    }

    fun getMeasurementByCusId(customerId: Int): Measurement? {
        return tailorDao.getMeasurementByCusId(customerId)
    }

    fun getClothing(clothingId: Int): LiveData<Clothing> {
        return tailorDao.getClothing(clothingId)
    }


    fun getClothingById(clothingId: Int): Clothing? {
        return tailorDao.getClothingById(clothingId)
    }

    fun getClothingListByCusId(customerId: Int): List<Clothing>? {
        return tailorDao.getClothingListByCusId(customerId)
    }


    fun getCurrentCustomer(customerId: Int): Customer {
        return tailorDao.getCurrentCustomer(customerId)
    }

    fun getNotificationId(customerId: Int, clothingId: Int): NotificationEntity? {
        return tailorDao.getNotificationId(customerId, clothingId)
    }

    fun getLatestClothing(): Clothing? {
        return tailorDao.getLatestClothing()
    }

    fun deleteNotification(customerId: Int, clothingId: Int) {
        tailorDao.deleteNotification(customerId, clothingId)
    }


}