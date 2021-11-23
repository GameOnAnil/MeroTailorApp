package com.gameonanil.tailorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gameonanil.tailorapp.databinding.FragmentClothesListBinding


class ClothesListFragment : Fragment() {
    private var _binding: FragmentClothesListBinding? = null
    private val binding: FragmentClothesListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothesListBinding.inflate(layoutInflater)




        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}