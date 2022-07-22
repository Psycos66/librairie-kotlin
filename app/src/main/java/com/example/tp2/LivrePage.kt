package com.example.tp2

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class LivrePage : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.livre_page)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val isRead = findViewById<CheckBox>(R.id.is_read)
        val title = findViewById<TextView>(R.id.title)
        val date = findViewById<TextView>(R.id.date)
        val resume = findViewById<TextView>(R.id.resume_content)
        val image = findViewById<ImageView>(R.id.imageBooks)

        val db = Firebase.firestore

        db.collection("book")
            .document(intent.getStringExtra("id").toString())
            .get().addOnCompleteListener { result ->
                val livre = result.result
                if (livre.get("isRead") as Boolean) {
                    isRead.isChecked = true
                }
                title.text = "Titre de l'oeuvre : " + livre.get("name").toString()
                date.text = "Date de parution : " + livre.get("date").toString()
                resume.text = livre.get("resume").toString()
                setTitle(livre.get("name").toString())
                Picasso.get().load(livre.get("path").toString()).into(image)
            }.addOnFailureListener {
                Log.e("Error : ", it.toString())
            }

        isRead.setOnCheckedChangeListener { _, b ->
            db.collection("book").document(intent.getStringExtra("id").toString())
                .update("isRead",b)
        }
        resume.movementMethod = ScrollingMovementMethod()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}