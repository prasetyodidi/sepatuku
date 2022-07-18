package com.didi.sepatuku.domain.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.DetailShoe
import com.didi.sepatuku.domain.model.Shoe
import kotlinx.coroutines.flow.Flow

interface ShoeRepository {

    fun getAllShoes(): Flow<Resource<List<Shoe>>>

    fun getDetailShoeByName(name: String): Flow<Resource<DetailShoe>>
}