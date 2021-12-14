package com.gameonanil.tailorapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.adapter.ClothesListAdapter
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.databinding.FragmentClothesListBinding
import com.gameonanil.tailorapp.utils.SwipeGesture
import com.gameonanil.tailorapp.viewmodel.ClothingListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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
        /** TO USE OPTIONS MENU*/
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

        customerId = ClothesListFragmentArgs.fromBundle(requireArguments()).customerId


        mAdapter = ClothesListAdapter(requireContext(), null, null, this)
        binding.clothingListRecycler.adapter = mAdapter

        clothingListViewModel = ViewModelProvider(this).get(ClothingListViewModel::class.java)


        getDataByDate()
        binding.apply {
            fabAddClothing.setOnClickListener {
                val action =
                    ClothesListFragmentDirections.actionClothesListFragmentToAddClothesFragment(
                        customerId!!
                    )
                findNavController().navigate(action)
            }
        }

        val item = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Are you sure?")
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                        mAdapter.notifyDataSetChanged()
                    }).setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                        mAdapter.deleteItem(viewHolder.adapterPosition)
                    })
                alertDialog.show()

            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(binding.clothingListRecycler)




        return binding.root
    }

    override fun handleDeleteItem(clothing: Clothing, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            clothingListViewModel.deleteClothing(clothing)
            withContext(Dispatchers.Main) {
                mAdapter.notifyOurItemDeleted(position)
            }
        }
    }

    private fun getDataByPrice() {


        var customer: Customer? = null
        var clothingList: List<Clothing>? = null
        CoroutineScope(Dispatchers.IO).launch {
            customer = clothingListViewModel.getCurrentCustomer(customerId!!)
            clothingList = clothingListViewModel.getClothingList(customerId!!)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                val sortedList = clothingList!!.sortedBy { it.price }

                mAdapter.setClothingList(customer!!, sortedList)
            }
        }
    }

    private fun getDataByDate() {
        var customer: Customer? = null
        var clothingList: List<Clothing>? = null
        CoroutineScope(Dispatchers.IO).launch {
            customer = clothingListViewModel.getCurrentCustomer(customerId!!)
            clothingList = clothingListViewModel.getClothingList(customerId!!)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                val sortedList = clothingList!!.sortedBy {
                    LocalDate.parse(it.dueDate, DateTimeFormatter.ofPattern("dd MMM yyyy"))
                }
                mAdapter.setClothingList(customer!!, sortedList)
                for (clothes in clothingList!!) {
                    Log.d(TAG, "getDataByDate: date=${clothes.dueDate}")
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.clothes_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_sort -> {
                true
            }
            R.id.item_sort_due_date -> {
                getDataByDate()
                true
            }
            R.id.item_sort_price -> {
                getDataByPrice()
                true
            }
            else -> super.onOptionsItemSelected(item)

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

    override fun handlePaymentClicked(clothing: Clothing) {
        Log.d(TAG, "handlePaymentClicked: CLOTHING=$clothing")
        val action = ClothesListFragmentDirections.actionClothesListFragmentToPayFragment(
            customerId!!,
            clothing.clothingId!!
        )
        findNavController().navigate(action)
    }


}
