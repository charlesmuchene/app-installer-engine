package com.charlesmuchene.installer.models

/**
 * Installer/Automator commands
 */
object Commands {

    private const val androidJunitRunner = "com.charlesmuchene.installer.test/android.support.test.runner.AndroidJUnitRunner"
    private val automatorRunner = arrayOf("adb", "shell", "am", "instrument", "-w", "-r", "-e", "debug", "false", "-e", "class")

    val resetDeviceBridge = (automatorRunner + createAutomatorCommand("reset")) + androidJunitRunner

    val connectToWifi = (automatorRunner + createAutomatorCommand("connectToWifi")) + androidJunitRunner

    val addGoogleAccount = (automatorRunner + createAutomatorCommand("addGoogleAccount")) + androidJunitRunner

    // TODO Determine the path of Installer app

    val pushInstaller = arrayOf("adb", "push", "path_to_debug.apk", "/data/local/tmp/com.charlesmuchene.installer")
    val installInstaller = arrayOf("adb", "shell", "pm", "install", "-t", "-r",
            "\"/data/local/tmp/com.charlesmuchene.installer\"")

    // TODO Determine the path of the Automator app

    val pushAutomator = arrayOf("adb", "push", "path_to_test.apk", "/data/local/tmp/com.charlesmuchene.installer.test")
    val installAutomator = arrayOf("adb", "shell", "pm", "install", "-t", "-r",
            "\"/data/local/tmp/com.charlesmuchene.installer.test\"")

    // TODO Determine the path of the SB Driver app

    val pushSBDriverApp = arrayOf("adb", "push", "path_to_sb_driver_app", "/data/local/tmp/com.safeboda.driver")
    val installSBDriverApp = arrayOf("adb", "shell", "pm", "install", "-t", "-r", "-g",
            "\"/data/local/tmp/com.safeboda.driver\"")

    /**
     * Create command
     *
     * @param subCommand
     * @return Command
     */
    private fun createAutomatorCommand(subCommand: String): String =
            "'com.charlesmuchene.installer.InstallerAutomator#$subCommand'"
}