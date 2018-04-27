package com.andary.kifah.taxirequest


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.junit.experimental.results.ResultMatchers.isSuccessful
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.text.TextUtils
import com.andary.kifah.taxirequest.R.id.message


class RegisterActivity: AppCompatActivity(){

    private var firebaseAuth: FirebaseAuth? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth?.getCurrentUser()
        if (currentUser !=null)
        {
            finish()
            startActivity(Intent(applicationContext, MapsActivity::class.java))

        }
    }

    private fun registerUser() {

        //getting email and password from edit texts
        val email = textInputEditTextEmail.toString().trim()
        val password = textInputEditTextPassword.toString().trim()

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
            return
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog?.setMessage("Registering Please Wait...")
        progressDialog?.show()

        //creating a new user
        firebaseAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    //checking if success
                    if (task.isSuccessful) {
                        finish()
                        startActivity(Intent(applicationContext, MapsActivity::class.java))
                    } else {
                        //display some message here
                        Toast.makeText(this@RegisterActivity, "Registration Error", Toast.LENGTH_LONG).show()
                    }
                    progressDialog?.dismiss()
                })

    }
}