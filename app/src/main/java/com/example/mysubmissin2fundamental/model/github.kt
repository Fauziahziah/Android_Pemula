package com.example.mysubmissin2fundamental.model

import android.os.Parcel
import android.os.Parcelable


data class github(
    var id: Int = 0,
    var username: String? = "",
    var avatar: String? = "",
    var name: String? =""

 ) : Parcelable {
     constructor(parcel: Parcel) : this(
         parcel.readInt(),
         parcel.readString(),
         parcel.readString(),
         parcel.readString()

     ) {
     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
         parcel.writeInt(id)
         parcel.writeString(username)
         parcel.writeString(avatar)
         parcel.writeString(name)
     }

     override fun describeContents(): Int {
         return 0
     }

     companion object CREATOR : Parcelable.Creator<github> {
         override fun createFromParcel(parcel: Parcel): github {
             return github(parcel)
         }
         override fun newArray(size: Int): Array<github?> {
             return arrayOfNulls(size)

         }
     }
 }
