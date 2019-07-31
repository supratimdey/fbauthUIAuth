package com.example.firebaseuiauth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_hero_acticity.*

class HeroActicity : AppCompatActivity() {

    lateinit var  ref: DatabaseReference
    lateinit var  heroList: MutableList<Hero>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_acticity)

        heroList = mutableListOf()

        ref = FirebaseDatabase.getInstance().getReference("heros")

        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                 if(p0.exists()){
                     heroList.clear()
                     for (h in p0.children){
                         val hero = h.getValue(Hero::class.java)
                         heroList.add(hero!!)
                     }

                     val adapter = HeroAdapter(this@HeroActicity,R.layout.heros,heroList)

                     listview.adapter = adapter
                 }
            }//  onDataChanged

        })// ValueEventListener

    } // onCreate

    override fun onBackPressed() {
        super.onBackPressed()
        AuthUI.getInstance().signOut(this).addOnCompleteListener {
            if(it.isSuccessful){
                finish()
            }

        }

    }
}
