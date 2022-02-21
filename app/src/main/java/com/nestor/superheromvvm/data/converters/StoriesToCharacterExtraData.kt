package com.nestor.superheromvvm.data.converters

import com.nestor.superheromvvm.data.model.CharacterComicsDataWrapper
import com.nestor.superheromvvm.data.model.CharacterExtraData
import com.nestor.superheromvvm.data.model.CharacterSeriesDataWrapper
import com.nestor.superheromvvm.data.model.CharacterStoriesDataWrapper

fun CharacterStoriesDataWrapper.toCharacterExtraData(): List<CharacterExtraData> {
    return this.data.results.map {
        CharacterExtraData(
            name = it.title,
            thumbnail = if (it.thumbnail != null) "${it.thumbnail.path}.${it.thumbnail.extension}" else null
        )
    }
}