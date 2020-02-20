package ro.david.mobosworkshop.data

import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import ro.david.mobosworkshop.R
import ro.david.mobosworkshop.ui.home.CreateItemFragment.Companion.TAG

class AuthenticationManager {

    private val auth = FirebaseAuth.getInstance()
    private var onSignInResult: ((FirebaseUser?) -> Unit)? = null

    fun isSignedIn() = auth.currentUser != null

    fun getUser() = auth.currentUser

    fun signInAnonymously(onSignInResult: (user: FirebaseUser?) -> Unit) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInAnonymously:success")
                val user = auth.currentUser
                onSignInResult(user)
            } else {
                Log.w(TAG, "signInAnonymously:failure", task.exception)
                onSignInResult(null)
            }
        }
    }

    fun signInWithGoogle(fragment: Fragment, onSignInResult: (FirebaseUser?) -> Unit) {
        this.onSignInResult = onSignInResult

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(fragment.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        GoogleSignIn.getClient(fragment.requireContext(), gso).let { client ->
            fragment.startActivityForResult(client.signInIntent, RC_SIGN_IN)
        }
    }

    fun onActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id!!)

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    onSignInResult?.invoke(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    onSignInResult?.invoke(null)
                }
            }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }

}