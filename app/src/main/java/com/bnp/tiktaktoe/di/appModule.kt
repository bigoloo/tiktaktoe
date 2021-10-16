package com.bnp.tiktaktoe.di


import com.bnp.tiktaktoe.TikTakController
import com.bnp.tiktaktoe.TikTakViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val gameApplicationModule = module {
    single {
        TikTakController()
    }
    viewModel {
        TikTakViewModel(get())
    }


}