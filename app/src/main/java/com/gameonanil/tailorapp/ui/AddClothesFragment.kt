package com.gameonanil.tailorapp.ui


import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.data.entity.NotificationEntity
import com.gameonanil.tailorapp.databinding.FragmentAddClothesBinding
import com.gameonanil.tailorapp.utils.Notification
import com.gameonanil.tailorapp.utils.channelId
import com.gameonanil.tailorapp.utils.messageExtra
import com.gameonanil.tailorapp.utils.titleExtra
import com.gameonanil.tailorapp.viewmodel.AddClothingViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class AddClothesFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    companion object {
        private const val TAG = "AddClothesFragment"
    }

    private var _binding: FragmentAddClothesBinding? = null
    private val binding: FragmentAddClothesBinding get() = _binding!!
    private lateinit var mViewModel: AddClothingViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var mCustomerId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddClothesBinding.inflate(layoutInflater, container, false)

        /**Setting Up Toolbar*/
        val navHostFragment = NavHostFragment.findNavController(this)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.clothesListFragment,
            )
        )
        NavigationUI.setupWithNavController(
            binding.toolbar,
            navHostFragment,
            appBarConfiguration
        )

        mViewModel = ViewModelProvider(this).get(AddClothingViewModel::class.java)
        mCustomerId = AddClothesFragmentArgs.fromBundle(requireArguments()).customerId

        createNotificationChannel()

        binding.apply {
            btnAddClothes.setOnClickListener {
                if (etTypeOfOrder.text!!.isEmpty()) {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "Please Enter Type Of Order",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (etTotalPrice.text!!.isEmpty()) {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "Please Enter Total Price",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (etAdvance.text!!.isEmpty()) {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "Please Enter Advance",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (etDueDate.text!!.isEmpty()) {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "Please Enter Due Date",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (etChati.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Chati", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (etBaulaLambai.text!!.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please Enter Baula Lambai",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (etKum.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Kum", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (etKamarLambai.text!!.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please Enter Kamar Lambai",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (etPuraLambai.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Pura Lambai", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (etKafGhera.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Kaf Ghera", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (etKamarGhera.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Kamar Ghera", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (etKakhi.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Kakhi", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val typeOfOrder = etTypeOfOrder.text.toString()
                val totalPrice = etTotalPrice.text.toString().toInt()
                val advance = etAdvance.text.toString().toInt()
                val dueDate = etDueDate.text!!.trim().toString()
                var isPaid: Boolean = false
                if (totalPrice - advance == 0) {
                    isPaid = true
                }


                val chati = etChati.text!!.trim().toString().toInt()
                val baulaLambai = etBaulaLambai.text!!.trim().toString().toInt()
                val kafGhera = etKafGhera.text!!.trim().toString().toInt()
                val kakhi = etKakhi.text!!.trim().toString().toInt()
                val kamarGhera = etKamarGhera.text!!.trim().toString().toInt()
                val kamarLambai = etKamarLambai.text!!.trim().toString().toInt()
                val kum = etKum.text!!.trim().toString().toInt()
                val puraLambai = etPuraLambai.text!!.trim().toString().toInt()
                mCustomerId?.let { cusId ->
                    val mObj = Measurement(
                        null,
                        cusId,
                        chati,
                        kum,
                        baulaLambai,
                        kamarLambai,
                        puraLambai,
                        kafGhera,
                        kakhi,
                        kamarGhera
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.IO) {
                            saveClothingToDb(
                                mCustomerId!!,
                                typeOfOrder,
                                totalPrice,
                                advance,
                                dueDate,
                                isPaid
                            )
                        }
                        withContext(Dispatchers.IO) {
                            insertOrUpdateMeasurement(mObj)
                        }
                        withContext(Dispatchers.IO) {
                            val clothing = async { mViewModel.getLatestClothing() }
                            clothing.await()?.let {
                                Log.d(
                                    TAG,
                                    "onCreateView: customerID:$mCustomerId and clothing:${it.clothingId}"
                                )
                                val notification = async {
                                    mViewModel.getNotificationId(mCustomerId!!, it.clothingId!!)
                                }
                                notification.await()?.let {
                                    withContext(Dispatchers.Main) {
                                        Log.d(TAG, "onCreateView: notification ->$it")
                                        scheduleNotification(it)
                                    }
                                }
                            }
                        }
                    }


                }


            }

            etDueDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    requireContext(),
                    this@AddClothesFragment,
                    year,
                    month,
                    dayOfWeek
                ).show()

            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initMeasurement()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Channel"
            val desc = "A description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = desc
            val notificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleNotification(notificationEntity: NotificationEntity) {
        val intent = Intent(requireContext().applicationContext, Notification::class.java)
        val title = "Notify title for ${notificationEntity.notificationId!!}"
        val message = "Custom msg"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext().applicationContext,
            notificationEntity.notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getCustomTime()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }

        findNavController().navigateUp()
    }

    private fun getCustomTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 5)

        return calendar.timeInMillis
    }

    private fun saveClothingToDb(
        customerId: Int,
        typeOfOrder: String,
        totalPrice: Int,
        advance: Int,
        dueDate: String,
        isPaid: Boolean
    ) {
        try {
            mViewModel.insertClothing(
                Clothing(
                    null,
                    customerId,
                    typeOfOrder,
                    totalPrice,
                    totalPrice - advance,
                    dueDate,
                    isPaid
                )
            )

            CoroutineScope(Dispatchers.IO).launch {
                mViewModel.getLatestClothing()?.let {
                    mViewModel.insertNotification(
                        NotificationEntity(
                            null,
                            customerId,
                            it.clothingId!!
                        )
                    )
                }

            }.invokeOnCompletion {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        requireContext(),
                        "Clothes Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        } catch (e: SQLiteException) {
            Log.d(TAG, "saveClothingToDb: ERROR:${e.message}")
        }

    }

    private fun initMeasurement() {
        mViewModel.getMeasurement(customerId = mCustomerId!!)
        mViewModel.measurement.observe(requireActivity(), Observer {
            it?.let {
                binding.apply {
                    etChati.setText(it.chati.toString())
                    etBaulaLambai.setText(it.baulaLambai.toString())
                    etKum.setText(it.kum.toString())
                    etKamarLambai.setText(it.kamarLambai.toString())
                    etPuraLambai.setText(it.puraLambai.toString())
                    etKafGhera.setText(it.kafGhera.toString())
                    etKamarGhera.setText(it.kamarGhera.toString())
                    etKakhi.setText(it.kakhi.toString())
                }
            }
        })
    }

    private fun insertOrUpdateMeasurement(measurement: Measurement) {
        CoroutineScope(Dispatchers.IO).launch {
            val measure = mViewModel.getMeasurementById(mCustomerId!!)
            Log.d(TAG, "insertOrUpdateMeasurement: testvalue=$measure")
            if (measure == null) {
                mViewModel.insertMeasurement(measurement)
                Log.d(TAG, "insertOrUpdateMeasurement: INSERTED!!!")
            } else {
                mViewModel.updateMeasurement(
                    Measurement(
                        measureId = measure.measureId,
                        measurement.customerId,
                        measurement.chati,
                        measurement.kum,
                        measurement.baulaLambai,
                        measurement.kamarLambai,
                        measurement.puraLambai,
                        measurement.kafGhera,
                        measurement.kakhi,
                        measurement.kamarGhera
                    )
                )
                Log.d(TAG, "insertOrUpdateMeasurement: UDated!!!")
            }

        }

    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance()
        date.set(Calendar.YEAR, year)
        date.set(Calendar.MONTH, month)
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//        val formattedDate = DateFormat.getDateInstance().format(date.time)
        val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.US).format(date.time)
        binding.etDueDate.setText(formattedDate.toString())
    }

}