package com.nhlynn.movie_tmdb.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieVO(

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = 1,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backDropPath: String? = null,

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    val popularity: Double? = null,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String? = null,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean? = false
) : Parcelable

const val UPCOMING = "UPCOMING"
const val POPULAR = "POPULAR"