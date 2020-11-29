package com.example.mysubmissin2fundamental.model

import android.os.Parcel
import android.os.Parcelable

data class FollowerGithub (
    var usernamefollowers: String? = "",
    var avatarfollowers: String? ="",
    var namefollowers: String? =""

 ): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(usernamefollowers)
        parcel.writeString(avatarfollowers)
        parcel.writeString(namefollowers)
            }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FollowerGithub> {
        override fun createFromParcel(parcel: Parcel): FollowerGithub {
            return FollowerGithub(
                parcel
            )
        }

        override fun newArray(size: Int): Array<FollowerGithub?> {
            return arrayOfNulls(size)
        }
    }
}
