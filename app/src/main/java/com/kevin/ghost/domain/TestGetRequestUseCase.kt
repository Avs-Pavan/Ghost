package com.kevin.ghost.domain

import javax.inject.Inject

class TestGetRequestUseCase @Inject constructor(
    private val ghostRepo: GhostRepo
) {
    suspend operator fun invoke(): TestModel {
        return ghostRepo.testGet()
    }
}
