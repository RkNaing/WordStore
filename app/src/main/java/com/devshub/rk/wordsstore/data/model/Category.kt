package com.devshub.rk.wordsstore.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by ZMN on 12/10/18.
 **/
@Entity(tableName = "CATEGORY_TBL")
@Parcelize
data class Category(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String,
    var description: String

) : Parcelable {

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<Category> =
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                    oldItem == newItem

            }
    }

}

