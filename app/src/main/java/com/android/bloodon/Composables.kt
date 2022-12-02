package com.android.bloodon

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val log = "myLogCompose"
const val log2 = "myLogCompose2"
var bloodGroup: String = ""
var age: String = ""
var fullName: String = ""
var gender: String = ""
var phoneNumber: String = ""
var post: String = ""
var bloodUnitPost: String = ""
var priority: String = ""
var note: String = ""
var acceptNote: String = ""
var state: String = ""
var storedVerificationId: String = ""
var otp: String = ""
var notName: String = ""
var notNote: String = ""
var notBloodGroup: String = ""
var notState: String = ""
var notNumber: String = ""
val bloodRed = Color(0xFFDE0A1E)
val secondaryText = Color(0xFF353535)

@Composable
fun AppBarCompose(title: String) {
    val poppinsFont = FontFamily(Font(R.font.poppins_regular))
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = bloodRed
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                fontFamily = poppinsFont
            )
        }
    }
}

@Composable
fun CenteredText(text: String, color: Color, fontSize: Int, fontWeight: FontWeight, topPadding: Int) {
    val poppinsFont = FontFamily(Font(R.font.poppins_regular))
    val scale = remember {
        Animatable(initialValue = 0f)
    }
    val coroutineScope = rememberCoroutineScope()

    Text(
        modifier = Modifier
            .scale(scale.value)
            .padding(top = topPadding.dp, end = 6.dp, bottom = 6.dp)
            .onGloballyPositioned {
                coroutineScope.launch {
                    delay(150L)
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        ,
        text = text,
        color = color,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        fontFamily = poppinsFont,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BloodGroupsCard(bloodGroup: String, onClick: () -> Unit) {
    val selected = remember { mutableStateOf(false) }
    val cardColor = if (selected.value) Color(0xFFF3F4F5) else Color.White
    Card(
        Modifier
            .padding(bottom = 6.dp, end = 4.dp, top = 6.dp)
//            .width(60.dp)
            .height(50.dp)
            .clickable {
                onClick.invoke()
            },
        border = BorderStroke(Dp.Hairline, Color.LightGray),
        backgroundColor = (cardColor),
        shape = RoundedCornerShape(10.dp),
        ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                text = bloodGroup,
                fontSize = 18.sp,
                fontWeight = W500,
                fontFamily = poppinsFont,
                color = bloodRed,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BloodAcceptCard(text: String, requesterUid: String) {
    val uid = FirebaseAuth.getInstance().uid.toString()
    val openAcceptDialog = remember { mutableStateOf(false) }
    if (openAcceptDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White,
            onDismissRequest = {
                openAcceptDialog.value = false
            },
            title = null,
            text = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
                ) {
                    TextViewCompose(
                        text = "Donate Blood",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        fontColor = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(start = 40.dp, end = 40.dp, top = 2.dp, bottom = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(bloodRed)
                    )
                    AcceptTextField()
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
                            database
                                .getReference("Users/$uid/User Details/Messages/Sent/$requesterUid/note")
                                .setValue(acceptNote)
                            database
                                .getReference("Users/$requesterUid/User Details/Messages/Received/$uid/note")
                                .setValue(acceptNote)
                            openAcceptDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = bloodRed,
                            contentColor = Color.White
                        )
                    ) {
                        Text("send", fontFamily = poppinsFont)
                    }
                }
            },
            )
    } //Alert Dialog
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp, end = 0.dp, top = 4.dp)
            .border(Dp.Hairline, Color.LightGray, RoundedCornerShape(4.dp))
            .clickable {
                openAcceptDialog.value = true
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .padding(4.dp)
            ,
            text = text,
            fontSize = 12.sp,
            fontWeight = W500,
            fontFamily = poppinsFont,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PostTextField() {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var selectedText by remember { mutableStateOf("") }
    val maxChar = 100
    val focusManager = LocalFocusManager.current
    var fontSize by remember {
        mutableStateOf(15.sp)
    }
    OutlinedTextField(
        placeholder = { Text(text = "Add a detailed note...", color = Color.Gray, fontSize = 14.sp)},
        value = selectedText,
        onValueChange = {
            selectedText = it.take(maxChar)
            note = it
            fontSize = when (it.length) {
                in 30..40 -> 14.sp
                in 41..80 -> 13.sp
                in 81..101 -> 12.sp
                else -> 15.sp
            }
            if (it.length > maxChar){
                focusManager.moveFocus(FocusDirection.Down) // Or receive a lambda function
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Gray,
            focusedIndicatorColor = Color.Red,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Red,
            trailingIconColor = Color.Red,
            cursorColor = Color.Red,
            textColor = Color.Black,
            disabledTrailingIconColor = Color.Gray,
            disabledTextColor = Color.LightGray,
            disabledIndicatorColor = Color.Gray,
            disabledLabelColor = Color.Gray,
        ),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
        enabled = true,
        shape = RoundedCornerShape(6.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        maxLines = 2,
        textStyle = TextStyle(fontSize = fontSize)
    )
}

@Composable
fun AcceptTextField() {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var selectedText by remember { mutableStateOf("") }
    val maxChar = 100
    val focusManager = LocalFocusManager.current
    var fontSize by remember {
        mutableStateOf(15.sp)
    }
    OutlinedTextField(
        placeholder = { Text(text = "Add a note...", color = Color.Gray, fontSize = 14.sp)},
        value = selectedText,
        onValueChange = {
            selectedText = it.take(maxChar)
            acceptNote = it
            fontSize = when (it.length) {
                in 30..40 -> 14.sp
                in 41..80 -> 13.sp
                in 81..101 -> 12.sp
                else -> 15.sp
            }
            if (it.length > maxChar){
                focusManager.moveFocus(FocusDirection.Down) // Or receive a lambda function
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Gray,
            focusedIndicatorColor = Color.Red,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Red,
            trailingIconColor = Color.Red,
            cursorColor = Color.Red,
            textColor = Color.Black,
            disabledTrailingIconColor = Color.Gray,
            disabledTextColor = Color.LightGray,
            disabledIndicatorColor = Color.Gray,
            disabledLabelColor = Color.Gray,
        ),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
        enabled = true,
        shape = RoundedCornerShape(6.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        maxLines = 2,
        textStyle = TextStyle(fontSize = fontSize)
    )
}

@Composable
fun MyTextField(label: String, isInt: Boolean, type: String) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var selectedText by remember { mutableStateOf("") }
    when (type) {
        "age" -> {
            age = selectedText
        }
        "fullName" -> {
            fullName = selectedText
        }
        "phoneNumber" -> {
            phoneNumber = selectedText
        }
        "otp" -> {
            otp = selectedText
        }
    }
    OutlinedTextField(
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Gray,
            focusedIndicatorColor = Color.Red,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Red,
            trailingIconColor = Color.Red,
            cursorColor = Color.Red,
            textColor = Color.DarkGray,
            disabledTrailingIconColor = Color.Gray,
            disabledTextColor = Color.DarkGray,
            disabledIndicatorColor = Color.Gray,
            disabledLabelColor = Color.Gray,
        ),
        value = selectedText,
        onValueChange = {
            selectedText = it
//            storedVerificationId = selectedText
                        },
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
        label = { Text(label, fontSize = 14.sp) },
        enabled = true,
        shape = RoundedCornerShape(6.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isInt) KeyboardType.Number else KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        maxLines = 1,
    )
}

@Composable
fun ButtonCompose(buttonText: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(20.dp)
            .width(120.dp)
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(bloodRed, Color.White)
    ) {
        Text(text = buttonText, fontSize = 12.sp)
    }
}

@Composable
fun listViewCompose(label: String, stringList: List<String>, numbersList: List<Int>, isString: Boolean, type: String): String {
    var isExpanded by remember { mutableStateOf(false) }
    val list = if (isString) stringList else numbersList
    var selectedText by remember { mutableStateOf("") }
    when (type) {
        "bloodGroup" -> {
            bloodGroup = selectedText
        }
        "gender" -> {
            gender = selectedText
        }
        "bloodUnits" -> {
            bloodUnitPost = selectedText
        }
        "priority" -> {
            priority = selectedText
        }
        "state" -> {
            state = selectedText
        }
    }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    if (isPressed) {
        isExpanded = !isExpanded
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Gray,
                focusedIndicatorColor = Color.Red,
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = Color.Red,
                trailingIconColor = Color.Red,
                cursorColor = Color.Red,
                textColor = Color.DarkGray,
                disabledTrailingIconColor = Color.Gray,
                disabledTextColor = Color.DarkGray,
                disabledIndicatorColor = Color.Gray,
                disabledLabelColor = Color.Gray,
            ),
            value = selectedText,
            onValueChange = { selectedText = it },
            readOnly = true,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable {
                    isExpanded = !isExpanded
                },
            label = { Text(label, fontSize = 14.sp, fontFamily = poppinsFont) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { isExpanded = !isExpanded })
            },
            enabled = true,
            shape = RoundedCornerShape(6.dp),
            interactionSource = interactionSource
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .background(Color.White, RoundedCornerShape(0.dp))
        ) {
            list.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label.toString()
                        isExpanded = false
                    }
                ) {
                    Box(modifier = Modifier
                        .padding(Dp.Hairline)
                        .fillMaxSize()
                        .background(Color.Transparent, RoundedCornerShape(6.dp))) {
                        TextViewCompose(
                            label.toString(),
                            Modifier
                                .padding(10.dp)
                                .background(Color.Transparent, RoundedCornerShape(6.dp)),
                            Color.Black,
                            TextAlign.Start)
                    }
                }
            }
        }
    }
    return selectedText
}

