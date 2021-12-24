package com.gameonanil.tailorapp.ui

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.databinding.FragmentClothingDetailsBinding
import com.gameonanil.tailorapp.utils.*
import com.gameonanil.tailorapp.utils.Notification
import com.gameonanil.tailorapp.viewmodel.TailorViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class ClothingDetailsFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    companion object {
        private const val TAG = "ClothingDetailsFragment"
    }

    private var _binding: FragmentClothingDetailsBinding? = null
    private val binding: FragmentClothingDetailsBinding get() = _binding!!

    private lateinit var mViewModel: TailorViewModel
    private var mClothingId: Int? = null
    private var mCustomerId: Int? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothingDetailsBinding.inflate(inflater, container, false)

        //SETUP TOOLBAR NAVIGATION
        val navHostFragment = NavHostFragment.findNavController(this)
        appBarConfiguration = AppBarConfiguration(setOf(R.layout.fragment_clothes_list))
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment, appBarConfiguration)


        mViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)

        mClothingId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).clothingId
        mCustomerId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).customerId
        initDetails()
        createNotificationChannel()

        binding.apply {
            btnUpdate.setOnClickListener {
                binding.apply {
                    if (etTypeOfOrder.text!!.isEmpty()) {
                        Toast.makeText(
                            requireContext().applicationContext,
                            "Please Enter Type Of Order",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    if (etTotalPrice.text!!.isEmpty() || etTotalPrice.text!!.toString() == ".") {
                        Toast.makeText(
                            requireContext().applicationContext,
                            "Please Enter Total Price",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    if (etRemaining.text!!.isEmpty() || etRemaining.text!!.toString() == ".") {
                        Toast.makeText(
                            requireContext().applicationContext,
                            "Please Enter Remaining",
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
                        Toast.makeText(requireContext(), "Please Enter Kum", Toast.LENGTH_SHORT)
                            .show()
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
                        Toast.makeText(
                            requireContext(),
                            "Please Enter Pura Lambai",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
                    if (etKafGhera.text!!.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Please Enter Kaf Ghera",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
                    if (etKamarGhera.text!!.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Please Enter Kamar Ghera",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
                    if (etKakhi.text!!.isEmpty()) {
                        Toast.makeText(requireContext(), "Please Enter Kakhi", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }

                    updateDetails()

                }
            }


            etDueDate.setOnClickListener {
                pickDate()
            }


        }

        return binding.root
    }

    private fun pickDate() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), this, year, month, day).show()

    }

    override fun onStart() {
        super.onStart()
        mClothingId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).clothingId
        mCustomerId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).customerId
        initDetails()
    }

    private fun updateDetails() {

        binding.apply {

            val typeOfOrder = etTypeOfOrder.text.toString()
            val totalPrice = etTotalPrice.text!!.trim().toString().toInt()
            val remaining = etRemaining.text!!.trim().toString().toInt()
            val dueDate = etDueDate.text!!.trim().toString()

            val chati = etChati.text!!.trim().toString().toInt()
            val baulaLambai = etBaulaLambai.text!!.trim().toString().toInt()
            val kafGhera = etKafGhera.text!!.trim().toString().toInt()
            val kakhi = etKakhi.text!!.trim().toString().toInt()
            val kamarGhera = etKamarGhera.text!!.trim().toString().toInt()
            val kamarLambai = etKamarLambai.text!!.trim().toString().toInt()
            val kum = etKum.text!!.trim().toString().toInt()
            val puraLambai = etPuraLambai.text!!.trim().toString().toInt()

            val notificationId = getCustomTime()

            var isPaid = false
            if (remaining == 0) {
                isPaid = true
            }
            val clothing =
                Clothing(
                    mClothingId,
                    mCustomerId!!,
                    typeOfOrder,
                    totalPrice,
                    remaining,
                    dueDate,
                    isPaid,
                    notificationId
                )
            Log.d(TAG, "updateDetails: CLOTHING:$clothing")
            val measureObject = Measurement(
                null, mCustomerId!!, chati,
                kum,
                baulaLambai,
                kamarLambai,
                puraLambai,
                kafGhera,
                kakhi,
                kamarGhera
            )
            CoroutineScope(Dispatchers.IO).launch {
                insertOrUpdateMeasure(measureObject, clothing)
            }.invokeOnCompletion {
                CoroutineScope(Dispatchers.Main).launch {
                    scheduleNotification()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun insertOrUpdateMeasure(measurement: Measurement, clothing: Clothing) {

        val measure = mViewModel.getMeasurementById(mCustomerId!!)
        if (measure == null) {
            mViewModel.insertMeasurement(measurement)
            mViewModel.updateClothing(clothing)
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
            mViewModel.updateClothing(clothing)
            Log.d(TAG, "insertOrUpdateMeasurement: UDated!!!")
        }

    }

    private fun initDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            mViewModel.getClothingById(mClothingId!!).let { clothing ->
                withContext(Dispatchers.Main) {
                    binding.apply {
                        etTypeOfOrder.setText(clothing!!.clothingName)
                        etTotalPrice.setText(clothing.price.toString())
                        etRemaining.setText(clothing.remaining.toString())
                        etDueDate.setText(clothing.dueDate)
                    }
                }
            }
        }





        CoroutineScope(Dispatchers.IO).launch {
            mViewModel.getMeasurementById(mCustomerId!!).let {
                withContext(Dispatchers.Main) {
                    binding.apply {
                        etChati.setText(it!!.chati.toString())
                        etBaulaLambai.setText(it.baulaLambai.toString())
                        etKafGhera.setText(it.kafGhera.toString())
                        etKakhi.setText(it.kakhi.toString())
                        etKamarGhera.setText(it.kamarGhera.toString())
                        etKamarLambai.setText(it.kamarLambai.toString())
                        etKum.setText(it.kum.toString())
                        etPuraLambai.setText(it.puraLambai.toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance()
        date.set(Calendar.YEAR, year)
        date.set(Calendar.MONTH, month)
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        //val formattedDate = DateFormat.getDateInstance().format(date.time)
        val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.US).format(date.time)
        binding.etDueDate.setText(formattedDate.toString())
    }


    private fun getCustomTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 5)

        return calendar.timeInMillis
    }


    private fun scheduleNotification() {
        val intent = Intent(requireContext().applicationContext, Notification::class.java)
        val title = "Notify title"
        val message = "Custom msg"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext().applicationContext,
            notificationId,
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

}
