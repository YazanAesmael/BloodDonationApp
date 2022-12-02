package com.android.bloodon

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainViewModel: ViewModel() {
    val response: MutableState<BloodDataState> = mutableStateOf(BloodDataState.Empty)

    init {
        fetchBloodDataFromFirebase()
    }

    private fun fetchBloodDataFromFirebase() {
        response.value = BloodDataState.Loading
        val database = Firebase.database("https://blood-donor-7d295-default-rtdb.asia-southeast1.firebasedatabase.app")
        val uid = FirebaseAuth.getInstance().uid.toString()
        database.getReference("Users/").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    postDetails.clear()
                    for (dataSnap in snapshot.children) {
                        val userUid = dataSnap.key.toString()

                        fun getUnit() {
                            database.getReference("Users/$userUid/User Details/post/bloodUnits/")
                                .get().addOnSuccessListener {
                                bloodUnitPost = it.value.toString()
                            }
                        }
                        fun getNote() {
                            database.getReference("Users/$userUid/User Details/post/").child("note/").get().addOnSuccessListener {
                                note = it.value.toString()
                            }
                        }
                        fun getPriority() {
                            database.getReference("Users/$userUid/User Details/post").child("priority/").get().addOnSuccessListener {
                                priority= it.value.toString()
                            }
                        }
                        fun getGroup() {
                            database.getReference("Users/$userUid/User Details/post").child("bloodGroup/").get().addOnSuccessListener {
                                bloodGroup= it.value.toString()
                            }
                        }
                        fun getName() {
                            database.getReference("Users/$userUid/User Details/post").child("name/").get().addOnSuccessListener {
                                fullName= it.value.toString()
                            }
                        }
                        fun getState() {
                            database.getReference("Users/$userUid/User Details/post").child("state/").get().addOnSuccessListener {
                                state= it.value.toString()
                            }
                        }

                        getPriority()
                        getNote()
                        getUnit()
                        getGroup()
                        getName()
                        getState()

                          database.getReference("Users/$userUid/User Details/post/")
                            .addValueEventListener(
                                object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        myUidPosts.clear()
                                        snapshot.children.forEach {
                                            when (it.key) {
                                                "note" -> {
                                                    val postList = BloodRequestsData(fullName, note, priority, bloodUnitPost, bloodGroup, state, userUid)
                                                    if (postDetails.contains(postList)) return
                                                    if (postList.userUid == uid) return
                                                    postDetails.add(postList)
                                                    response.value = BloodDataState.Success(postDetails)
                                                }
                                            }
                                        }
                                    }
                                    override fun onCancelled(error: DatabaseError) {
                                        response.value = BloodDataState.Failure(error.message)
                                    }
                                })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = BloodDataState.Failure(error.message)
                }

            }
        )
    }
}