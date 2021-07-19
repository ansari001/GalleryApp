package com.android.galleryapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.galleryapp.R
import com.android.galleryapp.listeners.OnDialogGenericListener
import com.android.galleryapp.utils.AppUtils
import com.android.galleryapp.utils.DialogUtils
import com.eid.h2hospital.ui.data.PreferenceHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.toolbar.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "SingInActivity"
    private val RC_SIGN_IN = 9001
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        setUI()
        initGoogleClient()
        setListeners()
    }

    private fun setUI() {
        ivBack.visibility = View.GONE
        ivProfile.visibility=View.GONE
        tvToolbarTitle.text = getString(R.string.sign_in)
    }

    private fun setListeners() {
        llGoogleSignIn.setOnClickListener(this)
    }

    private fun initGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            updatePreference(currentUser)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth!!.currentUser
                    if (user != null) {
                        runOnUiThread {
                            updatePreference(user)
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    runOnUiThread {
                        updatePreference(null)
                    }
                }
            }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account!!.id)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun updatePreference(user: FirebaseUser?) {
        PreferenceHelper.getInstance()
            ?.setString(this, PreferenceHelper.PROFILE_URL, user?.photoUrl.toString())
        PreferenceHelper.getInstance()
            ?.setString(this, PreferenceHelper.DISPLAY_NAME, user?.displayName.toString())
        PreferenceHelper.getInstance()
            ?.setString(this, PreferenceHelper.EMAIL, user?.email.toString())
        startActivity(Intent(this, GalleryActivity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llGoogleSignIn -> {
                if (AppUtils.isNetworkAvailable(this)) {
                    signIn()
                } else {
                    DialogUtils.showConfirmationDialog(
                        this,
                        getString(R.string.internet_not_available),
                        getString(R.string.okay),
                        "",
                        false,
                        object : OnDialogGenericListener {
                            override fun onPositiveClick() {

                            }

                            override fun onNegativeClick() {

                            }
                        })
                }
            }
        }
    }
}