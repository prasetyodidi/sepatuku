package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.DetailShoe
import com.didi.sepatuku.domain.repository.ShoeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDetailShoe(
    private val shoeRepository: ShoeRepository
) {
    operator fun invoke(name: String): Flow<Resource<DetailShoe>> {

        if (name.isBlank()) {
            return flow {

            }
        }

        return shoeRepository.getDetailShoeByName(name)
    }
}