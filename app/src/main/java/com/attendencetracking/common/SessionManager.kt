package com.attendencetracking.common

import android.content.Context
import android.content.SharedPreferences
import com.attendencetracking.model.UserModel

class SessionManager(context: Context) {
    lateinit var preference : SharedPreferences
    lateinit var pref : SharedPreferences.Editor

    init {
        preference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE);
        pref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).edit();
    }

    fun storeString(key:String,value:Any){
        when(value) {
            is String->  pref.putString(key, value)
            is Boolean->  pref.putBoolean(key, value)
            is Float ->  pref.putFloat(key, value)
            is Long->  pref.putLong(key, value)
            else -> pref.putString(key, value.toString())
        }
        pref.commit()
    }
    fun setLogin(value:Boolean){
        pref.putBoolean(Constants.login,value)
        pref.commit()
    }
    fun setLogout(){
        pref.putBoolean(Constants.login,false)
        pref.clear()
        pref.commit()
    }
    fun setUser(username : String, password:String,type : String){
        pref.putString(Constants.username,username)
        pref.putString(Constants.password,password)
        pref.putString(Constants.type,type)
        pref.commit()
    }

    fun isLogedIn(): Boolean {
        return preference.getBoolean(Constants.login,false)
    }
    fun isAdmin(): Boolean {
        val admin = preference.getString(Constants.type,"")
        return admin=="admin"
    }
    fun getUser():UserModel{
        val username = preference.getString(Constants.username,"") ?: ""
        val type = preference.getString(Constants.type,"") ?: ""
        return UserModel(username,type)
    }
}