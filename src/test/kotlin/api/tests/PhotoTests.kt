package api.tests

import api.clients.PhotoClient
import api.models.Photo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PhotoTests {

    companion object {
        private val EXPECTED_PHOTO = Photo(
            id = 1,
            albumId = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )

        @JvmStatic
        fun invalidPhotosIds() = listOf(
            0,
            -1,
            50001
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


    @ParameterizedTest(name = "Photo id {0} should return 404")
    @MethodSource("invalidPhotosIds")
    fun `should return 404 for non existing post ids`(photoId: Int) {

        photoClient.getPhoto(photoId)
            .then()
            .statusCode(404)
    }

    @Test
    fun `should have photo in list by album Id`() {
        val photos = photoClient.getPhotosAsListByAlbumId(EXPECTED_PHOTO.albumId)
        assertTrue(photos.any { it.id == EXPECTED_PHOTO.id })
    }

    @Test
    fun `should get all photos by album Id`() {

        val photos = photoClient.getPhotosAsListByAlbumId(EXPECTED_PHOTO.albumId)

        assertAll(
            { assertFalse(photos.isEmpty()) },
            { assertTrue(photos.all { it.albumId == EXPECTED_PHOTO.albumId }) }
        )
    }
}