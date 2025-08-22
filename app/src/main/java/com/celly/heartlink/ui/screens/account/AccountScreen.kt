package com.celly.heartlink.ui.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_JOURNAL
import com.celly.heartlink.navigation.ROUT_MOODTRACKER
import com.celly.heartlink.navigation.ROUT_RESOURCES
import com.celly.heartlink.navigation.ROUT_SETTINGS
import androidx.lifecycle.viewmodel.compose.viewModel
// Assuming you have a UserViewModel
// import com.celly.heartlink.data.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    // Add a ViewModel to interact with the database
    // userViewModel: UserViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Account",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUT_SETTINGS) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back to Settings",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF673AB7)
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF673AB7)
            ) {
                // Home Icon
                IconButton(
                    onClick = { navController.navigate(ROUT_HOME) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color.White
                        )
                        Text(text = "Home", fontSize = 10.sp, color = Color.White)
                    }
                }
                // Check-in Icon
                IconButton(
                    onClick = { navController.navigate(ROUT_MOODTRACKER) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "Check-in",
                            tint = Color.White
                        )
                        Text(text = "Check-in", fontSize = 10.sp, color = Color.White)
                    }
                }
                // Journal Icon
                IconButton(
                    onClick = { navController.navigate(ROUT_JOURNAL) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Journal",
                            tint = Color.White
                        )
                        Text(text = "Journal", fontSize = 10.sp, color = Color.White)
                    }
                }
                // Resources Icon
                IconButton(
                    onClick = { navController.navigate(ROUT_RESOURCES) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Resources",
                            tint = Color.White
                        )
                        Text(text = "Resources", fontSize = 10.sp, color = Color.White)
                    }
                }
                // Settings Icon
                IconButton(
                    onClick = { navController.navigate(ROUT_SETTINGS) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                        Text(text = "Settings", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                AccountSection(
                    title = "Update Profile",
                    icon = Icons.Default.Person,
                    content = {
                        Text(
                            text = "Manage your profile information and picture.",
                            modifier = Modifier.padding(bottom = 16.dp),
                            color = Color(0xFF616161)
                        )
                        Button(
                            onClick = {
                                // TODO: Replace with logic to update the profile in your Room database
                                // For example:
                                // val updatedUser = User(id = 1, name = "New Name", email = "newemail@example.com")
                                // userViewModel.updateUser(updatedUser)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
                        ) {
                            Text("Edit Profile", color = Color.White)
                        }
                    }
                )
            }

            item {
                AccountSection(
                    title = "Change Password",
                    icon = Icons.Default.Lock,
                    content = {
                        Text(
                            text = "Update your password to keep your account secure.",
                            modifier = Modifier.padding(bottom = 16.dp),
                            color = Color(0xFF616161)
                        )
                        Button(
                            onClick = {
                                // TODO: Replace with logic to change password in your Room database
                                // For example:
                                // userViewModel.changePassword(newPassword)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
                        ) {
                            Text("Change Password", color = Color.White)
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButton(
                    onClick = {
                        // TODO: Replace with logic to log out and clear session
                        // For example:
                        // userViewModel.logout()
                        // navController.navigate("login_route") { popUpTo(0) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF673AB7)),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
                ) {
                    Text(
                        text = "Log Out",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AccountSection(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF673AB7),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF616161)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color(0xFFEAD6FD), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(navController = rememberNavController())
}