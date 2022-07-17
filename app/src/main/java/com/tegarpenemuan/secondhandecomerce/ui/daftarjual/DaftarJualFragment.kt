package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentDaftarJualBinding
import com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter.DaftarJualAdapter
import com.tegarpenemuan.secondhandecomerce.ui.jual.JualFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DaftarJualViewModel by viewModels()

    lateinit var productSellerAdapter: DaftarJualAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarJualBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //definisi recylerview
        viewModel.getProductSeller()

        bindview()
        bindviewModel()

        return root
    }

    private fun bindview() {
        productSellerAdapter =
            DaftarJualAdapter(
                requireContext(),
                listener = object : DaftarJualAdapter.EventListener {
                    override fun onClick(item: GetProductResponse) {
                        Toast.makeText(requireContext(), item.image_name, Toast.LENGTH_SHORT).show()
                    }

                    override fun addProduct(item: GetProductResponse) {
//                        findNavController().navigate(
//                            R.id.action_navigation_daftar_jual_to_navigation_jual
//                        )
                    }
                },
                ArrayList()
            )
        binding.rvProduct.adapter = productSellerAdapter
    }

    private fun bindviewModel() {
        /** Tambah Produk*/
        viewModel.shouldShowGetProductSeller.observe(viewLifecycleOwner) {
            it.add(
                index = 0,
                element = GetProductResponse(
                    base_price = 0,
                    created_at = "",
                    description = "",
                    id = 0,
                    image_name = "",
                    image_url = R.drawable.ic_add.toString(),
                    location = "",
                    name = "Tambah Produk",
                    updated_at = "",
                    user_id = 0
                )
            )
            productSellerAdapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}