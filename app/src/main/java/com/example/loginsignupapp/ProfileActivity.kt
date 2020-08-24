package com.example.loginsignupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginsignupapp.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var emailId: TextView
    private lateinit var mobileNumber: TextView
    private lateinit var latitude: TextView
    private lateinit var longitude: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_profile)

        val bundle : Bundle? = intent.extras

        Log.d("Profile", "onCreate: ${bundle?.get("firstName")} ")

        firstName = findViewById(R.id.first_name_text_view)
        lastName = findViewById(R.id.last_name_text_view)
        emailId = findViewById(R.id.email_text_view)
        mobileNumber = findViewById(R.id.mobile_text_view)
        latitude = findViewById(R.id.latitude_text_view)
        longitude = findViewById(R.id.longitude_text_view)

        firstName.text = bundle?.get("firstName") as CharSequence?
        lastName.text = bundle?.get("lastName") as CharSequence?
        emailId.text = bundle?.get("email") as CharSequence?
        mobileNumber.text = bundle?.get("mobileNumber") as CharSequence?
        latitude.text = (bundle?.get("latitude") as Double?).toString()
        longitude.text = (bundle?.get("longitude") as Double?).toString()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}