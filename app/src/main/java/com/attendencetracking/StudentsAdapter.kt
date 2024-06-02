package com.attendencetracking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.attendencetracking.model.Students


class StudentsAdapter(var students : ArrayList<Students>) : RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val rollno = itemView.findViewById<TextView>(R.id.rollno)
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout

        // Inflate the custom layout
        val contactView: View = inflater.inflate(R.layout.student_list_item, parent, false)

        // Return a new holder instance

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        holder.name.setText(students[position].name)
        holder.rollno.setText(students[position].rollno)
    }
}