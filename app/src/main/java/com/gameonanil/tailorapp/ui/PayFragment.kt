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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.databinding.FragmentPayBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PayFragment : Fragment() {
    companion object {
        private const val TAG = "PayFragment"
    }

    private var _binding: FragmentPayBinding? = null
    private val binding: FragmentPayBinding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mViewModel: TailorViewModel
    private var mCustomerId: Int? = null
    private var mClothingId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPayBinding.inflate(inflater, container, false)


        /**SETTING TOOLBAR NAVIGATION**/
        val navHostFragment = NavHostFragment.findNavController(this)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.layout.fragment_clothes_list
            )
        )
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment, appBarConfiguration)

        mClothingId = PayFragmentArgs.fromBundle(requireArguments()).clothingId
        mCustomerId = PayFragmentArgs.fromBundle(requireArguments()).customerId
        mViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)


        initDetails()

        binding.apply {

            btnConfirm.setOnClickListener {

                if (etPayment.text!!.isEmpty() || etPayment.text!!.toString() == ".") {
                    Toast.makeText(requireContext(), "Please Enter Payment", Toast.LENGTH_SHORT)
                        .show()
                    Log.d(TAG, "Please Enter Payment")
                    return@setOnClickListener
                }
                if (!TextUtils.isDigitsOnly(etPayment.text)) {
                    Toast.makeText(requireContext(), "Wrong Payment Format", Toast.LENGTH_SHORT)
                        .show()
                    Log.d(TAG, "Wrong Payment Format")
                    return@setOnClickListener
                }
                if (etPayment.text!!.toString().toFloat().toInt() < 0) {
                    Toast.makeText(
                        requireContext(),
                        "Payment Cannot Be Negative",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "Payment Cannot Be Negative")
                }
                val remainingWithRs = tvRemaining.text.toString()
                val remaining = remainingWithRs.substringAfter(".").trim().toInt()
                if (etPayment.text!!.toString().toFloat().toInt() > remaining) {
                    Toast.makeText(
                        requireContext(),
                        "Payment Exceeds Remaining Payment",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onCreateView: Payment Excceds Limit")
                }
                //AFTER CHECKING FORMAT
                val paid = etPayment.text.toString().trim().toFloat().toInt()
                updateDatabase(paid)
            }
        }


        return binding.root
    }

    private fun updateDatabase(paid: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mViewModel.getClothingById(mClothingId!!).let {
                val clothing = Clothing(
                    mClothingId!!,
                    mCustomerId!!,
                    it!!.clothingName,
                    it.price,
                    it.remaining - paid,
                    it.dueDate
                )
                Log.d(TAG, "updateDatabase: clothing=${clothing}")
                mViewModel.updateClothing(clothing)
            }
        }.invokeOnCompletion {
            lifecycleScope.launch(Dispatchers.Main) {
                findNavController().navigateUp()
            }
        }

    }


    private fun initDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            mViewModel.getClothingById(mCustomerId!!).let {
                withContext(Dispatchers.Main) {
                    val totalPrice = "Rs. ${it!!.price}"
                    val remaining = "Rs. ${it.remaining}"
                    val advance = "Rs. ${(it.price - it.remaining)}"
                    binding.tvTotalPrice.text = totalPrice
                    binding.tvRemaining.text = remaining
                    binding.tvAdvance.text = advance
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}