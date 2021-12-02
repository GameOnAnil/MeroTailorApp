package com.gameonanil.tailorapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.gameonanil.tailorapp.R
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.databinding.MainRecyclerListBinding

class MainRecyclerAdapter(
    private val context: Context,
    private var mCustomerList: MutableList<Customer>,
    private val mainInterface: MainRecyclerInterface
) : RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    init {
        mCustomerList = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = MainRecyclerListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(mCustomerList[position], position)
    }

    override fun getItemCount() = mCustomerList.size

    fun setCustomerList(customerLists: List<Customer>) {
        mCustomerList.clear()
        for (cus in customerLists) {
            mCustomerList.add(cus)
        }
        notifyDataSetChanged()
    }

    fun notifyDeleteCustomer(position: Int) {
        mCustomerList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: MainRecyclerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                mainInterface.handleItemClicked(mCustomerList[adapterPosition])
            }
        }

        fun bindTo(customer: Customer, currentPosition: Int) {
            binding.tvCustomerName.text = customer.customerName
            binding.tvPhone.text = customer.customerPhone

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