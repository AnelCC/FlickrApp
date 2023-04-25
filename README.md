# Welcome to FlickrApp

# About the project
This is using this Api Libraries 

. Api **flickr.photos.search** [Doc](https://www.flickr.com/services/api/flickr.photos.search.html)
. Api **flickr.photos.getRecent** [Doc](https://www.flickr.com/services/api/flickr.photos.getRecent.html)


# Build by branches

1. Planing the architecture [Code here](https://github.com/AnelCC/FlickrApp/pull/1)
0. Thinking in the UI [Code here](https://github.com/AnelCC/FlickrApp/pull/2)
0. Working on the real data [Code here](https://github.com/AnelCC/FlickrApp/pull/3)
0. Use Navigation [Code here](https://github.com/AnelCC/FlickrApp/pull/4)
0. Error Always happened [Code here](https://github.com/AnelCC/FlickrApp/pull/5)
0. Testing [Code here](https://github.com/AnelCC/FlickrApp/pull/6)


### Package Structure

```
com.anelcc.FlickrApp    # Root Package
.
├── core                # Core and Data are part of the data layer contains application data and business logic. 

│   │── ApiService      # For API Service,this handled the method or endpoints from network API.
│   └── Constants      
├── data                # For API Repositories, Model classes, and  local data and to handle network responses.
│   │── Model           # The data layer is made of repositories that each can contain zero to many data sources.
│   │── ManagerService  # The data layer is for repository and data sources. 
│   └── Repository             
│
├── di                   # Dependencies injection will help to provide the information easy and faster
│   └── NetworkModule    
│
├── domain                       # The domain layer is responsible for encapsulating complex business logic, 
│   │── BuildPictureListUseCase  # or simple business logic that is reused by multiple ViewModels. 
│   │── PicturesFlickrUseCase    # Use Case is the package where the user’s work is defined, that is,
│   └── SearchPicturesUseCase    # the whole business logic of the project.
│
├── presentation           # The role of the UI layer (or presentation layer) is to display the application data on the screen. 
│   │── detail screen      # UI elements that render the data on the screen. 
│   │── home screen        # These elements using Views or Jetpack Compose functions.
│   │── utils screen       # State holders (such as ViewModel classes) that hold data, expose it to the UI, and handle logic.
│   └── View Model         # View model It exposes state to the UI and encapsulates related business logic. but it control all UI elements states.
│
├── ui.theme               # This contain the style and colors setted in the app
├── utils                  # This contain view shared by app level lile Top Bar navigation that can be used for other screens
├── FlickrApp              # This define the start point and all modules and entry points that should be installed in the component 
│                          # need to be transitive compilation dependencies of the annotated application.
└── MainActivity           # Main Activity define the first screen or the launcher view.
```


### Preview 🎉


# Project Setup
### Android Studio Version
This project is using android studio Flamingo | 2022.2.1 to development. 
If you find incompatibility please check the official documentation:
[Read here](https://developer.android.com/build/releases/gradle-plugin#android_gradle_plugin_and_android_studio_compatibility) 
[Download Android Studio](https://developer.android.com/studio?gclid=CjwKCAjwov6hBhBsEiwAvrvN6J06MsyTHC2vc6OaC3UmQMKKGS53eT4uH49OKzbIxLOPJk0eWotbUBoC2PwQAvD_BwE&gclsrc=aw.ds) 

### Kotlin Version and gradle version
Gradle version is 8.0.0 make sure you have set up in the **gradle-wrapper.properties** as:
distributions/gradle-8.0-bin.zip

Latest Kotlin EAP release: **1.7.20**
This project is running on the version 1.7.20 make sure you have your project correct setup.
**id 'org.jetbrains.kotlin.android' version '1.7.20' apply false**

If you need more info please check this:
[Read here](https://developer.android.com/jetpack/androidx/releases/compose-kotlin)
[Read here](https://kotlinlang.org/docs/install-eap-plugin.html)
[Read here](https://kotlinlang.org/docs/whatsnew1720.html#support-for-kotlin-k2-compiler-plugins)


### Android JDK Version
Make sure you have the correct version of the JDK setup in your gradle project. 
if you did know how select or download go to: 
Android Studio > settings > Build, Execution, Deployment › Build Tools › Grade
in **Grade projects: Gradle** select 
**Use Gradle from: 'gradle-wrapper.properties' file
Gradle JDK: JetBrains Runtime version 17.0.6/...**
[JDK Version Doc](https://www.oracle.com/java/technologies/javase/17-0-6-relnotes.html)

### Library References
0. Android Clean Architecture [Read here](https://developer.android.com/topic/architecture)
1. Android Components Navigation [Read here](https://developer.android.com/jetpack/docs/guide)
0. Kotlin [Read here](https://developer.android.com/kotlin/ktx)
0. Android Coroutines [Here](https://developer.android.com/kotlin/coroutines).
0. Android flow [Here](https://developer.android.com/kotlin/flow).
0. Retrofit [Read here](https://square.github.io/retrofit/)
0. okhttp3 [Read here](https://square.github.io/okhttp/)
0. Gson [Read here](https://github.com/google/gson#readme)
0. Dependency Injections  [Read here](https://developer.android.com/training/dependency-injection/hilt-android)
0. Jetpack [Read here](https://developer.android.com/jetpack/getting-started)
0. Hilt & Jetpack  [Read here](https://developer.android.com/jetpack/androidx/releases/hilt)
0. Compose [Read here](https://developer.android.com/jetpack/androidx/releases/compose-ui)
0. Compose State [Read here](https://developer.android.com/jetpack/compose/state)
0. MVVM [Read here](https://blog.mindorks.com/mvc-mvp-mvvm-architecture-in-android)
0. View Models [Read here](https://developer.android.com/topic/libraries/architecture/viewmodel)
0. DataModel [Read here](https://developer.android.com/topic/libraries/architecture/viewmodel)
0. Coil Compose  [Read here](https://developer.android.com/jetpack/compose/graphics/images/loading)
0. Test  [Read here](https://developer.android.com/studio/test/test-in-android-studio#:~:text=Click%20Run%20%3E%20Edit%20Configurations%20from,test%20type%2C%20and%20test%20class.)
0. Coroutines testing  [Read here](https://developer.android.com/kotlin/coroutines/test)
0. Mocking Android dependencies  [Read here](https://developer.android.com/training/testing/local-tests#mocking-dependencies)
