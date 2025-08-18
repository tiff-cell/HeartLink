package com.celly.heartlink.ui.screens.counselors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

// Define a consistent color palette for your app
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val Orange500 = Color(0xFFFF9800)
val DarkGreen = Color(0xFF4CAF50)

// Data class to represent a counselor
data class Counselor(
    val id: Int,
    val name: String,
    val specialization: String,
    val status: String // "Online", "Offline", "Busy"
)

// Data class for a chat message
data class ChatMessage(
    val sender: String,
    val message: String,
    val isUser: Boolean
)

// Dummy data for a list of counselors
val counselors = listOf(
    Counselor(1, "Dr. Alice", "HIV/AIDS Counseling", "Online"),
    Counselor(2, "Mr. Ben", "Mental Health Support", "Busy"),
    Counselor(3, "Ms. Carol", "Medication Adherence", "Online"),
    Counselor(4, "Dr. David", "Nutritional Guidance", "Offline")
)

/**
 * Main Composable for the navigation graph, hosting all screens.
 */
@Composable
fun CounselorApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "counselorsList") {
        composable("counselorsList") {
            CounselorsScreen(navController = navController)
        }
        composable(
            "chatScreen/{counselorId}",
            arguments = listOf(navArgument("counselorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val counselorId = backStackEntry.arguments?.getInt("counselorId")
            if (counselorId != null) {
                CounselorChatScreen(counselorId = counselorId)
            }
        }
    }
}

/**
 * Main screen displaying a list of available counselors.
 *
 * @param navController The navigation controller for screen transitions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounselorsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Connect with a Counselor",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple500)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = com.celly.heartlink.ui.screens.clinics.Purple500,
                contentColor = Color.White
            ) {
                // Home Icon
                IconButton(
                    onClick = { navController.navigate("home_route") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text(text = "Home", fontSize = 12.sp)
                    }
                }
                // Check-in Icon
                IconButton(
                    onClick = { navController.navigate("mood_tracker_route") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Face, contentDescription = "Check-in")
                        Text(text = "Check-in", fontSize = 12.sp)
                    }
                }
                // Journal Icon
                IconButton(
                    onClick = { navController.navigate("journal_route") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Create, contentDescription = "Journal")
                        Text(text = "Journal", fontSize = 12.sp)
                    }
                }
                // Settings Icon
                IconButton(
                    onClick = { navController.navigate("settings_route") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                        Text(text = "Settings", fontSize = 12.sp)
                    }
                }
            }
        },
        containerColor = LightGray
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Available Counselors",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey700
                )
            }
            items(counselors) { counselor ->
                CounselorCard(counselor = counselor, onClick = {
                    // Navigate to chat screen with the selected counselor
                    navController.navigate("chatScreen/${counselor.id}")
                })
            }
        }
    }
}

/**
 * A clickable card for each counselor.
 *
 * @param counselor The counselor data to display.
 * @param onClick The action to perform when the card is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounselorCard(counselor: Counselor, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Counselor",
                tint = Purple500,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = counselor.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey700
                )
                Text(
                    text = counselor.specialization,
                    fontSize = 14.sp,
                    color = Grey700.copy(alpha = 0.7f)
                )
            }
            Text(
                text = counselor.status,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = when (counselor.status) {
                    "Online" -> DarkGreen
                    "Busy" -> Orange500
                    else -> Grey700
                }
            )
        }
    }
}

/**
 * The screen for chatting with a selected counselor.
 *
 * @param counselorId The ID of the counselor to chat with.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounselorChatScreen(counselorId: Int) {
    // Dummy chat messages. In a real app, this would be a ViewModel state.
    var messages by remember {
        mutableStateOf(
            listOf(
                ChatMessage("Counselor", "Hello! How can I help you today?", false)
            )
        )
    }
    var currentMessage by remember { mutableStateOf("") }
    val counselor = counselors.find { it.id == counselorId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = counselor?.name ?: "Counselor",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple500)
            )
        },
        containerColor = LightGray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true
            ) {
                items(messages.reversed()) { message ->
                    MessageBubble(message = message)
                }
            }

            // Input section at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = currentMessage,
                    onValueChange = { currentMessage = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type your message...") },
                    shape = RoundedCornerShape(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    if (currentMessage.isNotBlank()) {
                        messages = messages + ChatMessage("You", currentMessage, true)
                        currentMessage = ""
                        // TODO: Implement logic to send the message and get a response
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Purple500,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

/**
 * A chat message bubble.
 *
 * @param message The chat message to display.
 */
@Composable
fun MessageBubble(message: ChatMessage) {
    val bubbleColor = if (message.isUser) Purple500 else Color(0xFFE0E0E0)
    val textColor = if (message.isUser) Color.White else Grey700
    val alignment = if (message.isUser) Alignment.End else Alignment.Start

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = bubbleColor)
        ) {
            Text(
                text = message.message,
                modifier = Modifier.padding(12.dp),
                color = textColor
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CounselorsScreenPreview() {
    CounselorApp()
}