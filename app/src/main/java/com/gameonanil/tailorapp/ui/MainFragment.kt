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
import com.gameonanil.tailorapp.TailorViewModel
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    companion object {
        private const val TAG = "MainFragment"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private lateinit var mViewModel: TailorViewModel

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

        //loading db
        mViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            mViewModel.insertClothing(Clothing(0, 1, 213213, 12321))
        }
        mViewModel.customerWithClothing.observe(requireActivity(), Observer {
            Log.d(TAG, "onCreateView:$it ")
        })




        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}