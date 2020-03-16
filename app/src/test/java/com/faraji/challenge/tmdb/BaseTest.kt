package com.faraji.challenge.tmdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.MockitoAnnotations

abstract class BaseTest : KoinTest {
    @ExperimentalCoroutinesApi
    private val mainThreadSurrogate by lazy { TestCoroutineDispatcher() }


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        startKoin {
            loadKoinModules(listOf(appTestModule, networkTestModule))
        }

    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainThreadSurrogate.cleanupTestCoroutines()
        stopKoin()
    }
}