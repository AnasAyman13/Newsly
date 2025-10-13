package com.news.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.news.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       auth= Firebase.auth
        binding= ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
            val email=binding.etEmail.text.toString()
            val pass=binding.etPassword.text.toString()
            if(email.isBlank()||pass.isBlank())
                Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show()
            else if(pass.length<6)
                Toast.makeText(this, "Short password ", Toast.LENGTH_SHORT).show()
            else{
                binding.loadingProgress.isVisible=true
                //login code
                login(email,pass)
            }
        }

        //Create a new Account
binding.tvSignUp.setOnClickListener {
    val i= Intent(this, Signup::class.java)
    startActivity(i)
    finish()
}
        //forget password
        binding.tvForgotPassword.setOnClickListener {
            binding.loadingProgress.isVisible=true
            val email=binding.etEmail.text.toString()
            Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email sent!", Toast.LENGTH_SHORT).show()
                        binding.loadingProgress.isVisible=false
                    }
                }
        }
    }

    fun login(email:String,pass:String){
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //will use in one time login depend on remember me check
                    val editor=getSharedPreferences("NewslyPrefs", Context.MODE_PRIVATE).edit()
                    editor.putBoolean("remember",binding.cbRememberMe.isChecked)
                    editor.apply()
                    Log.d("Trace Login", "signInWithEmail:success")
                    val user = auth.currentUser
                    binding.loadingProgress.isVisible=false
                    Toast.makeText(this, "Welcome,${user?.email}", Toast.LENGTH_SHORT).show()
                    if(user!!.isEmailVerified){
                    val i = Intent(this, MainActivity::class.java)
                    i.putExtra("user",user)
                    startActivity(i)
                    finish()
                    }else{
                        Toast.makeText(this, "Check ur email!", Toast.LENGTH_SHORT).show()
                        binding.loadingProgress.isVisible=false
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Trace Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT,
                    ).show()
                    binding.loadingProgress.isVisible=false

                }
            }
    }
//one time login logic check if the session still in firebase (user->not null)
    override fun onStart() {
        super.onStart()

        val pref=getSharedPreferences("NewslyPrefs", Context.MODE_PRIVATE)
        val remember=pref.getBoolean("remember",false)
        val currentUser=auth.currentUser
        if(remember&&currentUser!=null){
            binding.loginContainer.isVisible=false
            binding.loadingProgress.isVisible = true
            currentUser.reload()?.addOnCompleteListener {
                task->
                if(task.isSuccessful&&currentUser!=null){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{
                    auth.signOut()
                    binding.loginContainer.isVisible=true
                    binding.loadingProgress.isVisible = false
                }
            }

        }else{
            auth.signOut()
        }
    }
}