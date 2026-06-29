package api.tests

import annotations.ApiTest
import annotations.SmokeTest
import api.clients.PostClient
import api.models.CreatePostRequest
import api.models.Post
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Test

@ApiTest
class PostTests {

    companion object {
        private val EXPECTED_POST = Post(
            id = 1,
            userId = 1,
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
        )
        private const val TOTAL_POSTS = 100

        @JvmStatic
        fun invalidPostIds() = listOf(
            0,
            -1,
            TOTAL_POSTS + 1,
            9999
        )

        @JvmStatic
        fun validPosts() = listOf(
            CreatePostRequest(1, "Title 1", "Body 1"),
            CreatePostRequest(2, "Title 2", "Body 2"),
            CreatePostRequest(3, "Title 3", "Body 3")
        )
    }

    private val postClient = PostClient()

    @SmokeTest
    @Test
    fun `should get post by id`() {

        val post = postClient.getPostAsModel(EXPECTED_POST.id)

        assertAll(
            { assertEquals(EXPECTED_POST.id, post.id) },
            { assertEquals(EXPECTED_POST.userId, post.userId) },
            { assertEquals(EXPECTED_POST.title, post.title) },
            { assertEquals(EXPECTED_POST.body, post.body) },
        )
    }

    @SmokeTest
    @Test
    fun `should have post in list`() {

        val posts = postClient.getPostsAsList()

        assertTrue(posts.any { it.id == EXPECTED_POST.id })
    }

    @SmokeTest
    @Test
    fun `should get all posts`() {

        val posts = postClient.getPostsAsList()

        assertAll(
            { assertFalse(posts.isEmpty()) },
            { assertEquals(TOTAL_POSTS, posts.size) }
        )
    }

    @Test
    fun `all posts should have valid fields`() {

        val posts = postClient.getPostsAsList()

        assertTrue(
            posts.all {
                it.id > 0 &&
                        it.userId > 0 &&
                        it.title.isNotBlank() &&
                        it.body.isNotBlank()
            }
        )
    }

    @SmokeTest
    @Test
    fun `first post should contain required data`() {

        val post = postClient.getPostsAsList().first()

        assertTrue(post.id > 0)
        assertTrue(post.title.isNotBlank())
    }

    @SmokeTest
    @Test
    fun `user 1 should have 10 posts`() {

        val posts = postClient.getPostsAsList()

        val userPosts = posts.filter { it.userId == 1 }

        assertEquals(10, userPosts.size)
    }

    /**
     * Всегда только 100 постов
     */
    @SmokeTest
    @ParameterizedTest(name = "Post id {0} should return 404")
    @MethodSource("invalidPostIds")
    fun `should return 404 for non existing post ids`(postId: Int) {

        postClient.getPost(postId)
            .then()
            .statusCode(404)
    }

    @SmokeTest
    @Test
    fun `should create post`() {

        val request = CreatePostRequest(
            userId = 1,
            title = "New post",
            body = "Created from test"
        )

        val post = postClient.createPost(request)
            .then()
            .statusCode(201)
            .extract()
            .`as`(Post::class.java)

        assertAll(
            { assertEquals(request.userId, post.userId) },
            { assertEquals(request.title, post.title) },
            { assertEquals(request.body, post.body) },
            { assertTrue(post.id > 0) }
        )
    }

    @ParameterizedTest
    @MethodSource("validPosts")
    fun `should create posts with valid data`(
        request: CreatePostRequest
    ) {
        postClient.createPost(request)
            .then()
            .statusCode(201)
    }

    @Disabled("JSONPlaceholder does not validate request payload")
    @Test
    fun `should reject empty title`() {

        val request = CreatePostRequest(
            userId = 1,
            title = "",
            body = "Body"
        )

        postClient.createPost(request)
            .then()
            .statusCode(400)
    }
}