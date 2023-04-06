package com.example.sellon.ui.login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.sellon.BaseActivity
import com.example.sellon.MainActivity
import com.example.sellon.R
import com.example.sellon.databinding.ActivityLoginBinding
import com.example.sellon.utilities.Constants
import com.example.sellon.utilities.SharedPref
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity: BaseActivity(), View.OnClickListener {

    private var callbackManager: CallbackManager? = null
    private lateinit var auth: FirebaseAuth
    private var RC_SIGN_IN = 100
    private val TAG = LoginActivity::class.java.simpleName
    private var googleSignInOptions: GoogleSignInOptions? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private lateinit var binding: ActivityLoginBinding

    private val EMAIL = "email"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FacebookSdk.sdkInitialize(getApplicationContext())
        callbackManager = CallbackManager.Factory.create();
        auth = FirebaseAuth.getInstance()
        binding.loginButton.setReadPermissions(Arrays.asList(EMAIL));

        clickListeners()
        configureGoogleSignin()
        registerFbCallBack()


    }
    private fun clickListeners() {
        binding.btnFb.setOnClickListener(this)
        binding.btnGoogle.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_fb -> {
                binding.loginButton.performClick()
            }
            R.id.btn_google -> {
                googleSignIn()
            }
        }
    }

    private fun registerFbCallBack() {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult?) {
                    handleFacebookAccess(loginResult?.accessToken)
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(error: FacebookException) {


                }
            })
    }

    private fun handleFacebookAccess(accessToken: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(accessToken?.token!!)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val account = auth.currentUser

                    SharedPref(this).setString(Constants.USER_ID, account?.uid!!)

                    if (account.email != null)
                        SharedPref(this).setString(Constants.USER_EMAIL, account.email!!)

                    if (account.displayName != null)
                        SharedPref(this).setString(Constants.USER_NAME, account.displayName!!)

                    if (account.photoUrl != null)
                        SharedPref(this).setString(Constants.USER_PHOTO, account.photoUrl.toString()!!)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }




    private fun googleSignIn() {

        val signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun configureGoogleSignin() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions!!)

    }

    @Deprecated("Deprecated in Java")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...):

        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Google Sign In was successful, authenticate with Firebase val account = task.getResult (ApiException::class.java)
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {

                // Google Sign In failed, update UI appropriately

                Log.w(TAG, "Google SignIn failed", e)
            }

        } else {
            callbackManager?.onActivityResult(requestCode, resultCode, data);
        }
    }


    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credentials).addOnCompleteListener {
            if (it.isSuccessful) {

                if (account.email != null)
                    SharedPref(this).setString(Constants.USER_EMAIL, account.email!!)

                if (account.email != null)
                    SharedPref(this).setString(Constants.USER_ID, account.id!!)

                if (account.displayName != null)
                    SharedPref(this).setString(Constants.USER_NAME, account.displayName!!)

                if (account.photoUrl != null)
                    SharedPref(this).setString(Constants.USER_PHOTO, account.photoUrl.toString())

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Google SignIn failed", Toast.LENGTH_LONG).show()
            }
        }
    }


}