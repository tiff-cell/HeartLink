package com.celly.heartlink.ui.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// This would be your Room database entity class
data class Professional(
    val id: Int = 0,
    val name: String,
    val email: String,
    val role: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalRegistrationScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Doctor", "Counsellor")
    var selectedRole by remember { mutableStateOf(roles[0]) }

    // State to hold the registered professional details for display
    var registeredProfessional by remember { mutableStateOf<Professional?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Professional Registration",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Name TextField
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        // Role Dropdown Menu
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
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
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
        Spacer(Modifier.height(16.dp))

        // Description TextField
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Short Bio/Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(Modifier.height(24.dp))

        // Registration Button
        Button(
            onClick = {
                // Here you would integrate with your Room database
                // For demonstration, we'll just update the state
                val professional = Professional(
                    name = name,
                    email = email,
                    role = selectedRole,
                    description = description
                )
                // Save to Room database (placeholder)
                // db.professionalDao().insert(professional)

                // Update the state to display the registered data
                registeredProfessional = professional

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        // Display the registered professional details on the screen
        registeredProfessional?.let { professional ->
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "Registered Professional Details:",
                style = MaterialTheme.typography.titleLarge
            )
            Text("Name: ${professional.name}")
            Text("Role: ${professional.role}")
            Text("Email: ${professional.email}")
            Text("Description: ${professional.description}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfessionalRegistrationScreenPreview() {
    ProfessionalRegistrationScreen(rememberNavController())
}
