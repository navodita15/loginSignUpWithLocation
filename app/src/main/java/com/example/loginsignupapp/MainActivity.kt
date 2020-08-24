package com.example.loginsignupapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginsignupapp.db.User
import com.example.loginsignupapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
        private const val PERMISSION_REQUEST = 200

    }

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var emailId: EditText
    private lateinit var password: EditText
    private lateinit var mobileNumber: EditText

    private lateinit var userViewModel: UserViewModel

    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disableView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)
            ) {
                enableView()
            } else {
                requestPermissions(
                    permissions,
                    PERMISSION_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {
            enableView()
        }

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        initialiseViews()

        val signUButton: Button = findViewById(R.id.sign_up_button)
        val loginButton: Button = findViewById(R.id.login_button)

        signUButton.setOnClickListener {

            val firstNameText = firstName.text.toString()
            val lastNameText = lastName.text.toString()
            val emailIdText = emailId.text.toString()
            val passwordText = password.text.toString()
            val mobileNumberText = mobileNumber.text.toString()

            val user: User = User(
                firstNameText,
                lastNameText,
                emailIdText,
                passwordText,
                mobileNumberText,
                longitude,
                latitude
            )
            val bundle: Bundle = Bundle()
            bundle.putString("firstName", firstNameText)
            bundle.putString("lastName", lastNameText)
            bundle.putString("email", emailIdText)
            bundle.putString("mobileNumber", mobileNumberText)
            bundle.putDouble("latitude", latitude)
            bundle.putDouble("longitude", longitude)
            userViewModel.insert(user)
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtras(bundle)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            userViewModel.allUsers.observe(this, Observer {
                Log.d(TAG, "onCreate: $it")
            })

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun enableView() {
        getCurrentLocation(applicationContext)
        sign_up_button.isEnabled = true
        sign_up_button.isClickable = true
        login_button.isEnabled = true
        login_button.isClickable = true

    }

    private fun disableView() {
        sign_up_button.isEnabled = false
        sign_up_button.isClickable = false
        login_button.isEnabled = false
        login_button.isClickable = false
    }


    private fun initialiseViews() {
        firstName = findViewById(R.id.first_name_edit_text)
        lastName = findViewById(R.id.last_name_edit_text)
        emailId = findViewById(R.id.email_edit_text)
        password = findViewById(R.id.password_edit_text)
        mobileNumber = findViewById(R.id.mobile_edit_text)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Go to settings and enable the permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            if (allSuccess)
                getCurrentLocation(applicationContext)
            enableView()

        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }


}
