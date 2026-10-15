# O2 Scratch Card 🎫📱

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%2525%2525Compose-Material%2525%25253-green.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/Dependency%2525%2525Injection-Hilt-orange.svg)]()
[![Coroutines & Flow](https://img.shields.io/badge/Async-Coroutines%2525%2525 &%2525%2525 Flow-purple.svg)]()
[![Tested](https://img.shields.io/badge/Testing-JUnit%2525%2525 |%2525%2525 Turbine%2525%2525 |%2525%2525 MockK-success.svg)]()

A modern, production-grade Android application built with **Jetpack Compose**, **Kotlin Coroutines**, and **Clean Architecture principles**. The application simulates scratching a digital scratch card to reveal a unique code, and subsequently activating that code against a simulated O2 backend API with robust error handling, network monitoring, and local persistence.

---

## 🚀 Key Features

* **Interactive Scratching Simulation:** Smooth progress tracking and visual feedback when revealing secret codes.
* **Backend API Integration & Retry Logic:** Robust Retrofit client featuring custom retry policies and detailed OkHttp logging.
* **Offline-First Persistence:** Reactive local storage using Jetpack DataStore Preferences.
* **Modern Material 3 UI & Edge-to-Edge:** Immersive adaptive layouts supporting system bar insets and automatic Light/Dark theme adaptation.
* **Accessibility (a11y):** Full TalkBack support with meaningful content descriptions across all interactive elements.
* **Comprehensive Test Suite:** 100% unit-tested ViewModels and Repositories utilizing Turbine for asynchronous Flow/Effect verification and MockK for isolation.

---

## 🛠️ Tech Stack & Architecture

* **UI Framework:** Jetpack Compose, Material 3, Navigation Compose.
* **Architecture:** MVVM (Model-View-ViewModel) with unidirectional data flow (UDF).
* **Dependency Injection:** Hilt (Dagger).
* **Asynchronous & Reactive:** Kotlin Coroutines, `StateFlow`, `SharedFlow`, `Flow`.
* **Local Persistence:** Jetpack DataStore Preferences.
* **Networking:** Retrofit, Kotlinx Serialization, OkHttp Logging Interceptor.
* **Testing:** JUnit 4, Kotlinx Coroutines Test, Turbine, MockK, In-Memory DataStore.

---

## 📂 Project Structure

```tree
sk.o2.scratchcard/
│
├── data/
│   ├── enums/        # Alert, Chip, and Dialog types
│   ├── local/        # Constants, ScratchStatus, Screen models
│   ├── remote/       # O2Api interface & DTOs
│   └── repository/   # Repository implementations (Api, Preferences, NetworkMonitor)
│
├── di/               # Hilt modules (Api, Coroutine, Network, Preferences)
│
├── domain/
│   ├── model/        # Domain data classes (UserSettings, ActivationResult)
│   └── repository/   # Repository interfaces
│
├── ext/              # Extension functions (Modifiers, Custom shadows)
│
└── ui/
    ├── screens/      # Feature screens & ViewModels (Home, Detail, Activate)
    ├── theme/        # Material 3 Theme, Colors, and Typography
    └── views/        # Reusable design system components (Header, Cards, Buttons, Alerts)
```

---

## 🧪 Testing

The project features an extensive unit test suite covering business logic, state transitions, and error handling:

* **ViewModel Tests:** `ActivateViewModelTest`, `DetailViewModelTest`, `HomeViewModelTest` (tested with `runTest`, `StandardTestDispatcher`, and **Turbine** for Flow/Effect assertions).
* **Repository Tests:** `ApiRepositoryImplTest` (mocked API responses, network availability checks, retry behavior) and `PreferencesRepositoryImplTest` (in-memory DataStore testing using `PreferenceDataStoreFactory` and `TemporaryFolder`).

To run all unit tests locally:
```bash
./gradlew testDebugUnitTest
```

---

## ⚙️ Getting Started

1. **Prerequisites:** Android Studio Ladybug / Koala or newer, JDK 17.
2. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/O2ScratchCard.git
   ```
3. **Open the project** in Android Studio.
4. **Sync Gradle** and run the app on an emulator or physical device.

---

## 📝 License

This project is developed as a technical assessment / showcase of senior Android engineering best practices.
