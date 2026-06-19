package api.models

data class Post (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

/**
 * Dto для созадния постов
 */
data class CreatePostRequest(
    val userId: Int,
    val title: String,
    val body: String
)