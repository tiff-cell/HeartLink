package com.celly.heartlink.ui.screens.resources

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

// Data class for articles (including a URL for links)
data class Article(
    val title: String,
    val source: String,
    val url: String
)

// Dummy data for preview and demonstration purposes
val dummyArticles = listOf(
    Article("Understanding HIV Treatment Options", "WHO", "https://www.who.int/news-room/fact-sheets/detail/hiv-aids"),
    Article("Mental Wellness and Chronic Illness", "NIH", "https://www.nimh.nih.gov/health/topics/mental-health-and-chronic-illness"),
    Article("The Importance of a Support System", "CDC", "https://www.cdc.gov/hiv/basics/livingwithhiv/mental-health.html")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Resources Center",
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
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { EducationalContentSection(dummyArticles) }
            item { ClinicDirectorySection(onExploreClick = { /* Navigate to directory screen */ }) }
            item { FinancialLegalSection(onLearnMoreClick = { /* Navigate to financial resources */ }) }
        }
    }
}

@Composable
fun EducationalContentSection(articles: List<Article>) {
    Column {
        Text(
            text = "Educational Content",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = Grey700
        )
        Spacer(modifier = Modifier.height(8.dp))
        articles.forEach { article ->
            ArticleCard(article = article)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(article: Article) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // This is how you would handle "functioning links"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = article.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Grey700,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Source: ${article.source}",
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                color = Grey700
            )
        }
    }
}

@Composable
fun ClinicDirectorySection(onExploreClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Clinic & Service Directory",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Find local clinics, support groups, and more.",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
            }
            IconButton(onClick = onExploreClick) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Explore Directory",
                    tint = Purple500
                )
            }
        }
    }
}

@Composable
fun FinancialLegalSection(onLearnMoreClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Financial & Legal Resources",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Information on financial assistance and legal rights.",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
            }
            IconButton(onClick = onLearnMoreClick) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Learn More",
                    tint = Purple500
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResourcesScreenPreview() {
    ResourcesScreen(navController = rememberNavController())
}