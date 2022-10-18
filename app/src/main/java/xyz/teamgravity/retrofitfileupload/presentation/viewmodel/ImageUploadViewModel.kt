package xyz.teamgravity.retrofitfileupload.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.retrofitfileupload.core.util.RonaldoProvider
import xyz.teamgravity.retrofitfileupload.data.repository.FileRepository
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val repository: FileRepository,
    private val ronaldo: RonaldoProvider,
) : ViewModel() {

    val imageProgressState = mutableStateOf(0)
    val pdfProgressState = mutableStateOf(0)
    val isImageProgressing = mutableStateOf(false)
    val isPDFProgressing = mutableStateOf(false)

    fun onUploadImage() {
        viewModelScope.launch {

            isImageProgressing.value = true
            repository.uploadImage(ronaldo.provideImage()) {
                imageProgressState.value = it
                if (it == 100) {
                    isImageProgressing.value = false
                }
            }
            println("Uploaded Success!")
        }
    }

    fun onUploadPDF() {
        viewModelScope.launch {

            isPDFProgressing.value = true
            repository.uploadPDF(ronaldo.provideBook()) {
                pdfProgressState.value = it
                if (it == 100) {
                    isPDFProgressing.value = false
                }
            }
            println("Uploaded Success!")
        }
    }
}