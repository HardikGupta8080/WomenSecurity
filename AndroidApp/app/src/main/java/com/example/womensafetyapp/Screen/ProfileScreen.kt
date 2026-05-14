package com.example.womensafetyapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.womensafetyapp.data.TokenManager
import com.example.womensafetyapp.data.UserProfile
import com.example.womensafetyapp.util.logoutAndReturnToAuth

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val tokenManager = remember(context) { TokenManager(context) }
    val userProfile = remember { tokenManager.getUserProfile() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        ProfileHeader()
        
        // User Profile Card
        UserProfileCard(userProfile = userProfile)
        
        // Primary Emergency Contact
        PrimaryEmergencyContactCard()
        
        // Settings Options
        SettingsSection()
        
        // Sign Out Button
        SignOutButton(
            onSignOut = {
                logoutAndReturnToAuth(context, tokenManager)
            }
        )
        
        // Bottom spacing
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ProfileHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Profile Settings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Manage your account and safety preferences",
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun UserProfileCard(userProfile: UserProfile) {
    val username = userProfile.username.ifBlank { "User" }
    val email = userProfile.email.ifBlank { "Not available" }
    val phoneNumber = userProfile.phoneNumber.ifBlank { "Not available" }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Avatar
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFE91E63), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            
            // User Information
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = username,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                ProfileDetail(label = "Username", value = username)
                ProfileDetail(label = "Email", value = email)
                ProfileDetail(label = "Phone", value = phoneNumber)
            }
        }
    }
}

@Composable
fun ProfileDetail(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            lineHeight = 18.sp
        )
    }
}

@Composable
fun PrimaryEmergencyContactCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Primary Emergency Contact",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFD32F2F)
            )
            Text(
                text = "Mom - +1 (555) 987-6543",
                fontSize = 14.sp,
                color = Color(0xFFD32F2F),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SettingsSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SettingsItem(
            icon = Icons.Default.Settings,
            title = "Account Settings",
            onClick = { 
                // TODO: Navigate to account settings
            }
        )
        
        SettingsItem(
            icon = Icons.Default.Security,
            title = "Privacy & Security",
            onClick = { 
                // TODO: Navigate to privacy settings
            }
        )
        
        SettingsItem(
            icon = Icons.Default.Notifications,
            title = "Notification Settings",
            onClick = { 
                // TODO: Navigate to notification settings
            }
        )
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF666666),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Go to $title",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SignOutButton(onSignOut: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSignOut() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Sign Out",
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Text(
                text = "Sign Out",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFE91E63),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
