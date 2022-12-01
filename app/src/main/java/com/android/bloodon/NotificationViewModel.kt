package com.android.bloodon

import android.util.Log
import androidx.compose.animation.core.snap
import androidx.compose.runtime.Composer.Companion.Empty
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotificationViewModel: ViewModel() {
    val response: MutableState<NotificationDataState> = mutableStateOf(NotificationDataState.Empty)

    init {
        fetchNotificationBloodDataFromFirebase()
    }

    private fun fetchNotificationBloodDataFromFirebase() {
        val uid = FirebaseAuth.getInstance().uid.toString()
        response.value = NotificationDataState.Loading
        val database = Firebase.database("https://blood-donor-7d295-default-rtdb.asia-southeast1.firebasedatabase.app")
        database.getReference("Users/$uid/User Details/Messages/Received/")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        notificationDetails.clear()
                        for (dataSnap in snapshot.children) {
                            val rUid = dataSnap.key
                            fun getNote() {
                                database.getReference("Users/$uid/User Details/Messages/Received/$rUid")
                                    .child("note/").get().addOnSuccessListener {
                                        notNote = it.value.toString()
                                    }
                            }
                            fun getPhoneNumber() {
                                database.getReference("Users/$rUid/User Details")
                                    .child("phoneNumber/").get().addOnSuccessListener {
                                        notNumber = it.value.toString()
                                    }
                            }
                            fun getGroup() {
                                database.getReference("Users/$rUid/User Details")
                                    .child("bloodGroup/").get().addOnSuccessListener {
                                        notBloodGroup= it.value.toString()
                                    }
                            }
                            fun getName() {
                                database.getReference("Users/$rUid/User Details")
                                    .child("fullName/").get().addOnSuccessListener {
                                        notName= it.value.toString()
                                    }
                            }
                            fun getState() {
                                database.getReference("Users/$rUid/User Details/post")
                                    .child("state/").get().addOnSuccessListener {
                                        notState= it.value.toString()
                                    }
                            }

                            getPhoneNumber()
                            getNote()
                            getName()
                            getGroup()
                            getState()
                            database.getReference("Users/$uid/User Details/Messages/Received/$rUid/")
                                .addValueEventListener(
                                    object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            snapshot.children.forEach {
                                                when (it.key) {
                                                    "note" -> {
                                                        val notificationList = NotificationsData(
                                                            notName,
                                                            notNote,
                                                            notBloodGroup,
                                                            notState,
                                                            notNumber
                                                        )
                                                        if (notificationDetails.contains(notificationList)) return
                                                        notificationDetails.add(notificationList)
                                                        response.value = NotificationDataState.Success(notificationDetails)
                                                    }
                                                }
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            response.value = NotificationDataState.Failure(error.message)
                                        }
                                    })
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        response.value = NotificationDataState.Failure(error.message)
                    }
                }
            )
    }
}