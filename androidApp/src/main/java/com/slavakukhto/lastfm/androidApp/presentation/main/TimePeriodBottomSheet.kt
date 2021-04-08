package com.slavakukhto.lastfm.androidApp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.DialogTimePeriodBinding
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod

class TimePeriodBottomSheet : BottomSheetDialogFragment() {

    companion object {

        fun newInstance(callbackTimePeriod: (TimeStampPeriod) -> Unit): TimePeriodBottomSheet {
            val dialog = TimePeriodBottomSheet()
            dialog.callbackTimePeriod = callbackTimePeriod
            return dialog
        }
    }

    private val binding: DialogTimePeriodBinding by viewBinding { DialogTimePeriodBinding.bind(it) }
    private lateinit var callbackTimePeriod: (TimeStampPeriod) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_time_period, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.tvOverall.setOnClickListener {
            callbackTimePeriod.invoke(TimeStampPeriod.OVERALL)
            dismiss()
        }
        binding.tv7days.setOnClickListener {
            callbackTimePeriod.invoke(TimeStampPeriod.DAYS7)
            dismiss()
        }
        binding.tv1month.setOnClickListener {
            callbackTimePeriod.invoke(TimeStampPeriod.MONTH1)
            dismiss()
        }
        binding.tv3month.setOnClickListener {
            callbackTimePeriod.invoke(TimeStampPeriod.MONTH3)
            dismiss()
        }
        binding.tv6month.setOnClickListener {
            callbackTimePeriod.invoke(TimeStampPeriod.MONTH6)
            dismiss()
        }
        binding.tv12month.setOnClickListener {
            callbackTimePeriod.invoke(TimeStampPeriod.MONTH12)
            dismiss()
        }
    }
}
