package com.didi.sepatuku

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
import com.didi.sepatuku.presentation.detail_shoe.DetailShoeViewModel
import com.didi.sepatuku.presentation.shoe.ShoeViewModel
import com.didi.sepatuku.presentation.shoe_favorite.FavoriteViewModel
import com.didi.sepatuku.presentation.shopping_cart.ShoppingCartViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShoeApp : Application(){
    val appModule = module {

        fun provideShoppingCarUseCase(repository: ShoppingCartRepository): ShoppingCartUseCase{
            return ShoppingCartUseCase(
                GetShoppingCartItems(repository),
                DeleteShoppingCartItem(repository),
                UpdateShoppingCartItem(repository),
                InsertShoppingCartItem(repository)
            )
        }

        fun provideShoeFavoriteUseCase(repository: ShoeFavoriteRepository): FavoriteUseCase{
            return FavoriteUseCase(
                GetFavorites(repository),
                DeleteFavorite(repository),
                InsertFavorite(repository)
            )
        }

        fun provideShoeUseCase(repository: ShoeRepository): ShoeUseCase{
            return ShoeUseCase(
                GetShoes(repository),
                GetDetailShoe(repository)
            )
        }

        fun provideShoppingCartRepository(db: AppDatabase): ShoppingCartRepository{
            return ShoppingCartRepositoryImpl(db.shoppingCartDao())
        }

        fun provideShoeFavoriteRepository(db: AppDatabase): ShoeFavoriteRepository{
            return ShoeFavoriteRepositoryImpl(db.favoriteDao())
        }

        fun provideShoeRepository(db: AppDatabase, api: ShoeApi): ShoeRepository{
            return ShoeRepositoryImpl(db.shoeDao(), db.favoriteDao(), api)
        }

        fun provideAppDatabase(): AppDatabase{
            return Room.databaseBuilder(
                this@ShoeApp,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).build()
        }

        fun provideShoeApi(): ShoeApi{
            return Retrofit.Builder()
                .baseUrl(ShoeApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ShoeApi::class.java)
        }

        single { ShoppingCartViewModel(get()) }
        single { FavoriteViewModel(get()) }
        single { DetailShoeViewModel(get(), get(), get()) }
        single { ShoeViewModel(get()) }
        single<ShoppingCartUseCase> { provideShoppingCarUseCase(get()) }
        single<FavoriteUseCase> { provideShoeFavoriteUseCase(get()) }
        single<ShoeUseCase> { provideShoeUseCase(get()) }
        single<ShoppingCartRepository> { provideShoppingCartRepository(get()) }
        single<ShoeFavoriteRepository> { provideShoeFavoriteRepository(get()) }
        single<ShoeRepository> { provideShoeRepository(get(), get()) }
        single<AppDatabase> { provideAppDatabase() }
        single<ShoeApi> { provideShoeApi() }
    }



    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ShoeApp)
            modules(appModule)
        }
    }
}