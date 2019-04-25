package com.example.isaquecoelho.mbeventapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.isaquecoelho.mbeventapp.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var userAuth: FirebaseAuth

    private val LOG_TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    CatalogFragment.newInstance()
                )
                .commitNow()
        }

        userAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        MainActivity.validateConnection(this)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

        fun validateConnection(context: Context?) {
            if(FirebaseAuth.getInstance().currentUser == null){
                goToLogin(context)
            }
        }

        private fun goToLogin(context: Context?) {
            val loginIntent = LoginActivity.newInstance(context)
            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(loginIntent)
        }
    }

}
