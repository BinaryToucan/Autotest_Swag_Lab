package api.tests

import api.clients.PhotoClient
import api.models.Photo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import kotlin.test.Test

class PhotoTests {

    companion object {
        private val EXPECTED_PHOTO = Photo(
            id = 1,
            albumId = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )
    }

    private val photoClient = PhotoClient()

    @Test
    fun `should get photo by id`() {
        val photo = photoClient.getPhotoAsModel(EXPECTED_PHOTO.id)

        assertAll(
            { assertEquals(EXPECTED_PHOTO.id, photo.id) },
            { assertEquals(EXPECTED_PHOTO.albumId, photo.albumId) },
            { assertEquals(EXPECTED_PHOTO.title, photo.title) },
            { assertEquals(EXPECTED_PHOTO.url, photo.url) },
            { assertEquals(EXPECTED_PHOTO.thumbnailUrl, photo.thumbnailUrl) },
        )
    }
}