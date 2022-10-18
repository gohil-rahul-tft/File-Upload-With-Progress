package xyz.teamgravity.retrofitfileupload.core.util

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.IOException
import okio.source
import java.io.File
import java.io.FileInputStream

/** Returns a new request body that transmits the content of this. */
/*
fun File.asRequestBodyWithProgress(
    contentType: MediaType? = null,
    progressCallback: ((progress: Float) -> Unit)?,
): RequestBody {
    return object : RequestBody() {
        override fun contentType() = contentType

        override fun contentLength() = length()

        override fun writeTo(sink: BufferedSink) {
            val fileLength = contentLength()
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            val inputStream = FileInputStream(this@asRequestBodyWithProgress)
            var uploaded = 0L
            inputStream.use {

                var read: Int = inputStream.read(buffer)
                val handler = Handler(Looper.getMainLooper())

                while (read != -1) {
                    progressCallback?.let {
                        uploaded += read
//                        val progress = (uploaded.toDouble() / fileLength.toDouble()).toFloat()
                        val progress = (100 * uploaded.toDouble() / fileLength.toDouble()).toFloat()
                        handler.post { it(progress) }

                        sink.write(buffer, 0, read)
                    }
                    read = inputStream.read(buffer)
                }

            }
        }
    }
}*/


fun File.toRequestBody(progressCallback: ((progress: Int) -> Unit)?): RequestBody {
    return object : RequestBody() {

        private var currentProgress = 0
        private var uploaded = 0L

        override fun contentType(): MediaType? {
            val fileType = name.substringAfterLast('.', "")
            return fileType.toMediaTypeOrNull()
        }

        @Throws(IOException::class)
        override fun writeTo(sink: BufferedSink) {
            source().use { source ->
                do {
                    val read = source.read(sink.buffer, 2048)
                    if (read == -1L) return // exit at EOF
                    sink.flush()
                    uploaded += read

                    /**
                     * The value of newProgress is going to be in between 0.0 - 2.0
                     */

                    var newProgress = ((uploaded.toDouble() / length().toDouble()))

                    /**
                     * To map it between 0.0 - 100.0
                     * Need to multiply it with 50
                     * (OutputMaxRange/InputMaxRange)
                     * 100 / 2 = 50
                     */
                    newProgress = (50 * newProgress)
//                    newProgress = (100 * newProgress)

                    if (newProgress.toInt() != currentProgress) {
                        progressCallback?.invoke(newProgress.toInt())
                    }
                    currentProgress = newProgress.toInt()
                } while (true)
            }
        }
    }
}
