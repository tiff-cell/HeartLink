package com.celly.swaggy.ui.theme.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.celly.heartlink.R
import com.celly.heartlink.navigation.ROUT_ABOUT
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_REGISTER
import com.celly.heartlink.viewmodel.AuthViewModel


// Define custom colors and font for a youthful and creative look
val Purple200 = Color(0xFFCE93D8) // A soft, light purple
fun getPurple500() = Color(0xFF673AB7) // A deeper, vibrant purple
fun getGrey700() = Color(0xFF616161) // A dark, sophisticated grey
fun getLightGray() = Color(0xFFF5F5F5) // A very light, clean grey


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onLoginSuccess: () -> Unit
){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Observe login logic
    LaunchedEffect(authViewModel) {
        authViewModel.loggedInUser = { user ->
            if (user == null) {
                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            } else {
                if (user.role == "Buyer") {
                    navController.navigate(ROUT_HOME) {
                    }
                } else {
                    navController.navigate(ROUT_ABOUT) {
                    }
                }
            }
        }
    }

    // Use a Scaffold for a consistent structure and a nice background
    Scaffold(
        containerColor = getLightGray(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Heartlink",
                        fontFamily =FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = getPurple500()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = getLightGray()
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Creative and unique app logo/illustration
            Image(
                painter = painterResource(id = R.drawable.app_logo), // Replace with your logo
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Username input field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", fontFamily =FontFamily.Cursive) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getPurple500(),
                    unfocusedBorderColor = getGrey700()
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password input field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", fontFamily = FontFamily.Cursive) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getPurple500(),
                    unfocusedBorderColor = getGrey700()
                ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Interactive login button with a unique style
            Button(
                onClick = { ROUT_HOME},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = getPurple500()),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    fontFamily =FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Register link for new users
            TextButton(onClick = { navController.navigate(ROUT_REGISTER) }) {
                Text(
                    text = "Don't have an account? Register",
                    fontFamily =FontFamily.Cursive,
                    color = getGrey700()
                )
            }
        }
    }
}