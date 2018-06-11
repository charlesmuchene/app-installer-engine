package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.ui.HomeScreen
import javax.swing.UIManager

/**
 * Application class
 */
object Application {

    private val homeScreen: HomeScreen by lazy { HomeScreen(runner) }
    private val runner: Runner by lazy { Runner() }

    @JvmStatic
    fun main(args: Array<String>) {
        showUI()
        runInitializationSequence()
        homeScreen.enableUI(true)
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
        with(runner) {
            runSystemAction(SystemAction.InitializeInstaller)?.let(homeScreen::addOutput)
            runSystemAction(SystemAction.InitializeAutomator)?.let(homeScreen::addOutput)
//            runSystemAction(SystemAction.InstallApplication)?.let(homeScreen::addOutput)
        }
    }

}