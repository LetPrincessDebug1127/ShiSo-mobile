package com.example.shiso.ui

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
import com.example.shiso.R
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

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
            .fillMaxHeight(0.8f)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        Spacer(modifier = Modifier.height(screenHeight * 0.1f))
        ImageHomeScreen1()
        Text(
            text = strings["homescreenLine1"] ?: "",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF000000)
        )
        Spacer(modifier = Modifier.height(30.dp))

        ImageHomeScreen2()
        Text(
            text = buildAnnotatedString {
                append(strings["homescreenLine2"] ?: "")
                append(" ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(strings["homescreenLine3"] ?: "")
                }
                append(" ")
                append(strings["homescreenLine4"] ?: "")
            },
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color(0xFF000000)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .clickable { /* Xử lý sự kiện click*/ }
        ) {
            BasicText(
                text = AnnotatedString(strings["loginWithGmail"] ?: ""),
                style = TextStyle(color = Color(0xFF248A50), fontSize = 20.sp, fontWeight = FontWeight.Bold,
                ),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "EN",
                color = Color(0xFF248A50),
                modifier = Modifier.clickable {
                    language = "en"
                    LanguageUtils.setLocale(context, "en")
                }
            )
            Text(text = " | ", color = Color(0xFF248A50))
            Text(
                text = "VI",
                color = Color(0xFF248A50),
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
        contentScale = ContentScale.Fit, // Giữ nguyên kích thước ảnh
        modifier = Modifier
            .fillMaxWidth()   // Chiếm toàn bộ chiều ngang
            .heightIn(min = 250.dp) // Đặt chiều cao tối thiểu
    )

}

@Composable
fun ImageHomeScreen2() {
    Image(
        painter = painterResource(id = R.drawable.homescreen2),
        contentDescription = "Shiso Homescreen",
        contentScale = ContentScale.Fit, // Giữ nguyên kích thước ảnh
        modifier = Modifier
            .fillMaxWidth()   // Chiếm toàn bộ chiều ngang
            .heightIn(min = 250.dp) // Đặt chiều cao tối thiểu
    )
}


// Để sau này làm, giờ lười
//        Button(
//            onClick = { navController.navigate("CreateAccount") },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF248A50))
//        ) {
//            Text(text = strings["createAccountButton"] ?: "")
//        }
//
//
//
//        Text(
//            text = strings["alreadyHaveAccount"] ?: "",
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp,
//            color = Color(0xFF000000),
//            modifier = Modifier.clickable { navController.navigate("LogInAccount") }
//        )