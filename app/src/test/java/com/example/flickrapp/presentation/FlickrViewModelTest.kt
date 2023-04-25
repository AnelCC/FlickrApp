package com.example.flickrapp.presentation

import com.example.flickrapp.MainDispatcherRule
import com.example.flickrapp.data.PhotoResponse
import com.example.flickrapp.data.PhotosMetaData
import com.example.flickrapp.data.PicturesResponse
import com.example.flickrapp.data.model.Picture
import com.example.flickrapp.domain.BuildPictureListUseCase
import com.example.flickrapp.domain.PicturesFlickrUseCase
import com.example.flickrapp.domain.SearchPicturesUseCase
import com.example.flickrapp.utils.ErrorText
import com.example.flickrapp.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class FlickrViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @RelaxedMockK
    private lateinit var buildPictureListUseCase: BuildPictureListUseCase

    @RelaxedMockK
    private lateinit var picturesFlickrUseCase: PicturesFlickrUseCase

    @RelaxedMockK
    private lateinit var searchPicturesUseCase: SearchPicturesUseCase


    private lateinit var photoList: List<PhotoResponse>
    lateinit var picturesResponse: Resource<PicturesResponse>
    private lateinit var picturesResponseError: Resource<Resource.Error>
    lateinit var picturesResponseModel: PicturesResponse
    lateinit var photosMetaData: PhotosMetaData
    lateinit var picturesList: List<Picture>
    lateinit var pictureModel: Picture

    private lateinit var viewModel: FlickrViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockData()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldAllPicturesAndBuildPictureListUseCaseBeCalled() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns Resource.Success(null)
        coEvery { buildPictureListUseCase.invoke(Resource.Success(null)) } returns emptyList()

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )


        viewModel.onLoadInitData()

        coVerify {
            picturesFlickrUseCase.invoke()
        }
        coVerify {
            buildPictureListUseCase.invoke(Resource.Success(null))
        }
    }

    @Test
    fun shouldSearchPicturesAndBuildPictureListUseCaseBeCalled() = runTest {
        coEvery { searchPicturesUseCase.invoke("cat") } returns Resource.Success(null)
        coEvery { buildPictureListUseCase.invoke(Resource.Success(null)) } returns emptyList()

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )

        val job = launch {
            viewModel.onSearchPictures("cat")
        }

        runCurrent()

        coVerify {
            searchPicturesUseCase.invoke("cat")
        }

        coVerify {
            buildPictureListUseCase.invoke(Resource.Success(null))
        }

        job.cancel()
    }

    @Test
    fun shouldAllPicturesUseCaseBeCalled() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns Resource.Error(
            ErrorText.DynamicString("Server error"),
            400
        )

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )


        viewModel.onLoadInitData()

        coVerify {
            picturesFlickrUseCase.invoke()
        }

        coVerify(exactly = 0) {
            buildPictureListUseCase.invoke(Resource.Success(null))
        }
    }

    @Test
    fun shouldSearchPicturesUseCaseBeCalled() = runTest {
        coEvery { searchPicturesUseCase.invoke("cat") } returns Resource.Error(
            ErrorText.DynamicString(
                "Server error"
            ), 400
        )

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )


        val job = launch {
            viewModel.onSearchPictures("cat")
        }

        runCurrent()

        coVerify {
            searchPicturesUseCase.invoke("cat")
        }

        coVerify(exactly = 0) {
            buildPictureListUseCase.invoke(Resource.Success(null))
        }

        job.cancel()
    }

    @Test
    fun shouldInitializedLoadingAndGetData() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns Resource.Success(picturesResponseModel)

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )


        assertEquals(viewModel.uiState.value, Resource.Loading(true))

        val job = launch {
            viewModel.onLoadInitData()
        }

        coVerify {
            picturesFlickrUseCase.invoke()
        }

        coVerify(exactly = 0) {
            buildPictureListUseCase.invoke(Resource.Success(null))
        }


        assertEquals(Resource.Success(picturesResponse), viewModel.uiState.value)

        job.cancel()
    }

    @Test
    fun shouldNoGetInfo() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns Resource.Success(picturesResponseModel)

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )

        assertEquals(viewModel.uiState.value, Resource.Loading(true))

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        assertEquals(Resource.Success(picturesResponse), viewModel.uiState.value)

        val job = launch {
            viewModel.noInfo()
        }

        runCurrent()

        assertEquals(Resource.Success(null), viewModel.uiState.value)

        job.cancel()
    }


    @Test
    fun shouldSelectItem() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns picturesResponse
        coEvery { buildPictureListUseCase.invoke(picturesResponse) } returns picturesList

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )

        assertEquals(viewModel.uiState.value, Resource.Loading(true))

        val job = launch {
            viewModel.onSelectItem(pictureModel)
        }

        runCurrent()

        assertEquals(pictureModel, viewModel.selectedPicture.value)

        job.cancel()
    }

    @Test
    fun shouldUpdateSearchText() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns picturesResponse
        coEvery { buildPictureListUseCase.invoke(picturesResponse) } returns picturesList

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )

        assertEquals(Resource.Loading(true), viewModel.uiState.value)
        assertEquals("", viewModel.searchText.value)

        val job = launch {
            viewModel.onSearchTextChange("text")
        }

        runCurrent()

        coVerify {
            searchPicturesUseCase.invoke("text")
        }
        coVerify {
            buildPictureListUseCase.invoke(picturesResponse)
        }

        assertEquals(Resource.Success(picturesResponse), viewModel.uiState.value)
        assertEquals("text", viewModel.searchText.value)

        job.cancel()
    }

    @Test
    fun shouldUpdateSearchTextWhenIsEmpty() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns picturesResponse
        coEvery { buildPictureListUseCase.invoke(picturesResponse) } returns picturesList

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )

        assertEquals(Resource.Loading(true), viewModel.uiState.value)
        assertEquals("", viewModel.searchText.value)

        val job = launch {
            viewModel.onSearchTextChange("")
        }

        runCurrent()

        coVerify(exactly = 0) {
            searchPicturesUseCase.invoke("")
        }
        coVerify {
            picturesFlickrUseCase.invoke()
        }
        coVerify {
            buildPictureListUseCase.invoke(picturesResponse)
        }

        assertEquals(Resource.Success(picturesResponse), viewModel.uiState.value)
        assertEquals("", viewModel.searchText.value)

        job.cancel()
    }

    @Test
    fun shouldUpdateSearchTextWhenIsReset() = runTest {
        coEvery { picturesFlickrUseCase.invoke() } returns picturesResponse
        coEvery { buildPictureListUseCase.invoke(picturesResponse) } returns picturesList

        viewModel = FlickrViewModel(
            picturesFlickrUseCase,
            searchPicturesUseCase,
            buildPictureListUseCase
        )

        assertEquals(Resource.Loading(true), viewModel.uiState.value)
        assertEquals("", viewModel.searchText.value)

        val job = launch {
            viewModel.resetTextChanged()
        }

        runCurrent()

        coVerify(exactly = 0) {
            searchPicturesUseCase.invoke("")
        }
        coVerify {
            picturesFlickrUseCase.invoke()
        }
        coVerify {
            buildPictureListUseCase.invoke(picturesResponse)
        }

        assertEquals(Resource.Success(picturesResponse), viewModel.uiState.value)
        assertEquals("", viewModel.searchText.value)

        job.cancel()
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
        val url0 =
            "https://farm${photoList[0].farm}.staticflickr.com/${photoList[0].server}/${photoList[0].id}_${photoList[0].secret}.jpg"
        val url1 =
            "https://farm${photoList[1].farm}.staticflickr.com/${photoList[1].server}/${photoList[1].id}_${photoList[1].secret}.jpg"
        val url2 =
            "https://farm${photoList[2].farm}.staticflickr.com/${photoList[2].server}/${photoList[2].id}_${photoList[2].secret}.jpg"

        pictureModel = Picture("123456", url0, "cat")

        picturesList = listOf(
            Picture("123456", url0, "cat"),
            Picture("654123", url1, "dog"),
            Picture("654321", url2, "fish")
        )

        photosMetaData = PhotosMetaData(1, photoList)
        picturesResponseModel = PicturesResponse(photosMetaData)
        picturesResponse = Resource.Success(PicturesResponse(photosMetaData))
        picturesResponseError = Resource.Error(ErrorText.DynamicString("Server error"), 400)
    }
}
