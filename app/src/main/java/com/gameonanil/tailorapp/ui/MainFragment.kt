package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gameonanil.tailorapp.adapter.MainRecyclerAdapter
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.databinding.FragmentMainBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel


class MainFragment : Fragment(), MainRecyclerAdapter.MainRecyclerInterface {
    companion object {
        private const val TAG = "MainFragment"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private lateinit var mAdapter: MainRecyclerAdapter
    private lateinit var customerList: MutableList<Customer>
    private lateinit var mViewModel: TailorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)

        customerList = mutableListOf()
        mAdapter = MainRecyclerAdapter(requireContext(), customerList, this)
        binding.recyclerMain.adapter = mAdapter
        mViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)

        mViewModel.customerList.observe(requireActivity(), Observer {
            mAdapter.setCustomerList(it)
        })
        binding.apply {

            btnAddCustomer.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToAddCustomerFragment()
                findNavController().navigate(action)
            }
            btnClothesList.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToClothesListFragment(1)
                findNavController().navigate(action)
            }

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

}