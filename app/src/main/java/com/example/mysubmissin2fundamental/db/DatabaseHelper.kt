package com.example.mysubmissin2fundamental.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mysubmissin2fundamental.db.DatabaseContract.GithubColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {

    companion object{

        private const val DATABASE_NAME = "dbgithub"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_GITHUB = "CREATE TABLE $TABLE_NAME" +
                "(${DatabaseContract.GithubColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "${DatabaseContract.GithubColumns.NAME} TEXT NOT NULL," +
                 "${DatabaseContract.GithubColumns.USERNAME} TEXT NOT NULL," +
                 "${DatabaseContract.GithubColumns.AVATAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GITHUB)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}