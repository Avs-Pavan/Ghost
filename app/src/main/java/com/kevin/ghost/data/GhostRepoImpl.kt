package com.kevin.ghost.data

import com.kevin.ghost.data.model.TestDTO
import com.kevin.ghost.domain.GhostRepo
import com.kevin.ghost.domain.TestModel
import javax.inject.Inject

class GhostRepoImpl @Inject constructor(
    private val testAPI: TestAPI
) : GhostRepo {
    override suspend fun testGet(): TestModel {
        return testAPI.getTest().body()?.toDomain() ?: TestModel("", "")
    }

    private fun TestDTO.toDomain(): TestModel? {
        return TestModel(
            title = title,
            body = body
        )
    }
}