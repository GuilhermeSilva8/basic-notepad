package com.example.basicnotepad

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "text_table")
data class Text(
    var text: String,
    var date: String
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Text> {
        override fun createFromParcel(parcel: Parcel): Text {
            return Text(parcel)
        }

        override fun newArray(size: Int): Array<Text?> {
            return arrayOfNulls(size)
        }
    }
}
