package fr.echos.testutils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

@ExperimentalCoroutinesApi
abstract class BaseCoroutinesTest(
    protected val scheduler: TestCoroutineScheduler = TestCoroutineScheduler(),
    protected val testDispatcher: TestDispatcher = StandardTestDispatcher(scheduler),
) {
    @BeforeEach
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    open fun tearDown() {
        Dispatchers.resetMain()
    }
}