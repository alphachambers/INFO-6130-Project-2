package com.example.androidproject2

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidproject2.databinding.FragmentTwoBinding
import java.text.SimpleDateFormat
import java.util.*

class FragmentTwo : Fragment() {

    private lateinit var binding: FragmentTwoBinding
    private var wheelRotationAngle: Float = 0f

    private lateinit var turningWheelAnimation: AnimationDrawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start turning wheel animation
        binding.turningWheelImageView.setBackgroundResource(R.drawable.wheel_animation)
        val turningWheelAnimation = binding.turningWheelImageView.background as AnimationDrawable
        turningWheelAnimation.start()

        // Update date and time every second
        updateDateTime()
    }

    private fun updateDateTime() {
        val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val date = dateFormat.format(Date())
                val time = timeFormat.format(Date())

                requireActivity().runOnUiThread {
                    binding.dateAndTimeTextView.text = "$date\n$time"
                }
            }
        }, 0, 1000) // Update every second
    }
}
