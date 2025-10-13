package com.news.app


import User
import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.news.app.databinding.ActivitySignupBinding
//import com.news.app.User
class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivitySignupBinding.inflate(layoutInflater)
        // Initialize Firebase Auth
        auth = Firebase.auth
        //initalize firestore
//        firestore = FirebaseFirestore.getInstance()

        setContentView(binding.root)
        //already have an account
        binding.tvLogin.setOnClickListener {
            val i= Intent(this,LoginActivity::class.java)
            startActivity(i)
            finish()
        }
        //signup button
        binding.btnSignUp.setOnClickListener {
            val email=binding.etEmail.text.toString()
            val pass=binding.etPassword.text.toString()
            val confPass=binding.etConfirmPassword.text.toString()
            val checkPolicy=binding.cbAgree
            if (email.isBlank() || pass.isBlank() || confPass.isBlank()) {
                Toast.makeText(
                    this,
                    "Missing fields, plz fill all the Required fields!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!checkPolicy.isChecked) {
                Toast.makeText(
                    this,
                    "Make sure you have agreed for the polices and checked it!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (pass != confPass) {
                Toast.makeText(
                    this,
                    "The confirm password doesnt match the password,Plz match them!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.loadingProgress.isVisible=true
                signUpUser(email, pass)
            }
        }
        //user have verified and clicked on i have verified
        binding.btnCheckVerification.setOnClickListener {
            val user = Firebase.auth.currentUser
            if (user == null) {
                Toast.makeText(this, "User no longer Exsist, please try again", Toast.LENGTH_SHORT).show()
                binding.btnCheckVerification.isVisible = false
                return@setOnClickListener
            }
            user?.reload()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (user.isEmailVerified) {
                        Toast.makeText(this, "Email verified successfully!", Toast.LENGTH_SHORT).show()

                        Firebase.auth.signOut()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Email not verified yet. Please check again.", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(this, "Faild to load user,try again", Toast.LENGTH_SHORT).show()
                    binding.btnCheckVerification.isVisible = false
                }
            }
        }


    }
    //create a new user
    fun signUpUser(email:String,password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d("Trace signUp", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Welcome ${user?.email}, please verify your email!", Toast.LENGTH_SHORT).show()
                    //save the user in firestore
                    val userID=user!!.uid
                    val u =User(userID,email,password)
                    Log.d("FirestoreDebug", "About to save user with ID: $userID and email: $email")
                    Firebase.firestore.collection("Users")
                        .document(userID)
                        .set(u)
                        .addOnSuccessListener {
                            Log.d("FirestoreDebug", "User added successfully to Firestore")
                        }

                        .addOnFailureListener { e ->
                            Log.e("FirestoreDebug", "Failed to save user: ${e.message}", e)
                        }

                    verifyEmail()
                } else {
                    Log.w("Trace signUp", "createUserWithEmail:failure", task.exception)
                    binding.loadingProgress.isVisible=false
                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    //send email with verification
    private fun verifyEmail() {
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "check ur email", Toast.LENGTH_SHORT).show()
                    binding.loadingProgress.isVisible = false
                    binding.btnCheckVerification.isVisible = true
                }
            }
    }

}