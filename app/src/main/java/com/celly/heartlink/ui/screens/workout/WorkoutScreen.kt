package com.celly.heartlink.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.R

// Placeholder data for workout plans
data class WorkoutPlan(
    val title: String,
    val description: String,
    val difficulty: String
)

private val workoutPlans = listOf(
    WorkoutPlan("Beginner's Core", "A simple routine to strengthen your core.", "Easy"),
    WorkoutPlan("Full Body Burn", "Targets all major muscle groups in a 30-minute session.", "Medium"),
    WorkoutPlan("Advanced HIIT", "High-intensity interval training for maximum calorie burn.", "Hard")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Workout Plans",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF673AB7))
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
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_working_out),
                    contentDescription = "Someone working out",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                ProgressTrackerCard()
            }
            item {
                Text(
                    text = "Featured Workout Plans",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF616161),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(workoutPlans) { plan ->
                WorkoutPlanCard(plan)
            }
        }
    }
}

@Composable
fun ProgressTrackerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Daily Progress",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF616161)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = { 0.7f }, // Placeholder progress value (70%)
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF673AB7),
                trackColor = Color(0xFFE0E0E0)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "70% of your daily goal completed",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun WorkoutPlanCard(plan: WorkoutPlan) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = plan.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF616161)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = plan.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Difficulty: ${plan.difficulty}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF673AB7)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutsScreenPreview() {
    WorkoutsScreen(navController = rememberNavController())
}