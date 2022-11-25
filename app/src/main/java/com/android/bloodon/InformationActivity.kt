package com.android.bloodon

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MarkEmailUnread
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

internal val poppinsFont = FontFamily(Font(R.font.poppins_regular))
private lateinit var auth: FirebaseAuth
var userName = ""
var textCompose = ""
val bloodGroups = listOf("A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-")
val bloodUnits = listOf("1", "2", "3", "4", "5")
val indianCities = listOf(
    "Andhra Pradesh",
    "Arunachal Pradesh",
    "Assam",
    "Bihar",
    "Chhattisgarh",
    "Goa",
    "Gujarat",
    "Haryana",
    "Himachal Pradesh",
    "Jammu and Kashmir",
    "Jharkhand",
    "Karnataka",
    "Kerala",
    "Madhya Pradesh",
    "Maharashtra",
    "Manipur",
    "Meghalaya",
    "Mizoram",
    "Nagaland",
    "Odisha",
    "Punjab",
    "Rajasthan",
    "Sikkim",
    "Tamil Nadu",
    "Telangana",
    "Tripura",
    "Uttarakhand",
    "Uttar Pradesh",
    "West Bengal",
    "Andaman and Nicobar Islands",
    "Chandigarh",
    "Dadra and Nagar Haveli",
    "Daman and Diu",
    "Delhi",
    "Lakshadweep",
    "Puducherry"
)
var bloodGroupProfile: String = "A+"
var ageProfile: String = "22"
var fullNameProfile: String = "Yazan Aesmael"
var genderProfile: String = "Male"
var phoneNumberProfile: String = "7205698527"
var stateProfile: String = "Odisha"

fun getSplashText(): String {
    val splashTextInfo = listOf(
        "Donating blood has benefits for your emotional and physical health",
        "Donating blood helps get rid of negative feelings",
        "Donating blood improves your emotional well-being",
        "Donating blood benefits your physical health",
        "Donating blood provides a sense of belonging and reduce isolation",
    )
    textCompose = splashTextInfo.shuffled()[0]
    return splashTextInfo.random()
}

