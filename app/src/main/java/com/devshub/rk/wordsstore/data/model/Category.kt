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

        fun getStockedCategories(): List<Category> {
            return mutableListOf<Category>().also {
                it.add(
                    Category(
                        title = "Clause",
                        description = "A group of words with a verb and a subject."
                    )
                )
                it.add(
                    Category(
                        title = "Idiom",
                        description = "An expression whose meaning is not predictable from the usual meanings of its constituent elements.A language, dialect, or style of speaking peculiar to a people."
                    )
                )
                it.add(
                    Category(
                        title = "Phrase",
                        description = "Any group of words that does not contain a subject completing an action."
                    )
                )
                it.add(Category(title = "Newly Learned Vocab", description = "Newly learned words."))
                it.add(
                    Category(
                        title = "Slang",
                        description = "Very informal language that is usually spoken rather than written, used especially by particular groups of people."
                    )
                )
                it.add(
                    Category(
                        title = "Stylish Vocab",
                        description = "Words to describe common things in a special manner."
                    )
                )

            }
        }
    }

}

