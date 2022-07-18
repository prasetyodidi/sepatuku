package com.didi.sepatuku.data.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.data.local.dao.FavoriteDao
import com.didi.sepatuku.data.local.dao.ShoesDao
import com.didi.sepatuku.data.remote.ShoeApi
import com.didi.sepatuku.domain.model.DetailShoe
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

class ShoeRepositoryImpl(
    private val shoeDao: ShoesDao,
    private val favDao: FavoriteDao,
    private val api: ShoeApi
) : ShoeRepository {
    override fun getAllShoes(): Flow<Resource<List<Shoe>>> = flow {
        emit(Resource.Loading())

        val shoes = shoeDao.getAllDetailShoes().map { it.intoShoe() }
        emit(Resource.Loading(data = shoes))
        delay(2000)

        try {
            val remoteShoe = api.getAllShoes()
            shoeDao.deleteShoesById(remoteShoe.map { it.id })
            shoeDao.insertAllDetailShoe(remoteShoe.map { it.intoShoeWithSize() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data =
                    shoes
                )
            )
        } catch (e: IOException){
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = shoes
                )
            )
        }

        val newShoes = shoeDao.getAllDetailShoes()
        emit(Resource.Success(newShoes.map { it.intoShoe() }))
    }

    override fun getDetailShoeByName(name: String): Flow<Resource<DetailShoe>> = flow {
        emit(Resource.Loading())

        val detailShoe = shoeDao.getDetailShoeByName(name).intoDetailShoe()
        val favorite = favDao.getAll().first().find { it.name == name }
        if (favorite != null){
            detailShoe.isFavorite = true
        }

        emit(Resource.Success(detailShoe))
    }

}