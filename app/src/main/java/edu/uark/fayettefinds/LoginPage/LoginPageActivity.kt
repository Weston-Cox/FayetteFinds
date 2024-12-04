package edu.uark.fayettefinds.LoginPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.uark.fayettefinds.ListBusinessCards.ListBusinessCardsActivity
import edu.uark.fayettefinds.R

class LoginPageActivity : AppCompatActivity() {
    private val TAG = "LoginPageActivity"
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText


    //******************************************************************************************************
    // onCreate
    // Description: Called when the activity is being created
    // Parameters: Bundle
    // Returns: Unit
    //******************************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)

//        deleteDatabase(this)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            loginUser(etEmail.text.toString(), etPassword.text.toString())
        }
        findViewById<Button>(R.id.btnLogin).setOnClickListener{
            loginUser(etEmail.text.toString(), etPassword.text.toString())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_page)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun loginUser(email:String, password:String): Unit {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_LONG).show()
            return
        }

        Toast.makeText(this, "User signed in!", Toast.LENGTH_LONG).show()
        val launchTaskListIntent = Intent(this, ListBusinessCardsActivity::class.java)
        startActivity(launchTaskListIntent)
        finish()
    }

    fun deleteDatabase(context: Context): Unit {
        context.deleteDatabase("businesscard_database")
    }
}