package com.celly.heartlink.ui.screens.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ViewDoctorsCounsellors(navController: NavController) {
    // In a real application, you would fetch the list of professionals from your Room database here
    // Example: val professionals by professionalDao.getAllProfessionals().collectAsState(initial = emptyList())
    // For this demonstration, we'll use a placeholder list
    val professionals = listOf(
        Professional(1, "Dr. Jane Doe", "jane.doe@example.com", "Doctor", "Specializes in cardiology."),
        Professional(2, "Counsellor John Smith", "john.smith@example.com", "Counsellor", "Focuses on mental health and wellness.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Doctors & Counsellors",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Divider()

        if (professionals.isEmpty()) {
            Text("No professionals registered yet.")
        } else {
            professionals.forEach { professional ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = professional.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = professional.role,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = professional.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Email: ${professional.email}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewDoctorsCounsellorsPreview() {
    ViewDoctorsCounsellors(rememberNavController())
}