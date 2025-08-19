package com.celly.heartlink.ui.screens.journal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.celly.heartlink.R
import java.time.LocalTime

// Define colors to maintain app consistency
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val HappyColor = Color(0xFFFFF176) // Light yellow for happy moods
val SadColor = Color(0xFFB0BEC5)     // Blue-grey for sad moods
val NeutralColor = Color(0xFFE0E0E0) // Lighter grey for neutral moods

// Data class to represent a single journal entry
data class JournalEntry(
   val id: Int,
   val title: String,
   val date: String,
   val content: String,
   val mood: Mood,
   val imageUri: String? = null // Optional URI for a user-selected image
)

enum class Mood {
   HAPPY, SAD, NEUTRAL
}

// Dummy data for preview purposes
val dummyJournalEntries = listOf(
   JournalEntry(1, "Feeling Hopeful", "August 12, 2025", "Today was a good day. I connected with a few people on the forum and felt a sense of belonging. Medication felt manageable.", Mood.HAPPY, "https://picsum.photos/400/200?random=1"),
   JournalEntry(2, "A Tough Day", "August 10, 2025", "Struggled with side effects today. It's hard sometimes, but I know I'm not alone.", Mood.SAD),
   JournalEntry(3, "My Journey Starts", "August 8, 2025", "Just started using the app. The journal feature seems like a great way to track my progress and feelings.", Mood.NEUTRAL, "https://picsum.photos/400/200?random=2")
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(navController: NavController) {
   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text(
                  text = "My Journal",
                  fontFamily = FontFamily.Cursive,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
               )
            },
            colors = TopAppBarDefaults.topAppBarColors(
               containerColor = Purple500,
               titleContentColor = Color.White,
               navigationIconContentColor = Color.White
            ),
            navigationIcon = {
               IconButton(onClick = { navController.popBackStack() }) {
                  Icon(
                     imageVector = Icons.Default.ArrowBack,
                     contentDescription = "Back"
                  )
               }
            }
         )
      },
      bottomBar = {
         BottomAppBar(
            containerColor = Purple500,
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
      floatingActionButton = {
         ExtendedFloatingActionButton(
            onClick = { /* Navigate to a new journal entry screen with image selector */ },
            containerColor = Purple500,
            contentColor = Color.White,
            icon = { Icon(Icons.Default.Add, contentDescription = "Add new entry") },
            text = { Text("New Entry") }
         )
      },
      containerColor = Color.Transparent // Set Scaffold background to transparent
   ) { paddingValues ->
      Box(
         modifier = Modifier.fillMaxSize()
      ) {
         // Background Image
         Image(
            painter = painterResource(id = R.drawable.background_paper), // Replace with your image resource
            contentDescription = "Journal background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
         )
         // A semi-transparent overlay to ensure text readability
         Box(
            modifier = Modifier
               .fillMaxSize()
               .background(Color.White.copy(alpha = 0.8f))
         )
         LazyColumn(
            modifier = Modifier
               .fillMaxSize()
               .padding(paddingValues)
               .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
         ) {
            item {
               Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                  WelcomeMessage()
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                     text = "How are you feeling?",
                     fontSize = 20.sp,
                     fontWeight = FontWeight.Bold,
                     fontFamily = FontFamily.Default,
                     color = Grey700,
                  )
               }
            }
            items(dummyJournalEntries) { entry ->
               JournalEntryCard(entry = entry) {
                  // Handle click to view full entry details
               }
            }
         }
      }
   }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WelcomeMessage() {
   val currentTime = LocalTime.now()
   val message = when {
      currentTime.hour < 12 -> "Good morning,"
      currentTime.hour < 18 -> "Good afternoon,"
      else -> "Good evening,"
   }

   Row(
      verticalAlignment = Alignment.CenterVertically
   ) {
      Icon(
         imageVector = Icons.Default.SelfImprovement,
         contentDescription = "Welcome Icon",
         tint = Purple500,
         modifier = Modifier.size(24.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
         text = "$message",
         fontSize = 24.sp,
         fontWeight = FontWeight.SemiBold,
         fontFamily = FontFamily.Default,
         color = Grey700,
      )
   }
}

// Keep the existing composables for JournalEntryCard and MoodIcon
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEntryCard(entry: JournalEntry, onClick: () -> Unit) {
   Card(
      modifier = Modifier
         .fillMaxWidth()
         .clickable(onClick = onClick),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
   ) {
      Column(
         modifier = Modifier
            .fillMaxWidth()
      ) {
         // Display the image if it exists
         entry.imageUri?.let { uri ->
            Image(
               painter = rememberAsyncImagePainter(model = uri),
               contentDescription = "Journal entry image",
               modifier = Modifier
                  .fillMaxWidth()
                  .height(200.dp)
                  .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
               contentScale = ContentScale.Crop
            )
         }

         // Text content of the card
         Column(modifier = Modifier.padding(16.dp)) {
            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
            ) {
               Text(
                  text = entry.date,
                  fontSize = 14.sp,
                  color = Grey700.copy(alpha = 0.7f),
                  fontFamily = FontFamily.Default
               )
               MoodIcon(mood = entry.mood)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
               text = entry.title,
               fontSize = 20.sp,
               fontWeight = FontWeight.Bold,
               fontFamily = FontFamily.Default,
               color = Grey700,
               maxLines = 1,
               overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
               text = entry.content,
               fontSize = 16.sp,
               maxLines = 3,
               overflow = TextOverflow.Ellipsis,
               fontFamily = FontFamily.Default,
               color = Grey700
            )
         }
      }
   }
}

@Composable
fun MoodIcon(mood: Mood) {
   val (icon, color) = when (mood) {
      Mood.HAPPY -> Icons.Default.SentimentSatisfied to Color(0xFFFFC107)
      Mood.SAD -> Icons.Default.SentimentDissatisfied to Color(0xFF2196F3)
      Mood.NEUTRAL -> Icons.Default.SentimentNeutral to Color(0xFF9E9E9E)
   }

   Icon(
      imageVector = icon,
      contentDescription = "$mood mood",
      tint = color,
      modifier = Modifier.size(24.dp)
   )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
   JournalScreen(navController = rememberNavController())
}