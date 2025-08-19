package com.celly.heartlink.ui.screens.reminders

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.R // Assuming you have a res/drawable folder with your images
import java.time.LocalDate
import java.time.format.DateTimeFormatter


// Define colors to maintain app consistency
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val Teal200 = Color(0xFF03DAC5)
val Pink500 = Color(0xFFE91E63)

// Data classes to represent a reminder and an appointment
data class HealthReminder(
    val id: Int,
    val title: String,
    val time: String,
    val type: ReminderType
)

enum class ReminderType {
    MEDICATION, LAB_TEST
}

data class Appointment(
    val id: Int,
    val doctorName: String,
    val date: LocalDate,
    val time: String,
    val location: String
)

// Dummy data for preview purposes
val dummyReminders = listOf(
    HealthReminder(1, "Take HIV Medication", "8:00 AM", ReminderType.MEDICATION),
    HealthReminder(2, "Refill Prescription", "10:00 AM", ReminderType.MEDICATION),
    HealthReminder(3, "Schedule Viral Load Test", "Upcoming", ReminderType.LAB_TEST)
)

@RequiresApi(Build.VERSION_CODES.O)
val dummyAppointments = listOf(
    Appointment(1, "Dr. Alice Smith", LocalDate.of(2025, 8, 20), "2:30 PM", "Heartlink Clinic"),
    Appointment(2, "Dr. John Doe", LocalDate.of(2025, 9, 5), "11:00 AM", "City Hospital"),
    Appointment(3, "Dr. Alice Smith", LocalDate.of(2025, 10, 10), "9:00 AM", "Heartlink Clinic")
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthRemindersScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Reminders", "Appointments")
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Health Reminders",
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
                containerColor = Purple500,
                contentColor = Color.White
            ) {
                IconButton(onClick = { navController.navigate("home_route") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text(text = "Home", fontSize = 12.sp)
                    }
                }
                IconButton(onClick = { navController.navigate("mood_tracker_route") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Face, contentDescription = "Check-in")
                        Text(text = "Check-in", fontSize = 12.sp)
                    }
                }
                IconButton(onClick = { navController.navigate("journal_route") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Create, contentDescription = "Journal")
                        Text(text = "Journal", fontSize = 12.sp)
                    }
                }
                IconButton(onClick = { navController.navigate("settings_route") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                        Text(text = "Settings", fontSize = 12.sp)
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    when (selectedTabIndex) {
                        0 -> { /* Navigate to AddReminderScreen */ }
                        1 -> { /* Navigate to AddAppointmentScreen */ }
                    }
                },
                modifier = Modifier.padding(16.dp),
                containerColor = Pink500,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = "Add new") },
                text = {
                    Text(
                        text = if (selectedTabIndex == 0) "Add Reminder" else "Add Appointment",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        containerColor = LightGray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Purple500,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color.White
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTabIndex) {
                0 -> RemindersList(dummyReminders)
                1 -> AppointmentsList(dummyAppointments)
            }
        }
    }
}

// Function to handle scheduling local notifications
fun scheduleNotification(
    context: Context,
    title: String,
    message: String,
    delayMillis: Long
) {
    // Placeholder for real notification logic
}

@Composable
fun RemindersList(reminders: List<HealthReminder>) {
    if (reminders.isEmpty()) {
        EmptyStateMessage("No reminders set yet. Tap '+' to add one.")
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reminders) { reminder ->
                ReminderCard(reminder = reminder) {
                    // Handle click to view or log reminder
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderCard(reminder: HealthReminder, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .clickable(onClick = onClick)
            .animateContentSize(animationSpec = tween(durationMillis = 300)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon based on reminder type
            val icon = when (reminder.type) {
                ReminderType.MEDICATION -> Icons.Default.Dry
                ReminderType.LAB_TEST -> Icons.Default.Science
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Teal200.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = reminder.type.name,
                    tint = Teal200,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reminder.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Time: ${reminder.time}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentsList(appointments: List<Appointment>) {
    if (appointments.isEmpty()) {
        EmptyStateMessage("You have no upcoming appointments.")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Upcoming Appointments",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(appointment = appointment) {
                        // Handle click to view appointment details
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentCard(appointment: Appointment, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Pink500.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Appointment Icon",
                    tint = Pink500,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Dr. ${appointment.doctorName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Date: ${appointment.date.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
                Text(
                    text = "Time: ${appointment.time}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    color = Grey700
                )
            }
        }
    }
}

@Composable
fun EmptyStateMessage(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // You'll need to add a vector asset named 'ic_empty_state_illustration.xml' to your res/drawable folder
        Image(
            painter = painterResource(id = R.drawable.ic_empty_state_illustration),
            contentDescription = "Empty State Illustration",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Grey700,
            fontFamily = FontFamily.Default,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HealthRemindersScreenPreview() {
    HealthRemindersScreen(navController = rememberNavController())
}