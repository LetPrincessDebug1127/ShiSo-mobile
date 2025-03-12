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
import utils.LanguageUtils
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shiso.R

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    var language by remember { mutableStateOf(LanguageUtils.getCurrentLanguage()) }

    val strings = remember(language) {
        mapOf(
            "homescreenLine1" to context.getString(R.string.homescreenLine1),
            "homescreenLine2" to context.getString(R.string.homescreenLine2),
            "homescreenLine3" to context.getString(R.string.homescreenLine3),
            "homescreenLine4" to context.getString(R.string.homescreenLine4),
            "createAccountButton" to context.getString(R.string.createAccountButton),
            "alreadyHaveAccount" to context.getString(R.string.alreadyHaveAccount),
            "loginWithGmail" to context.getString(R.string.loginWithGmail)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF9ED))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageHomeScreen1()
        Text(
            text = strings["homescreenLine1"] ?: "",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF000000)
        )
        ImageHomeScreen2()
        Text(
            text = strings["homescreenLine2"] ?: "",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF000000)
        )
        Text(
            text = strings["homescreenLine3"] ?: "",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF000000)
        )
        Text(
            text = strings["homescreenLine4"] ?: "",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF000000)
        )

        Button(onClick = { navController.navigate("CreateAccount") }) {
            Text(text = strings["createAccountButton"] ?: "")
        }

        Text(
            text = strings["alreadyHaveAccount"] ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF000000),
            modifier = Modifier.clickable { navController.navigate("LogInAccount") }
        )

        GoogleLoginButton(
            buttonText = strings["loginWithGmail"] ?: "",
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
fun GoogleLoginButton(buttonText: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    Button(onClick = { onSuccess("MockUser") }) {
        Text(buttonText)
    }
}


@Composable
fun ImageHomeScreen1() {
    Image(
        painter = painterResource(id = R.drawable.homesreen1),
        contentDescription = "Shiso Homescreen",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun ImageHomeScreen2() {
    Image(
        painter = painterResource(id = R.drawable.homescreen2),
        contentDescription = "Shiso Homescreen",
        modifier = Modifier.size(200.dp)
    )
}