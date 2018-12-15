package com.devshub.rk.wordsstore.data.repositories

import com.devshub.rk.wordsstore.data.repositories.impl.CategoryRepositoryImpl
import com.devshub.rk.wordsstore.data.repositories.impl.WordRepositoryImpl

/**
 * Created by ZMN on 12/13/18.
 **/

val categoryRepository: CategoryRepository = CategoryRepositoryImpl()

val wordRepository: WordRepository = WordRepositoryImpl()