@Composable
fun SplashScreen( navController: NavController) {
    getAge()
    getName()
    getGroup()
    getPhoneNumber()
    getState()
    getGender()
    getUserName()
    Log.d(log2, "textCompose: $textCompose")
    MainViewModel().response
    auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    if (currentUser?.uid != null) {
        LaunchedEffect(key1 = true) {
            delay(2000L)
            navController.navigate("mainBloodScreen_screen") {
                popUpTo(0)
            }
        }
    }else {
        LaunchedEffect(key1 = true) {
            delay(2000L)
            navController.navigate("phoneNumber_screen") {
                popUpTo(0)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = "Blood Donation Logo",
                contentScale = ContentScale.Fit
            )
            Box(modifier = Modifier.padding(22.dp)) {
                CenteredText(
                    text = textCompose,
                    color = Color.Black,
                    fontSize = 22,
                    fontWeight = FontWeight.SemiBold,
                    topPadding = 82
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(clicked: Boolean) {
    val downClicked = remember{
        mutableStateOf(clicked)
    }
    if (downClicked.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White,
            onDismissRequest = {
                downClicked.value = !downClicked.value
            },
            title = null,
            text = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
                ) {

                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp, end = 8.dp, start = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                            .fillMaxWidth()
                            .focusable(true),
                        onClick = {
                                  downClicked.value = !downClicked.value
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = bloodRed,
                            contentColor = Color.White)
                    ) {
                        Text("save", fontFamily = poppinsFont)
                    }
                }
            },
        )
    } // Alert Dialog
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TextViewCompose(
            text = "Profile",
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(start = 10.dp, end = 10.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier
            .padding(start = 80.dp, end = 80.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(bloodRed)
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .border(Dp.Hairline, Color.LightGray, RoundedCornerShape(10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileRowCompose("Full Name", fullNameProfile, true)
            ProfileRowCompose("Phone Number", phoneNumberProfile, false)
            ProfileRowCompose("Location", stateProfile, true)
            ProfileRowCompose("Gender", genderProfile, true)
            ProfileRowCompose("Blood Group", bloodGroupProfile, false)
            ProfileRowCompose("Age", "$ageProfile years old", true)
            Row(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        downClicked.value = !downClicked.value
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    border = BorderStroke(Dp.Hairline, Color.LightGray),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit", tint = Color.DarkGray)
                }
                Button(
                    onClick = {
                        downClicked.value = !downClicked.value
                    },
                    modifier = Modifier
                        .padding(start = 0.dp, end = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    border = BorderStroke(Dp.Hairline, Color.LightGray),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete", tint = Color.DarkGray)
                }
            }
        }

    }
}

@Composable
fun LazyRequestsAround() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        SetBloodData(viewModel = MainViewModel())
    }
}

@Composable
fun LazyNotificationsAround() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        SetNotificationData(viewModel = NotificationViewModel())
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun MainBloodScreen(navController: NavController) {
    val uid = FirebaseAuth.getInstance().uid.toString()
    val openRequestDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        if (openRequestDialog.value) {
            AlertDialog(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = Color.White,
                onDismissRequest = {
                    openRequestDialog.value = false
                },
                title = null,
                text = {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp)
                    ) {
                        TextViewCompose(
                            text = "Request Blood",
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .fillMaxWidth(),
                            fontColor = Color.Black,
                            textAlign = TextAlign.Center)
                        Spacer(
                            modifier = Modifier
                                .padding(start = 40.dp, end = 40.dp, top = 2.dp, bottom = 20.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(bloodRed)
                        )
                        listViewCompose(
                            label = "Blood group required",
                            stringList = bloodGroups,
                            numbersList = listOf(0),
                            isString = true,
                            "bloodGroup"
                        )
                        listViewCompose(
                            label = "How many blood units?",
                            stringList = bloodUnits,
                            numbersList = listOf(0),
                            isString = true,
                            "bloodUnits"
                        )
                        listViewCompose(
                            label = "State/City",
                            stringList = indianCities,
                            numbersList = listOf(0),
                            isString = true,
                            "state"
                        )
                        listViewCompose(
                            label = "Priority",
                            stringList = listOf("Emergency", "I can wait"),
                            numbersList = listOf(0),
                            isString = true,
                            "priority"
                        )
                        PostTextField()
                    }
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 20.dp, end = 8.dp, start = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(start = 30.dp, end = 30.dp)
                                .fillMaxWidth()
                                .focusable(true),
                            onClick = {
                                val bloodPostData = BloodData(
                                    userName,
                                    uid,
                                    bloodGroup,
                                    bloodUnitPost,
                                    priority,
                                    note,
                                    state
                                )
                                database
                                    .getReference("Users/$uid/User Details/post/")
                                    .setValue(bloodPostData)
                                    .addOnSuccessListener { openRequestDialog.value = false }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = bloodRed,
                                contentColor = Color.White
                            )
                        ) {
                            Text("post request", fontFamily = poppinsFont)
                        }
                    }
                },
            )
        }  //Alert Dialog
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .padding(4.dp)
                .background(Color.White)) {
                Text(text = "Hello $userName",
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    letterSpacing = TextUnit(0.0F, TextUnitType.Unspecified),
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp, start = 4.dp))
                Text(text = "Are you looking for blood donation?",
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.Normal,
                    color = secondaryText,
                    fontSize = 18.sp,
                    letterSpacing = TextUnit(0.0F, TextUnitType.Unspecified),
                    modifier = Modifier.padding(start = 4.dp))
            }
            Icon(
                imageVector = Icons.Outlined.MarkEmailUnread,
                contentDescription = "",
                modifier =
                Modifier
                    .padding(top = 12.dp, start = 6.dp)
                    .padding(2.dp)
                    .clickable {
                        navController.navigate("donateNotes_screen") {

                        }
                    },
                tint = Color(0xA3DE0A1E)
            )

        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        ){
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 0.dp, start = 4.dp, end = 4.dp)
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MyCardCompose("Donate Blood",
                        painterResource(id = R.drawable.donate_blood_)
                    ) {
                        navController.navigate("lazyRequestsAround_screen")
                    }
                    MyCardCompose("Request Blood",
                        painterResource(id = R.drawable.blood_request_log)
                    ) {
                        openRequestDialog.value = true
                    }
                }
            }
        }
    }
}

