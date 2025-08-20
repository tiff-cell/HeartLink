package com.celly.heartlink.ui.screens.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.R

// Define the colors
private val Purple500 = Color(0xFF673AB7)
private val Grey700 = Color(0xFF616161)
private val LightGray = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodRecommendationsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nutrition Guide",
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
        containerColor = LightGray
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = "Nourish Your Body, Strengthen Your Life",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Grey700,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "A guide to affordable, healthy foods that support your immune system and overall well-being.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {
                FoodCard(
                    imageRes = R.drawable.ic_beans, // You need to add these images
                    foodName = "Beans & Legumes",
                    benefits = "Packed with protein to build muscles, and fiber for good digestion. Lentils, chickpeas, and peas are very affordable.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item {
                FoodCard(
                    imageRes = R.drawable.ic_whole_grains, // You need to add this image
                    foodName = "Whole Grains",
                    benefits = "Provides sustained energy to combat fatigue. Choose brown rice, maize meal, and oats for B-vitamins and fiber.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item {
                FoodCard(
                    imageRes = R.drawable.ic_green_veg, // You need to add this image
                    foodName = "Dark Leafy Greens",
                    benefits = "Rich in Vitamin A, C, and Iron to boost your immune system. Spinach, sukuma wiki, and kale are great local choices.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item {
                FoodCard(
                    imageRes = R.drawable.ic_eggs, // You need to add this image
                    foodName = "Eggs",
                    benefits = "An excellent and cheap source of high-quality protein and Vitamin D. Easy to prepare in many ways.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item {
                FoodCard(
                    imageRes = R.drawable.ic_avocado, // You need to add this image
                    foodName = "Avocado",
                    benefits = "Provides healthy fats to help maintain a healthy weight and absorb nutrients. A great energy booster.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item {
                FoodCard(
                    imageRes = R.drawable.ic_sweet_potatoes, // You need to add this image
                    foodName = "Sweet Potatoes",
                    benefits = "A fantastic source of Vitamin A (beta-carotene) to protect your cells and support your vision.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item {
                FoodCard(
                    imageRes = R.drawable.ic_fruit, // You need to add this image
                    foodName = "Seasonal Fruits",
                    benefits = "Boost your vitamin intake with affordable, local fruits like mangoes, oranges, and bananas. Perfect for quick snacks.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

// Add this reusable FoodCard composable to the same file
@Composable
fun FoodCard(
    imageRes: Int,
    foodName: String,
    benefits: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "$foodName image",
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = foodName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Purple500
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = benefits,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey700
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodRecommendationsScreenPreview() {
    FoodRecommendationsScreen(navController = rememberNavController())
}