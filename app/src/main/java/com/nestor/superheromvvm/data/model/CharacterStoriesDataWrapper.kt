package com.nestor.superheromvvm.data.model


import com.google.gson.annotations.SerializedName

data class CharacterStoriesDataWrapper(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: CharacterStoriesDataWrapper,
    val etag: String,
    val status: String
) {
    data class CharacterStoriesDataWrapper(
        val count: Int,
        val limit: Int,
        val offset: Int,
        val results: List<Stories>,
        val total: Int
    ) {
        data class Stories(
            val characters: Characters,
            val comics: Comics,
            val creators: Creators,
            val description: String,
            val events: Events,
            val id: Int,
            val modified: String,
            val originalIssue: OriginalIssue,
            val resourceURI: String,
            val series: Series,
            val thumbnail: Thumbnail?,
            val title: String,
            val type: String
        ) {
            data class Characters(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            ) {
                data class Item(
                    val name: String,
                    val resourceURI: String
                )
            }

            data class Thumbnail(
                val extension: String,
                val path: String
            )

            data class Comics(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            ) {
                data class Item(
                    val name: String,
                    val resourceURI: String
                )
            }

            data class Creators(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            ) {
                data class Item(
                    val name: String,
                    val resourceURI: String,
                    val role: String
                )
            }

            data class Events(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            ) {
                data class Item(
                    val name: String,
                    val resourceURI: String
                )
            }

            data class OriginalIssue(
                val name: String,
                val resourceURI: String
            )

            data class Series(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            ) {
                data class Item(
                    val name: String,
                    val resourceURI: String
                )
            }
        }
    }
}