package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gameonanil.tailorapp.databinding.FragmentAddCustomerBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel


class AddCustomerFragment : Fragment() {
    private lateinit var mViewModel: TailorViewModel
    private var _binding: FragmentAddCustomerBinding? = null
    private val binding: FragmentAddCustomerBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddCustomerBinding.inflate(inflater)

        binding.apply {
            btnAddCustomer.setOnClickListener {

            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}