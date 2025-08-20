package com.celly.heartlink.ui.screens.welcoming

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.celly.heartlink.R
import com.celly.heartlink.navigation.ROUT_HOME
import com.celly.heartlink.navigation.ROUT_REGISTER

// Define a new, softer color palette for this specific screen
val GradientStart = Color(0xFFEAD6FD) // Lighter purple
val GradientEnd = Color(0xFFF7F7F7)   // Soft gray

@Composable
fun WelcomeMessageScreen(navController: NavController) {
    // Pulsating radial background animation
    val infiniteTransition = rememberInfiniteTransition()
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Button press animation
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    val messageText = buildAnnotatedString {
        append("Welcome to a place where your heart is our link. ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)) {
            append("You are seen. You are loved. You are appreciated.")
        }
        append(" The journey ahead is yours to write, and you have the strength to conquer it all. This is not the end; it's the beginning of a beautiful new chapter. Let's start it together.")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFEAD6FD).copy(alpha = 0.5f + animatedProgress * 0.5f), Color.White),
                    radius = 800f * (1 + animatedProgress)
                )
            ),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // New: Image or Logo at the top
            Image(
                painter = painterResource(id = R.drawable.img), // Replace with your logo's resource ID
                contentDescription = "Heartlink Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Hello, Brave Heart.",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Cursive,
                color = Color(0xFF673AB7),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = messageText,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                color = Color(0xFF616161)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { navController.navigate(ROUT_REGISTER)  },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(56.dp)
                    .scale(scale)
                    .pointerInput(Unit) {
                        detectPressAndRelease(
                            onPress = { isPressed = true },
                            onRelease = { isPressed = false }
                        )
                    },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Begin My Journey",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AnimatedHeart() {
    val infiniteTransition = rememberInfiniteTransition()
    val xOffset by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val yOffset by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = painterResource(id = R.drawable.heart_icon), // You will need to add a heart icon here
        contentDescription = "Animated Heart",
        modifier = Modifier
            .size(70.dp)
            .offset(x = xOffset.dp, y = yOffset.dp)
    )
}

// Helper function for press and release detection
suspend fun androidx.compose.ui.input.pointer.PointerInputScope.detectPressAndRelease(
    onPress: () -> Unit,
    onRelease: () -> Unit
) {
    awaitEachGesture {
        awaitFirstDown(requireUnconsumed = false)
        onPress()
        waitForUpOrCancellation()
        onRelease()
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeMessageScreenPreview() {
    WelcomeMessageScreen(navController = rememberNavController())
}