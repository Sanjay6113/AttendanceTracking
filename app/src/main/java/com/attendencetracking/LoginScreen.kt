package com.attendencetracking

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.attendencetracking.common.Helper
import com.attendencetracking.common.SessionManager
import com.attendencetracking.databinding.ActivityLoginScreenBinding
import com.attendencetracking.viewmodel.LoginViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginScreen : AppCompatActivity() {
    lateinit var binding : ActivityLoginScreenBinding
    lateinit var viewModel : LoginViewModel
    private val db = Firebase.firestore
    lateinit var sessionManager : SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this,AndroidViewModelFactory(this.application)).get(LoginViewModel::class.java)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val actionbar = supportActionBar!!
        actionbar.setDisplayHomeAsUpEnabled(true)
        sessionManager = SessionManager(this)

        binding.login.setOnClickListener {
            login(binding.username,binding.password)
        }
    }

    fun login(username : EditText, password : EditText){
        if(!username.text.isNullOrBlank() && username.text!!.length>=3 && password.text.isNotEmpty()) {
            db.collection("login").whereEqualTo("username", username.text.toString())//.whereEqualTo("password",password.text)
                .get().addOnCompleteListener {task->
                    if (task.isSuccessful){
                        val docs = task.result.documents
                        if(docs.isNotEmpty()) {
                            val pass = docs[0].get("password").toString()
                            val type = docs[0].get("type").toString()
                            if (pass==password.text.toString()) {
                                Helper().showToastLong(
                                    application,
                                    "Login successfully"
                                )
                                sessionManager.setLogin(true)
                                sessionManager.setUser(username.text.toString(),pass,type)
                                startActivity((Intent(this,DashboardScreen::class.java)))
                                finish()
                            } else {
                                Helper().showToastLong(
                                    application,
                                    "User name or password is wrong"
                                )
                                username.text.clear()
                                password.text.clear()
                            }
                        }else{
                            Helper().showToastLong(
                                application,
                                "User not found"
                            )

                        }
                    }

                }
        }else{
            Helper().showToastLong(application,"Enter valid user name and password")
        }
    }
}
