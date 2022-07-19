package com.example.tp2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), MyAdapterInterface {

    private lateinit var livreRecyclerview: RecyclerView
    private lateinit var livreArrayList: ArrayList<Livre>

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.livre_list_activity)

        val db = Firebase.firestore

        livreRecyclerview = findViewById(R.id.livreList)
        livreRecyclerview.layoutManager = LinearLayoutManager(this)
        livreRecyclerview.setHasFixedSize(true)

        livreArrayList = arrayListOf()
        getLivreData(db)
    }

    private fun getLivreData(db: FirebaseFirestore) {
        db.collection("book")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    livreArrayList.add(
                        Livre(
                            document.id, document.get("author").toString(),
                            document.get("name").toString(),
                            document.get("resume").toString(),
                            document.get("path").toString(),
                            document.get("isRead") as Boolean,
                            document.get("date").toString()
                        )
                    )
                }
                livreRecyclerview.adapter = MyAdapter(livreArrayList,this)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    override fun onClickItem(id: String) {
        intent = Intent(this,LivrePage::class.java).apply {
            putExtra("id", id)
        }
        startActivity(intent)
    }
}
