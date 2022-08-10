package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeRepository
import kotlinx.coroutines.flow.Flow

class GetShoes(
    private val repository: ShoeRepository
) {
    operator fun invoke(): Flow<Resource<List<Shoe>>> {
        return repository.getAllShoes()
    }
}