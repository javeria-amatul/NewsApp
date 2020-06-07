package com.javeria.newsapp.data.local.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
class News() : Parcelable {

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("totalResult")
    @Expose
    private var totalResult: Int = 0

    @SerializedName("articles")
    @Expose
    @Embedded
    var articleList: List<Article>? = null

    constructor(parcel: Parcel) : this() {
        status = parcel.readString()
        totalResult = parcel.readInt()
        articleList = parcel.createTypedArrayList(Article)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeInt(totalResult)
        parcel.writeTypedList(articleList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }
    }

}