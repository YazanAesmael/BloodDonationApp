package com.android.bloodon

sealed class BloodDataState {
    class Success(val postDetails: MutableList<BloodRequestsData>): BloodDataState()
    class Failure(val message: String): BloodDataState()
    object Loading: BloodDataState()
    object Empty: BloodDataState()
}

sealed class NotificationDataState {
    class Success(val postDetails: MutableList<NotificationsData>): NotificationDataState()
    class Failure(val message: String): NotificationDataState()
    object Loading: NotificationDataState()
    object Empty: NotificationDataState()
}