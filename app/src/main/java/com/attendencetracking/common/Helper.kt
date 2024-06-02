package com.attendencetracking.common

import android.content.Context
import android.widget.Toast

class Helper {
    fun showToastLong(ctx:Context,str:String){
        Toast.makeText(ctx,str, Toast.LENGTH_LONG).show()
    }
    fun showToastShort(ctx:Context,str:String){
        Toast.makeText(ctx,str, Toast.LENGTH_SHORT).show()
    }
}