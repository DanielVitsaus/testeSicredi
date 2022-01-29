package com.example.testesicredi.views.checkin

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import com.example.testesicredi.R
import com.example.testesicredi.databinding.FragmentCheckInBinding
import com.example.testesicredi.lifecycle.LifecycleEventObserver
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckInSheetDialog : BottomSheetDialogFragment() {

    private val viewModel by viewModel<CheckInViewModel>()
    private lateinit var binding: FragmentCheckInBinding
    private val args by navArgs<CheckInSheetDialogArgs>()

    @CallSuper
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        try {
            sheetDialog.setOnShowListener {
                val bottomSheet = sheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)!!

                val shape = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.background_bottom_sheet
                )
                ViewCompat.setBackground(bottomSheet, shape)
            }
        } catch (t: Throwable) {}
        return sheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setEventId(args.eventId)
        return FragmentCheckInBinding.inflate(inflater, container, false).also {
            binding = it
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onRegistered.observe(viewLifecycleOwner, LifecycleEventObserver{
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(requireContext().getString(R.string.dialog_title))
                .setMessage(requireContext().getString(it))
                .setPositiveButton(requireContext().getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
            dismiss()
        })
    }
}