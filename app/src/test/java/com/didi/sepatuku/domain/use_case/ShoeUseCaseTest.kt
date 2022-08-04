package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.data.repository.FakeShoeRepository
import com.didi.sepatuku.domain.repository.ShoeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class ShoeUseCaseTest{

    @Test
    fun `test get shoes`() = runBlocking{
        val items = shoeUseCase.getShoe().first()
        assertNotNull(items.data)
        assertEquals(26, items.data?.size)
    }

    @Test
    fun `test get detail shoe`() = runBlocking{
        val byName = shoeUseCase.getDetailShoe("c").first()
        assertNotNull(byName)
        assertEquals("c", byName.data?.name)
    }

    companion object{
        private lateinit var fakeRepository: ShoeRepository
        lateinit var shoeUseCase: ShoeUseCase

        @BeforeAll
        @JvmStatic
        fun setUp(){
            fakeRepository = FakeShoeRepository()
            shoeUseCase = ShoeUseCase(
                GetShoes(fakeRepository),
                GetDetailShoe(fakeRepository)
            )
        }
    }
}