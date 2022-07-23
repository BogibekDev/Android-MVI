package dev.bogibek.android_mvi.activity.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bogibek.android_mvi.activity.create.helper.CreateHelper
import dev.bogibek.android_mvi.repository.CreateRepository
import java.util.*

class CreateViewModelFactory(private val mainHelper: CreateHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            return CreateViewModel(CreateRepository(mainHelper)) as T
        }
        throw IllformedLocaleException("Unknown class name")
    }
}