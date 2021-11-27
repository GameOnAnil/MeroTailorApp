package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.databinding.FragmentClothingDetailsBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel
import kotlinx.coroutines.*

class ClothingDetailsFragment : Fragment() {
    companion object {
        private const val TAG = "ClothingDetailsFragment"
    }

    private var _binding: FragmentClothingDetailsBinding? = null
    private val binding: FragmentClothingDetailsBinding get() = _binding!!

    private lateinit var mViewModel: TailorViewModel
    private var mClothingId: Int? = null
    private var mCustomerId: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothingDetailsBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)

        mClothingId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).clothingId
        mCustomerId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).customerId
        initDetails()

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
                    if (etTotalPrice.text!!.isEmpty()) {
                        Toast.makeText(
                            requireContext().applicationContext,
                            "Please Enter Total Price",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    if (etRemaining.text!!.isEmpty()) {
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
        }



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mClothingId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).clothingId
        mCustomerId = ClothingDetailsFragmentArgs.fromBundle(requireArguments()).customerId
        initDetails()
    }

    private fun updateDetails() {
        /**UPDATE clothing*/
        binding.apply {

            val typeOfOrder = etTypeOfOrder.text.toString()
            val totalPrice = etTotalPrice.text!!.trim().toString().toFloat()
            val remaining = etRemaining.text!!.trim().toString().toFloat()
            val dueDate = etDueDate.text!!.trim().toString()

            val chati = etChati.text!!.trim().toString().toInt()
            val baulaLambai = etBaulaLambai.text!!.trim().toString().toInt()
            val kafGhera = etKafGhera.text!!.trim().toString().toInt()
            val kakhi = etKakhi.text!!.trim().toString().toInt()
            val kamarGhera = etKamarGhera.text!!.trim().toString().toInt()
            val kamarLambai = etKamarLambai.text!!.trim().toString().toInt()
            val kum = etKum.text!!.trim().toString().toInt()
            val puraLambai = etPuraLambai.text!!.trim().toString().toInt()

            val clothing =
                Clothing(mClothingId, mCustomerId!!, typeOfOrder, totalPrice, remaining, dueDate)

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
            mViewModel.getClothingByCusId(mCustomerId!!).let {
                withContext(Dispatchers.Main) {
                    binding.apply {
                        etTypeOfOrder.setText(it!!.clothingName)
                        etTotalPrice.setText(it.price.toString())
                        etRemaining.setText(it.remaining.toString())
                        etDueDate.setText(it.dueDate)
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

}