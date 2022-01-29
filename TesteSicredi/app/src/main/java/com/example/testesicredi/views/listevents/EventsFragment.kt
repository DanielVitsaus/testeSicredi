package com.example.testesicredi.views.listevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testesicredi.databinding.FragmentEventsBinding
import com.example.testesicredi.lifecycle.LifecycleEventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsFragment: Fragment() {

    private val viewModel by viewModel<EventsViewModel>()
    private lateinit var binding: FragmentEventsBinding
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        eventsAdapter = EventsAdapter(requireContext(), viewModel)
        return FragmentEventsBinding.inflate(inflater, container, false).also {
            binding = it
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) {
            eventsAdapter.submitList(it)
        }

        viewModel.onSelectedEvent.observe(viewLifecycleOwner, LifecycleEventObserver{
            val direction = EventsFragmentDirections.actionEventsFragmentToEventDetailsFragment(it.id)
            findNavController().navigate(direction)
        })

        binding.rvEvents.adapter = eventsAdapter

    }
}