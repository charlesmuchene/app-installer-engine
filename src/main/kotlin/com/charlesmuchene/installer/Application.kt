package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.ui.HomeScreen
import javax.swing.SwingUtilities
import javax.swing.UIManager

/**
 * Application class
 */
object Application {

    private val homeScreen: HomeScreen by lazy { HomeScreen(runner) }
    private val runner: Runner by lazy(LazyThreadSafetyMode.NONE) { Runner() }

    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater {
            showUI()
            runInitializationSequence()
            homeScreen.enableUI(true)
        }
    }

    /**
     * Show UI
     */
    private fun showUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        homeScreen.showScreen()
    }

    /**
     * Run initialization sequence
     */
    private fun runInitializationSequence() {
        runner.runSystemAction(SystemAction.InitializeBridge)
    }

}