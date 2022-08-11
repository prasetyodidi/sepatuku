package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.data.repository.FakeShoeFavoriteRepository
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeFavoriteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class FavoriteUseCaseTest{

    @Test
    fun `test insert favorite`() = runBlocking{
        val item = Shoe(
            "ab",
            1,
            "az"
        )
        assertDoesNotThrow {
            runBlocking {
                shoeFavoriteUseCase.insertFavorite(item)
            }
        }
    }

    @Test
    fun `test get all items`() = runBlocking{
        val items = shoeFavoriteUseCase.getFavorites().first()
        assertEquals(26, items.data?.size)
    }

    @Test
    fun `test delete item by name`() = runBlocking{
        val items1 = shoeFavoriteUseCase.getFavorites().first()
        assertNotNull(items1.data?.size)
        val size: Int = items1.data!!.size
        shoeFavoriteUseCase.deleteFavorite("c")
        runBlocking {
            val items2 = shoeFavoriteUseCase.getFavorites().first()
            assertEquals(size-1, items2.data?.size)
        }
    }

    companion object{
        lateinit var shoeFavoriteUseCase: FavoriteUseCase
        private lateinit var fakeRepository: ShoeFavoriteRepository

        @BeforeAll
        @JvmStatic
        fun setUp(){
            fakeRepository = FakeShoeFavoriteRepository()
            shoeFavoriteUseCase = FavoriteUseCase(
                GetFavorites(fakeRepository),
                DeleteFavorite(fakeRepository),
                InsertFavorite(fakeRepository)
            )

            val listInsert = mutableListOf<Shoe>()
            ('a'..'z').forEachIndexed { index, c ->
                val item = Shoe(
                    name = c.toString(),
                    price = index,
                    imageUrl = c.toString()
                )
                listInsert.add(item)
            }
            listInsert.forEach { item ->
                runBlocking { shoeFavoriteUseCase.insertFavorite(item) }
            }
        }
    }
}