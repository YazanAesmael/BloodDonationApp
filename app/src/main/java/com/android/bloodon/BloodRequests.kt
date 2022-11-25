package com.android.bloodon

data class BloodRequestsData(
    var name: String = "",
    var note: String = "",
    var priority: String = "",
    var bloodUnits: String = "",
    var bloodGroup: String = "",
    var state: String = "",
    var userUid: String = ""
)

data class NotificationsData(
    var name: String = "",
    var note: String = "",
    var bloodGroup: String = "",
    var state: String = "",
    var number: String = "",
)

