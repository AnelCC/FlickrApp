package com.example.flickrapp.domain

import com.example.flickrapp.data.PhotoResponse
import com.example.flickrapp.data.PhotosMetaData
import com.example.flickrapp.data.PicturesResponse
import com.example.flickrapp.data.Repository
import com.example.flickrapp.utils.ErrorText
import com.example.flickrapp.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class SearchPicturesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: Repository

    private lateinit var useCase: SearchPicturesUseCase

    private lateinit var photoList: List<PhotoResponse>
    lateinit var picturesResponse: Resource<PicturesResponse>
    lateinit var picturesResponseModel: PicturesResponse
    lateinit var photosMetaData: PhotosMetaData
    val query: String = "Cats"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockData()

        useCase = SearchPicturesUseCase(repository)
    }

    @After
    fun tearDown() {
        mockData()
        useCase = SearchPicturesUseCase(repository)
    }

    @Test
    fun shouldAllReposBeCalledWithAnEmptyList() = runBlocking {
        coEvery { repository.getPictureSearchBy(query) } returns picturesResponse

        useCase.invoke(query)

        coVerify {
            repository.getPictureSearchBy(query)
        }
    }

    @Test
    fun shouldReturnListFromRepositoryAndMatchTheSize() = runBlocking {
        coEvery { repository.getPictureSearchBy(query) } returns picturesResponse


        assert(useCase.invoke(query) is Resource.Success)
        val userCasePicturesList = useCase.invoke(query) as Resource.Success

        coVerify { repository.getPictureSearchBy(query) }
        assert(userCasePicturesList.data?.photos?.photo?.size == photoList.size)
    }

    @Test
    fun shouldReturnErrorTypeIfSuccessDataIsNull() = runBlocking {
        picturesResponse = Resource.Success(null)
        coEvery { repository.getPictureSearchBy(query) } returns picturesResponse


        assert(useCase.invoke(query) is Resource.Error)
        val userCasePicturesList = useCase.invoke(query) as Resource.Error

        assert(userCasePicturesList.message.asString() == "2131427336")
        assert(userCasePicturesList.code == null)
    }

    @Test
    fun shouldReturnError400() = runBlocking {
        picturesResponse = Resource.Error(ErrorText.DynamicString("Server error"), 400)
        coEvery { repository.getPictureSearchBy(query) } returns picturesResponse

        assert(useCase.invoke(query) is Resource.Error)
        val userCasePicturesList = useCase.invoke(query) as Resource.Error

        coVerify { repository.getPictureSearchBy(query) }

        assert(userCasePicturesList.message.asString() == "2131427338")
    }

    @Test
    fun shouldReturnError500() = runBlocking {
        picturesResponse = Resource.Error(ErrorText.DynamicString("Server error"), 500)
        coEvery { repository.getPictureSearchBy(query) } returns picturesResponse


        assert(useCase.invoke(query) is Resource.Error)
        val userCasePicturesList = useCase.invoke(query) as Resource.Error

        coVerify { repository.getPictureSearchBy(query) }

        assert(userCasePicturesList.message.asString() == "2131427337")
    }

    @Test
    fun shouldReturnErrorAny() = runBlocking {
        picturesResponse = Resource.Error(ErrorText.DynamicString("Server error"), 600)
        coEvery { repository.getPictureSearchBy(query) } returns picturesResponse

        assert(useCase.invoke(query) is Resource.Error)
        val userCasePicturesList = useCase.invoke(query) as Resource.Error

        coVerify { repository.getPictureSearchBy(query) }

        assert(userCasePicturesList.message.asString() == "2131427337")
    }


    private fun mockData() {
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

        photosMetaData = PhotosMetaData(1, photoList)
        picturesResponseModel = PicturesResponse(photosMetaData)
        picturesResponse = Resource.Success(PicturesResponse(photosMetaData))
    }

}