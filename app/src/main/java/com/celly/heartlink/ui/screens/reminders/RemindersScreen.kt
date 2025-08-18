package com.celly.heartlink.ui.screens.reminders

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Define colors to maintain app consistency
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)

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
    val date: LocalDate, // Changed to LocalDate for easier calendar integration
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigate to a dedicated "add new" screen based on the selected tab
                    when (selectedTabIndex) {
                        0 -> { /* Navigate to AddReminderScreen */ }
                        1 -> { /* Navigate to AddAppointmentScreen */ }
                    }
                },
                containerColor = Purple500,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new item"
                )
            }
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
                        text = { Text(title, fontFamily = FontFamily.Default) }
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
    // This is a placeholder for the notification scheduling logic.
    // In a real app, you would use a dedicated service like AlarmManager or WorkManager
    // to schedule an exact or inexact alarm that triggers a notification at the right time.
    // This requires specific permissions and setup in the AndroidManifest.xml.

    // A simple implementation might involve a BroadcastReceiver and AlarmManager.
    // Example:
    // val intent = Intent(context, NotificationReceiver::class.java).apply {
    //     putExtra("title", title)
    //     putExtra("message", message)
    // }
    // val pendingIntent = PendingIntent.getBroadcast(...)
    // val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    //     // Check for SCHEDULE_EXACT_ALARM permission
    // }
    // alarmManager.setExact(...)
}

@Composable
fun RemindersList(reminders: List<HealthReminder>) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderCard(reminder: HealthReminder, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentsList(appointments: List<Appointment>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Simple calendar view - a more robust implementation would use a library
        // or a custom composable with more features.
        // We'll display a header and then list the appointments for the selected date.
        val selectedDate by remember { mutableStateOf(LocalDate.now()) }
        Text(
            text = "Appointments on ${selectedDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault()))}",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        //
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(appointments.filter { it.date == selectedDate }) { appointment ->
                AppointmentCard(appointment = appointment) {
                    // Handle click to view appointment details
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
            .heightIn(min = 100.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HealthRemindersScreenPreview() {
    HealthRemindersScreen(navController = rememberNavController())
}

class RemindersScreen(navController: NavHostController) {

}
