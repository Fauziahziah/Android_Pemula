package com.example.mysubmissin2fundamental.helper

import android.database.Cursor
import com.example.mysubmissin2fundamental.db.DatabaseContract
import com.example.mysubmissin2fundamental.model.github

object MappingHelper {
    fun mapCursorToArrayList(GithubsCursor: Cursor?): ArrayList<github> {
        val GithubsList = ArrayList<github>()

        GithubsCursor?.apply{
            while(moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.GithubColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.GithubColumns.NAME))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.GithubColumns.AVATAR))
                GithubsList.add(github(id, name, username,avatar))
            }
        }
      return GithubsList
    }
}