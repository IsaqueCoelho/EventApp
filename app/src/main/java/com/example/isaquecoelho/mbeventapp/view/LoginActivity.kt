package com.example.isaquecoelho.mbeventapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.isaquecoelho.mbeventapp.R
import com.example.isaquecoelho.mbeventapp.utils.StringUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        listenerViews()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if(currentUser != null){
            goToCatalog()
        }

    }

    companion object {
        fun newInstance(context: Context?): Intent{
            return Intent(context, LoginActivity::class.java)
        }
    }

    private fun listenerViews() {
        button_login.setOnClickListener {
            loadingData(true)
            if(!isFomrValid()){
                Toast.makeText(this, getString(R.string.error_toast_invalid_form), Toast.LENGTH_LONG).show()
                loadingData(false)
            } else {
                getAuthorization()
            }
        }
    }

    private fun getAuthorization() {
        val stringUtils = StringUtils()

        val passwordHash: String = stringUtils.passwordHash(textinputedittext_password.text.toString())

        auth.signInWithEmailAndPassword(textinputedittext_email.text.toString(), passwordHash)
            .addOnCompleteListener(this) {task ->  
                if(task.isSuccessful){
                    goToCatalog()
                } else {
                    Toast.makeText(this, getString(R.string.error_toast_invalid_login), Toast.LENGTH_LONG).show()
                }
                loadingData(false)
            }
    }

    private fun isFomrValid(): Boolean {

        if (textinputedittext_email.text.isNullOrBlank() || textinputedittext_email.text!!.length < 5){
            textinputedittext_email.error = getString(R.string.error_textinputedittext_email_invalid)
            return false
        }

        if (textinputedittext_password.text.isNullOrBlank() || textinputedittext_password.text!!.length < 4){
            textinputedittext_password.error = getString(R.string.error_textinputedittext_password_invalid)
            return false
        }

        return true
    }

    private fun goToCatalog(){
        val mainIntent = MainActivity.newIntent(this)
        mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainIntent)
    }

    private fun loadingData(status: Boolean){
        progressbar_loading.visibility = if ( status ) View.VISIBLE else View.GONE

        textinputedittext_email.isEnabled = !status
        textinputedittext_password.isEnabled = !status
        button_login.isEnabled = !status
    }
}
