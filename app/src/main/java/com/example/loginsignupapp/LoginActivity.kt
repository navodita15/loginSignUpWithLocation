package com.example.loginsignupapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginsignupapp.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var emailId: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        emailId = findViewById(R.id.login_email_edit_text)
        password = findViewById(R.id.login_password_edit_text)

        val loginButton: Button = findViewById(R.id.login_login_button)

        loginButton.setOnClickListener {

            userViewModel.getPassword(emailId.text.toString()).observe(this, Observer {
                if (it.password == password.text.toString()) {
                    val bundle: Bundle = Bundle()
                    Log.d("Profile", "onCreate: login == $it")
                    bundle.putString("firstName", it.firstName)
                    bundle.putString("lastName", it.lastName)
                    bundle.putString("email", it.emailId)
                    bundle.putString("mobileNumber", it.mobileNumber)
                    bundle.putDouble("latitude", it.latitude)
                    bundle.putDouble("longitude", it.longitude)
                    navigateToHomePage(bundle)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Wrong emailId / password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        }
    }

    private fun navigateToHomePage(bundle: Bundle) {
        //TODO: set flag to remove before activities
        val intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtras(bundle)
        Log.d("Profile", "login page : ${bundle?.get("firstName")} ")
        startActivity(intent)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}