@Composable
fun DonateChatScreen(notificationDetails: MutableList<NotificationsData>) {
    Column(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextViewCompose(
            text = "Notifications",
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(start = 10.dp, end = 10.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier
            .padding(start = 80.dp, end = 80.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(bloodRed)
        )
        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White, RoundedCornerShape(6.dp))
        ) {
            items(notificationDetails) { item ->
                CardNotifications(false, item)
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        TextViewCompose(text = "Phone Number",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        MyTextField(label = "", isInt = true, "")
        TextViewCompose(text = "Password",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        passwordMyTextField(label = "")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonCompose(buttonText = "Login",
                onClick = {
//                    navController.navigate("bloodGroup_screen")
                    Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show()

                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Don't have an account?")
                                            withStyle(
                                                style = SpanStyle(
                                                    textDecoration = TextDecoration.Underline
                                                )
                                            ) {
                                                append("\nRegister Here.")
                                            }
                },
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 4.dp)
                    .clickable {
                        navController.navigate("phoneNumber_screen") {
                            popUpTo(0)
                        }
                    }
                ,
                fontFamily = poppinsFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin,
            )
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
        TextViewCompose(text = "What's Your Name",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        MyTextField(label = "FullName", isInt = false, "fullName")
        TextViewCompose(text = "What is Your Blood Group?",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        listViewCompose(
            label = "Blood Group",
            stringList = listOf("A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"),
            numbersList = listOf(0),
            isString = true,
            "bloodGroup"
        )
        TextViewCompose(text = "Gender:",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        listViewCompose(
            label = "Gender",
            stringList = listOf("Female", "Male", "Other"),
            numbersList = (18..65).toList(),
            isString = true,
            "gender"
        )
        TextViewCompose(text = "How Old Are You?",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        MyTextField(label = "Age", isInt = true, "age")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonCompose(
                buttonText = "Continue",
                onClick = {
                    uploadData(context)
                    navController.navigate("mainBloodScreen_screen"){
                        popUpTo(0)
                        getUserName()
                    }
                }
            )
        }
    }
}

@Composable
fun PhoneNumberScreen(navController: NavController) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextViewCompose(text = "What's Your Phone Number?",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        MyTextField(label = "", isInt = true, "phoneNumber")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonCompose(
                buttonText = "Get OTP",
                onClick = {
                    onLoginClicked(context, phoneNumber, navController)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "OR",
                fontFamily = poppinsFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .background(Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.padding(10.dp)) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.google_logo_48),
                            contentDescription = "Blood Donation Logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "Sign up with google",
                            color = Color.Blue,
                            fontFamily = poppinsFont,
                            fontSize = 12.sp,
                            modifier = Modifier.clickable {
                                Toast.makeText(context, "nope. Who needs google anyway!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AuthenticatePhoneNumberScreen(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        TextViewCompose(text = "you'll receive an OTP shortly.",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
            fontColor = Color.Black,
            textAlign = TextAlign.Start)
        MyTextField(label = "OTP", isInt = true, "otp")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonCompose(
                buttonText = "Continue",
                onClick = {
                    verifyPhoneNumberWithCode(context, storedVerificationId, otp, navController)
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Resend OTP.",
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        navController.navigate("authPhone_screen") {
                            popUpTo(0)
                        }
                    }
                ,
                fontFamily = poppinsFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun InfoPreview() {
    Column(
        modifier = Modifier
            .background(Color(0xFFF3F4F5))
            .fillMaxSize()
    ) {
//        AppBarCompose("")
//        MainBloodScreen(rememberNavController())
//        DonateChatScreen(notificationDetails = mutableListOf(NotificationsData()))
        ProfileScreen(false)
    }
}