@Composable
fun TextViewCompose(text: String, modifier: Modifier, fontColor: Color, textAlign: TextAlign) {
    val poppinsFont = FontFamily(Font(R.font.poppins_regular))
    Text(
        text = text,
        modifier = modifier,
        fontFamily = poppinsFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Thin,
        textAlign = textAlign,
        color = fontColor,
        maxLines = 1,
    )
}

@Composable
fun passwordMyTextField(label: String): String {
//    var emailTextFieldState by remember {
//        mutableStateOf("")
//    }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var selectedText by remember { mutableStateOf("") }
    OutlinedTextField(
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Gray,
            focusedIndicatorColor = Color.Red,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Red,
            trailingIconColor = Color.DarkGray,
            cursorColor = Color.Red,
            textColor = Color.DarkGray,
            disabledTrailingIconColor = Color.Gray,
            disabledTextColor = Color.DarkGray,
            disabledIndicatorColor = Color.Gray,
            disabledLabelColor = Color.Gray,
        ),
        value = selectedText,
        onValueChange = { selectedText = it },
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
        label = { Text(label) },
        enabled = true,
        shape = RoundedCornerShape(6.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        visualTransformation =
        if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        }
    )
    return selectedText
}

@Composable
fun MyCardCompose(text: String, painter: Painter,  onClick: () -> Unit) {
    val cardRoundedCornerShapeSize = 10.dp
    Card(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .shadow(6.dp, RoundedCornerShape(cardRoundedCornerShapeSize))
            .width(140.dp)
            .height(160.dp)
            .clickable {
                onClick.invoke()
            },
//            .border(1.dp, bloodRed, RoundedCornerShape(10.dp)),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, start = 0.dp, end = 0.dp, bottom = 8.dp)
                    .background(Color.Transparent, RoundedCornerShape(cardRoundedCornerShapeSize))
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .blur(0.dp, BlurredEdgeTreatment.Rectangle),
                    painter = painter,
                    contentDescription = "Blood Donation picture",
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = text,
                fontSize = 16.sp,
                fontFamily = poppinsFont,
                color = secondaryText,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun CardNotifications(clicked: Boolean, notDetails: NotificationsData) {
    val nameVal = notDetails.name
    val downClicked = remember {
        mutableStateOf(clicked)
    }
    val scale = remember {
        Animatable(initialValue = 0.96f)
    }
    val coroutineScope = rememberCoroutineScope()
    val arrowDirection = if (downClicked.value) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown
    var unitScale = {}
    val cardBorderColor = remember {
        mutableStateOf(Color.LightGray)
    }
    Card(
        modifier = Modifier
            .animateContentSize()
            .scale(scale.value)
            .padding(top = 2.dp, start = 6.dp, end = 6.dp, bottom = 4.dp)
            .fillMaxWidth()
            .border(Dp.Hairline, cardBorderColor.value, RoundedCornerShape(10.dp))
            .onGloballyPositioned {
                unitScale.invoke()
            },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp, top = 4.dp)
        ) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
               Text(text = nameVal,
                   fontSize = 18.sp,
                   color = Color.Black,
                   fontWeight = FontWeight.SemiBold,
                   fontFamily = poppinsFont,
                   textAlign = TextAlign.Start,
                   modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 2.dp),
                   maxLines = 1)
               if (downClicked.value) {
                   Icon(
                       imageVector = Icons.Outlined.DeleteOutline,
                       contentDescription = "delete",
                       modifier = Modifier.padding(end = 10.dp),
                       tint = Color.DarkGray
                   )
               }
           }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = notDetails.note,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFont,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 2.dp),
                    maxLines = 4
                )
            }

            if (downClicked.value) {
                cardBorderColor.value = Color(0x99DE0A1E)
                unitScale = {
                    coroutineScope.launch {
                        delay(150L)
                        scale.animateTo(
                            targetValue = 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "location",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFont,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 8.dp, bottom = 2.dp)
                            .width(60.dp),
                        maxLines = 1)
                    Text(
                        text = notDetails.state,
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFont,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 2.dp),
                        maxLines = 1
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "number",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFont,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 8.dp, bottom = 2.dp)
                            .width(60.dp),
                        maxLines = 1)
                    SelectionContainer {
                        Text(
                            text = notDetails.number,
                            fontSize = 18.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFont,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(start = 6.dp, top = 6.dp, bottom = 2.dp),
                            maxLines = 1,
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline
                            )
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "group",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFont,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 8.dp, bottom = 2.dp)
                            .width(60.dp),
                        maxLines = 1)
                    Text(
                        text = notDetails.bloodGroup,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFont,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 6.dp, top = 6.dp, bottom = 2.dp)
                            .blur(0.dp),
                        maxLines = 1
                    )

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        downClicked.value = !downClicked.value
                        cardBorderColor.value = Color.LightGray
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = arrowDirection,
                    contentDescription = "Arrow Down",
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun CardPostResultCompose(post: BloodRequestsData) {
    val nameVal = post.name
    val postVal = post.note
    val stateVal = post.state
    val priorityVal = post.priority
    val bloodGroupVal = post.bloodGroup
    val bloodUnitsVal = post.bloodUnits
    val requesterUid = post.userUid
    val fontSize = when (postVal.length) {
        in 0..60 -> 16.sp
        in 61..101 -> 14.sp
        else -> 16.sp
    }
    Card(
        modifier = Modifier
            .padding(top = 2.dp, start = 6.dp, end = 6.dp, bottom = 4.dp)
            .fillMaxWidth()
            .height(126.dp)
            .border(Dp.Hairline, Color.Transparent, RoundedCornerShape(10.dp)),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxHeight()
                    .weight(3f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "'$postVal'",
                    fontSize = fontSize,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFont,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 4.dp),
                    maxLines = 4)
                Box {
                    Column {
                        Text(text = "Name: $nameVal",
                            fontSize = 12.sp,
                            color = secondaryText,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 10.dp))
                        Text(text = "Location: $stateVal",
                            fontSize = 12.sp,
                            color = secondaryText,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 10.dp))
                        Text(text = "Priority: $priorityVal",
                            fontSize = 12.sp,
                            color = secondaryText,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 10.dp))
                        Text(text = "Blood units required: $bloodUnitsVal",
                            fontSize = 12.sp,
                            color = secondaryText,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 10.dp, bottom = 4.dp))
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally) {
                BloodGroupsCard(bloodGroup = bloodGroupVal) {}
                BloodAcceptCard(text = "Donate", requesterUid)
            }
        }
    }
}

