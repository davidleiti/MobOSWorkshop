package ro.david.mobosworkshop.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.SignInButton
import ro.david.mobosworkshop.R
import ro.david.mobosworkshop.data.AuthenticationManager

class SettingsFragment : Fragment() {

    private val authManager = AuthenticationManager()

    private lateinit var googleSignInButton: SignInButton
    private lateinit var authStatusText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authStatusText = view.findViewById(R.id.auth_status)
        googleSignInButton = view.findViewById(R.id.google_sign_in_button)
        googleSignInButton.setOnClickListener {
            authManager.signInWithGoogle(this) { firebaseUser ->
                firebaseUser?.let {
                    authStatusText.text = getString(R.string.signed_in_with_google, it.displayName)
                } ?: run {
                    Toast.makeText(
                        context,
                        "Failed to sign in with Google",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        if (authManager.isSignedIn()) {
            authStatusText.text = getString(R.string.user_sign_in_anonymously)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authManager.onActivityResult(requestCode, data)
    }
}