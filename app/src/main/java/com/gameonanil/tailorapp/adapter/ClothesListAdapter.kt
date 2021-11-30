package com.gameonanil.tailorapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer
import com.gameonanil.tailorapp.databinding.ClothesListRecyclerItemBinding

class ClothesListAdapter(
    private val context: Context,
    private var mCustomer: Customer?,
    private var mClothingList: List<Clothing>?,
    private val listener: ClothesListListener
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
        holder.bindTo(mCustomer!!, mClothingList!![position])
    }

    override fun getItemCount(): Int {
        return if (mClothingList != null) {
            mClothingList!!.size
        } else {
            0
        }
    }

    fun setClothingList(customer: Customer, clothingList: List<Clothing>) {
        mClothingList = clothingList
        mCustomer = customer
        notifyDataSetChanged()

    }


    inner class TailorViewHolder(private val binding: ClothesListRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(customer: Customer, currentClothing: Clothing) {
            itemView.setOnClickListener {
                listener.handleItemClicked(currentClothing)
            }

            binding.apply {
                tvUserName.text = customer.customerName
                tvClothingName.text = currentClothing.clothingName
                tvPrice.text = currentClothing.price.toString()
                tvDueDate.text = currentClothing.dueDate


                val remaining = currentClothing.remaining
                if (remaining.toInt() == 0) {
                    btnPay.isVisible = false
                    tvPaid.isVisible = true
                } else {
                    btnPay.isVisible = true
                    tvPaid.isVisible = false
                }
            }
        }

    }

    interface ClothesListListener {
        fun handleItemClicked(clothing: Clothing)
    }
}