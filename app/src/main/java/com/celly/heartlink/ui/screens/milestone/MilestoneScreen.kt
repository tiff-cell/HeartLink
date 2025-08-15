package com.celly.heartlink.ui.screens.milestone

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.ui.screens.progress.getDarkGreen
import com.celly.heartlink.ui.screens.progress.getGrey700
import com.celly.heartlink.ui.screens.progress.getLightGray
import com.celly.heartlink.ui.screens.progress.getOrange500
import com.celly.heartlink.ui.screens.progress.getPurple500

// Data classes for milestones to keep the code clean and organized
data class Achievement(
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val icon: ImageVector = Icons.Default.Star, // Default icon
    val color: Color = if (isUnlocked) getDarkGreen() else getGrey700()
)

data class NextStep(
    val title: String,
    val action: String
)

// Dummy data for preview and demonstration
val allAchievements = listOf(
    Achievement(
        title = "First Journal Entry",
        description = "You completed your first journal entry!",
        isUnlocked = true,
        icon = Icons.AutoMirrored.Filled.ArrowForward
    ),
    Achievement(
        title = "10-Day Medication Streak",
        description = "You've been consistent with your medication for 10 days.",
        isUnlocked = true,
        icon = Icons.Default.Star
    ),
    Achievement(
        title = "First Community Post",
        description = "You shared your first post with the community.",
        isUnlocked = false
    ),
    Achievement(
        title = "Viewed 5 Educational Articles",
        description = "You're taking charge of your health education.",
        isUnlocked = false
    )
)

val nextStepData = NextStep(
    title = "Complete your next journal entry!",
    action = "Tap to open your journal"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MilestoneScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Milestones",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = getPurple500()
                )
            )
        },
        containerColor = getLightGray()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Your Achievements",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = getGrey700(),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(allAchievements) { achievement ->
                AchievementCard(achievement = achievement)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                WhatsNextCard(nextStep = nextStepData)
            }
        }
    }
}

@Composable
fun AchievementCard(achievement: Achievement) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = achievement.icon,
                contentDescription = achievement.title,
                tint = achievement.color,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = achievement.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = getGrey700()
                )
                Text(
                    text = achievement.description,
                    fontSize = 14.sp,
                    color = getGrey700().copy(alpha = 0.7f)
                )
            }
            if (achievement.isUnlocked) {
                Text(
                    text = "UNLOCKED",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = getDarkGreen()
                )
            } else {
                Text(
                    text = "LOCKED",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun WhatsNextCard(nextStep: NextStep) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = getPurple500()),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What's Next?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nextStep.title,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle navigation to the next action */ },
                colors = ButtonDefaults.buttonColors(containerColor = getOrange500()),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = nextStep.action, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MilestoneScreenPreview() {
    MilestoneScreen(navController = rememberNavController())
}