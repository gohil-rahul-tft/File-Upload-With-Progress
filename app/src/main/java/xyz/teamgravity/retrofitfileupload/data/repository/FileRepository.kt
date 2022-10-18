package xyz.teamgravity.retrofitfileupload.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import xyz.teamgravity.retrofitfileupload.core.util.toRequestBody
import xyz.teamgravity.retrofitfileupload.data.remote.api.FileApi
import xyz.teamgravity.retrofitfileupload.data.remote.constant.FileApiConst
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FileRepository @Inject constructor(
    private val api: FileApi,
) {

    companion object {
        private const val TAG = "FileRepository"
    }

    ///////////////////////////////////////////////////////////////////////////
    // UPLOAD
    ///////////////////////////////////////////////////////////////////////////

    suspend fun uploadImage(file: File, progress: (progress: Int) -> Unit): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext try {
//                api.uploadImage(
//                    MultipartBody.Part.createFormData(
//                        FileApiConst.TYPE_IMAGE,
//                        file.name,
//                        file.asRequestBody()
//                        // file.asRequestBody("image/*".toMediaTypeOrNull())
//                    )
//                )
                api.uploadImage(
                    MultipartBody.Part.createFormData(
                        FileApiConst.TYPE_IMAGE,
                        file.name,
                        file.toRequestBody {
                            Log.d(TAG, "uploadImage: PROGRESS $it")
                            progress(it)
                        }
                    )

                )

                true
            } catch (e: HttpException) {
                false
            } catch (e: IOException) {
                false
            }
        }
    }


    suspend fun uploadPDF(file: File, progress: (progress: Int) -> Unit): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                api.uploadPDF(
                    MultipartBody.Part.createFormData(
                        FileApiConst.TYPE_PDF,
                        file.name,
                        file.toRequestBody {
                            Log.d(TAG, "uploadPDF: PROGRESS $it")
                            progress(it)
                        }
                    )

                )
                true
            } catch (e: HttpException) {
                Log.d(TAG, "uploadPDF: ${e.message}")
                false
            } catch (e: IOException) {
                Log.d(TAG, "uploadPDF: ${e.message}")
                false
            }
        }
    }

}