package com.anirban.firebasedemo01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.anirban.firebasedemo01.feature_authentication.presentation.navigation.AuthenticationNavGraph
import com.anirban.firebasedemo01.core.theme.CustomAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    AuthenticationNavGraph(navController = navController)
                }
            }
        }
    }
}

//@Composable
//fun ButtonUI() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Button(onClick = {
//            makeUser()
//        }) {
//            Text(text = "Click Me")
//        }
//    }
//}
//
//fun makeUser() {
//    val firebaseAuth = FirebaseAuth.getInstance()
//
//    firebaseAuth.createUserWithEmailAndPassword("jeetbasak2002@gmail.com", "123456")
//        .addOnCompleteListener {
//            if (it.isSuccessful) {
//                d("Sign in ", "Successful")
//            } else {
//                d("Sign in ", it.exception.toString())
//            }
//        }
//
//
//    firebaseAuth.signInWithEmailAndPassword("jeetbasak2002@gmail.com", "123456")
//        .addOnCompleteListener {
//            if (it.isSuccessful) {
//                d("Login ", "Successful")
//            } else {
//                d("Login ", it.exception.toString())
//            }
//        }
//    insertData()
//}
//
//fun insertData() {
//
//    d("Insert Data", "Reached")
//
//    val firebaseAuth = FirebaseAuth.getInstance()
//    val firebaseDatabase =
//        FirebaseDatabase.getInstance("https://fir-demo01-e8063-default-rtdb.asia-southeast1.firebasedatabase.app/")
//            .reference.child("tasks")
//            .child(firebaseAuth.currentUser?.uid.toString())
//
//    firebaseDatabase.push().setValue("Anirban").addOnCompleteListener {
//        if (it.isSuccessful) {
//            d("Database", "Successful")
//        } else {
//            d("Database", "Failed")
//        }
//    }
//}