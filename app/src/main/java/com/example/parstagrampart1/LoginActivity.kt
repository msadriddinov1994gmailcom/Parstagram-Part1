package com.example.parstagrampart1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.signIn).setOnClickListener{
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.signUp).setOnClickListener{
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            signUpUser(username, password)
        }
        findViewById<Button>(R.id.signOut).setOnClickListener(){
            ParseUser.logOut()
            val currentUser = ParseUser.getCurrentUser()
            Toast.makeText(this, "Successfully signed out.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Toast.makeText(this, "Login successfully.", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Login successfully.")
                goToMainActivity()

            } else {
                e.printStackTrace()
                Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Login failed.")
            }})
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun signUpUser(username: String, password: String) {
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                Toast.makeText(this, "Sign up successfully.", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Sign up successfully.")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Sign up failed.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Sign up failed. $e")
            }
        }
    }
companion object{
    val TAG = "LoginActivity"
}

}