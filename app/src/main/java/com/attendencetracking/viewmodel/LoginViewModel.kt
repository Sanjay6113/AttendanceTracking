package com.attendencetracking.viewmodel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import com.attendencetracking.common.Helper
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.EventListener

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Firebase.firestore

    fun login(username : EditText, password : EditText ){
        if(!username.text.isNullOrBlank() && username.text!!.length>=3 && password.text.isNotEmpty()) {
            println("username : "+username.text)
            db.collection("login").whereEqualTo("username", username.text.toString())//.whereEqualTo("password",password.text)
                .get().addOnCompleteListener {task->
                    if (task.isSuccessful){
                        println("task : "+task.result)
                    }

                }
                /*.addSnapshotListener(object : EventListener,
                    com.google.firebase.firestore.EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (value != null) {
                            println("value : " + value.documents)
                        }else{
                            Helper().showToastLong(getApplication(),"Enter valid User name and password")
                        }
                    }

                })*/
        }else{
            Helper().showToastLong(getApplication(),"Enter valid User name")
        }
    }
}