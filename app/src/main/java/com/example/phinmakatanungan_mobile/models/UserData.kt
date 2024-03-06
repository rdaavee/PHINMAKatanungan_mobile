package com.example.phinmakatanungan_mobile.models

import android.os.Parcel
import android.os.Parcelable

data class UserData(
    val user_id: String,
    val account_status: String,
    val user_role: String,
    val first_name: String,
    val middle_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val year_level: String,
    val course_id: String,
    val department_id: String,
    val school_id: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(user_id)
        parcel.writeString(account_status)
        parcel.writeString(user_role)
        parcel.writeString(first_name)
        parcel.writeString(middle_name)
        parcel.writeString(last_name)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(year_level)
        parcel.writeString(course_id)
        parcel.writeString(department_id)
        parcel.writeString(school_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }
}

