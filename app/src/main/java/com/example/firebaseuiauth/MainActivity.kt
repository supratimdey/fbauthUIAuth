package com.example.firebaseuiauth

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private  lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())

    val RC_SIGN_IN: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        this.mAuthStateListener = FirebaseAuth.AuthStateListener{

            val user = it.currentUser

            if(user != null ) {
                Toast.makeText(this, "User logged in", Toast.LENGTH_LONG).show();

             //   val heroIntent = Intent(this,HeroActicity::class.java)

             //   startActivity(heroIntent)

                val fireStoreIntent = Intent(this,FirestoreActivity::class.java)

                startActivity(fireStoreIntent)


            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_LONG).show();
            }
        }

        signInBtn.setOnClickListener{
            signIn()
        }
    }

    private fun signIn() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN)
        {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK)
                {
                    val user = FirebaseAuth.getInstance().currentUser
                    displayTxt.text = user?.email ?: " NO Email"
                    signOutBtn.visibility = View.VISIBLE

                    val fireStoreIntent = Intent(this,FirestoreActivity::class.java)

                    startActivity(fireStoreIntent)


                    signOutBtn.setOnClickListener{
                    AuthUI.getInstance().signOut(this).addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            signOutBtn.visibility = View.INVISIBLE
                            displayTxt.text = ""
                        }
                    }
                } //  signoutbuttononclick listener
            }
        }
    } // onactivityresult

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener (mAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthStateListener)
    }
}