@Composable
fun SetBloodData(viewModel: MainViewModel) {
    when (val result = viewModel.response.value) {
        is BloodDataState.Loading -> {
            Text(text = "Loading")
        }
        is BloodDataState.Empty-> {
            Text(text = "Empty")
        }
        is BloodDataState.Success -> {
            ShowLazyList(postDetails = result.postDetails)
        }
        is BloodDataState.Failure -> {
            Text(text = "Failure")
        }
    }
}

@Composable
fun ColumnCompose() {
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
            textAlign = TextAlign.Start
        )
        Spacer(
            modifier = Modifier
                .padding(start = 80.dp, end = 80.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(bloodRed)
        )
    }
}

@Composable
fun SetNotificationData(viewModel: NotificationViewModel) {
    when (val result = viewModel.response.value) {
        is NotificationDataState.Loading -> {
            ColumnCompose()
        }
        is NotificationDataState.Empty-> {
            ColumnCompose()
        }
        is NotificationDataState.Success -> {
            DonateChatScreen(result.postDetails)
        }
        is NotificationDataState.Failure -> {
            ColumnCompose()
        }
    }
}

@Composable
fun ShowLazyList(postDetails: MutableList<BloodRequestsData>) {
    Column(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextViewCompose(
            text = "Blood Requests Around",
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
            items(postDetails) { post ->
                CardPostResultCompose(post = post)
            }
        }
    }
}

