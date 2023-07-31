package com.record.notes.features.home

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.record.notes.R
import com.record.notes.features.main.ButtonNavigationBar
import com.record.notes.features.main.ScreenItem
import com.sitaram.gameyo.features.util.CancelButton
import com.sitaram.gameyo.features.util.HeadingTextComponent
import com.sitaram.gameyo.features.util.InputAmountTextField
import com.sitaram.gameyo.features.util.InputTextField
import com.sitaram.gameyo.features.util.SaveButton
import java.text.SimpleDateFormat
import java.util.Date

private val homeViewModel = HomeViewModel()

//@Preview
@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeViewScreen(buttonNavController: NavHostController, navController: NavHostController) {
    val context = LocalContext.current
    val customerList = remember { mutableStateOf(ArrayList<CustomerPojo>()) }

    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            // get data from gameModelView class
            LaunchedEffect(true) {
                homeViewModel.getCustomer(context) {
                    customerList.value = it as ArrayList<CustomerPojo>
                }
            }
            // call the composable function
            CustomerDetails(buttonNavController, navController, customerList, context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetails(
    buttonNavController: NavHostController,
    navController: NavHostController,
    customerList: MutableState<ArrayList<CustomerPojo>>,
    context: Context,
) {
    val showDialog = remember { mutableStateOf(false) }
    var id: String
    var name: String? = null
    var date: String? = null
    var work: String? = null
    var amounts: String? = null
    // view of home screen with data
    LazyColumn {
        items(customerList.value.size) {
            // show the dialog bok
            var isShowDetails by remember { mutableStateOf(false) }
            id = customerList.value[it].id.toString()
            name = customerList.value[it].name.toString()
            date = customerList.value[it].date.toString()
            work = customerList.value[it].work.toString()
            amounts = customerList.value[it].amount.toString()
            // card
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(5.dp)
                    .background(color = Color.White),
                shape = MaterialTheme.shapes.medium,
                onClick = { isShowDetails = !isShowDetails },
                border = BorderStroke(0.1.dp, Color.Gray)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentHeight()
                                .padding(start = 15.dp, end = 5.dp),
                            painter = painterResource(id = R.drawable.ic_person),
                            contentDescription = null
                        )
                        // customer name
                        Text(
                            text = customerList.value[it].name.toString(),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(end = 5.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            // how dialog box
                            IconButton(onClick = {
                                showDialog.value = true
                            }) {
                                // icon
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .wrapContentWidth()
                                )
                            }
                        }
                    }

                    // how the extra details
                    if (isShowDetails) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, bottom = 15.dp)
                                .background(color = Color.White),
                        ) {
                            Row(Modifier.fillMaxWidth()) {
                                // record date
                                Text(text = "Date: ", modifier = Modifier.width(70.dp))
                                Text(text = date!!, fontSize = 15.sp)
                            }
                            // work
                            Row(Modifier.fillMaxWidth()) {
                                Text(text = "Work: ", modifier = Modifier.width(70.dp))
                                Text(
                                    text = work!!,
                                    fontSize = 15.sp
                                )
                            }
                            // amounts
                            Row(Modifier.fillMaxWidth()) {
                                Text(text = "Amount: ", modifier = Modifier.width(70.dp))
                                Text(
                                    text = amounts!!,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }

                    // show dialog box
                    if (showDialog.value) {
                        AlertDialog(
                            onDismissRequest = { showDialog.value = false },
                            title = { Text(text = "Update") },
                            text = { Text(text = "Do you want to update your data?\nके तपाईं आफ्नो डाटा अपडेट गर्न चाहनुहुन्छ?") },
                            modifier = Modifier.fillMaxWidth(),
                            dismissButton = {
                                TextButton(
                                    onClick = { showDialog.value = false }
                                ) {
                                    Text(text = "No")
                                }
                            },
                            // update button
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog.value = false
//                                            val isUpdate = homeViewModel.getUpdateCustomer(id, name, date, work, amounts, context)
//                                            if (isUpdate == true) {
//                                                showDialog.value = false
//                                                navController.navigate(ScreenItem.Update.route)
//                                            }
                                    }
                                ) {
                                    Text(text = "Update")
                                }
                                TextButton(
                                    onClick = {
                                        // from delete
                                        val isDeleted = homeViewModel.getDeleteCustomerById(
                                            id,
                                            name!!,
                                            context
                                        )
                                        if (isDeleted) {
                                            buttonNavController.navigate(ButtonNavigationBar.Home.route)
                                            showDialog.value = false
                                        }
                                    }
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomerDetails(buttonNavController: NavHostController, navController: NavHostController, customerList: MutableState<ArrayList<CustomerPojo>>, context: Context, ) {
//    val showDialog = remember { mutableStateOf(false) }
//    val showUpdate = remember { mutableStateOf(true) }
//    var id: String
//    var name: String? = null
//    var date: String? = null
//    var work: String? = null
//    var amounts: String? = null
//    // view of home screen with data
//    if (showUpdate.value) {
//        LazyColumn {
//            items(customerList.value.size) {
//                // show the dialog bok
//                var isShowDetails by remember { mutableStateOf(false) }
//                id = customerList.value[it].id.toString()
//                name = customerList.value[it].name.toString()
//                date = customerList.value[it].date.toString()
//                work = customerList.value[it].work.toString()
//                amounts = customerList.value[it].amount.toString()
//                // card
//                Card(
//                    modifier = Modifier
//                        .wrapContentHeight()
//                        .padding(5.dp)
//                        .background(color = Color.White),
//                    shape = MaterialTheme.shapes.medium,
//                    onClick = { isShowDetails = !isShowDetails },
//                    border = BorderStroke(0.1.dp, Color.Gray)
//                ) {
//                    Column(
//                        Modifier
//                            .fillMaxWidth()
//                            .background(color = Color.White)
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                        ) {
//                            Image(
//                                modifier = Modifier
//                                    .wrapContentHeight()
//                                    .wrapContentHeight()
//                                    .padding(start = 15.dp, end = 5.dp),
//                                painter = painterResource(id = R.drawable.ic_person),
//                                contentDescription = null
//                            )
//                            // customer name
//                            Text(
//                                text = customerList.value[it].name.toString(),
//                                fontSize = 15.sp,
//                                modifier = Modifier.padding(start = 5.dp)
//                            )
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .wrapContentHeight()
//                                    .padding(end = 5.dp),
//                                horizontalArrangement = Arrangement.End
//                            ) {
//                                // how dialog box
//                                IconButton(onClick = {
//                                    showDialog.value = true
//                                }) {
//                                    // icon
//                                    Icon(
//                                        imageVector = Icons.Default.MoreVert,
//                                        contentDescription = null,
//                                        modifier = Modifier
//                                            .wrapContentHeight()
//                                            .wrapContentWidth()
//                                    )
//                                }
//                            }
//                        }
//
//                        // how the extra details
//                        if (isShowDetails) {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(start = 15.dp, bottom = 15.dp)
//                                    .background(color = Color.White),
//                            ) {
//                                Row(Modifier.fillMaxWidth()) {
//                                    // record date
//                                    Text(text = "Date: ", modifier = Modifier.width(70.dp))
//                                    Text(text = date!!, fontSize = 15.sp)
//                                }
//                                // work
//                                Row(Modifier.fillMaxWidth()) {
//                                    Text(text = "Work: ", modifier = Modifier.width(70.dp))
//                                    Text(
//                                        text = work!!,
//                                        fontSize = 15.sp
//                                    )
//                                }
//                                // amounts
//                                Row(Modifier.fillMaxWidth()) {
//                                    Text(text = "Amount: ", modifier = Modifier.width(70.dp))
//                                    Text(
//                                        text = amounts!!,
//                                        fontSize = 15.sp
//                                    )
//                                }
//                            }
//                        }
//
//                        // show dialog box
//                        if (showDialog.value) {
//                            AlertDialog(
//                                onDismissRequest = { showDialog.value = false },
//                                title = { Text(text = "Update") },
//                                text = { Text(text = "Do you want to update your data?\nके तपाईं आफ्नो डाटा अपडेट गर्न चाहनुहुन्छ?") },
//                                modifier = Modifier.fillMaxWidth(),
//                                dismissButton = {
//                                    TextButton(
//                                        onClick = { showDialog.value = false }
//                                    ) {
//                                        Text(text = "No")
//                                    }
//                                },
//                                // update button
//                                confirmButton = {
//                                    TextButton(
//                                        onClick = {
//                                            showDialog.value = false
//                                            showUpdate.value = false
////                                            val isUpdate = homeViewModel.getUpdateCustomer(id, name, date, work, amounts, context)
////                                            if (isUpdate == true) {
////                                                showDialog.value = false
////                                                navController.navigate(ScreenItem.Update.route)
////                                            }
//                                        }
//                                    ) {
//                                        Text(text = "Update")
//                                    }
//                                    TextButton(
//                                        onClick = {
//                                            // from delete
//                                            val isDeleted = homeViewModel.getDeleteCustomerById(
//                                                id,
//                                                name!!,
//                                                context
//                                            )
//                                            if (isDeleted) {
//                                                buttonNavController.navigate(ButtonNavigationBar.Home.route)
//                                                showDialog.value = false
//                                            }
//                                        }
//                                    ) {
//                                        Text(text = "Delete")
//                                    }
//                                }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    } else {
//        // update screen
//        var isEmptyMessage by remember { mutableStateOf(true) }
//        // check the empty text fields
//        val isNotEmpty by remember {
//            mutableStateOf(true)
//            derivedStateOf {
//                name?.isNotEmpty() == true && date?.isNotEmpty() == true && work?.isNotEmpty() == true && amounts?.isNotEmpty() == true
//            }
//        }
//
//        // on click function for record
//        val onClickUpdateAction: () -> Unit = {
//            if (isNotEmpty) {
//
//                isEmptyMessage = true // show error message
////            SQLiteDBHelper(context).updateCustomer(id, name, date, work, amounts, context)
////            val recordViewModel = RecordViewModel()
////            val isValidRecord = recordViewModel.getRecordDetails(name, date, work, amounts, context)
////            if (isValidRecord) {
////                // Navigate to the home screen
////                navController.navigate(ScreenItem.Main.route)
////            }
//            } else {
//                Toast.makeText(context, "Please, fill in the blank!\nकृपया, खाली ठाउँ भर्नुहोस्।", Toast.LENGTH_SHORT).show()
//                isEmptyMessage = false // hide error message
//            }
//        }
//
//        // clear the text filed
//        val onClearAction: ()-> Unit = {
//            name = ""
//            work =""
//            amounts = ""
//        }
//
//        Surface(Modifier.fillMaxSize()) {
//            // child layout file
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(15.dp)
//                    .verticalScroll(rememberScrollState()),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.img), contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.White)
//                        .height(130.dp)
//                )
//
//                HeadingTextComponent(
//                    value = stringResource(id = R.string.login_your_details),
//                    color = colorResource(id = R.color.black)
//                )
//
//                Spacer(modifier = Modifier.padding(top = 30.dp))
//                Column(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(20.dp)
//                        .background(Color.White)
//                ) {
//                    // customer name
//                    InputTextField(
//                        date,
//                        onValueChange = { date = it },
//                        label = stringResource(id = R.string.date),
//                        painterResource = painterResource(R.drawable.ic_date_time),
//                        isEmptyMessage = isEmptyMessage
//                    )
//
//                    // customer name
//                    InputTextField(
//                        name,
//                        onValueChange = { name = it },
//                        label = stringResource(id = R.string.customer_name),
//                        painterResource = painterResource(R.drawable.ic_person),
//                        isEmptyMessage = isEmptyMessage
//                    )
//
//                    // work
//                    InputTextField(
//                        work,
//                        onValueChange = { work = it },
//                        label = stringResource(id = R.string.work),
//                        painterResource = painterResource(R.drawable.ic_work),
//                        isEmptyMessage = isEmptyMessage
//                    )
//
//                    // amounts
//                    InputAmountTextField(
//                        amounts!!,
//                        onValueChange = { amounts = it },
//                        label = stringResource(id = R.string.amounts),
//                        isEmptyMessage = isEmptyMessage
//                    )
//
//                    Spacer(modifier = Modifier.padding(top = 20.dp))
//
//                    // update button
//                    SaveButton(
//                        value = stringResource(id = R.string.update),
//                        onClickAction = onClickUpdateAction
//                    )
//
//                    CancelButton(
//                        value = stringResource(id = R.string.clear),
//                        onClickAction = onClearAction
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun MenuExample() {
//    var expanded by remember { mutableStateOf(false) }
//
//    // Create a list of menu items
//    val menuItems = listOf("Update", "Delete")
//    Box {
//        // Button to show/hide the menu
//        Button(
//            onClick = { expanded = !expanded },
//            contentPadding = PaddingValues(8.dp)
//        ) {
//            Text("Open Menu")
//        }
//
//        // DropdownMenu to display the menu items
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            menuItems.forEach { item ->
//                DropdownMenuItem(
//                    onClick = {
//                        expanded = false
//                        // Handle the selected menu item here
////                        Toast.makeText(LocalContext.current, "Clicked: $item", Toast.LENGTH_SHORT).show()
//                    }
//                ) {
//                    Text(item)
//                }
//            }
//        }
//    }
//}