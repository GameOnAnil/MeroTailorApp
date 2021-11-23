package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameonanil.tailorapp.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)

        binding.apply {
            btnAddClothes.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToAddClothesFragment()
                findNavController().navigate(action)
            }
            btnAddCustomer.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToAddCustomerFragment()
                findNavController().navigate(action)
            }
            btnClothesList.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToClothesListFragment()
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