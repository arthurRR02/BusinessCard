package com.example.businesscard.di

import androidx.room.Room
import com.example.businesscard.data.BusinessCardDatabase
import com.example.businesscard.data.BusinessCardRepository
import com.example.businesscard.ui.main.MainViewModel
import com.example.businesscard.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), BusinessCardDatabase::class.java, "businessCardDB")
            .fallbackToDestructiveMigration().build()
    }
    single { get<BusinessCardDatabase>().businessCardDao() }
    single { BusinessCardRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}