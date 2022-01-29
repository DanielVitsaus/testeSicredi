package com.example.testesicredi.views.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testesicredi.R
import com.example.testesicredi.databinding.FragmentEventDetailsBinding
import com.example.testesicredi.lifecycle.LifecycleEventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsFragment: Fragment() {

    private val viewModel by viewModel<EventDetailsViewModel>()
    private val args by navArgs<EventDetailsFragmentArgs>()
    private lateinit var binding: FragmentEventDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getEvent(args.eventId)
        return FragmentEventDetailsBinding.inflate(inflater, container, false).also {
            binding = it
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> {
                    shareEvent()
                    true
                }
                else -> false
            }
        }

        viewModel.onEvent.observe(viewLifecycleOwner, {
            binding.event = it
        })

        viewModel.onCheckInEvent.observe(viewLifecycleOwner, LifecycleEventObserver{
            val direction = EventDetailsFragmentDirections.actionEventDetailsFragmentToCheckInSheetDialog(it.id)
            findNavController().navigate(direction)
        })
    }

    private fun shareEvent() {
        val text = viewModel.getInfoEvent()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}