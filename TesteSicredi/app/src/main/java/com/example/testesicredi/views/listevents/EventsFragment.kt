package com.example.testesicredi.views.listevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testesicredi.databinding.FragmentEventsBinding
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
        viewModel.getEvents()
        eventsAdapter = EventsAdapter(requireContext(), viewModel)
        return FragmentEventsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }
}