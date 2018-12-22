package com.devshub.rk.wordsstore.utils

import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.model.WordWithCategory

/**
 * Created by ZMN on 12/13/18.
 **/

typealias CompletionCallback = (Boolean) -> Unit

typealias CategoryItemClickCallback = (Category) -> Unit

typealias WordWithCategoryCallback = (WordWithCategory) -> Unit
