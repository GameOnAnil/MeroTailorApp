package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.adapter.ClothesListAdapter
import com.gameonanil.tailorapp.databinding.FragmentClothesListBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel


class ClothesListFragment : Fragment() {
    companion object {
        private const val TAG = "ClothesListFragment"
    }

    private var _binding: FragmentClothesListBinding? = null
    private val binding: FragmentClothesListBinding get() = _binding!!
    private lateinit var mAdapter: ClothesListAdapter
    private lateinit var tailorViewModel: TailorViewModel
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


        mAdapter = ClothesListAdapter(requireContext(), null)
        binding.clothingListRecycler.adapter = mAdapter

        tailorViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)

        customerId?.let {
            tailorViewModel.getCustomerWithClothing(it)
        }


        tailorViewModel.customerWithClothing.observe(requireActivity(), Observer {
            it?.let {
                mAdapter.setClothingList(it)
            }

        })

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}