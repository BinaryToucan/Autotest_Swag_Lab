package api.tests

import api.clients.PostClient
import api.models.Post
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Test

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
    }

    private val postClient = PostClient()

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

    @Test
    fun `should have post in list`() {

        val posts = postClient.getPostsAsList()

        assertTrue(posts.any { it.id == EXPECTED_POST.id })
    }

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

    @Test
    fun `user 1 should have 10 posts`() {

        val posts = postClient.getPostsAsList()

        val userPosts = posts.filter { it.userId == 1 }

        assertEquals(10, userPosts.size)
    }

    /**
     * Всегда только 100 постов
     */
    @ParameterizedTest(name = "Post id {0} should return 404")
    @MethodSource("invalidPostIds")
    fun `should return 404 for non existing post ids`(postId: Int) {

        postClient.getPost(postId)
            .then()
            .statusCode(404)
    }

}