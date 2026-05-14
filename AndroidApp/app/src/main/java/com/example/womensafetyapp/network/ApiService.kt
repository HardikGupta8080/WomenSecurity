package com.example.womensafetyapp.network

import com.example.womensafetyapp.data.EmergencyContact
import com.example.womensafetyapp.data.AuthResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Get all emergency contacts for a user
    @GET("contacts")
    suspend fun getEmergencyContacts(
        @Header("Authorization") token: String? = null
    ): Response<ContactsResponse>
    
    // Add a new emergency contact
    @POST("contacts")
    suspend fun addEmergencyContact(
        @Header("Authorization") token: String? = null,
        @Body contact: CreateContactRequest
    ): Response<EmergencyContact>
    
    // Update an existing contact
    @PUT("contacts/{id}")
    suspend fun updateEmergencyContact(
        @Path("id") contactId: String,
        @Header("Authorization") token: String? = null,
        @Body contact: UpdateContactRequest
    ): Response<EmergencyContact>
    
    // Delete a contact
    @DELETE("contacts/{id}")
    suspend fun deleteEmergencyContact(
        @Path("id") contactId: String,
        @Header("Authorization") token: String? = null
    ): Response<Unit>
    
    // Auth endpoints
    @POST("auth/login")
    suspend fun login(@Body request: com.example.womensafetyapp.data.LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: com.example.womensafetyapp.data.RegisterRequest): Response<AuthResponse>

    // Get contact by ID
    @GET("contacts/{id}")
    suspend fun getContactById(
        @Path("id") contactId: String,
        @Header("Authorization") token: String? = null
    ): Response<EmergencyContact>
}

// Response models
data class ContactsResponse(
    val success: Boolean,
    val message: String?,
    val data: List<EmergencyContact>
)

data class CreateContactRequest(
    val name: String,
    val phoneNumber: String,
    val relationship: String
)

data class UpdateContactRequest(
    val name: String,
    val phoneNumber: String,
    val relationship: String
)

// API Error Response
data class ApiError(
    val success: Boolean = false,
    val message: String,
    val errorCode: String? = null
)
