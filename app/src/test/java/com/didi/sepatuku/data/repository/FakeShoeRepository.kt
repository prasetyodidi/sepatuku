package com.didi.sepatuku.data.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.DetailShoe
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeShoeRepository: ShoeRepository {
    private val listDetail = mutableListOf<DetailShoe>()
    init {
        val shoesToInsert = mutableListOf<DetailShoe>()
        ('a'..'z').forEachIndexed { index, c ->
            shoesToInsert.add(
                DetailShoe(
                    id = index.toString(),
                    name = c.toString(),
                    price = index * 100000,
                    imageUrl = c.toString(),
                    sizes = listOf(1, 2, 3, 4, 5),
                    desc = "desc $c",
                    stock = index + 1,
                    isFavorite = index % 2 != 0
                )
            )
        }
        shoesToInsert.shuffle()
        listDetail.addAll(shoesToInsert)
    }
    override fun getAllShoes(): Flow<Resource<List<Shoe>>> = flow {
        emit(
            Resource.Success(data = listDetail.map { it.intoShoe() })
        )
    }

    override fun getDetailShoeByName(name: String): Flow<Resource<DetailShoe>> = flow{
        emit(
            Resource.Success(data = listDetail.find { it.name == name })
        )
    }
}