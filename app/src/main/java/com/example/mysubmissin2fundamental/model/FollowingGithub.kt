package com.example.mysubmissin2fundamental.model

import android.os.Parcel
import android.os.Parcelable

data class FollowingGithub(
    var usernamefollowing : String? ="",
    var avatarfollowing : String? = "",
    var namefollowing : String? =""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(usernamefollowing)
        parcel.writeString(avatarfollowing)
        parcel.writeString(namefollowing)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FollowingGithub> {
        override fun createFromParcel(parcel: Parcel): FollowingGithub {
            return FollowingGithub(
                parcel
            )
        }

        override fun newArray(size: Int): Array<FollowingGithub?> {
            return arrayOfNulls(size)
        }
    }
}
