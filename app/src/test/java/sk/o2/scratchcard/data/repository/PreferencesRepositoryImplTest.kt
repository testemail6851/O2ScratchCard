package sk.o2.scratchcard.data.repository

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class PreferencesRepositoryImplTest {

    private val code = "12345"

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDataStore = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = { temporaryFolder.newFile("test_prefs.preferences_pb") }
    )

    private val repository = PreferencesRepositoryImpl(testDataStore)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserSettings emits default values when datastore is empty`() = runTest {
        repository.getUserSettings().test {
            val settings = awaitItem()
            assert(settings.code == null)
            assert(!settings.isCodeActivated)
        }
    }

    @Test
    fun `saveCode saves code and sets isCodeActivated to false`() = runTest {
        repository.saveCode(code)

        val settings = repository.getUserSettings().first()
        assert(settings.code == code)
        assert(!settings.isCodeActivated)
    }

    @Test
    fun `updateIsCodeActivated updates activation status`() = runTest {
        repository.updateIsCodeActivated(true)

        val settings = repository.getUserSettings().first()
        assert(settings.isCodeActivated)
    }

    @Test
    fun `resetSettings clears all preferences`() = runTest {
        repository.saveCode(code)
        repository.updateIsCodeActivated(true)

        repository.resetSettings()

        val settings = repository.getUserSettings().first()
        assert(settings.code == null)
        assert(!settings.isCodeActivated)
    }

}