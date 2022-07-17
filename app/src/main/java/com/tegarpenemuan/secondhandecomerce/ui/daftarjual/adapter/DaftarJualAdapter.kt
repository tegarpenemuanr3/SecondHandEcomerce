package com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.common.ChangeCurrency
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemDaftarJualBinding

class DaftarJualAdapter(
    val context: Context,
    private val listener: EventListener,
    private var list: ArrayList<GetProductResponse>
) : RecyclerView.Adapter<DaftarJualAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemDaftarJualBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: ArrayList<GetProductResponse>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemDaftarJualBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        when (item) {
            list[0] -> {
                holder.binding.cl.visibility = View.GONE
                holder.binding.ll.visibility = View.VISIBLE
                holder.binding.ivImageProduct.setImageResource(R.drawable.ic_add)
                holder.binding.tvAdd.text = "Tambah Produk"

                holder.itemView.setOnClickListener {
                    listener.addProduct(item)
                }
            }
            else -> {
                holder.binding.cl.visibility = View.VISIBLE
                holder.binding.ll.visibility = View.GONE
                holder.binding.tvNamaProduct.text = item.name
                Glide.with(holder.binding.root.context)
                    .load(item.image_url)
                    .transform(RoundedCorners(20))
                    .into(holder.binding.ivImageProduct)
                holder.binding.tvHargaProduct.text =
                    ChangeCurrency.gantiRupiah(item.base_price.toString())

                holder.itemView.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: GetProductResponse)
        fun addProduct(item: GetProductResponse)
    }
}