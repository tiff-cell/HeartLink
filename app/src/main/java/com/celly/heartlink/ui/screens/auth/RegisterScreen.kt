package com.celly.swaggy.ui.theme.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.model.User
import com.celly.heartlink.navigation.ROUT_LOGIN
import com.celly.heartlink.viewmodel.AuthViewModel
import com.celly.heartlink.R

// Re-using the same theme colors and font from the login screen
val Purple500 = Color(0xFF673AB7) // A deeper, vibrant purple
val Grey700 = Color(0xFF616161) // A dark, sophisticated grey
val LightGray = Color(0xFFF5F5F5) // A very light, clean grey


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf("Buyer") }
    val roleOptions = listOf("Buyer", "Seller")
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    "Create Your Account",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    color = getGrey700()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", fontFamily = FontFamily.Cursive) },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Username Icon", tint = getGrey700()) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getPurple500(),
                    unfocusedBorderColor = getGrey700()
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", fontFamily = FontFamily.Cursive) },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon", tint = getGrey700()) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getPurple500(),
                    unfocusedBorderColor = getGrey700()
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Role
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = role,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Role", fontFamily = FontFamily.Cursive) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getPurple500(),
                        unfocusedBorderColor = getGrey700()
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roleOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, fontFamily = FontFamily.Cursive, color = getGrey700()) },
                            onClick = {
                                role = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input Field with Show/Hide Toggle
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", fontFamily = FontFamily.Cursive) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon", tint = getGrey700()) },
                trailingIcon = {
                    val image = if (passwordVisible) painterResource(R.drawable.passwordshow) else painterResource(R.drawable.passwordhide)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, contentDescription = if (passwordVisible) "Hide Password" else "Show Password", tint = getGrey700())
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getPurple500(),
                    unfocusedBorderColor = getGrey700()
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Input Field with Show/Hide Toggle
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", fontFamily =FontFamily.Cursive) },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password Icon", tint = getGrey700()) },
                trailingIcon = {
                    val image = if (confirmPasswordVisible) painterResource(R.drawable.passwordshow) else painterResource(R.drawable.passwordhide)
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(image, contentDescription = if (confirmPasswordVisible) "Hide Password" else "Show Password", tint = getGrey700())
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getPurple500(),
                    unfocusedBorderColor = getGrey700()
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Register Button
            Button(
                onClick = {
                    if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.registerUser(User(username = username, email = email, role = role, password = password))
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = getPurple500()),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Register",
                    fontSize = 18.sp,
                    fontFamily =FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate(ROUT_LOGIN) }
            ) {
                Text(
                    "Already have an account? Sign in!",
                    fontFamily =FontFamily.Cursive,
                    color = getGrey700()
                )
            }
        }
    }
}