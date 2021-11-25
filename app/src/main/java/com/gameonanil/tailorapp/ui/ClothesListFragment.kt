package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gameonanil.tailorapp.adapter.TailorRecyclerAdapter
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.databinding.FragmentClothesListBinding
import com.gameonanil.tailorapp.viewmodel.TailorViewModel


class ClothesListFragment : Fragment() {
    companion object {
        private const val TAG = "ClothesListFragment"
    }

    private var _binding: FragmentClothesListBinding? = null
    private val binding: FragmentClothesListBinding get() = _binding!!

    private lateinit var mAdapter: TailorRecyclerAdapter
    private lateinit var clothingList: MutableList<Clothing>

    private lateinit var tailorViewModel: TailorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothesListBinding.inflate(layoutInflater)
        clothingList = mutableListOf()

        mAdapter = TailorRecyclerAdapter(requireContext(), clothingList)
        binding.clothingListRecycler.adapter = mAdapter

        tailorViewModel = ViewModelProvider(this).get(TailorViewModel::class.java)

        tailorViewModel.customerWithClothing.observe(requireActivity(), Observer {
            it?.let {
                clothingList = it.clothing as MutableList<Clothing>
                mAdapter.setClothingList(clothingList)
            }

        })


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}