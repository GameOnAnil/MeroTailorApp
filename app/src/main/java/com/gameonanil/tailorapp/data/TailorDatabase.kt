package com.gameonanil.tailorapp.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.Measurement

@Database(
    entities = [Customer::class, Clothing::class, Measurement::class],
    version = 1,
    exportSchema = false
)
abstract class TailorDatabase : RoomDatabase() {
    abstract val tailorDao: TailorDao

    companion object {
        @Volatile
        private var INSTANCE: TailorDatabase? = null

        fun getInstance(context: Context): TailorDatabase {


            val tempInstance: TailorDatabase? = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                TailorDatabase::class.java,
                "tailor_db"
            ).fallbackToDestructiveMigration().build()
            INSTANCE = newInstance
            return newInstance

        }
    }
}