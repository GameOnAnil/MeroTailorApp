package com.gameonanil.tailorapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.databinding.MainRecyclerListBinding

class MainRecyclerAdapter(
    private val context: Context,
    private var customerList: List<Customer>,
    private val mainInterface: MainRecyclerInterface
) : RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = MainRecyclerListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(customerList[position])
    }

    override fun getItemCount() = customerList.size

    fun setCustomerList(customerLists: List<Customer>) {
        customerList = customerLists
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: MainRecyclerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                mainInterface.handleItemClicked(customerList[adapterPosition])
            }
        }

        fun bindTo(customer: Customer) {
            binding.tvCustomerName.text = customer.customerName
            binding.tvPhone.text = customer.customerPhone.toString()
        }

    }

    interface MainRecyclerInterface {
        fun handleItemClicked(customer: Customer)
    }
}