package com.yazan98.autohub.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yazan98.autohub.R
import com.yazan98.data.models.GithubRepositoryModel
import io.vortex.android.utils.random.VortexImageLoaders
import kotlinx.android.synthetic.main.fragment_repo_dialog.*

class RepositoryDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RepositoryOwnerImage?.let {
            VortexImageLoaders.loadLargeImageWithFresco("https://avatars1.githubusercontent.com/u/42048915?v=4", it, 80, 80)
        }
    }

    fun showRepositoryInfo(repo: GithubRepositoryModel, fragmentManager: FragmentManager) {
        RepoTitle?.text = repo.name
        show(fragmentManager, "")
        fragmentManager.executePendingTransactions()
    }
}
