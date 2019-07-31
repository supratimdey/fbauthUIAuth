package com.example.firebaseuiauth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_firestore.*

class FirestoreActivity : AppCompatActivity() {

    private var docRef: DocumentReference? = FirebaseFirestore.getInstance().document("sampledata/Quote")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)

        fetchFBData()

    }

    private fun fetchFBData() {
        docRef?.get()?.addOnSuccessListener {
           if ( it.exists() ) {
              tvQuote.text = it["quote"].toString()
           }
        }
    }
}
