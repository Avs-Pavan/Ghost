package com.kevin.ghost.domain

interface GhostRepo {
    suspend fun testGet(): Result<TestModel, Exception>
}