package com.kevin.ghost.di

import com.kevin.ghost.data.GhostRepoImpl
import com.kevin.ghost.domain.GhostRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindRepo(repo: GhostRepoImpl): GhostRepo
}