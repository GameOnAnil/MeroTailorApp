package com.gameonanil.tailorapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.data.relation.CustomerWithClothing
import com.gameonanil.tailorapp.databinding.MainRecyclerListBinding

class MainRecyclerAdapter(
    private val context: Context,
    private var mCustomerWithClothingList: MutableList<CustomerWithClothing>,
    private val mainInterface: MainRecyclerInterface
) : RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    init {
        mCustomerWithClothingList = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = MainRecyclerListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(
            mCustomerWithClothingList[position].customer,
            mCustomerWithClothingList[position].clothing,
            position
        )
    }

    override fun getItemCount() = mCustomerWithClothingList.size

    fun setCustomerList(customerLists: List<CustomerWithClothing>) {
        mCustomerWithClothingList.clear()
        for (index in customerLists.indices) {
            mCustomerWithClothingList.add(customerLists[index])
        }
        notifyDataSetChanged()
    }

    fun notifyDeleteCustomer(position: Int) {
        mCustomerWithClothingList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: MainRecyclerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                mainInterface.handleItemClicked(mCustomerWithClothingList[adapterPosition].customer)
            }
        }

        fun bindTo(customer: Customer, clothingList: List<Clothing>, currentPosition: Int) {
            binding.apply {
                tvCustomerName.text = customer.customerName
                tvPhone.text = customer.customerPhone
                val totalClothing = clothingList.size
                val totalClothingString = "/${totalClothing}"
                tvTotalClothes.text = totalClothingString

                var clothesCount = 0
                for (clothes in clothingList) {
                    if (clothes.isPaid) {
                        clothesCount++
                    }
                }
                tvPaidCount.text = clothesCount.toString()
            }


            val popupMenu = PopupMenu(context, binding.btnDots)
            popupMenu.menuInflater.inflate(R.menu.drop_down_main, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it!!.itemId) {
                    R.id.itemDelete -> {
                        mainInterface.handleDeleteClicked(customer, currentPosition)
                        true
                    }
                    R.id.itemEdit -> {

                        true
                    }
                    else -> false
                }
            }
            binding.btnDots.setOnClickListener {
                popupMenu.show()
            }
        }

    }

    interface MainRecyclerInterface {
        fun handleItemClicked(customer: Customer)
        fun handleDeleteClicked(customer: Customer, position: Int)
        fun handleEditClicked(customer: Customer)
    }
}