@Composable
fun MainScreenCompose(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        bottomBar = { BottomBarCompose(navController) }
    ){ padding ->
        Column(modifier = Modifier.padding(padding)) {
            MainBloodScreen(navController)
        }
    }
}

@Composable
fun ProfileScreenCompose(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        scaffoldState = scaffoldState,
        bottomBar = { BottomBarCompose(navController) },
        backgroundColor = Color.White
    ){ padding ->
        Column(modifier = Modifier
            .padding(padding)
            .background(Color.White)) {
            ProfileScreen(false, navController)
        }
    }
}

@Composable
fun SettingsRow(title: String, isChecked: Boolean) {
    Row(modifier = Modifier
        .padding(bottom = 10.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            fontSize = 26.sp,
            color =  Color.DarkGray,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 4.dp)
        )
        val checkedState = remember { mutableStateOf(isChecked) }
        Switch(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = bloodRed,
                checkedTrackColor = bloodRed,
                uncheckedThumbColor = Color.DarkGray,
                uncheckedTrackColor = Color.DarkGray
            )
        )
    }
}

@Composable
fun ProfileRowCompose(label: String, text: String, isCustom: Boolean) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .fillMaxWidth()
            .border(Dp.Hairline, Color.Transparent, RoundedCornerShape(6.dp))
            .background(Color.White, RoundedCornerShape(4.dp)),
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color =  Color.DarkGray,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 4.dp)
        )
        Text(
            text = text,
            fontSize = 24.sp,
            color =  Color.Black,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 4.dp)
        )
