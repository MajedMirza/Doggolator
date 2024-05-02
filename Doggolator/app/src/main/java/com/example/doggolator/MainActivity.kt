package com.example.doggolator

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@Suppress("UNUSED_PARAMETER")
class MainActivity : AppCompatActivity() {

    private lateinit var items: TextView
    private val selectedBreeds = mutableListOf<String>()
    private lateinit var showPopupInstruct : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        items = findViewById(R.id.selectedItems)
        showPopupInstruct = findViewById(R.id.instructButton)
        showPopupInstruct.setOnClickListener{
            showPopup()
        }
    }

    @SuppressLint("InflateParams")
    private fun showPopup(){
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup, null)

        val width = 950
        val height = 1000

        val instructWindow = PopupWindow(popupView, width, height, true)
        instructWindow.showAtLocation(popupView,Gravity.BOTTOM, 0,700)

        val closeButton = popupView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener{
            instructWindow.dismiss()
        }
    }

    fun onDogButtonClick(view: View) {
        val selectedButton = view as ImageButton

        // Reset background color of all image buttons
        val dogButtons = listOf(R.id.dog1, R.id.dog2, R.id.dog3, R.id.dog4)
        dogButtons.forEach { buttonId ->
            val button = findViewById<ImageButton>(buttonId)
            if (button != selectedButton) {
                button?.apply {
                    setBackgroundColor(Color.TRANSPARENT)
                    alpha = 1.0f // Reset alpha to fully opaque for non-selected buttons
                }
            }
        }

        // Set background color and adjust transparency of the clicked image button to indicate selection
        selectedButton.setBackgroundColor(Color.TRANSPARENT) // Remove any background color
        selectedButton.alpha = 0.5f // Set alpha to change transparency level

        val breed = when (selectedButton.id) {
            R.id.dog1 -> "Dachshund"
            R.id.dog2 -> "German Shepherd"
            R.id.dog3 -> "Golden Retriever"
            R.id.dog4 -> "Shiba Inu"
            else -> ""
        }

        if (!selectedBreeds.contains(breed)) {
            selectedBreeds.add(breed)
        }

        items.text = selectedBreeds.joinToString(", ")
    }


    @SuppressLint("SetTextI18n")
    fun onGetBreedButtonClick(view: View) {
        Log.d("MainActivity", "onGetBreedButtonClick called")

        // Reset background color and transparency of all image buttons
        val dogButtons = listOf(R.id.dog1, R.id.dog2, R.id.dog3, R.id.dog4)
        dogButtons.forEach { findViewById<ImageButton>(it).apply {
            setBackgroundColor(Color.TRANSPARENT)
            alpha = if (selectedBreeds.contains(tag)) 0.5f else 1.0f
        } }

        // Check if exactly two specific dog breeds are selected
        if (selectedBreeds.size != 2) {
            items.text = "Please select exactly two dog breeds."

            // Clear the selected breeds
            selectedBreeds.clear()

            // Hide the ImageView
            val imageView = findViewById<ImageView>(R.id.invivi)
            imageView.visibility = View.GONE
            return
        }

        // Prepare the message based on the comparison
        val message: String
        val imageResource: Int? // To store image resource ID

        when {
            (selectedBreeds.contains("Dachshund") && selectedBreeds.contains("German Shepherd")) -> {
                message = "The combination of Dachshund & German Shepard creates Dachshund Shepherd!"
                imageResource = R.drawable.specbreed4 // Set image resource for Dachshund Shepherd
            }
            (selectedBreeds.contains("Dachshund") && selectedBreeds.contains("Golden Retriever")) -> {
                message = "The combination of Dachshund & Golden Retriever equals the specific breed Golden Doxs!"
                imageResource = R.drawable.specbreed1 // Set image resource for Golden Doxs
            }
            (selectedBreeds.contains("Dachshund") && selectedBreeds.contains("Shiba Inu")) -> {
                message = "The combination of Dachshund & Shiba Inu equals the specific breed Shibadox!"
                imageResource = R.drawable.specbreed6 // Set image resource for Shiba Inu
            }
            (selectedBreeds.contains("Golden Retriever") && selectedBreeds.contains("German Shepherd")) -> {
                message = "The combination of Golden Retriever & German Shepherd equals the specific breed The Golden Shepherd!"
                imageResource = R.drawable.specbreed2 // Set image resource for The Golden Shepherd
            }
            (selectedBreeds.contains("Golden Retriever") && selectedBreeds.contains("Shiba Inu")) -> {
                message = "The combination of Golden Retriever & Shiba Inu equals the specific breed Golden Shiba!"
                imageResource = R.drawable.specbreed3 // Set image resource for Golden Shiba
            }
            (selectedBreeds.contains("German Shepherd") && selectedBreeds.contains("Shiba Inu")) -> {
                message = "The combination of German Shepherd & Shiba Inu equals the specific breed Shepherd Inus!"
                imageResource = R.drawable.specbreed5 // Set image resource for Shepherd Inus
            }
            // Add cases for other specific breeds here...
            else -> {
                message = "Selected breeds combination does not match any specific breed."
                imageResource = null // No specific image for this case
            }
        }

        // Set the message to the textview
        items.text = message

        // Set the image if there's a corresponding image resource
        if (imageResource != null) {
            // Find the ImageView in your layout
            val imageView = findViewById<ImageView>(R.id.invivi)
            // Set the image resource
            imageView.setImageResource(imageResource)
            // Make the image view visible
            imageView.visibility = View.VISIBLE
        } else {
            // If there's no corresponding image resource, hide the image view
            val imageView = findViewById<ImageView>(R.id.invivi)
            imageView.visibility = View.GONE
        }
    }


    fun onClearButtonClick(view: View) {
        // Clear selected breeds and text in TextView
        selectedBreeds.clear()
        items.text = ""

        // Reset background color and transparency of all image buttons
        val dogButtons = listOf(R.id.dog1, R.id.dog2, R.id.dog3, R.id.dog4)
        dogButtons.forEach { findViewById<ImageButton>(it).apply {
            setBackgroundColor(Color.TRANSPARENT)
            alpha = 1.0f // Reset alpha to fully opaque
        } }

        // Hide the ImageView if it's not null
        val imageView = findViewById<ImageView>(R.id.invivi)
        imageView?.visibility = View.INVISIBLE
    }








}
