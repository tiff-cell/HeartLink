package com.celly.heartlink.ui.screens.clinics

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.compose.*
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Data Models for Google Places API Response
data class PlacesResponse(
    val results: List<Place>
)

data class Place(
    val name: String,
    val geometry: Geometry,
    @SerializedName("vicinity") val address: String
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)

// Retrofit API Service
interface GooglePlacesApiService {
    @GET("place/nearbysearch/json")
    suspend fun findNearbyPlaces(
        @Query("location") location: String, // "lat,lng"
        @Query("radius") radius: Int = 5000, // 5km radius
        @Query("type") type: String = "hospital|pharmacy",
        @Query("keyword") keyword: String = "HIV clinic antiretroviral",
        @Query("key") apiKey: String
    ): PlacesResponse
}

// Composable for the Clinic Finder Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicFinderScreen(apiKey: String) { // FIX: Changed type from NavHostController to String
    val context = LocalContext.current
    val placesApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GooglePlacesApiService::class.java)
    }

    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var clinics by remember { mutableStateOf<List<Place>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // State to track if permission has been granted
    var isPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            isPermissionGranted = isGranted
            if (!isGranted) {
                Toast.makeText(context, "Location permission is required to find nearby clinics.", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        }
    )

    LaunchedEffect(isPermissionGranted) {
        if (isPermissionGranted) {
            try {
                // The `fetchLocationAndClinics` function is now correctly called with a String apiKey
                val (loc, places) = fetchLocationAndClinics(context, placesApiService, apiKey)
                userLocation = loc
                clinics = places
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                isLoading = false
            }
        } else {
            // Request permission on first composition if not already granted
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Find Clinics",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF673AB7))
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && isPermissionGranted) {
                CircularProgressIndicator()
                Text(text = "Finding clinics...", modifier = Modifier.padding(top = 80.dp))
            } else if (userLocation != null) {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(userLocation!!, 13f)
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = userLocation!!),
                        title = "Your Location"
                    )
                    clinics.forEach { clinic ->
                        Marker(
                            state = MarkerState(
                                position = LatLng(clinic.geometry.location.lat, clinic.geometry.location.lng)
                            ),
                            title = clinic.name,
                            snippet = "Address: ${clinic.address}"
                        )
                    }
                }
            } else if (!isPermissionGranted) {
                Text("Location permission is required to show nearby clinics.")
            } else {
                Text("Could not get your location. Please ensure location services are enabled.")
            }
        }
    }
}

// Function to fetch location and clinics
@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
private suspend fun fetchLocationAndClinics(
    context: Context,
    apiService: GooglePlacesApiService,
    apiKey: String,
): Pair<LatLng, List<Place>> {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val location = fusedLocationClient.lastLocation.await()

    if (location == null) {
        throw IllegalStateException("Location is null. Check device settings.")
    }

    val userLatLng = LatLng(location.latitude, location.longitude)
    val response = apiService.findNearbyPlaces(
        location = "${location.latitude},${location.longitude}",
        apiKey = apiKey
    )
    return Pair(userLatLng, response.results)
}

// Preview Composable
@Preview(showBackground = true)
@Composable
fun ClinicFinderScreenPreview() {
    // You must pass a String for the API key in the preview
    ClinicFinderScreen(apiKey = "YOUR_DUMMY_API_KEY")
}