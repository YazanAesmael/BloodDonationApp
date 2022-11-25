package com.android.bloodon

class UserDetails(
    val bloodGroup: String,
    val age: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String,
    val post: String
)

data class PostDetails(
    var post: String? = null,
    var bloodGroup: String? = null,
    var age: String? = null,
    var fullName: String? = null,
    var gender: String? = null,
    var phoneNumber: String? = null
)

class BloodData(
    val name: String,
    val uid: String,
    val bloodGroup: String,
    val bloodUnits: String,
    val priority: String,
    val note: String,
    val state: String,
)

class NoteData(
    val note: String,
    val senderUid: String,
    val receiverUid: String
)


