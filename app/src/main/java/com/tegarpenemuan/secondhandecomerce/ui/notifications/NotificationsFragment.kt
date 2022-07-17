package com.tegarpenemuan.secondhandecomerce.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tegarpenemuan.secondhandecomerce.data.api.getNotification.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentNotificationsBinding
import com.tegarpenemuan.secondhandecomerce.ui.notifications.adapter.NotificationsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationsViewModel by viewModels()

    lateinit var notificationsAdapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getNotification()
        bindview()
        bindviewModel()
        return root
    }

    private fun bindview() {
        notificationsAdapter =
            NotificationsAdapter(listener = object : NotificationsAdapter.EventListener {
                override fun onClick(item: GetNotifResponseItem) {
                    Toast.makeText(requireContext(),item.buyer_name, Toast.LENGTH_SHORT).show()
                }

            }, emptyList())
        binding.rvNotifikasi.adapter = notificationsAdapter
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetNotification.observe(viewLifecycleOwner){
            notificationsAdapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}