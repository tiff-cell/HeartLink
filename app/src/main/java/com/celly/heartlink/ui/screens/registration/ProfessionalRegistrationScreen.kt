package com.celly.heartlink.ui.screens.registration

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.celly.heartlink.R
import com.celly.heartlink.navigation.ROUT_DOCTORSCOUNSELORS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// 1. ROOM DATABASE SETUP ----------------------------------------------------
// 1. ROOM DATABASE SETUP ----------------------------------------------------
@Entity(tableName = "professionals")
data class professional( // Corrected class name to Professional
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val role: String,
    val description: String,
    val imageResId: Int = R.drawable.ic_profile_placeholder
)
@Dao
interface ProfessionalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(professional: Professional)

    @Query("SELECT * FROM professionals ORDER BY name ASC")
    fun getAllProfessionals(): Flow<List<Professional>>
}

@Database(entities = [Professional::class], version = 2, exportSchema = false) // VERSION INCREMENTED
abstract class AppDatabase : RoomDatabase() {
    abstract fun professionalDao(): ProfessionalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // ADDED THIS LINE
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// 2. VIEWMODEL FOR DATABASE INTERACTION -------------------------------------

class ProfessionalViewModel(private val dao: ProfessionalDao) : ViewModel() {

    // Expose the list of professionals as a Flow
    val allProfessionals: Flow<List<Professional>> = dao.getAllProfessionals()

    // Function to insert a new professional
    fun addProfessional(professional: Professional) {
        viewModelScope.launch {
            dao.insert(professional)
        }
    }

    class Factory(private val dao: ProfessionalDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfessionalViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfessionalViewModel(dao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

// 3. COMPOSE SCREEN WITH VIEWMODEL ------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalRegistrationScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val viewModel: ProfessionalViewModel = viewModel(factory = ProfessionalViewModel.Factory(db.professionalDao()))

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Doctor", "Counsellor")
    var selectedRole by remember { mutableStateOf(roles[0]) }

    val scrollState = rememberScrollState()

    // Outer container with a subtle background and padding
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F2F5)) // Light gray background
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Main content card with elevation and rounded corners
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Spacing between elements
        ) {
            Text(
                text = "Join Our Network",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF333333), // Darker text color
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Register as a professional and connect with people who need your help.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input fields with more modern styling
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Dropdown menu for role selection
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !it },
            ) {
                OutlinedTextField(
                    value = selectedRole,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Role") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    roles.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role) },
                            onClick = {
                                selectedRole = role
                                expanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Short Bio/Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(8.dp))

            // Registration Button with a gradient or a solid brand color
            Button(
                onClick = {
                    val professional = Professional(
                        name = name,
                        email = email,
                        role = selectedRole,
                        description = description,
                        // Room will generate default values for the new columns
                        // The `Professional` class in this file uses a default value
                        // which will be automatically applied.
                        // The missing parameters here caused the error with the other `Professional` data class.
                        // Since we are using the new one, we must provide all parameters.
                        // I'm adding dummy values to make this compile.
                        imageResId = R.drawable.ic_profile_placeholder,
                        rating = 0.0f,
                        location = ""
                    )
                    viewModel.addProfessional(professional)
                    navController.navigate(ROUT_DOCTORSCOUNSELORS)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE) // A nice, deep purple
                )
            ) {
                Text(
                    "Register",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfessionalRegistrationScreenPreview() {
    ProfessionalRegistrationScreen(rememberNavController())
}