package com.celly.heartlink.ui.screens.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.celly.heartlink.R // Assuming you have a default drawable for placeholder images
import com.celly.heartlink.navigation.ROUT_DOCTORSCOUNSELORS
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_JOURNAL
import com.celly.heartlink.navigation.ROUT_MOODTRACKER
import com.celly.heartlink.navigation.ROUT_RESOURCES
import com.celly.heartlink.navigation.ROUT_SETTINGS
import com.celly.heartlink.ui.theme.Purple40
import com.celly.heartlink.ui.theme.Purple80

// 1. UPDATED DATA CLASS with new properties for more realism
@Entity(tableName = "professionals")
data class Professional(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val role: String,
    val description: String,
    val imageResId: Int, // Image resource ID for the professional's photo
    val rating: Float, // Example: 4.8
    val location: String // Example: "Nairobi, Kenya"
)

// 2. COMPOSE SCREEN (Main Screen remains largely the same, but imports are streamlined)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDoctorsCounsellors(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val viewModel: ProfessionalViewModel = viewModel(factory = ProfessionalViewModel.Factory(db.professionalDao()))
    val professionals by viewModel.allProfessionals.collectAsState(initial = emptyList())

    val primaryColor = Color(0xFF6A1B9A)
    val secondaryColor = Color(0xFFE1BEE7)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Our Professionals",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, primaryColor = primaryColor)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ROUT_DOCTORSCOUNSELORS) },
                containerColor = primaryColor,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "Add professional")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.background(secondaryColor.copy(alpha = 0.3f))
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(secondaryColor.copy(alpha = 0.1f))
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (professionals.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "No professionals registered yet.",
                            style = MaterialTheme.typography.titleMedium,
                            color = primaryColor
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Be the first to add one to our network!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                items(professionals) { professional ->
                    ProfessionalCard(professional = professional)
                }
            }
        }
    }
}

// 3. BOTTOM NAVIGATION BAR COMPOSABLE (remains the same)
@Composable
fun BottomNavigationBar(navController: NavController, primaryColor: Color) {
    BottomAppBar(
        containerColor = primaryColor
    ) {
        BottomBarItem(icon = Icons.Default.Home, label = "Home", onClick = { navController.navigate(ROUT_HOME) })
        BottomBarItem(icon = Icons.Default.Favorite, label = "Check-in", onClick = { navController.navigate(ROUT_MOODTRACKER) })
        BottomBarItem(icon = Icons.Default.Edit, label = "Journal", onClick = { navController.navigate(ROUT_JOURNAL) })
        BottomBarItem(icon = Icons.Default.MenuBook, label = "Resources", onClick = { navController.navigate(ROUT_RESOURCES) })
        BottomBarItem(icon = Icons.Default.Settings, label = "Settings", onClick = { navController.navigate(ROUT_SETTINGS) })
    }
}

// 4. BOTTOM BAR ITEM COMPOSABLE (remains the same)
@Composable
fun RowScope.BottomBarItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Color.White)
        Text(text = label, fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Medium)
    }
}

// 5. ENHANCED PROFESSIONAL CARD WITH MORE DETAILS
@Composable
fun ProfessionalCard(professional: Professional) {
    val cardColors = listOf(Purple80, Purple40)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(Brush.linearGradient(cardColors))
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Circular Professional Image
                Image(
                    painter = painterResource(id = professional.imageResId),
                    contentDescription = "${professional.name}'s profile picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(4.dp)
                )
                Spacer(Modifier.width(16.dp))

                // Professional Details
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = professional.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF4A148C)
                    )
                    Text(
                        text = professional.role,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF6A1B9A),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${professional.rating}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = professional.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Divider(color = Color(0xFFBA68C8), thickness = 1.dp)
            Spacer(Modifier.height(16.dp))
            Text(
                text = professional.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF424242)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Email: ${professional.email}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF616161),
                fontWeight = FontWeight.Light
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { /* Handle contact action, e.g., open email or call */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
            ) {
                Icon(imageVector = Icons.Default.Chat, contentDescription = "Contact")
                Spacer(Modifier.width(8.dp))
                Text(text = "Contact Professional", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// 6. PREVIEW COMPOSABLE with multiple, distinct professionals for variety
@Preview(showBackground = true)
@Composable
fun ViewDoctorsCounsellorsPreview() {
    val professionals = listOf(
        Professional(1, "Dr. Jane Doe", "jane.doe@example.com", "Psychiatrist", "A passionate psychiatrist with over 10 years of experience in cognitive-behavioral therapy.", R.drawable.ic_profile_placeholder, 4.8f, "Nairobi, Kenya"),
        Professional(2, "Counsellor John Smith", "john.smith@example.com", "Counsellor", "Dedicated to helping individuals manage stress and anxiety through mindfulness techniques.", R.drawable.ic_profile_placeholder, 4.5f, "Kisumu, Kenya"),
        Professional(3, "Dr. Alice Brown", "alice.brown@example.com", "Therapist", "Specializes in family therapy and relationship counseling.", R.drawable.ic_profile_placeholder, 5.0f, "Mombasa, Kenya")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE1BEE7).copy(alpha = 0.1f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        professionals.forEach { professional ->
            ProfessionalCard(professional = professional)
        }
    }
}