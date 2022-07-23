package dev.bogibek.android_mvi.activity.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bogibek.android_mvi.activity.update.helper.UpdateHelper
import dev.bogibek.android_mvi.repository.UpdateRepository
import java.util.*

class UpdateViewModelFactory(private val mainHelper: UpdateHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateViewModel::class.java)) {
            return UpdateViewModel(UpdateRepository(mainHelper)) as T
        }
        throw IllformedLocaleException("Unknown class name")
    }
}