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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.celly.heartlink.R
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_JOURNAL
import com.celly.heartlink.navigation.ROUT_MOODTRACKER
import com.celly.heartlink.navigation.ROUT_RESOURCES
import com.celly.heartlink.navigation.ROUT_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// region ROOM DATABASE SETUP
@Entity(tableName = "health_reminders")
data class HealthReminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val time: String,
    val type: ReminderType
)

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val doctorName: String,
    val date: LocalDate,
    val time: String,
    val location: String
)

enum class ReminderType {
    MEDICATION, LAB_TEST
}

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: HealthReminder)

    @Query("SELECT * FROM health_reminders ORDER BY time ASC")
    fun getAllReminders(): Flow<List<HealthReminder>>
}

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments ORDER BY date ASC")
    fun getAllAppointments(): Flow<List<Appointment>>
}

@Database(entities = [HealthReminder::class, Appointment::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "reminders_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// region VIEWMODEL
class ReminderViewModel(private val reminderDao: ReminderDao, private val appointmentDao: AppointmentDao) : ViewModel() {
    val allReminders: Flow<List<HealthReminder>> = reminderDao.getAllReminders()
    val allAppointments: Flow<List<Appointment>> = appointmentDao.getAllAppointments()

    fun addReminder(reminder: HealthReminder) {
        viewModelScope.launch {
            reminderDao.insertReminder(reminder)
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentDao.insertAppointment(appointment)
        }
    }

    class Factory(private val reminderDao: ReminderDao, private val appointmentDao: AppointmentDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ReminderViewModel(reminderDao, appointmentDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
// endregion VIEWMODEL

// region COLORS
val Purple500 = Color(0xFF673AB7)
val Grey700 = Color(0xFF616161)
val LightGray = Color(0xFFF5F5F5)
val Teal200 = Color(0xFF03DAC5)
val Pink500 = Color(0xFFE91E63)
// endregion COLORS

// region MAIN SCREEN
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthRemindersScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Reminders", "Appointments")
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val viewModel: ReminderViewModel = viewModel(factory = ReminderViewModel.Factory(db.reminderDao(), db.appointmentDao()))

    // State for showing dialogs
    var showAddReminderDialog by remember { mutableStateOf(false) }
    var showAddAppointmentDialog by remember { mutableStateOf(false) }

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
                containerColor = Purple500
            ) {
                IconButton(onClick = { navController.navigate(ROUT_HOME) }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                        Text(text = "Home", fontSize = 10.sp, color = Color.White)
                    }
                }
                IconButton(onClick = { navController.navigate(ROUT_MOODTRACKER) }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Face, contentDescription = "Check-in", tint = Color.White)
                        Text(text = "Check-in", fontSize = 10.sp, color = Color.White)
                    }
                }
                IconButton(onClick = { navController.navigate(ROUT_JOURNAL) }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Journal", tint = Color.White)
                        Text(text = "Journal", fontSize = 10.sp, color = Color.White)
                    }
                }
                IconButton(onClick = { navController.navigate(ROUT_RESOURCES) }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Resources", tint = Color.White)
                        Text(text = "Resources", fontSize = 10.sp, color = Color.White)
                    }
                }
                IconButton(onClick = { navController.navigate(ROUT_SETTINGS) }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                        Text(text = "Settings", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    when (selectedTabIndex) {
                        0 -> showAddReminderDialog = true
                        1 -> showAddAppointmentDialog = true
                    }
                },
                modifier = Modifier.padding(16.dp),
                containerColor = Pink500,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = "Add new") },
                text = { Text(text = if (selectedTabIndex == 0) "Add Reminder" else "Add Appointment", fontWeight = FontWeight.Bold) }
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
                0 -> RemindersList(viewModel)
                1 -> AppointmentsList(viewModel)
            }
        }

        // Display dialogs based on state
        if (showAddReminderDialog) {
            AddReminderDialog(
                onDismiss = { showAddReminderDialog = false },
                onAddReminder = { reminder ->
                    viewModel.addReminder(reminder)
                    showAddReminderDialog = false
                }
            )
        }
        if (showAddAppointmentDialog) {
            AddAppointmentDialog(
                onDismiss = { showAddAppointmentDialog = false },
                onAddAppointment = { appointment ->
                    viewModel.addAppointment(appointment)
                    showAddAppointmentDialog = false
                }
            )
        }
    }
}
// endregion MAIN SCREEN

// region REMINDERS LIST
@Composable
fun RemindersList(viewModel: ReminderViewModel) {
    val reminders by viewModel.allReminders.collectAsState(initial = emptyList())
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
                ReminderCard(reminder = reminder) { /* Handle click */ }
            }
        }
    }
}
// endregion REMINDERS LIST

// region APPOINTMENTS LIST
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentsList(viewModel: ReminderViewModel) {
    val appointments by viewModel.allAppointments.collectAsState(initial = emptyList())
    if (appointments.isEmpty()) {
        EmptyStateMessage("You have no upcoming appointments.")
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(appointments) { appointment ->
                AppointmentCard(appointment = appointment) { /* Handle click */ }
            }
        }
    }
}
// endregion APPOINTMENTS LIST

// region DIALOGS AND HELPER COMPOSABLES
@Composable
fun EmptyStateMessage(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderDialog(onDismiss: () -> Unit, onAddReminder: (HealthReminder) -> Unit) {
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val reminderTypes = ReminderType.entries.map { it.name.replace("_", " ") }
    var selectedType by remember { mutableStateOf(reminderTypes[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Reminder") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Reminder Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (e.g., 8:00 AM)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !it },
                ) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        reminderTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotEmpty() && time.isNotEmpty()) {
                        onAddReminder(
                            HealthReminder(
                                title = title,
                                time = time,
                                type = ReminderType.valueOf(selectedType.replace(" ", "_"))
                            )
                        )
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentDialog(onDismiss: () -> Unit, onAddAppointment: (Appointment) -> Unit) {
    var doctorName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    // A more robust date picker would be used in a real app
    var date by remember { mutableStateOf(LocalDate.now().toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Appointment") },
        text = {
            Column {
                OutlinedTextField(
                    value = doctorName,
                    onValueChange = { doctorName = it },
                    label = { Text("Doctor's Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (e.g., 2025-08-22)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (e.g., 2:30 PM)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (doctorName.isNotEmpty() && location.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                        onAddAppointment(
                            Appointment(
                                doctorName = doctorName,
                                location = location,
                                date = LocalDate.parse(date),
                                time = time
                            )
                        )
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

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

@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HealthRemindersScreenPreview() {
    HealthRemindersScreen(navController = rememberNavController())
}