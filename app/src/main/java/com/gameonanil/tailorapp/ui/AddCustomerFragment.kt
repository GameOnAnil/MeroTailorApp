package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.databinding.FragmentAddCustomerBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel


class AddCustomerFragment : Fragment() {
    private lateinit var mViewModel: TailorViewModel
    private var _binding: FragmentAddCustomerBinding? = null
    private val binding: FragmentAddCustomerBinding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddCustomerBinding.inflate(inflater)

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

        binding.apply {
            btnAddCustomer.setOnClickListener {
                if (etEnterName.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Name", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (etEnterPhone.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Enter Phone", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val customerName = etEnterName.text!!.trim().toString()
                val phone = etEnterPhone.text!!.trim().toString().toInt()

                mViewModel.insertCustomer(Customer(null, customerName, phone))
                findNavController().navigateUp()
            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}