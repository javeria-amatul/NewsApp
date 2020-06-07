package com.javeria.newsapp.data.local.converter


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javeria.newsapp.data.local.db.entity.Source

class SourceConverter {

    @TypeConverter
    fun fromString(value: String) : Source {
        val listType = object : TypeToken<Source>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromSource(source: Source): String {
        val gson = Gson()
        val json = gson.toJson(source)
        return json
    }
}