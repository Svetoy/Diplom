package com.technical.diplom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import kotlinSource.Extensions.Extensions.toast
import com.technical.diplom.databinding.ActivitySignInBinding
import kotlinSource.FirebaseUtils.FirebaseUtils.firebaseAuth
import kotlinSource.FirebaseUtils.FirebaseUtils.firebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        button.setOnClickListener{
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()

        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("welcome back")
        }
    }

    fun notEmpty(): Boolean =
        etEmail.text.toString().trim().isNotEmpty() && etPassword.text.toString().trim()
            .isNotEmpty()

    fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("email sent to your $email")
                }
            }
        }
    }

    fun signIn() {

        email = etEmail.text.toString().trim()
        password = etPassword.text.toString().trim()

        if (notEmpty()) {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("SignIn completed successfully!")
                        sendEmailVerification()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        toast("failed to Authenticate !")
                    }
                }
        }else{
            toast("Please zapolni polya")
        }
    }
}