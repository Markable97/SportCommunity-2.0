package com.glushko.sportcommunity.presentation.about

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.glushko.sportcommunity.R

class AboutFragment: Fragment(R.layout.fragment_about_us) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textVersionBuild).apply {
            val versionName = requireContext().packageManager.getPackageInfo(
                requireActivity().packageName,
                PackageManager.PackageInfoFlags.of(0L)).versionName
            text = getString(R.string.app_version, versionName)
        }
    }

}