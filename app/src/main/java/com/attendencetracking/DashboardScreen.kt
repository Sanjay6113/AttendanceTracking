package com.attendencetracking

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.attendencetracking.common.Helper
import com.attendencetracking.common.SessionManager
import com.attendencetracking.databinding.ActivityDashboardScreenBinding
import com.attendencetracking.model.Students
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DashboardScreen : AppCompatActivity() {
    lateinit var binding : ActivityDashboardScreenBinding
    lateinit var sessionManager : SessionManager
    val students = ArrayList<Students>()
    val db  = Firebase.firestore
    lateinit var adapter : StudentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val actionbar = supportActionBar!!
        actionbar.setDisplayHomeAsUpEnabled(true)
        sessionManager = SessionManager(this)
        actionbar.setTitle(sessionManager.getUser().username)
        binding.textView.setText(sessionManager.getUser().type)

        if(sessionManager.isAdmin()){
            adapter = StudentsAdapter(students)
            binding.studentslist.adapter = adapter
            getStudents()
        }else{
            binding.button.visibility= View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.setLogout()
                Helper().showToastLong(this,"Logout Successfully")
                startActivity(Intent(this,LoginScreen::class.java))
                finish()
            }
            else -> {}
        }
        return true
    }

    fun getStudents(){
        db.collection("students").get().addOnCompleteListener { task->
            if(task.isSuccessful){
                if(task.result!=null){
                    if(task.result.documents.isNotEmpty()){
                        task.result.documents.forEach { data->
                            students.add(Students(data.get("name").toString(),data.id.toString()))
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}