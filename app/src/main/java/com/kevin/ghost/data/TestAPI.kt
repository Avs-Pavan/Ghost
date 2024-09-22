package com.kevin.ghost.data

import com.kevin.ghost.data.model.TestDTO
import com.kevin.ghost.ghostmocker.Mocked
import retrofit2.http.GET


interface TestAPI {
//
//    @Mocked(fileName = "test", responseCode = 200)
//    @GET("test")
//    suspend fun getTest(): retrofit2.Response<TestDTO>
//
    @Mocked(fileName = "test_400", responseCode = 400)
    @GET("test")
    suspend fun getTest(): retrofit2.Response<TestDTO>


}