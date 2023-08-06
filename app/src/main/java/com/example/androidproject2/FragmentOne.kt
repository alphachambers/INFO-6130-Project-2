package com.example.androidproject2

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidproject2.databinding.FragmentOneBinding
import java.util.*

class FragmentOne : Fragment() {

    private lateinit var binding: FragmentOneBinding
    private lateinit var turningWheelAnimation: AnimationDrawable

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seasonalImages: List<Drawable>
    private lateinit var seasonalMusic: List<Int>
    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using View Binding
        binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start cloud, sun, and birds animation
        binding.cloudImageView.setBackgroundResource(R.drawable.cloud_animation)
        val cloudAnimation = binding.cloudImageView.background as AnimationDrawable
        cloudAnimation.start()

        // Start birds animation
        binding.birdsImageView.setBackgroundResource(R.drawable.birds_animation)
        val birdsAnimation = binding.birdsImageView.background as AnimationDrawable
        birdsAnimation.start()

        // Start sun animation
        binding.sunImageView.setBackgroundResource(R.drawable.sun_animation)
        val sunAnimation = binding.sunImageView.background as AnimationDrawable
        sunAnimation.start()

        // Update background and images based on time
        updateBackgroundAndImages()

        // Initialize the media player
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.autumn_song)

        // Prepare the seasonal images
        seasonalImages = listOf(
            requireContext().getDrawable(R.drawable.spring)!!,
            requireContext().getDrawable(R.drawable.summer)!!,
            requireContext().getDrawable(R.drawable.autumn)!!,
            requireContext().getDrawable(R.drawable.winter)!!
        )

        // Prepare the seasonal music
        seasonalMusic = listOf(
            R.raw.spring_song,
            R.raw.summer_song,
            R.raw.autumn_song,
            R.raw.winter_song
        )

        // Set click listeners for buttons
        binding.startButton.setOnClickListener {
            startUpdates()
        }

        binding.stopButton.setOnClickListener {
            stopUpdates()
        }
    }

    private fun startUpdates() {
        // Start music playback
        mediaPlayer.start()

        // Schedule the background and image updates
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Update background color based on time
                updateBackgroundColor()

                // Update seasonal images and music based on time
                updateSeasonalContent()
            }
        }, 0, 5000) // Update every 5 seconds (adjust as needed)
    }

    private fun stopUpdates() {
        // Stop music playback
        mediaPlayer.pause()
        mediaPlayer.seekTo(0)

        // Cancel the scheduled updates
        timer.cancel()
    }

    // Add the updateBackgroundAndImages() function
    private fun updateBackgroundAndImages() {
        // Get current time
        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        // Set background color based on time (example: blue for morning, yellow for afternoon, etc.)
        val backgroundColor = when (currentTime) {
            in 0..11 -> R.color.colorMorning // Define color resources in res/values/colors.xml
            in 12..17 -> R.color.colorAfternoon
            else -> R.color.colorEvening
        }

        // Set the background color
        requireActivity().runOnUiThread {
            binding.fragmentContainer.setBackgroundColor(requireContext().getColor(backgroundColor))
        }

        // Set seasonal images based on time
        val currentSeasonalImages = when (currentTime) {
            in 0..11 -> listOf(
                R.drawable.cloud_1,
                R.drawable.sun3f,
                R.drawable.birds_1
            )
            in 12..17 -> listOf(
                R.drawable.cloud_1,
                R.drawable.sun3f,
                R.drawable.birds_1
            )
            else -> listOf(
                R.drawable.cloud_1,
                R.drawable.sun3f,
                R.drawable.birds_1
            )
        }

        // Set the images
        requireActivity().runOnUiThread {
            binding.cloudImageView.setImageResource(currentSeasonalImages[0])
            binding.sunImageView.setImageResource(currentSeasonalImages[1])
            binding.birdsImageView.setImageResource(currentSeasonalImages[2])
        }
    }

    private fun updateBackgroundColor() {
        // Get current time
        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        // Set background color based on time (example: blue for morning, yellow for afternoon, etc.)
        val backgroundColor = when (currentTime) {
            in 0..11 -> R.color.colorMorning // Define color resources in res/values/colors.xml
            in 12..17 -> R.color.colorAfternoon
            else -> R.color.colorEvening
        }

        // Set the background color
        requireActivity().runOnUiThread {
            binding.fragmentContainer.setBackgroundColor(requireContext().getColor(backgroundColor))
        }
    }

    private fun updateSeasonalContent() {
        // Get current month
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

        // Set seasonal image based on month
        val seasonalImage = seasonalImages[currentMonth / 3] // 3 months per season

        // Set the image
        requireActivity().runOnUiThread {
            binding.seasonalImageView.setImageDrawable(seasonalImage)
        }

        // Set seasonal music based on month
        val musicResource = seasonalMusic[currentMonth / 3] // 3 months per season

        // Change the music if necessary
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer = MediaPlayer.create(requireContext(), musicResource)
        mediaPlayer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release the media player resources
        mediaPlayer.release()
        timer.cancel()
    }
}
