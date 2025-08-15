package com.celly.heartlink.ui.screens.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Define colors to maintain app consistency
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)

// Data class to represent a single journal entry
data class JournalEntry(
   val id: Int,
   val title: String,
   val date: String,
   val content: String
)

// Dummy data for preview purposes
val dummyJournalEntries = listOf(
   JournalEntry(1, "Feeling Hopeful", "August 12, 2025", "Today was a good day. I connected with a few people on the forum and felt a sense of belonging. Medication felt manageable."),
   JournalEntry(2, "A Tough Day", "August 10, 2025", "Struggled with side effects today. It's hard sometimes, but I know I'm not alone."),
   JournalEntry(3, "My Journey Starts", "August 8, 2025", "Just started using the app. The journal feature seems like a great way to track my progress and feelings.")
)

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
      floatingActionButton = {
         FloatingActionButton(
            onClick = { /* Navigate to a new journal entry screen */ },
            containerColor = Purple500,
            contentColor = Color.White
         ) {
            Icon(
               imageVector = Icons.Default.Add,
               contentDescription = "Add new entry"
            )
         }
      },
      containerColor = LightGray
   ) { paddingValues ->
      LazyColumn(
         modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 8.dp),
         verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
         items(dummyJournalEntries) { entry ->
            JournalEntryCard(entry = entry) {
               // Handle click to view full entry details
            }
         }
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEntryCard(entry: JournalEntry, onClick: () -> Unit) {
   Card(
      modifier = Modifier
         .fillMaxWidth()
         .heightIn(min = 120.dp),
      shape = RoundedCornerShape(12.dp),
      onClick = onClick
   ) {
      Column(
         modifier = Modifier.padding(16.dp)
      ) {
         Text(
            text = entry.date,
            fontSize = 14.sp,
            color = Grey700,
            fontFamily = FontFamily.Default
         )
         Spacer(modifier = Modifier.height(4.dp))
         Text(
            text = entry.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = Grey700
         )
         Spacer(modifier = Modifier.height(8.dp))
         Text(
            text = entry.content,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily.Default,
            color = Grey700
         )
      }
   }
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
   JournalScreen(navController = rememberNavController())
}