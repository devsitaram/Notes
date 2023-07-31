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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.record.notes.R
import com.record.notes.features.main.ScreenItem

private val homeViewModel = HomeViewModel()
//@Preview
@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeViewScreen(navController: NavHostController) {
    val context = LocalContext.current
    val customerList = remember { mutableStateOf(ArrayList<CustomerPojo>()) }

    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)) {
            // get data from gameModelView class
            LaunchedEffect(true) {
                homeViewModel.getCustomer(context) {
                    customerList.value = it as ArrayList<CustomerPojo>
                }
            }
            // call the composable function
            CustomerDetails(navController, customerList, context)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetails(
    navController: NavHostController,
    customerList: MutableState<ArrayList<CustomerPojo>>,
    context: Context,
) {
    val showDialog = remember { mutableStateOf(false) }
    LazyColumn {
        items(customerList.value.size) {
            // show the dialog bok
            var isShowDetails by remember { mutableStateOf(false) }
            val customerId = customerList.value[it].id.toString()
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
                        // customer id
                        Text(
                            text = customerId,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentHeight()
                                .padding(start = 15.dp, end = 5.dp),
                        )
//                        Image(
//                            modifier = Modifier
//                                .wrapContentHeight()
//                                .wrapContentHeight()
//                                .padding(start = 15.dp, end = 5.dp),
//                            painter = painterResource(id = R.drawable.ic_person),
//                            contentDescription = null
//                        )
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
                                Text(text = "Date: ", modifier = Modifier.width(70.dp))
                                Text(
                                    text = customerList.value[it].date.toString(),
                                    fontSize = 15.sp
                                )
                            }
                            Row(Modifier.fillMaxWidth()) {
                                Text(text = "Work: ", modifier = Modifier.width(70.dp))
                                Text(
                                    text = customerList.value[it].work.toString(),
                                    fontSize = 15.sp
                                )
                            }
                            Row(Modifier.fillMaxWidth()) {
                                Text(text = "Amount: ", modifier = Modifier.width(70.dp))
                                Text(
                                    text = customerList.value[it].amount.toString(),
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
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog.value = false
                                        Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show()
                                        navController.navigate(ScreenItem.Update.route)
                                    }
                                ) {
                                    Text(text = "Update")
                                }
                                TextButton(
                                    onClick = {
                                        // from delete
                                        val isDeleted = homeViewModel.getCustomerId(customerId, context)
                                        if(isDeleted){
                                            showDialog.value = false
                                        }
//                                        Toast.makeText(context, "Successfully delete", Toast.LENGTH_SHORT).show()
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

@Composable
fun MenuExample() {
    var expanded by remember { mutableStateOf(false) }

    // Create a list of menu items
    val menuItems = listOf("Update", "Delete")
    Box {
        // Button to show/hide the menu
        Button(
            onClick = { expanded = !expanded },
            contentPadding = PaddingValues(8.dp)
        ) {
            Text("Open Menu")
        }

        // DropdownMenu to display the menu items
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        // Handle the selected menu item here
//                        Toast.makeText(LocalContext.current, "Clicked: $item", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(item)
                }
            }
        }
    }
}