package com.kevin.ghost.domain

interface GhostRepo {
    suspend fun testGet(): TestModel
}