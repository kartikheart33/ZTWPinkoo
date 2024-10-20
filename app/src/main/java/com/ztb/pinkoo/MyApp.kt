package com.ztb.pinkoo

import android.app.Application
import com.ztb.pinkoo.RoomDb.AppDatabase
import com.ztb.pinkoo.RoomDb.DataRepository

class MyApp : Application() {

    // Lazily initialize the database and repository
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { DataRepository(database.dataDao()) }

    override fun onCreate() {
        super.onCreate()
        // Any other global initialization logic can go here
    }
}