//        if (isCustom) {
//            Icon(imageVector = Icons.Outlined.Edit,
//                contentDescription = "Edit Field",
//                tint = Color.Gray)
//        }
    }
}

@Composable
fun BottomBarCompose(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
//        BottomNavItem.Request,
//        BottomNavItem.Donate,
        BottomNavItem.Account
    )
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .border(Dp.Hairline, Color.Gray, RoundedCornerShape(topEnd = 14.dp, topStart = 14.dp)),
        backgroundColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 10.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route)
                    {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposablesPreview() {
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
//        Spacer(modifier = Modifier
//            .padding(top = 20.dp)
//            .height(1.dp)
//            .fillMaxWidth()
//            .background(Color.Black))
//        PostTextField()
//        Spacer(modifier = Modifier
//            .padding(top = 20.dp)
//            .height(1.dp)
//            .fillMaxWidth()
//            .background(Color.Black))
//        BottomBarCompose(rememberNavController())
//        Spacer(modifier = Modifier
//            .padding(top = 20.dp)
//            .height(1.dp)
//            .fillMaxWidth()
//            .background(Color.Black))
//        CardPostResultCompose(post = BloodRequestsData("Yazan Aesmael", "Kims Hospital tomorrow morning", "Emergency", "2", "A+", "Odisha"))
        Spacer(modifier = Modifier
            .padding(bottom = 20.dp)
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Black))
        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(Color.Black), verticalAlignment = Alignment.CenterVertically) {
            ProfileRowCompose("Full Name", "Yazan", true)
//            CardNotifications(true, NotificationsData())
//            CardPostResultCompose(post = BloodRequestsData("Yazan Aesmael", "Need Some Blood ASAP", "Emergency", "2", "A+", "Delhi"))
//            CardViewCompose("Donate Blood", painterResource(id = R.drawable.blood_don), { }, 10.dp, 10.dp, 10.dp, 10.dp)
//            CardViewCompose("Request Blood", painterResource(id = R.drawable.blood_don), { }, 10.dp, 10.dp, 10.dp, 10.dp)
        }
        Spacer(modifier = Modifier.height(20.dp))
//        Spacer(modifier = Modifier
//            .padding(bottom = 20.dp)
//            .height(1.dp)
//            .fillMaxWidth()
//            .background(Color.Black))
//        TextViewCompose(text = "Phone Number",
//            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
//            fontColor = Color.Black,
//            textAlign = TextAlign.Start)
//        MyTextField(label = "FullName", true, "")
//        Spacer(modifier = Modifier
//            .padding(top = 20.dp)
//            .height(1.dp)
//            .fillMaxWidth()
//            .background(Color.Black))
//        listViewCompose(label = "Age", stringList = listOf(""), numbersList = (18..65).toList(), isString = false, "")
//        Spacer(modifier = Modifier
//            .padding(top = 20.dp)
//            .height(1.dp)
//            .fillMaxWidth()
//            .background(Color.Black))
//        ButtonCompose(buttonText = "Next", onClick = {})
//        Spacer(modifier = Modifier
//            .padding(bottom = 20.dp)
//            .height(20.dp)
//            .fillMaxWidth())
    }
}