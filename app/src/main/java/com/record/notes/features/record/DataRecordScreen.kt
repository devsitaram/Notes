package com.record.notes.features.record

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.record.notes.R
import com.record.notes.features.main.ButtonNavigationBar
import com.sitaram.gameyo.features.util.CancelButton
import com.sitaram.gameyo.features.util.HeadingTextComponent
import com.sitaram.gameyo.features.util.InputAmountTextField
import com.sitaram.gameyo.features.util.InputTextField
import com.sitaram.gameyo.features.util.SaveButton
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

private val compositeDisposable = CompositeDisposable()
val recordViewModel = RecordViewModel()
@SuppressLint("SimpleDateFormat")
@Composable
fun DataRecordViewScreen(buttonNavController: NavHostController) {
    val context = LocalContext.current

    // get data time
    val latestDate = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = latestDate.format(Date())

    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(currentDate) }
    var work by remember { mutableStateOf("") }
    var amounts by remember { mutableStateOf("") }

    var isEmptyMessage by remember { mutableStateOf(true) }
    // check the empty text fields
    val isNotEmpty by remember {
        derivedStateOf {
            name.isNotEmpty() && date.isNotEmpty() && work.isNotEmpty() && amounts.isNotEmpty()
        }
    }

    // on click function for record
    val onClickAction: () -> Unit = {
        if (isNotEmpty) {
            isEmptyMessage = true // show error message
            val recordViewModel = RecordViewModel()
            val isValidRecord = recordViewModel.getRecordDetails(name, date, work, amounts, context)
            if (isValidRecord) {
                // Navigate to the home screen
                buttonNavController.navigate(ButtonNavigationBar.Home.route)
            }
        } else {
            Toast.makeText(context, "Please, fill in the blank!\nकृपया, खाली ठाउँ भर्नुहोस्।", Toast.LENGTH_SHORT).show()
            isEmptyMessage = false // hide error message
        }
    }

    // clear the text filed
    val onClearAction: ()-> Unit = {
        name = ""
        work =""
        amounts = ""
    }

    Surface(Modifier.fillMaxSize()) {

        // Home screen data show file
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img), contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(130.dp)
            )

            HeadingTextComponent(
                value = stringResource(id = R.string.login_your_details),
                color = colorResource(id = R.color.black)
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.White)
            ) {
                // customer name
                InputTextField(
                    date,
                    onValueChange = { date = it },
                    label = stringResource(id = R.string.date),
                    painterResource = painterResource(R.drawable.ic_date_time),
                    isEmptyMessage = isEmptyMessage
                )

                // customer name
                InputTextField(
                    name,
                    onValueChange = { name = it },
                    label = stringResource(id = R.string.customer_name),
                    painterResource = painterResource(R.drawable.ic_person),
                    isEmptyMessage = isEmptyMessage
                )

                // work
                InputTextField(
                    work,
                    onValueChange = { work = it },
                    label = stringResource(id = R.string.work),
                    painterResource = painterResource(R.drawable.ic_work),
                    isEmptyMessage = isEmptyMessage
                )

                // amounts
                InputAmountTextField(
                    amounts,
                    onValueChange = { amounts = it },
                    label = stringResource(id = R.string.amounts),
                    isEmptyMessage = isEmptyMessage
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                // login button
                SaveButton(
                    value = stringResource(id = R.string.save),
                    onClickAction = onClickAction
                )

                CancelButton(
                    value = stringResource(id = R.string.clear),
                    onClickAction = onClearAction
                )
            }
        }
    }
}


//// name empty
//var isNameEmptyMessage by remember { mutableStateOf(true) }
//val isNameEmpty by remember {
//    derivedStateOf {
//        name.isNotEmpty()
//    }
//}
//
//// date empty
//var isDateEmptyMessage by remember { mutableStateOf(true) }
//val isDateEmpty by remember {
//    derivedStateOf {
//        name.isNotEmpty()
//    }
//}
//
//// work empty
//var isWorkEmptyMessage by remember { mutableStateOf(true) }
//val isWorkEmpty by remember {
//    derivedStateOf {
//        name.isNotEmpty()
//    }
//}
//
//// amount empty
//var isAmountEmptyMessage by remember { mutableStateOf(true) }
//val isAmountEmpty by remember {
//    derivedStateOf {
//        name.isNotEmpty()
//    }
//}
//
//val isEmpty by remember {
//    derivedStateOf {
//        name.isNotEmpty() && date.isNotEmpty() && work.isNotEmpty() && amounts.isNotEmpty()
//    }
//}



// on click function for record
//val onClickAction: () -> Unit = {
//    if (isNotEmpty) {
//        isEmptyMessage = true // show error message
//        try {
//            val success = Objects.requireNonNull<Completable>(recordViewModel.insertCustomerData(name, date, work, amounts, context))
//                .subscribeOn(Schedulers.io())
//                .subscribe(object : CompletableObserver {
//                    override fun onSubscribe(disposable: Disposable) {
//                        compositeDisposable.add(disposable)
//                    }
//
//                    override fun onComplete() {
//                        // recycler view
//                    }
//
//                    override fun onError(e: Throwable) {}
//                })
//            if (success != null) {
//                // Navigate to the home screen
//                buttonNavController.navigate(ButtonNavigationBar.Home.route)
//            }
//        } catch (exception: NullPointerException) {
//            Toast.makeText(context, "exception", Toast.LENGTH_SHORT).show()
//        }
////
////            val recordViewModel = RecordViewModel()
////            val isValidRecord = recordViewModel.recordDetails(name, date, work, amounts, context)
////            if (isValidRecord) {
////                // Navigate to the home screen
////                buttonNavController.navigate(ButtonNavigationBar.Home.route)
////            }
//    } else {
//        Toast.makeText(context, "Please, fill in the blank!\nकृपया, खाली ठाउँ भर्नुहोस्।", Toast.LENGTH_SHORT).show()
//        isEmptyMessage = false // hide error message
//    }
//}