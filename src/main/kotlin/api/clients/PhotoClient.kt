package api.clients

import api.models.Photo
import api.utils.Endpoints
import io.restassured.response.Response

class PhotoClient: BaseApiClient() {

    fun getPhoto(id: Int): Response =
        request()
            .get("${Endpoints.PHOTOS}$id")

    fun getPhotoAsModel(id: Int): Photo =
        getPhoto(id)
            .then()
            .statusCode(200)
            .extract()
            .`as`(Photo::class.java)

    fun getPhotos(): Response =
        request()
            .get(Endpoints.POSTS)

    fun getPhotosAsList(): List<Photo> =
        getPhotos()
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList("", Photo::class.java)
}