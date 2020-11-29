package com.example.mysubmissin2fundamental.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class GithubColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "github"
            const val _ID = "_id"
            const val NAME = "name"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
        }
    }
}