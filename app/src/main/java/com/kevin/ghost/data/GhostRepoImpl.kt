package com.kevin.ghost.data

import com.kevin.ghost.data.model.TestDTO
import com.kevin.ghost.domain.GhostRepo
import com.kevin.ghost.domain.Result
import com.kevin.ghost.domain.TestModel
import javax.inject.Inject

class GhostRepoImpl @Inject constructor(
    private val testAPI: TestAPI
) : GhostRepo {
    override suspend fun testGet(): Result<TestModel, Exception> {
        val response = testAPI.getTest()
        return if (response.isSuccessful) {
            Result.Success(response.body()?.toDomain() ?: TestModel("", ""))
        } else {
            Result.Error(
                Exception(
                    "Api called failed.\nResponse code: ${response.code()}\n ErrorBody:\n ${
                        response.errorBody()?.string()
                    }"
                )
            )
        }
    }

    private fun TestDTO.toDomain(): TestModel {
        return TestModel(
            title = title,
            body = body
        )
    }
}