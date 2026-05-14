package com.example.womensafetyapp.main


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.womensafetyapp.R
import com.example.womensafetyapp.data.TokenManager
import com.example.womensafetyapp.util.logoutAndReturnToAuth

@Preview(showBackground = true)
@Composable
fun TopHeader() {
    val context = LocalContext.current
    val tokenManager = remember(context) { TokenManager(context) }
    val userProfile = remember { tokenManager.getUserProfile() }
    val displayName = userProfile.username.ifBlank { "User" }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.woman), // replace with your logo
                contentDescription = "Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("SafeGuard", fontWeight = FontWeight.Bold)
                Text("Welcome, $displayName", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF4CAF50), shape = RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Online", color = Color.White, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.ExitToApp,
                contentDescription = "Logout",
                modifier = Modifier.clickable {
                    logoutAndReturnToAuth(context, tokenManager)
                }
            )
        }
    }
}
