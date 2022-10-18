package xyz.teamgravity.retrofitfileupload.core.util

import android.app.Application
import java.io.File
import javax.inject.Inject

class RonaldoProvider @Inject constructor(
    private val application: Application,
) {

    companion object {
        private const val TAG = "RonaldoProvider"
    }

    fun provideImage(): File {
        val file = File(application.cacheDir, "ronaldo.jpeg")
        file.createNewFile()
        file.outputStream().use { stream ->
            application.assets.open("image/ronaldo.jpeg").copyTo(stream)
        }
        return file
    }

    fun provideBook(): File {
        val file = File(application.cacheDir, "book.pdf").also { it.createNewFile() }
        file.outputStream().use { stream ->
            application.assets.open("pdf/book.pdf").copyTo(stream)
        }

        return file
    }
}