package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.adapter.ClothesListAdapter
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.databinding.FragmentClothesListBinding
import com.gameonanil.tailorapp.viewmodel.ClothingListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1


class ClothesListFragment : Fragment(), ClothesListAdapter.ClothesListListener {
    companion object {
        private const val TAG = "ClothesListFragment"
    }

    private var _binding: FragmentClothesListBinding? = null
    private val binding: FragmentClothesListBinding get() = _binding!!
    private lateinit var mAdapter: ClothesListAdapter
    private lateinit var clothingListViewModel: ClothingListViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var customerId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothesListBinding.inflate(layoutInflater)

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

        customerId = ClothesListFragmentArgs.fromBundle(requireArguments()).customerId


        mAdapter = ClothesListAdapter(requireContext(), null, null, this)
        binding.clothingListRecycler.adapter = mAdapter

        clothingListViewModel = ViewModelProvider(this).get(ClothingListViewModel::class.java)

//        customerId?.let {
//            clothingListViewModel.setCustomerId(it)
//        }
//
//        clothingListViewModel.customerWithClothing.observe(requireActivity(), Observer {
//            mAdapter.setClothingList(Customer(1, "Anil", 123), it)
//        })

        retrieveDate(Clothing::price)


        binding.apply {
            fabAddClothing.setOnClickListener {
                val action =
                    ClothesListFragmentDirections.actionClothesListFragmentToAddClothesFragment(
                        customerId!!
                    )
                findNavController().navigate(action)
            }
        }


        return binding.root
    }

    private fun retrieveDate(clothingProp: KProperty1<Clothing, *>) {
        val comparator: Comparator<Clothing> = when (clothingProp) {
            Clothing::dueDate -> compareBy { it.dueDate }
            Clothing::price -> compareBy { it.price }
            else -> compareBy { it.dueDate }
        }

        var customer: Customer? = null
        var clothingList: List<Clothing>? = null
        CoroutineScope(Dispatchers.IO).launch {
            customer = clothingListViewModel.getCurrentCustomer(customerId!!)
            clothingList = clothingListViewModel.getClothingList(customerId!!)
            Log.d(TAG, "getData: customer=$customer and clothinglist=$clothingList")
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {

                mAdapter.setClothingList(customer!!, clothingList!!.sortedWith(comparator))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun handleItemClicked(clothing: Clothing) {
        val action =
            ClothesListFragmentDirections.actionClothesListFragmentToClothingDetailsFragment(
                customerId!!,
                clothing.clothingId!!
            )
        findNavController().navigate(action)
    }


}
