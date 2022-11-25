package com.android.bloodon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
private lateinit var auth: FirebaseAuth
internal val database = Firebase.database("https://blood-donor-7d295-default-rtdb.asia-southeast1.firebasedatabase.app")
val myUidPosts = mutableListOf<String>()
val postDetails = mutableListOf<BloodRequestsData>()
val notificationDetails = mutableListOf<NotificationsData>()
val uid = FirebaseAuth.getInstance().uid.toString()

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun signInWithPhoneAuthCredential(context: Context, credential: PhoneAuthCredential, navController: NavController) {
    context.getActivity()?.let {
        auth = FirebaseAuth.getInstance()
        auth.signInWithCredential(credential)
            .addOnCompleteListener(it) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "verification success!", Toast.LENGTH_SHORT).show()
                    navController.navigate("age_screen")
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, "something went wrong...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}

fun verifyPhoneNumberWithCode(context: Context, verificationId: String, code: String, navController: NavController) {
    val credential = PhoneAuthProvider.getCredential(verificationId, code)
    signInWithPhoneAuthCredential(context,credential, navController)
}

fun onLoginClicked (context: Context, phoneNumber: String, navController: NavController) {
    auth = FirebaseAuth.getInstance()
    auth.setLanguageCode("en")
    val callback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(context,credential, navController)
        }

        override fun onVerificationFailed(p0: FirebaseException) {  }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            storedVerificationId = verificationId
            navController.navigate("authPhone_screen")
        }
    }
    val options = context.getActivity()?.let {
        PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(it)
            .setCallbacks(callback)
            .build()
    }
    if (options != null) {
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}

fun uploadData(context: Context) {
    val uid = FirebaseAuth.getInstance().uid.toString()
    val myRef = database.getReference("Users/$uid/User Details")
    val user = UserDetails(bloodGroup, age, fullName, gender, phoneNumber, post)
    myRef.setValue(user)
        .addOnSuccessListener {
            Toast.makeText(context, "account created successfully!", Toast.LENGTH_SHORT).show()
        }
}

fun getUserName() {
    val uid = FirebaseAuth.getInstance().uid.toString()
    database.getReference("Users/$uid/User Details/fullName").get().addOnSuccessListener {
        userName = it.value.toString()
    }
}

fun getPhoneNumber() {
    database.getReference("Users/$uid/User Details")
        .child("phoneNumber/").get().addOnSuccessListener {
            phoneNumberProfile = it.value.toString()
            Log.d(log, "userPhoneNumber: $phoneNumberProfile")
        }
}

fun getGroup() {
    database.getReference("Users/$uid/User Details")
        .child("bloodGroup/").get().addOnSuccessListener {
            bloodGroupProfile= it.value.toString()
        }
}

fun getName() {
    database.getReference("Users/$uid/User Details")
        .child("fullName/").get().addOnSuccessListener {
            fullNameProfile= it.value.toString()
        }
}

fun getState() {
    database.getReference("Users/$uid/User Details/post")
        .child("state/").get().addOnSuccessListener {
            stateProfile = it.value.toString()
        }
}

fun getGender() {
    database.getReference("Users/$uid/User Details")
        .child("gender/").get().addOnSuccessListener {
            genderProfile = it.value.toString()
        }
}

fun getAge() {
    database.getReference("Users/$uid/User Details")
        .child("age/").get().addOnSuccessListener {
            ageProfile = it.value.toString()
        }
}