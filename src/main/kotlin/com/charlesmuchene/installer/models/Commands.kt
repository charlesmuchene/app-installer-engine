package com.charlesmuchene.installer.models

/**
 * Installer/Automator commands
 */
object Commands {

    private const val androidJunitRunner = "com.charlesmuchene.installer.test/android.support.test.runner.AndroidJUnitRunner"
    private val automatorRunner = arrayOf("adb", "shell", "am", "instrument", "-w", "-r", "-e", "debug", "false", "-e", "class")

    val connectToWifi = (automatorRunner + "'com.charlesmuchene.installer.InstallerAutomator#connectToWifi'") +
            androidJunitRunner

    val addAccount = (automatorRunner + "'com.charlesmuchene.installer.InstallerAutomator#addAccount'") +
            androidJunitRunner

    val installApp = (automatorRunner + "'com.charlesmuchene.installer.InstallerAutomator#installApp'") +
            androidJunitRunner

    // TODO Determine the path of Installer app

    val pushInstaller = arrayOf("adb", "push", "path_to_debug.apk", "/data/local/tmp/com.charlesmuchene.installer")
    val installInstaller = arrayOf("adb", "shell", "pm", "install", "-t", "-r", "\"/data/local/tmp/com.charlesmuchene.installer\"")

    // TODO Determine the path of the Automator app

    val pushAutomator = arrayOf("adb", "push", "path_to_test.apk", "/data/local/tmp/com.charlesmuchene.installer.test")
    val installAutomator = arrayOf("adb", "shell", "pm", "install", "-t", "-r", "\"/data/local/tmp/com.charlesmuchene.installer.test\"")

}