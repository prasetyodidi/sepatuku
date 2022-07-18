package com.didi.sepatuku.di

import android.app.Application
import androidx.room.Room
import com.didi.sepatuku.data.local.AppDatabase
import com.didi.sepatuku.data.remote.ShoeApi
import com.didi.sepatuku.data.repository.ShoeFavoriteRepositoryImpl
import com.didi.sepatuku.data.repository.ShoeRepositoryImpl
import com.didi.sepatuku.data.repository.ShoppingCartRepositoryImpl
import com.didi.sepatuku.domain.repository.ShoeFavoriteRepository
import com.didi.sepatuku.domain.repository.ShoeRepository
import com.didi.sepatuku.domain.repository.ShoppingCartRepository
import com.didi.sepatuku.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingCartUseCase(repository: ShoppingCartRepository): ShoppingCartUseCase{
        return ShoppingCartUseCase(
            getShoppingCartItems = GetShoppingCartItems(repository),
            deleteShoppingCartItem = DeleteShoppingCartItem(repository),
            updateShoppingCartItem = UpdateShoppingCartItem(repository),
            insertShoppingCartItem = InsertShoppingCartItem(repository)
        )
    }

    @Singleton
    @Provides
    fun provideFavoriteUseCase(repository: ShoeFavoriteRepository): FavoriteUseCase{
        return FavoriteUseCase(
            getFavorites = GetFavorites(repository),
            deleteFavorite = DeleteFavorite(repository),
            insertFavorite = InsertFavorite(repository)
        )
    }

    @Singleton
    @Provides
    fun provideShoeUseCase(repository: ShoeRepository): ShoeUseCase{
        return ShoeUseCase(
            getShoe = GetShoes(repository),
            getDetailShoe = GetDetailShoe(repository)
        )
    }

    @Singleton
    @Provides
    fun provideShoppingCartRepository(
        db: AppDatabase
    ): ShoppingCartRepository{
        return ShoppingCartRepositoryImpl(db.shoppingCartDao())
    }

    @Singleton
    @Provides
    fun provideShoeFavoriteRepository(
        db: AppDatabase
    ): ShoeFavoriteRepository{
        return ShoeFavoriteRepositoryImpl(db.favoriteDao())
    }

    @Singleton
    @Provides
    fun provideShoeRepository(
        db: AppDatabase,
        api: ShoeApi
    ): ShoeRepository{
        return ShoeRepositoryImpl(db.shoeDao(), db.favoriteDao(), api)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideShoeApi(): ShoeApi{
        return Retrofit.Builder()
            .baseUrl(ShoeApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShoeApi::class.java)
    }
}