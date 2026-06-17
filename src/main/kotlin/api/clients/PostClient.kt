package api.clients

import api.models.Post
import api.utils.Endpoints
import io.restassured.response.Response

class PostClient: BaseApiClient() {

    fun getPost(id: Int): Response =
        request()
            .get("${Endpoints.POSTS}$id")

    fun getPostAsModel(id: Int): Post =
        getPost(id)
            .then()
            .statusCode(200)
            .extract()
            .`as`(Post::class.java)

    fun getPosts(): Response =
        request()
            .get(Endpoints.POSTS)

    fun getPostsAsList(): List<Post> =
        getPosts()
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList("", Post::class.java)
}