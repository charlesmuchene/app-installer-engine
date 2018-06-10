package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.ui.HomeScreen
import javax.swing.UIManager

/**
 * Application class
 */
class Application {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val runner = Runner()
//            with(runner) {
//                runSystemAction(SystemAction.InitializeInstaller)
//                runSystemAction(SystemAction.InitializeAutomator)
//                runSystemAction(SystemAction.InstallApplication)
//            }
            showUI(runner)
        }

        /**
         * Show UI
         *
         * @param runner [Runner] instance
         */
        private fun showUI(runner: Runner) {
            HomeScreen(runner)
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}