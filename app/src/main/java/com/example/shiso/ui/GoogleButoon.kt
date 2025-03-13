package com.example.shiso.ui
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

@Composable
fun GoogleLoginButton(context: Context, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    // Lấy Google Client ID từ google-services.json
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getClientIdFromGoogleServices(context))
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(Exception::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                handleFirebaseAuth(idToken, auth, onSuccess, onError)
            } else {
                Log.e("GoogleLogin", "ID Token is null")
                onError("Google Sign-In failed: ID Token is null")
            }
        } catch (e: Exception) {
            Log.e("GoogleLogin", "Google Sign-In error: ${e.localizedMessage}", e)
            onError(e.localizedMessage ?: "Google Sign-In failed")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Đăng nhập bằng Google", modifier = Modifier.padding(8.dp))
    }
}

private fun handleFirebaseAuth(idToken: String, auth: FirebaseAuth, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userId = user?.uid ?: ""
                onSuccess(userId)
                verifyIdTokenWithBackend(idToken) // Xác thực với backend sau khi đăng nhập Firebase thành công
            } else {
                Log.e("FirebaseAuth", "Auth failed: ${task.exception?.message}")
                onError(task.exception?.localizedMessage ?: "Firebase Authentication failed")
            }
        }
}

private fun verifyIdTokenWithBackend(idToken: String) {
    val client = OkHttpClient()
    val requestBody = """{"idToken": "$idToken"}""".toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("/auth/verify-token-google")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("GoogleLogin", "Failed to verify token with backend", e)
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    Log.e("GoogleLogin", "Backend verification failed: ${response.code}")
                } else {
                    val responseData = response.body?.string()
                    Log.d("GoogleLogin", "Backend Response: $responseData")
                }
            }
        }
    })
}

fun getClientIdFromGoogleServices(context: Context): String {
    val resId = context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
    return context.getString(resId)
}
