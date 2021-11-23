package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gameonanil.tailorapp.data.User
import com.gameonanil.tailorapp.data.UserViewModel
import com.gameonanil.tailorapp.databinding.FragmentAddCustomerBinding


class AddCustomerFragment : Fragment() {
    private lateinit var mViewModel: UserViewModel
    private var _binding: FragmentAddCustomerBinding? = null
    private val binding: FragmentAddCustomerBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddCustomerBinding.inflate(inflater)

        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.apply {
            btnAddCustomer.setOnClickListener {
                addUserToDb()
            }
        }


        return binding.root
    }

    private fun addUserToDb() {
        val userName: String = "Anil Thapa"
        val user = User(0, userName)
        mViewModel.addUser(user)
        Toast.makeText(context, "User Added Successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}