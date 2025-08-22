package com.celly.heartlink.ui.screens.resources

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    Article("The Importance of a Support System", "CDC", "https://www.cdc.gov/hiv/basics/livingwithhiv/mental-health.html"),
    Article("Navigating Legal Rights", "ACLU", "https://www.aclu.org/know-your-rights/lgbtq-rights/"),
    Article("Financial Planning with Chronic Conditions", "AARP", "https://www.aarp.org/money/credit-loans-debt/info-2023/financial-planning-with-chronic-illness.html")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

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
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // New: Search Bar
            item {
                SearchBar(searchQuery = searchQuery, onQueryChange = { searchQuery = it })
            }

            // New: Featured Articles Carousel
            item {
                FeaturedArticlesCarousel(articles = dummyArticles)
            }

            // Filtered articles based on search query
            val filteredArticles = dummyArticles.filter {
                it.title.contains(searchQuery, ignoreCase = true) || it.source.contains(searchQuery, ignoreCase = true)
            }

            items(filteredArticles) { article ->
                ArticleCard(article = article)
            }

            item { ClinicDirectorySection(onExploreClick = { /* Navigate to directory screen */ }) }

            // New: Expandable Financial/Legal section
            item { FinancialLegalSection(onLearnMoreClick = { /* Navigate to financial resources */ }) }
        }
    }
}

// ---
// ## New Component: Search Bar
// ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        placeholder = { Text("Search for articles...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Purple500,
            unfocusedBorderColor = Grey700
        )
    )
}

// ---
// ## New Component: Featured Articles Carousel
// ---
@Composable
fun FeaturedArticlesCarousel(articles: List<Article>) {
    Column {
        Text(
            text = "Featured Content",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = Grey700
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(articles) { article ->
                FeaturedArticleCard(article = article)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedArticleCard(article: Article) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(150.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Purple500.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.source,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

// ---
// ## New Component: Expandable Section
// ---
@Composable
fun FinancialLegalSection(onLearnMoreClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Purple500
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Divider(color = Grey700.copy(alpha = 0.2f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Legal aid for medical claims\n" +
                                "• Government financial assistance programs\n" +
                                "• Insurance plan guides\n" +
                                "• Understanding your rights in the workplace",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        color = Grey700
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onLearnMoreClick, colors = ButtonDefaults.buttonColors(containerColor = Purple500)) {
                        Text("Learn More")
                    }
                }
            }
        }
    }
}

// Keep the existing composables as they are:
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

@Preview(showBackground = true)
@Composable
fun ResourcesScreenPreview() {
    ResourcesScreen(navController = rememberNavController())
}