package com.celly.heartlink.ui.screens.healthy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_JOURNAL
import com.celly.heartlink.navigation.ROUT_MOODTRACKER
import com.celly.heartlink.navigation.ROUT_RESOURCES
import com.celly.heartlink.navigation.ROUT_SETTINGS

// Define colors to match your app's theme
private val Purple500 = Color(0xFF673AB7)
private val Grey700 = Color(0xFF616161)
private val LightGray = Color(0xFFF5F5F5)

// Sample data for healthy eating tips
data class HealthyTip(val title: String, val content: String)

val healthyTips = listOf(
    HealthyTip("Hydrate, Hydrate, Hydrate", "Drinking plenty of water throughout the day is crucial for metabolism and overall health. Aim for at least 8 glasses."),
    HealthyTip("Eat the Rainbow", "Incorporate a variety of colorful fruits and vegetables into your meals. Each color provides different essential vitamins and minerals."),
    HealthyTip("Mindful Eating", "Pay attention to what you're eating without distractions. This can help you recognize your body's hunger and fullness cues."),
    HealthyTip("Balance Your Plate", "Ensure your meals include lean protein, whole grains, and a mix of healthy fats to keep you full and energized."),
    HealthyTip("Snack Smart", "Choose healthy snacks like nuts, seeds, or yogurt to avoid unhealthy cravings and maintain energy levels between meals.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthyEatingScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Healthy Eating",
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.healthy_eating_image),
                    contentDescription = "A variety of healthy foods on a table",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Text(
                    text = "Welcome to the Healthy Eating section!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Grey700
                )
            }
            item {
                Text(
                    text = "Here you'll find tips and information on nutrition.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
            items(healthyTips) { tip ->
                TipCard(tip = tip)
            }
        }
    }
}

@Composable
fun TipCard(tip: HealthyTip) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = tip.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Grey700
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip.content,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HealthyEatingScreenPreview() {
    HealthyEatingScreen(navController = rememberNavController())
}