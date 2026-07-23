package api.clients

import api.models.CreatePostRequest
import api.models.Post
import api.utils.MainEndpoints
import io.restassured.response.Response

class PostClient: BaseApiClient() {

    fun getPost(id: Int): Response =
        request()
            .get("${MainEndpoints.POSTS}/$id")

    fun getPostAsModel(id: Int): Post =
        getPost(id)
            .then()
            .statusCode(200)
            .extract()
            .`as`(Post::class.java)

    fun getPosts(): Response =
        request()
            .get(MainEndpoints.POSTS)

    fun getPostsAsList(): List<Post> =
        getPosts()
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList("", Post::class.java)

    fun createPost(post: CreatePostRequest): Response =
        request()
            .body(post)
            .post(MainEndpoints.POSTS)
}