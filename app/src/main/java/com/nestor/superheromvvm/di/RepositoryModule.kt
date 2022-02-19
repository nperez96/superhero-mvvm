package com.nestor.superheromvvm.di

import com.nestor.superheromvvm.data.repository.character.CharacterRepository
import com.nestor.superheromvvm.data.repository.character.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCharacterRepository(characterRepository: CharacterRepositoryImpl): CharacterRepository
}