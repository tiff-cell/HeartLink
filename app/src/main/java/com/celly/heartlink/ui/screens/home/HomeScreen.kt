package com.celly.heartlink.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.R
import com.celly.heartlink.navigation.ROUT_COMMUNITY
import com.celly.heartlink.navigation.ROUT_HEALTHREMINDERS
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_JOURNAL
import com.celly.heartlink.navigation.ROUT_MOODTRACKER
import com.celly.heartlink.navigation.ROUT_RESOURCES
import com.celly.heartlink.navigation.ROUT_SETTINGS
import com.celly.heartlink.ui.screens.dailyaffirmation.getPurple500
import com.celly.heartlink.ui.theme.Purple5001
import java.time.LocalTime

// Define the colors and font to match your login and register screens
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val MorningYellow = Color(0xFFFFF176)
val AfternoonOrange = Color(0xFFFFA726)
val EveningBlue = Color(0xFF42A5F5)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Heartlink",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple500,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { /* Open navigation drawer */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    // Notification Icon
                    IconButton(onClick = { /* Handle notifications click */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                    // Message Icon
                    IconButton(onClick = { /* Handle message click */ }) {
                        Icon(
                            painter = painterResource(R.drawable.message),
                            contentDescription = "Messages"
                        )
                    }
                    // Profile Icon
                    IconButton(onClick = { /* Navigate to profile */ }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Purple500
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
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SearchBar() }
            item { WelcomeCard(username = "Stephannie") }
            item { WellnessRemindersCard(onButtonClick = { navController.navigate(ROUT_HEALTHREMINDERS) }) }
            item { JournalCard(onButtonClick = { navController.navigate(ROUT_JOURNAL) }) }
            item { ResourceCard(onButtonClick = { navController.navigate(ROUT_RESOURCES) }) }
            item { CommunityCard(onButtonClick = { navController.navigate(ROUT_COMMUNITY) }) }
        }
    }
}

// Search Bar Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search Heartlink...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Purple500,
            unfocusedBorderColor = Grey700
        )
    )
}

// Reusable Composable for the Welcome Card (updated)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WelcomeCard(username: String) {
    val currentTime = LocalTime.now()
    val greeting = when {
        currentTime.hour < 12 -> "Good morning,"
        currentTime.hour < 18 -> "Good afternoon,"
        else -> "Good evening,"
    }
    val imageRes = when {
        currentTime.hour < 12 -> R.drawable.ic_morning_sun // You need to add this image
        currentTime.hour < 18 -> R.drawable.ic_afternoon_sun // You need to add this image
        else -> R.drawable.ic_night_moon // You need to add this image
    }
    val cardColor = when {
        currentTime.hour < 12 -> MorningYellow.copy(alpha = 0.8f)
        currentTime.hour < 18 -> AfternoonOrange.copy(alpha = 0.8f)
        else -> EveningBlue.copy(alpha = 0.8f)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Purple5001),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Time of day illustration",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$greeting $username!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Grey700
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You're doing great! Keep up the good work.",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Default,
                color = Grey700
            )
        }
    }
}

// NEW: Combined Wellness Reminders and Appointments Card
@Composable
fun WellnessRemindersCard(onButtonClick: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 200)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(animatedScale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color = Purple500, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.calendar_img),
                    contentDescription = "Reminders and Appointments Icon",
                    modifier = Modifier.size(50.dp),
                    //tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Wellness Reminders",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "View upcoming medications and appointments.",
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = Purple500)
            ) {
                Text("View", fontFamily = FontFamily.Default)
            }
        }
    }
}

// Reusable Composable for the Journal Card (updated)
@Composable
fun JournalCard(onButtonClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Journal Icon",
                modifier = Modifier.size(48.dp),
                tint = Purple500
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Ready to check in on your feelings?",
                modifier = Modifier.weight(1f),
                fontFamily = FontFamily.Default,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onButtonClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Journal Entry",
                    tint = Purple500
                )
            }
        }
    }
}

// Reusable Composable for the Resource Card (updated)
@Composable
fun ResourceCard(onButtonClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Purple5001),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Daily Inspiration:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Purple500
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Staying connected with others can improve your mental health.",
                fontFamily = FontFamily.Default,
                color = Grey700
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onButtonClick) {
                Text("Explore Resources", fontFamily = FontFamily.Default, color = Purple500)
            }
        }
    }
}

// A simple community card to show community integration (updated)
@Composable
fun CommunityCard(onButtonClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = "Community Icon",
                modifier = Modifier.size(48.dp),
                tint = Purple500
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "See what the community is talking about.",
                modifier = Modifier.weight(1f),
                fontFamily = FontFamily.Default,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go to community",
                    tint = Purple500
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}