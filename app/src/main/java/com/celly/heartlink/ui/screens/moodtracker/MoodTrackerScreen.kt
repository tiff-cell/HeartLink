package com.celly.heartlink.ui.screens.clinics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent

// Define a consistent color palette for your app
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val Orange500 = Color(0xFFFF9800)
val DarkGreen = Color(0xFF4CAF50)

// Dummy data for a daily affirmation and a week's mood history
val dailyAffirmation = "You are doing great! Every day is a new beginning."
val moodData = entryModelOf(1f, 3f, 2f, 4f, 5f, 3f, 4f)

// Main composable for the Mood Tracker screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodTrackerScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Daily Check-in",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple500
                )
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                AffirmationCard(affirmation = dailyAffirmation)
            }
            item {
                MoodTrackerSection(navController = navController)
            }
            item {
                MoodHistoryGraph(moodData = moodData)
            }
            item {
                JournalingShortcutCard(onClick = {
                    navController.navigate(ROUT_JOURNAL)
                })
            }
        }
    }
}

// Composable for displaying a daily affirmation
@Composable
fun AffirmationCard(affirmation: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Today's Affirmation",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Grey700
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "\"$affirmation\"",
                fontSize = 18.sp,
                color = Orange500,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Composable for logging the user's current mood with a text field
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodTrackerSection(navController: NavController) {
    var moodText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How are you feeling right now?",
            fontSize = 16.sp,
            color = Grey700
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = moodText,
            onValueChange = { moodText = it },
            label = { Text("Describe your mood...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Purple500,
                unfocusedBorderColor = Grey700
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(ROUT_MOODTRACKER) },
            colors = ButtonDefaults.buttonColors(containerColor = Purple500),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Log Mood",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log My Mood",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MoodHistoryGraph(moodData: ChartEntryModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Last 7 Days Mood History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Grey700
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Chart(
                    chart = lineChart(
                        axisValuesOverrider = AxisValuesOverrider.fixed(
                            minY = 1f,
                            maxY = 5f
                        ),
                        lines = listOf(
                            LineChart.LineSpec(
                                lineColor = 4, // Fix applied here
                                pointConnector = DefaultPointConnector(cubicStrength = 0.5f),
                                point = null
                            )
                        )
                    ),
                    model = moodData,
                    startAxis = rememberStartAxis(title = "Mood"),
                    bottomAxis = rememberBottomAxis(
                        title = "Day",
                        valueFormatter = { value, _ -> "Day ${value.toInt() + 1}" }
                    )
                )
            }
        }
    }
}

// Composable for the Journaling Shortcut button
@Composable
fun JournalingShortcutCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Purple500),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Want to write more about this?",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Go to Journal",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodTrackerScreenPreview() {
    MoodTrackerScreen(navController = rememberNavController())
}