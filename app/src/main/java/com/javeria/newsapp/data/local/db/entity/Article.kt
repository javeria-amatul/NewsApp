package com.javeria.newsapp.data.local.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "articleList")
class Article() : Parcelable{

    @SerializedName("source")
    @Expose
    var source: Source? = null

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    var id: Long = 0

    @SerializedName("sourceName")
    @Expose
    var sourceName: String? = null

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @Expose
    @ColumnInfo
    var articleInsertionTime: Long = 0L

    constructor(parcel: Parcel) : this() {
        source = parcel.readParcelable(Source::class.java.classLoader)
        id = parcel.readLong()
        sourceName = parcel.readString()
        author = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        url = parcel.readString()
        urlToImage = parcel.readString()
        publishedAt = parcel.readString()
        content = parcel.readString()
        articleInsertionTime = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeLong(id)
        parcel.writeString(sourceName)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
        parcel.writeLong(articleInsertionTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }


}
