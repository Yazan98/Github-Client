package com.yazan98.autohub.fragments

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.AutohubApplication
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.OptionsAdapter
import com.yazan98.autohub.utils.ApplicationUtils
import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.linearVerticalLayout
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun initScreen(view: View) {
        (activity?.application as AutohubApplication).let {
            lifecycleScope.launch {
                startMainScreenRecycler()
                it.startGithubActions()
            }
        }
    }

    private suspend fun startMainScreenRecycler() {
        withContext(Dispatchers.Main) {
            activity?.let {
                MainRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = OptionsAdapter(ApplicationUtils.getMainScreenOptions())
                    (this.adapter as OptionsAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }

}
