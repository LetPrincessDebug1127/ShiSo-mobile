package com.example.shiso.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shiso.R
import utils.LanguageUtils

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    var language by remember { mutableStateOf(LanguageUtils.getCurrentLanguage()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageHomeScreen1()
        Text(
            text = context.getString(R.string.homescreenLine1),
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF333333)
        )
        ImageHomeScreen2()
        Text(
            text = context.getString(R.string.homescreenLine2),
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF333333)
        )
        Text(
            text = context.getString(R.string.homescreenLine3),
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF333333)
        )
        Text(
            text = context.getString(R.string.homescreenLine4),
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF333333)
        )

        Button(onClick = { navController.navigate("CreateAccount") }) {
            Text(text = context.getString(R.string.createAccountButton))
        }

        Text(
            text = context.getString(R.string.alreadyHaveAccount),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF248A50),
            modifier = Modifier.clickable { navController.navigate("LogInAccount") }
        )

        GoogleLoginButton(
            onSuccess = { user ->
                println("Logged in successfully: $user")
                navController.navigate("CheckSkinScreen")
            },
            onError = { error ->
                println("Login failed: $error")
            }
        )

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "EN",
                modifier = Modifier.clickable {
                    language = "en"
                    LanguageUtils.setLocale(context, "en")
                }
            )
            Text(text = " | ", color = Color(0xFF248A50))
            Text(
                text = "VI",
                modifier = Modifier.clickable {
                    language = "vi"
                    LanguageUtils.setLocale(context, "vi")
                }
            )
        }

        Text(
            text = "Test check skin screen",
            modifier = Modifier.clickable { navController.navigate("CheckSkinScreen") }
        )
    }
}

@Composable
fun GoogleLoginButton(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    Button(onClick = { onSuccess("MockUser") }) {
        Text("Login with Google")
    }
}

@Composable
fun ImageHomeScreen1() {
    // Load your image here
}

@Composable
fun ImageHomeScreen2() {
    // Load your image here
}
