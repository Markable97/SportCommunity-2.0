package com.glushko.sportcommunity.presentation.about

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.glushko.sportcommunity.BuildConfig
import com.glushko.sportcommunity.R

class AboutFragment: Fragment(R.layout.fragment_about_us) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textVersionBuild).apply {
            text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
        }
    }

}