package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.gameonanil.tailorapp.databinding.FragmentEditCustomerBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditCustomerFragment : Fragment() {
    companion object {
        private const val TAG = "EditCustomerFragment"
    }

    private var _binding: FragmentEditCustomerBinding? = null
    private val binding: FragmentEditCustomerBinding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mViewModel: TailorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCustomerBinding.inflate(inflater, container, false)

        /**Setting up actionbar navigation**/

        val navHostFragment = NavHostFragment.findNavController(this)
        appBarConfiguration = AppBarConfiguration(setOf(R.layout.fragment_main))
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment, appBarConfiguration)

        mViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)
        val currentCustomer = EditCustomerFragmentArgs.fromBundle(requireArguments()).customer

        binding.apply {
            etEnterName.setText(currentCustomer.customerName)
            etEnterPhone.setText(currentCustomer.customerPhone)

            btnAddCustomer.setOnClickListener {
                if (TextUtils.isEmpty(etEnterName.text)) {
                    Toast.makeText(requireContext(), "Please Enter Name", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onCreateView: Please Enter Name")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(etEnterPhone.text)) {
                    Toast.makeText(requireContext(), "Please Enter Phone", Toast.LENGTH_SHORT)
                        .show()
                    Log.d(TAG, "onCreateView: Please Enter Phone")
                    return@setOnClickListener
                }
                if (!TextUtils.isDigitsOnly(etEnterPhone.text.toString())) {
                    Toast.makeText(
                        requireContext(),
                        "Phone Number must be a digit",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onCreateView: Phone Number must be digit")
                    return@setOnClickListener
                }

                val name = etEnterName.text!!.toString().trim()
                val phone = etEnterPhone.text!!.toString().trim()

                updateDatabase(Customer(currentCustomer.customerId, name, phone))
            }
        }



        return binding.root
    }

    private fun updateDatabase(customer: Customer) {
        CoroutineScope(Dispatchers.IO).launch {
            mViewModel.updateCustomer(customer)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}