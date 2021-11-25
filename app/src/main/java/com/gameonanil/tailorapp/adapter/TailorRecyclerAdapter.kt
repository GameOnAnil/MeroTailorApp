package com.gameonanil.tailorapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.databinding.RecyclerListItemBinding

class TailorRecyclerAdapter(
    private val context: Context,
    private var clothingList: List<Clothing>
) : RecyclerView.Adapter<TailorRecyclerAdapter.TailorViewHolder>() {
    companion object {
        private const val TAG = "TailorRecyclerAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TailorViewHolder {
        val view = RecyclerListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return TailorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TailorViewHolder, position: Int) {
        holder.bindTo(clothingList[position])
    }

    override fun getItemCount() = clothingList.size

    fun setClothingList(clothingListA: List<Clothing>) {
        clothingList = clothingListA
        notifyDataSetChanged()

    }

    inner class TailorViewHolder(private val binding: RecyclerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(clothing: Clothing) {
            binding.apply {
                tvClothingName.text = clothing.clothingName.toString()
                tvPrice.text = clothing.price.toString()
            }
        }

    }
}