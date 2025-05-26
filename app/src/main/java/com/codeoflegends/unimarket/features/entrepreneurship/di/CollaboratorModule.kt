package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import com.codeoflegends.unimarket.features.entrepreneurship.data.remote.CollaboratorApi
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.CollaboratorRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repository.CollaboratorRepositoryImpl
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.collaborator.GetCollaboratorsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CollaboratorModule {

    @Binds
    @Singleton
    abstract fun bindCollaboratorRepository(
        collaboratorRepositoryImpl: CollaboratorRepositoryImpl
    ): CollaboratorRepository

    companion object {
        @Provides
        @Singleton
        fun provideCollaboratorApi(repository: CollaboratorRepository): GetCollaboratorsUseCase=
            GetCollaboratorsUseCase(repository)
    }

}
