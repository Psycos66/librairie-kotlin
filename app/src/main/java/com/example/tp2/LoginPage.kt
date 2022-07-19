package com.example.tp2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val buttonConnected = findViewById<Button>(R.id.buttonConnect)
        val editTextEmailAddress = findViewById<EditText>(R.id.editTextEmailAddress)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val email = editTextEmailAddress.text
        val password = editTextPassword.text

        auth = FirebaseAuth.getInstance()

        buttonConnected.setOnClickListener {
            var isBothBlank = false
            if (email.isNotBlank() && password.isNotBlank()) {
                login(email.toString(), password.toString())
            }
            if (email.isBlank() && password.isBlank()) {
                isBothBlank = true
                CustomAlert("L'email et le mot de passe ne sont pas renseigné")
            }
            if (!isBothBlank && email.isBlank()) {
                CustomAlert("L'email n'est pas renseigné")
            }
            if (!isBothBlank && password.isBlank()) {
                CustomAlert("Le mot de passe n'est pas renseigné")
            }
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Mauvais login ou mot de passe.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun CustomAlert(message: String) {
        AlertDialog.Builder(this).create().run {
            setMessage(message)
            setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ -> }
            show()
        }
    }
}