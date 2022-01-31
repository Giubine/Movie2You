package com.example.movie2you.base

import com.example.movie2you.utils.ErrorUtils
import com.example.movie2you.utils.ResponseApi
import retrofit2.Response
import java.lang.Exception

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = safeApiResult(call)

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): ResponseApi {
        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                ResponseApi.Success(response.body()!!)
            } else {
                val error = ErrorUtils.parseError(response)

                ResponseApi.Error(error.toString())
            }
        } catch (error: Exception) {
            ResponseApi.Error(error.message.toString())
        }
    }
}