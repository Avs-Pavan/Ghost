package com.kevin.ghost.ghostmocker

import android.content.res.AssetManager
import android.util.Log
import com.kevin.ghost.ghostmocker.GhostConstants.LOG_TAG
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Invocation


class GhostMockInterceptor(
    assetManager: AssetManager,
    private val isGlobalMockingEnabled: () -> Boolean = { true },
) : Interceptor {

    private val jsonManager = GhostJsonManager(assetManager)

    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val isMockingEnabled = isGlobalMockingEnabled()

        if (isMockingEnabled.not()) {
            return chain.proceed(initialRequest)
        }

        val method = initialRequest.tag(Invocation::class.java)?.method()
        val annotation = method?.getAnnotation(Mocked::class.java)
        if (annotation != null) {
            return getMockResponse(initialRequest, annotation.fileName, annotation.responseCode)
        }
        return chain.proceed(initialRequest)
    }

    private fun getMockResponse(request: Request, fileName: String, responseCode: Int): Response {
        Log.d(LOG_TAG, "Trying to mock request: ${request.url()} with file: $fileName")
        val jsonString = kotlin.run {
            jsonManager.getJsonFromFile(fileName)
        }
        val mockBody = ResponseBody.create(MediaType.parse("application/json"), jsonString)

        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .request(request)
            .code(responseCode)
            .message(mockBody.toString())
            .body(mockBody)
            .build()
    }

}