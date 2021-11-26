package com.gameonanil.tailorapp.ui

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.gameonanil.tailorapp.databinding.FragmentAddClothesBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel


class AddClothesFragment : Fragment() {
    companion object {
        private const val TAG = "AddClothesFragment"
    }

    private var _binding: FragmentAddClothesBinding? = null
    private val binding: FragmentAddClothesBinding get() = _binding!!
    private lateinit var mViewModel: TailorViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddClothesBinding.inflate(layoutInflater, container, false)

        /**Setting Up Toolbar*/
        val navHostFragment = NavHostFragment.findNavController(this)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
            )
        )
        NavigationUI.setupWithNavController(
            binding.toolbar,
            navHostFragment,
            appBarConfiguration
        )


        mViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)

        initMeasurement()

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
                val typeOfOrder = etTypeOfOrder.text.toString()
                val totalPrice = etTotalPrice.text.toString().toFloat()
                val advance = etAdvance.text.toString().toFloat()
                val dueDate = "2021/01/01"
                saveClothingToDb(1, typeOfOrder, totalPrice, advance, dueDate)

            }
        }

        return binding.root
    }

    private fun saveClothingToDb(
        customerId: Int,
        typeOfOrder: String,
        totalPrice: Float,
        advance: Float,
        dueDate: String,
    ) {
        try {
            mViewModel.insertClothing(
                Clothing(
                    null,
                    customerId,
                    typeOfOrder,
                    totalPrice,
                    totalPrice - advance,
                    dueDate
                )
            )

            Toast.makeText(requireContext(), "Clothes Added Successfully", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigateUp()
        } catch (e: SQLiteException) {
            Log.d(TAG, "saveClothingToDb: ERROR:${e.message}")
        }

    }

    private fun initMeasurement() {
        mViewModel.getMeasurement(1)
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}