package com.andary.kifah.taxirequest


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()
{
    //firebase auth object
    private var firebaseAuth: FirebaseAuth? = null

    //progress dialog
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        //attaching click listener
        appCompatButtonLogin.setOnClickListener {
            userLogin()
        }
        textViewLinkRegister.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth?.getCurrentUser()
        if (currentUser != null) {
            //close this activity
            finish()
            //opening profile activity
            startActivity(Intent(applicationContext, MapsActivity::class.java))
        }
    }

    //method for user login
    private fun userLogin() {
        val email = textInputEditTextEmail?.toString()
        val password = textInputLayoutPassword?.toString()


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

        //logging in the user
        firebaseAuth?.signInWithEmailAndPassword(email.toString(), password.toString())
                ?.addOnCompleteListener(this) { task ->
                    progressDialog?.dismiss()
                    //if the task is successfull
                    if (task.isSuccessful) {
                        //start the profile activity
                        finish()
                        startActivity(Intent(applicationContext, MapsActivity::class.java))
                    }
                }
    }
}





