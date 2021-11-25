package com.gameonanil.tailorapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing

@Dao
interface TailorDao {
    @Insert
    fun insertCustomer(customer: Customer)

    @Insert
    fun insertClothing(clothing: Clothing)

    @Query("SELECT * FROM customer_table WHERE customerId=:customerId ")
    fun getCustomerWithClothing(customerId: Int): LiveData<List<CustomerWithClothing>>
}