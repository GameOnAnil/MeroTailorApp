package com.gameonanil.tailorapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gameonanil.tailorapp.data.entity.CustomerWithClothing
import com.gameonanil.tailorapp.databinding.ClothesListRecyclerItemBinding

class ClothesListAdapter(
    private val context: Context,
    private var customerWithClothing: CustomerWithClothing?
) : RecyclerView.Adapter<ClothesListAdapter.TailorViewHolder>() {
    companion object {
        private const val TAG = "TailorRecyclerAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TailorViewHolder {
        val view =
            ClothesListRecyclerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return TailorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TailorViewHolder, position: Int) {
        customerWithClothing?.let { holder.bindTo(it, position) }
    }

    override fun getItemCount(): Int {
        return if (customerWithClothing != null) {
            customerWithClothing!!.clothing.size
        } else {
            0
        }
    }

    fun setClothingList(customerWithClothing: CustomerWithClothing) {
        this@ClothesListAdapter.customerWithClothing = customerWithClothing
        notifyDataSetChanged()

    }

    inner class TailorViewHolder(private val binding: ClothesListRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(customerWithClothing: CustomerWithClothing, position: Int) {
            binding.apply {
                tvUserName.text = customerWithClothing.customer.customerName
                tvClothingName.text = customerWithClothing.clothing[position].clothingName
                tvPrice.text = customerWithClothing.clothing[position].price.toString()
                tvDueDate.text = customerWithClothing.clothing[position].dueDate
            }
        }

    }

    interface ClothesListListener {
        fun handleItemClicked()
    }
}