package com.faraji.challenge.tmdb.ui.home

import com.faraji.challenge.tmdb.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.test.inject

class HomeViewModelTest: BaseTest() {
    private val viewModel: HomeViewModel by inject()


    @Test
    fun testGotList() {
        val state = HomeViewState()
        viewModel.homeState.observeForever {}
        assertEquals(viewModel.homeState.value, state.copy(isLoading = true))
    }

}