package com.example.androidproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidproject2.databinding.FragmentOneBinding

fun FragmentOneBinding.bind(fragment: Fragment, container: ViewGroup?) {
    // Inflate the layout using View Binding
    fragment.view?.let { rootView ->
        container?.let { containerView ->
            containerView.removeAllViews()
            val view = this.root
            containerView.addView(view)
        }
    }
}