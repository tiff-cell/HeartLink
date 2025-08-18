package com.celly.heartlink.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.data.UserDatabase
import com.celly.heartlink.repository.UserRepository
import com.celly.heartlink.ui.screens.about.AboutScreen
import com.celly.heartlink.ui.screens.clinics.ClinicFinderScreen
import com.celly.heartlink.ui.screens.clinics.MoodTrackerScreen
import com.celly.heartlink.ui.screens.community.CommunityScreen
import com.celly.heartlink.ui.screens.dailyaffirmation.DailyAffirmationScreen
import com.celly.heartlink.ui.screens.home.HomeScreen
import com.celly.heartlink.ui.screens.journal.JournalScreen
import com.celly.heartlink.ui.screens.progress.ProgressScreen
import com.celly.heartlink.ui.screens.reminders.RemindersScreen
import com.celly.heartlink.ui.screens.resources.ResourcesScreen
import com.celly.heartlink.ui.screens.settings.SettingsScreen
import com.celly.heartlink.ui.screens.splash.SplashScreen
import com.celly.heartlink.ui.screens.welcoming.WelcomeMessageScreen
import com.celly.heartlink.ui.screens.wellness.WellnessScreen
import com.celly.heartlink.viewmodel.AuthViewModel
import com.celly.swaggy.ui.theme.screens.auth.LoginScreen
import com.celly.swaggy.ui.theme.screens.auth.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {

    val context = LocalContext.current


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }
        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }
        composable(ROUT_SPLASH) {
           SplashScreen(navController)
        }
        composable(ROUT_COMMUNITY) {
            CommunityScreen(navController)
        }
        composable(ROUT_JOURNAL) {
            JournalScreen(navController)
        }
        composable(ROUT_RESOURCES) {
            ResourcesScreen(navController)
        }
        composable(ROUT_REMINDERS) {
            RemindersScreen(navController)
        }
        composable(ROUT_WELCOMING) {
            WelcomeMessageScreen(navController)
        }
        composable(ROUT_PROGRESS) {
            ProgressScreen(navController)
        }
        composable(ROUT_DAILYAFFIRMATION) {
            DailyAffirmationScreen(navController)
        }
        composable(ROUT_MOODTRACKER) {
            MoodTrackerScreen(navController)
        }
        composable(ROUT_SETTINGS) {
           SettingsScreen(navController)
        }
        composable(ROUT_WELLNESS) {
            WellnessScreen(navController)
        }
        composable(ROUT_CLINIC) {
            // Assuming API_KEY is a constant or variable with your Google Maps API key
            val apiKey = "YOUR_GOOGLE_MAPS_API_KEY_HERE"
            ClinicFinderScreen(apiKey)
        }


        //AUTHENTICATION

        // Initialize Room Database and Repository for Authentication
        val appDatabase = UserDatabase.getDatabase(context)
        val authRepository = UserRepository(appDatabase.userDao())
        val authViewModel: AuthViewModel = AuthViewModel(authRepository)
        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel, navController) {
                navController.navigate(ROUT_HOME) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }
        //End of Authentication


    }
}

