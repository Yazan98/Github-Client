package com.yazan98.autohub.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yazan98.autohub.R
import kotlinx.android.synthetic.main.dialog_trending_filter.*
import javax.inject.Inject

class TrendingFilterDialog @Inject constructor() : BottomSheetDialogFragment() {

    private var listener: FilterListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_trending_filter, container, false)
    }

    fun attachListener(listener: FilterListener) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DailyButton?.apply {
            this.setOnClickListener {
                listener?.onTimeFilter("daily")
                dismiss()
            }
        }

        WeeklyButton?.apply {
            this.setOnClickListener {
                listener?.onTimeFilter("weekly")
                dismiss()
            }
        }

        MonthlyButton?.apply {
            this.setOnClickListener {
                listener?.onTimeFilter("monthly")
                dismiss()
            }
        }

        FilterReset?.apply {
            this.setOnClickListener {
                listener?.onTimeFilter("daily")
                dismiss()
            }
        }
    }

    interface FilterListener {
        fun onTimeFilter(type: String)
    }
}