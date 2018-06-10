package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.ui.HomeScreen
import com.charlesmuchene.installer.utils.loadConfiguration
import java.util.*
import javax.swing.UIManager

/**
 * Application class
 */
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        val runner = Runner()
        val output: String? = runInitializationSequence(runner)
        showUI(runner, output)
    }

    /**
     * Show UI
     *
     * @param runner [Runner] instance
     * @param output System actions output
     */
    private fun showUI(runner: Runner, output: String? = null) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val homeScreen = HomeScreen(runner)
        output?.let(homeScreen::addOutput)
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