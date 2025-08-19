package com.celly.heartlink.ui.screens.workouts

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

private val Purple500 = Color(0xFF673AB7)
private val Grey700 = Color(0xFF616161)
private val LightGray = Color(0xFFF5F5F5)

// A data class to represent a workout plan
data class WorkoutPlan(val title: String, val description: String)

// Sample data for your workout plans
val workoutPlans = listOf(
    WorkoutPlan("Beginner's Full Body", "A simple routine for newcomers to build strength and endurance."),
    WorkoutPlan("HIIT for Fat Loss", "High-intensity interval training to burn calories and boost your metabolism."),
    WorkoutPlan("Core & Abs Crusher", "Focuses on strengthening your core with a series of planks, crunches, and leg raises."),
    WorkoutPlan("Upper Body Strength", "Targets the chest, back, and arms with a combination of compound and isolation exercises."),
    WorkoutPlan("Leg Day Power Up", "A challenging workout to build powerful legs and glutes."),
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.workout_motivation),
                    contentDescription = "A person doing a workout",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Text(
                    text = "Welcome to the Workouts section!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Grey700
                )
            }
            item {
                Text(
                    text = "Here you'll find personalized workout plans.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
            items(workoutPlans) { plan ->
                WorkoutPlanCard(plan)
            }
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
                color = Grey700
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = plan.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutsScreenPreview() {
    WorkoutsScreen(navController = rememberNavController())
}
