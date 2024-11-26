package com.example.musiqal.database.local

import android.content.Context

class UserSettingSharedPref(private val context: Context) {

    companion object
    {
        private val file_name="file_name"
        public val openFirstTime="firstTimeOpen"
    }
    public fun save(key:String,value:Boolean)
    {
        context.getSharedPreferences(file_name,Context.MODE_PRIVATE)
            .edit()
            .putBoolean(key,value).apply()
    }

    public fun get(key:String):Boolean
    {
        return context.getSharedPreferences(file_name,Context.MODE_PRIVATE)
            .getBoolean(key,false);
    }
}