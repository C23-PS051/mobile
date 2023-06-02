package com.dicoding.c23ps051.caferecommenderapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AuthViewModel : ViewModel() {

//    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun initGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

//    fun signInWithEmail(email: String, password: String) {
//
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val user = task.result?.user
//                    _signInResult.value = Result.success(user)
//                } else {
//                    val exception = task.exception
//                    _signInResult.value = Result.failure(exception!!)
//                }
//            }
//    }
}