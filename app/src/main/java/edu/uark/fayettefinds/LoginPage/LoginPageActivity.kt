package edu.uark.fayettefinds.LoginPage

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
import javax.security.auth.callback.PasswordCallback

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

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

//        auth = Firebase.auth
        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            loginUser(etEmail.text.toString(), etPassword.text.toString())
        }
        findViewById<Button>(R.id.btnLogin).setOnClickListener{
            loginUser(etEmail.text.toString(), etPassword.text.toString())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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


    //******************************************************************************************************
    // onStart
    // Description: Called when the activity is starting
    // Parameters: None
    // Returns: Unit
    //******************************************************************************************************
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        auth.signOut()
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            val launchSecondActivityIntent = Intent(this,TaskListScreenActivity::class.java)
//            startActivity(launchSecondActivityIntent)
//            finish()
//        }
//    }


    //******************************************************************************************************
    // createUserWithUsernamePassword
    // Description: Creates a user with a username and password
    // Parameters: String, String
    // Returns: Unit
    //******************************************************************************************************
//    fun createUserWithUsernamePassword(email: String, password: String): Unit {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//
//                    if (user != null) {
//                        Toast.makeText(this,"User signed in!", Toast.LENGTH_LONG).show()
//                        Log.d(TAG,"User UUID:${user.uid}")
//
//                        val launchTaskListIntent = Intent(this, TaskListScreenActivity::class.java).apply {
//                            putExtra("USER_ID", user.uid)
//                            putExtra("USER_EMAIL", user.email)
//                        }
//                        startActivity(launchTaskListIntent)
//                        finish()
//                    }
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                }
//            }
//    }


    //******************************************************************************************************
    // logInWithUsernameAndPassword
    // Description: Logs in with a username and password
    // Parameters: String, String, FirebaseAuth
    // Returns: Unit
    //******************************************************************************************************
//    fun logInWithUsernameAndPassword(email: String, password: String, auth: FirebaseAuth): Unit {
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_LONG).show()
//            return
//        }
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) {task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithEmail:success")
//                    val user = auth.currentUser
//                    if (user != null) {
//                        Toast.makeText(this,"User signed in!", Toast.LENGTH_LONG).show()
//                        Log.d(TAG,"User UUID:${user.uid}")
//
//                        val launchTaskListIntent = Intent(this, TaskListScreenActivity::class.java).apply {
//                            putExtra("USER_ID", user.uid)
//                            putExtra("USER_EMAIL", user.email)
//                        }
//                        startActivity(launchTaskListIntent)
//                        finish()
//                    }
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                }
//            }
//    }
}