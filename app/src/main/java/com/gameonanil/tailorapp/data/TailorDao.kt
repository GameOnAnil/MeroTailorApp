package com.gameonanil.tailorapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.data.entity.NotificationEntity
import com.gameonanil.tailorapp.data.relation.CustomerWithClothing

@Dao
interface TailorDao {
    /**INSERT**/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClothing(clothing: Clothing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeasurement(measurement: Measurement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: NotificationEntity)

    /**UPDATE**/
    @Update
    fun updateMeasurement(measurement: Measurement)

    @Update
    fun updateClothing(clothing: Clothing)

    @Update
    fun updateCustomer(customer: Customer)

    /**DELETE**/
    @Delete
    fun deleteClothing(clothing: Clothing)

    @Delete
    fun deleteClothingList(customerList: List<Clothing>): Int?

    @Delete
    fun deleteCustomer(customer: Customer): Int?

    @Delete
    fun deleteMeasurement(measurement: Measurement): Int?


    @Transaction
    @Query("SELECT * FROM customer_table WHERE customerId=:customerId")
    fun getCustomerWithClothing(customerId: Int): CustomerWithClothing

    @Query("SELECT * FROM MEASUREMENT_TABLE WHERE customerId=:customerId")
    fun getMeasurementByCustomerId(customerId: Int): LiveData<Measurement>

    @Query("SELECT * FROM clothing_table WHERE clothingId=:clothingId")
    fun getClothing(clothingId: Int): LiveData<Clothing>


    @Query("SELECT * FROM CUSTOMER_TABLE")
    fun getAllCustomer(): LiveData<List<Customer>>

    @Query("SELECT * FROM CUSTOMER_TABLE")
    fun getAllCustomerWithClothes(): LiveData<List<CustomerWithClothing>>

    @Query("SELECT * FROM MEASUREMENT_TABLE WHERE customerId=:customerId")
    fun getMeasurementByCusId(customerId: Int): Measurement?

    @Query("SELECT * FROM clothing_table WHERE clothingId=:clothingId")
    fun getClothingById(clothingId: Int): Clothing?

    @Query("SELECT * FROM clothing_table WHERE customerId=:customerId")
    fun getClothingListByCusId(customerId: Int): List<Clothing>?

    @Query("SELECT * FROM CUSTOMER_TABLE WHERE customerId=:customerId")
    fun getCurrentCustomer(customerId: Int): Customer


    @Query("SELECT * FROM notificationTable WHERE clothingId=:clothingId")
    fun getNotificationId(clothingId: Int): List<NotificationEntity>

    @Query("SELECT * FROM clothing_table ORDER BY clothingId DESC LIMIT 1")
    fun getLatestClothing(): Clothing?


}