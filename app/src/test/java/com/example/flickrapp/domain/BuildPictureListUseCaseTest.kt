package com.example.flickrapp.domain

import com.example.flickrapp.data.PhotoResponse
import com.example.flickrapp.data.PhotosMetaData
import com.example.flickrapp.data.PicturesResponse
import com.example.flickrapp.data.RepositoryProvider
import com.example.flickrapp.data.model.Picture
import com.example.flickrapp.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class BuildPictureListUseCaseTest {

    private lateinit var useCase: BuildPictureListUseCase
    private lateinit var photoList: List<PhotoResponse>

    lateinit var picturesResponse: Resource<PicturesResponse>
    lateinit var picturesResponseModel: PicturesResponse
    lateinit var photosMetaData: PhotosMetaData

    @RelaxedMockK
    lateinit var picturesProvider: RepositoryProvider
    lateinit var picturesProviderList: List<Picture>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockData()

        useCase = BuildPictureListUseCase()
    }

    @Test
    fun shouldReturnListSize() = runBlocking {
        val userCaseReposList = useCase.invoke(picturesResponse)

        assert(userCaseReposList.size == photoList.size)
    }

    @Test
    fun shouldReturnTheCorrectId() = runBlocking {
        val userCaseReposList = useCase.invoke(picturesResponse)

        assert(userCaseReposList[0].id == photoList[0].id)
        assert(userCaseReposList[1].id == photoList[1].id)
        assert(userCaseReposList[2].id == photoList[2].id)
    }


    @Test
    fun shouldReturnTheCorrectTitle() = runBlocking {
        val userCaseReposList = useCase.invoke(picturesResponse)

        assert(userCaseReposList[0].title == photoList[0].title)
        assert(userCaseReposList[1].title == photoList[1].title)
        assert(userCaseReposList[2].title == photoList[2].title)
    }

    @Test
    fun shouldReturnTheCorrectUrl() = runBlocking {
        val userCaseReposList = useCase.invoke(picturesResponse)

        val url0 =
            "https://farm${photoList[0].farm}.staticflickr.com/${photoList[0].server}/${photoList[0].id}_${photoList[0].secret}.jpg"
        val url1 =
            "https://farm${photoList[1].farm}.staticflickr.com/${photoList[1].server}/${photoList[1].id}_${photoList[1].secret}.jpg"
        val url2 =
            "https://farm${photoList[2].farm}.staticflickr.com/${photoList[2].server}/${photoList[2].id}_${photoList[2].secret}.jpg"

        assertEquals(userCaseReposList[0].url, url0)
        assertEquals(userCaseReposList[1].url, url1)
        assertEquals(userCaseReposList[2].url, url2)
    }

    @Test
    fun shouldReturnTheCorrectLocalDataProvider() = runBlocking {
        coEvery { picturesProvider.pictures } returns picturesProviderList
        val userCaseReposList = useCase.invoke(picturesResponse)

        assert(userCaseReposList[0].url.equals(picturesProviderList[0].url, true))
        assert(userCaseReposList[1].url.equals(picturesProviderList[1].url, true))
        assert(userCaseReposList[2].url.equals(picturesProviderList[2].url, true))
    }

    @Test
    fun shouldReturnTheCorrectLocalDataProviderWhenIsNotPresent() = runBlocking {
        photosMetaData = PhotosMetaData(1, listOf(null, null))
        picturesResponseModel = PicturesResponse(photosMetaData)
        picturesResponse = Resource.Success(PicturesResponse(photosMetaData))
        useCase = BuildPictureListUseCase()

        coEvery { picturesProvider.pictures } returns picturesProviderList
        val userCaseReposList = useCase.invoke(picturesResponse)


        assert(userCaseReposList.size == 2)
        assert(userCaseReposList[0].url.equals("https://farmnull.staticflickr.com/null/null_null.jpg"))
        assert(userCaseReposList[1].url.equals("https://farmnull.staticflickr.com/null/null_null.jpg"))
    }


    fun mockData() {
        photoList = listOf(
            PhotoResponse(
                "123456",
                "anel",
                "abecd1234",
                "server",
                1,
                "cat",
            ),
            PhotoResponse(
                "654123",
                "wlizabeth",
                "gahid1234",
                "server",
                2,
                "dog",
            ),
            PhotoResponse(
                "654321",
                "howard",
                "hijkl1234",
                "server",
                3,
                "fish",
            )
        )

        val url0 =
            "https://farm${photoList[0].farm}.staticflickr.com/${photoList[0].server}/${photoList[0].id}_${photoList[0].secret}.jpg"
        val url1 =
            "https://farm${photoList[1].farm}.staticflickr.com/${photoList[1].server}/${photoList[1].id}_${photoList[1].secret}.jpg"
        val url2 =
            "https://farm${photoList[2].farm}.staticflickr.com/${photoList[2].server}/${photoList[2].id}_${photoList[2].secret}.jpg"

        picturesProviderList = listOf(
            Picture("123456", url0, "cat"),
            Picture("654123", url1, "dog"),
            Picture("654321", url2, "fish")
        )
        photosMetaData = PhotosMetaData(1, photoList)
        picturesResponseModel = PicturesResponse(photosMetaData)
        picturesResponse = Resource.Success(PicturesResponse(photosMetaData))
    }
}