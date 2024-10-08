package com.example.mysubmissin2fundamental.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mysubmissin2fundamental.db.DatabaseContract.GithubColumns.Companion.TABLE_NAME
import com.example.mysubmissin2fundamental.db.DatabaseContract.GithubColumns.Companion._ID
import java.sql.SQLException

class GithubHelper (context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: GithubHelper? = null

        fun getInstance(context: Context) : GithubHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: GithubHelper(context)
            }

        private lateinit var database: SQLiteDatabase

    }

    init{
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)


    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int{
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int{
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }



}