package com.gilbertopapa.dev.androidmvvm.utils

import android.text.TextUtils
import com.gilbertopapa.dev.androidmvvm.data.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkHelper {

    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> getMessageDetail(
                throwable.code(),
                throwable.response()?.errorBody()!!
            )
            is IOException -> getSubExceptionMessageDetail(throwable)
            else -> throwable.localizedMessage!!
        }
    }

    private fun getMessageDetail(
        statusCode: Int,
        errorBody: ResponseBody?
    ): String {
        var message = ""
        when (statusCode) {
            HttpURLConnection.HTTP_BAD_REQUEST ->
                message =
                    getSubErrorMessageDetail(
                        "Bad Request,\nThe request cannot be fulfilled due to bad syntax.",
                        errorBody
                    )
            HttpURLConnection.HTTP_UNAUTHORIZED ->
                message = getSubErrorMessageDetail(
                    "Unauthorized,\nAuthentication is required and has failed or has not yet been provided.",
                    errorBody
                )
            HttpURLConnection.HTTP_FORBIDDEN -> message =
                "Forbidden,\nThe request was a valid request, but the server is refusing to respond to it."
            HttpURLConnection.HTTP_NOT_FOUND -> message =
                "End Point Not Found,\nThe requested resource could not be found but may be available again in the future."
            HttpURLConnection.HTTP_BAD_METHOD -> message =
                "Method not allowed,\nA request was made of a resource using a request method not supported by that resource."
            HttpURLConnection.HTTP_NOT_ACCEPTABLE -> message =
                "Not Acceptable,\nThe requested resource is only capable of generating content not acceptable\naccording to the Accept headers sent in the request."
            HttpURLConnection.HTTP_PROXY_AUTH -> message =
                "Proxy Authentication Required,\nThe client must first authenticate itself with the proxy."
            HttpURLConnection.HTTP_CLIENT_TIMEOUT -> message =
                "Request Timeout,\nThe server timed out waiting for the request."
            HttpURLConnection.HTTP_CONFLICT -> message =
                "Conflict,\nIndicates that the request could not be processed because of conflict in the request,\nsuch as an edit conflict in the case of multiple updates."
            HttpURLConnection.HTTP_GONE -> message =
                "Gone,\nIndicates that the resource requested is no longer available and will not be available again."
            HttpURLConnection.HTTP_LENGTH_REQUIRED -> message =
                "Length Required,\nThe request did not specify the length of its content, which is required by the requested resource."
            HttpURLConnection.HTTP_PRECON_FAILED -> message =
                "Precondition Failed,\nThe server does not meet one of the preconditions that the requester put on the request."
            HttpURLConnection.HTTP_ENTITY_TOO_LARGE -> message =
                "Request Entity Too Large,\nThe request is larger than the server is willing or able to process."
            HttpURLConnection.HTTP_REQ_TOO_LONG -> message =
                "Request-URI Too Long,\nThe URI provided was too long for the server to process."
            HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> message =
                "Unsupported Media Type,\nThe request entity has a media type which the server or resource does not support."

            HttpURLConnection.HTTP_INTERNAL_ERROR ->
                message = getSubErrorMessageDetail(
                    "Internal Server Error,\n" +
                            "A generic error message,\n" +
                            "given when an unexpected condition was encountered and no more specific message is suitable.",
                    errorBody
                )
            HttpURLConnection.HTTP_NOT_IMPLEMENTED -> message =
                "Not Implemented,\nThe server either does not recognize the request method, or it lacks the ability to fulfill the request."
            HttpURLConnection.HTTP_BAD_GATEWAY -> message =
                "Bad Gateway,\nThe server was acting as a gateway or proxy and received an invalid response from the upstream server."
            HttpURLConnection.HTTP_UNAVAILABLE -> message =
                "Service Unavailable,\nThe server is currently unavailable (because it is overloaded or down for maintenance)."
            HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> message =
                "Gateway Timeout,\nThe server was acting as a gateway or proxy and did not receive a timely response from the upstream server."
            HttpURLConnection.HTTP_VERSION -> message =
                "Http version not supported,\nThe server does not support the HTTP protocol version used in the request."
        }
        return message
    }

    private fun getSubErrorMessageDetail(
        defaultMessage: String,
        errorBody: ResponseBody?
    ): String {
        var message = defaultMessage
        try {
            if (errorBody != null) {
                val errorRes = Moshi.Builder().build().adapter(com.gilbertopapa.dev.androidmvvm.data.ErrorResponse::class.java)
                    .fromJson(errorBody.string())
                if (errorRes != null) {
                    if (!TextUtils.isEmpty(errorRes.errorDesription)) {
                        message = errorRes.errorDesription
                    } else if (!TextUtils.isEmpty(errorRes.message)) {
                        message = "${errorRes.error}\n${errorRes.message}"
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return message
    }

    private fun getSubExceptionMessageDetail(exception: IOException): String {
        var message = ""
        if (exception is UnknownHostException) {
            message =
                "No internet connection detected.\nPlease check your connection and try again."
        } else if (exception is SocketTimeoutException) {
            message = "Request timeout.\nPlease try again in sometime"
        }
        return message
    }


}
