package com.yazan98.autohub.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.SettingsAdapter
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.linearVerticalLayout
import kotlinx.android.synthetic.main.dialog_settings.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsDialog @Inject constructor() : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            startOptions()
        }
    }

    private suspend fun startOptions() {
        withContext(Dispatchers.Main) {
            activity?.let {
                SettingsRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = SettingsAdapter()
                    (this.adapter as SettingsAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SettingsRecycler?.adapter = null
    }
}