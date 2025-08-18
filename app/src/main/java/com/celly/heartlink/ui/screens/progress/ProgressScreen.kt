package com.celly.heartlink.ui.screens.progress

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.ui.screens.dailyaffirmation.getDarkGreen
import com.celly.heartlink.ui.screens.dailyaffirmation.getGrey700
import com.celly.heartlink.ui.screens.dailyaffirmation.getOrange500
import com.celly.heartlink.ui.screens.dailyaffirmation.getPurple500
import com.celly.swaggy.ui.theme.screens.auth.getLightGray
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

// Define colors to maintain app consistency
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val Orange500 = Color(0xFFFF9800)
val DarkGreen = Color(0xFF4CAF50)

// Dummy data for preview purposes
val medicationStreak = 21 // Example streak: 21 days
val journalingDays = (1..30).shuffled().take(15).toSet() // 15 random days with entries

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Progress",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = getPurple500(),
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
                },
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
        containerColor = getLightGray()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                StreakTrackerCard(currentStreak = medicationStreak)
            }
            item {
                JournalingConsistencyCard(journalingDays = journalingDays)
            }
            // Add more sections for achievements, etc. here
        }
    }
}

@Composable
fun StreakTrackerCard(currentStreak: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Medication Streak",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = getGrey700()
            )
            Spacer(modifier = Modifier.height(20.dp))
            StreakCircle(streak = currentStreak)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$currentStreak days in a row!",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = getPurple500()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Keep up the great work!",
                fontSize = 16.sp,
                color = getGrey700()
            )
        }
    }
}

@Composable
fun StreakCircle(streak: Int, maxStreak: Int = 30) {
    val progress = (streak.toFloat() / maxStreak).coerceAtMost(1.0f)
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(150.dp)
    ) {
        // Background Circle
        Canvas(modifier = Modifier.size(150.dp)) {
            drawCircle(color = Color.LightGray.copy(alpha = 0.3f), radius = size.minDimension / 2)
        }
        // Progress Arc
        Canvas(modifier = Modifier.size(150.dp)) {
            drawArc(
                color = getOrange500(),
                startAngle = 270f,
                sweepAngle = animatedProgress.value * 360f,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalingConsistencyCard(journalingDays: Set<Int>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Journaling Consistency",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = getGrey700()
            )
            Spacer(modifier = Modifier.height(16.dp))
            JournalingHeatmap(journalingDays = journalingDays)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalingHeatmap(journalingDays: Set<Int>) {
    val today = LocalDate.now()
    val daysInMonth = today.lengthOfMonth()
    val firstDayOfMonth = today.withDayOfMonth(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value // 1 for Monday, 7 for Sunday

    val daysOfWeek = DayOfWeek.values().map {
        it.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            daysOfWeek.forEach { dayName ->
                Text(
                    text = dayName.first().toString(),
                    fontWeight = FontWeight.Bold,
                    color = getGrey700()
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            // FIX: Replaced IntrinsicSize with a fixed height to avoid the crash.
            modifier = Modifier.height(200.dp)
        ) {
            // Placeholder for days before the 1st of the month
            items(firstDayOfWeek - 1) {
                Spacer(modifier = Modifier.size(30.dp))
            }
            items(daysInMonth) { day ->
                val dayOfMonth = day + 1
                val isJournaled = journalingDays.contains(dayOfMonth)
                DayBox(day = dayOfMonth.toString(), isJournaled = isJournaled)
            }
        }
    }
}

@Composable
fun DayBox(day: String, isJournaled: Boolean) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(
                color = if (isJournaled) com.celly.heartlink.ui.theme.Purple5001.copy(alpha = 0.8f) else Color.LightGray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isJournaled) Color.White else getGrey700()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProgressScreenPreview() {
    ProgressScreen(navController = rememberNavController())
}