package com.bnp.tiktaktoe

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class TikTakViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @RelaxedMockK
    private lateinit var tikTakController: TikTakController

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when restartGame is called restart method on tikTakController should be called `() {
        val viewModel = TikTakViewModel(tikTakController)
        viewModel.restartGame()
        verify {
            tikTakController.restart()
        }
    }

    @Test
    fun `when move is called move method on tikTakController should be called `() {
        val viewModel = TikTakViewModel(tikTakController)
        viewModel.move(1)
        verify {
            tikTakController.move(1)
        }
    }
}