package com.gameonanil.tailorapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing
import com.gameonanil.tailorapp.data.entity.Measurement

@Dao
interface TailorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClothing(clothing: Clothing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeasurement(measurement: Measurement)

    @Update
    fun updateMeasurement(measurement: Measurement)

    @Update
    fun updateClothing(clothing: Clothing)

    @Transaction
    @Query("SELECT * FROM customer_table WHERE customerId=:customerId ")
    fun getCustomerWithClothing(customerId: Int): LiveData<CustomerWithClothing>

    @Query("SELECT * FROM MEASUREMENT_TABLE WHERE customerId=:customerId")
    fun getMeasurementByCustomerId(customerId: Int): LiveData<Measurement>

    @Query("SELECT * FROM clothing_table WHERE clothingId=:clothingId")
    fun getClothing(clothingId: Int): LiveData<Clothing>


    @Query("SELECT * FROM CUSTOMER_TABLE")
    fun getAllCustomer(): LiveData<List<Customer>>

    @Query("SELECT * FROM MEASUREMENT_TABLE WHERE customerId=:customerId")
    fun getMeasurementByCusId(customerId: Int): Measurement?

    @Query("SELECT * FROM clothing_table WHERE customerId=:customerId")
    fun getClothingByCusId(customerId: Int): Clothing?


}