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
        runInitializationSequence(runner)?.let(homeScreen::addOutput)
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
     *
     * @param runner [Runner] instance
     * @return Output
     */
    private fun runInitializationSequence(runner: Runner): String? {
        var output: String? = null
        runner.run {
            output = runSystemAction(SystemAction.InitializeInstaller)
            output += runSystemAction(SystemAction.InitializeAutomator)
            output += runSystemAction(SystemAction.InstallApplication)
        }
        return output
    }

}