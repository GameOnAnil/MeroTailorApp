package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gameonanil.tailorapp.adapter.MainRecyclerAdapter
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.entity.Measurement
import com.gameonanil.tailorapp.data.relation.CustomerWithClothing
import com.gameonanil.tailorapp.databinding.FragmentMainBinding
import com.gameonanil.tailorapp.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment(), MainRecyclerAdapter.MainRecyclerInterface {
    companion object {
        private const val TAG = "MainFragment"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private lateinit var mAdapter: MainRecyclerAdapter
    private lateinit var customerList: MutableList<Customer>
    private lateinit var mViewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)

        customerList = mutableListOf()
        mAdapter = MainRecyclerAdapter(requireContext(), customerList, this)
        binding.recyclerMain.adapter = mAdapter
        mViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        mViewModel.customerList.observe(requireActivity(), Observer {
            mAdapter.setCustomerList(it)
        })
        binding.apply {

            fabMain.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToAddCustomerFragment()
                findNavController().navigate(action)
            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun handleItemClicked(customer: Customer) {
        val customerId = customer.customerId!!
        val action = MainFragmentDirections.actionMainFragmentToClothesListFragment(customerId)
        findNavController().navigate(action)

    }

    override fun handleDeleteClicked(customer: Customer, position: Int) {
        var customerWithClothing: CustomerWithClothing? = null
        var measurement: Measurement? = null
        CoroutineScope(Dispatchers.IO).launch {
            customerWithClothing = mViewModel.getCustomerWithClothing(customer.customerId!!)
            measurement = mViewModel.getMeasurement(customer.customerId)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.IO).launch {
                val deleteCustomer: Int? =
                    mViewModel.deleteCustomer(customerWithClothing!!.customer)
                val deleteClothingList: Int? =
                    mViewModel.deleteClothingList(customerWithClothing!!.clothing)
                Log.d(TAG, "handleDeleteClicked: DeleteCustomer=$deleteCustomer")
                Log.d(TAG, "handleDeleteClicked: DeleteCLO=$deleteClothingList")
                measurement?.let {
                    val deleteMeasurement: Int? = mViewModel.deleteMeasurement(it)
                    Log.d(TAG, "handleDeleteClicked: DeleteMEasurement=$deleteMeasurement!!")
                }
                withContext(Dispatchers.Main) {
                    if (deleteCustomer!! > 0) {
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }

        }
    }

    override fun handleEditClicked(customer: Customer) {
        Log.d(TAG, "handleEditClicked: ")
    }
}