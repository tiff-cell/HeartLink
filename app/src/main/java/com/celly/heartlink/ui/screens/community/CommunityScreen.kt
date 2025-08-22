package com.celly.heartlink.ui.screens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.R
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_JOURNAL
import com.celly.heartlink.navigation.ROUT_MOODTRACKER
import com.celly.heartlink.navigation.ROUT_RESOURCES
import com.celly.heartlink.navigation.ROUT_SETTINGS

// Consistent color palette for the app
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val Orange500 = Color(0xFFFF9800)
val DarkGreen = Color(0xFF4CAF50)

/**
 * Data class to represent a chat message, including the sender and content.
 */
data class ChatMessage(
    val sender: String,
    val message: String,
    val isUser: Boolean // To differentiate between user and other messages
)

/**
 * Main composable function for the Community Screen.
 * It combines educational information, a live chat section, and a chat input field.
 *
 * @param navController The navigation controller for screen transitions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Community Chat",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple500)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = com.celly.heartlink.ui.screens.home.Purple500
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
                EducationalCard()
            }
            item {
                Text(
                    text = "Live Chat",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey700
                )
            }
            item {
                ChatSection()
            }
        }
    }
}

/**
 * A card displaying an educational tip to promote a positive and inclusive environment.
 */
@Composable
fun EducationalCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Orange500),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                painter = painterResource(id = R.drawable.chat),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(32.dp)

            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Remember to be kind and supportive to everyone in the community. We are all here to help each other!",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Displays a list of chat messages and the input field.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSection() {
    // Dummy data to simulate a chat
    val messages = listOf(
        ChatMessage("User123", "Hey everyone! Feeling a bit down today.", false),
        ChatMessage("App Admin", "Hi there! Remember, it's okay not to be okay. We're here for you.", true),
        ChatMessage("HopefulHeart", "Hang in there, you've got this! Sending positive vibes your way.", false),
        ChatMessage("User123", "Thanks, that means a lot.", false)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp) // Fixed height for demonstration
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageBubble(message = message)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ChatInput()
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
    val alignment = if (message.isUser) Alignment.CenterStart else Alignment.CenterEnd

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 280.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.sender,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = textColor
                )
                Text(
                    text = message.message,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
        }
    }
}

/**
 * The user input field for sending messages.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInput() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        placeholder = { Text("Type a message...") },
        trailingIcon = {
            IconButton(onClick = { /* TODO: Implement sending message logic */ }) {

                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "",
                    tint = Purple500

                )


            }
        },
        colors =OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Purple500,
            unfocusedBorderColor = Grey700,
            cursorColor = Purple500
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    CommunityScreen(navController = rememberNavController())
}