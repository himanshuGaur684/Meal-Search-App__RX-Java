# Meal Search App - RxJava & Retrofit

This is a **Meal Search App** that demonstrates the usage of **RxJava Schedulers, Retrofit, and MVVM Architecture**. The app fetches meal data from [TheMealDB API](https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata) and implements reactive search functionality using **PublishSubject** with operators like `.debounce()`, `.distinctUntilChanged()`, and `.filter()`.

## Features

- **MVVM Architecture** for clean and maintainable code.
- **Retrofit** for making network requests.
- **RxJava Schedulers** for handling background and UI thread operations.
- **Reactive Search** implementation using `PublishSubject`.
- **Debounce & DistinctUntilChanged** to optimize API calls.

## Technologies Used

- **Kotlin**
- **Jetpack ViewModel**
- **RxJava & RxAndroid**
- **Retrofit**
- **LiveData**
- **Coil** (for image loading)

## API Reference

The app fetches meal details from [TheMealDB API](https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata):

```
GET https://www.themealdb.com/api/json/v1/1/search.php?s={meal_name}
```

## How It Works

1. User types in the search field.
2. **PublishSubject** captures text changes.
3. **Debounce** ensures API calls are made only after the user stops typing for a short duration.
4. **DistinctUntilChanged** prevents duplicate API calls for the same query.
5. **Filter** ensures valid queries are sent to the API.
6. **Schedulers** manage background operations and UI updates efficiently.

## How It Works

1. User types in the search field.
2. **PublishSubject captures text changes in real time**.
3. **Debounce ensures API calls are made only after the user stops typing for a short duration**.
4. **DistinctUntilChanged prevents duplicate API calls for the same query**.
5. **Filter ensures only valid queries are sent to the API**.
6. **RxJava Schedulers handle background operations and UI updates efficiently**.

## Installation

1. Clone this repository:
   ```sh
   git clone https://github.com/himanshuGaur684/Meal-Search-App__RX-Java.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle and Run the app.



## Dependencies

Add these dependencies to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.3")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("io.coil-kt:coil:2.3.0")
}
```

Feel free to contribute or raise issues! 🚀

