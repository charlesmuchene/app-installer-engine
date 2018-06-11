package com.charlesmuchene.installer.models

import com.charlesmuchene.installer.utils.*
import java.io.File
import java.util.*

/**
 * Installer/Automator commands
 */
object Commands {

    private val properties: Properties = loadConfiguration()

    private const val androidJunitRunner = "com.charlesmuchene.installer.test/com.charlesmuchene.installer.InstallerRunner"
    private val automatorRunner = arrayOf("adb", "shell", "am", "instrument", "-w", "-r", "-e", "class")

    val resetDeviceBridge = automatorRunner
            .plus(createAutomatorCommand("reset"))
            .plus(androidJunitRunner)

    val connectToWifi = automatorRunner
            .plus(createAutomatorCommand("connectToWifi"))
            .plus(Argument.NETWORK_PASSWORD.getArguments(properties))
            .plus(Argument.NETWORK_SSID.getArguments(properties))
            .plus(androidJunitRunner)

    val addGoogleAccount = automatorRunner
            .plus(createAutomatorCommand("addGoogleAccount"))
            .plus(Argument.ACCOUNT_PASSWORD.getArguments(properties))
            .plus(Argument.ACCOUNT_EMAIL.getArguments(properties))
            .plus(androidJunitRunner)

    val pushChecker = arrayOf("adb", "push", File(properties.getProperty(CHECKER_PATH_KEY)).absolutePath,
            "/data/local/tmp/com.charlesmuchene.installer")
    val installChecker = arrayOf("adb", "shell", "pm", "install", "-t", "-r",
            "\"/data/local/tmp/com.charlesmuchene.installer\"")

    val pushAutomator = arrayOf("adb", "push", File(properties.getProperty(AUTOMATOR_PATH_KEY)).absolutePath,
            "/data/local/tmp/com.charlesmuchene.installer.test")
    val installAutomator = arrayOf("adb", "shell", "pm", "install", "-t", "-r",
            "\"/data/local/tmp/com.charlesmuchene.installer.test\"")

    val pushSBDriverApp = arrayOf("adb", "push", File(properties.getProperty(SB_DRIVER_PATH_KEY)).absolutePath,
            "/data/local/tmp/com.safeboda.driver")
    val installSBDriverApp = arrayOf("adb", "shell", "pm", "install", "-t", "-r", "-g",
            "\"/data/local/tmp/com.safeboda.driver\"")

    /**
     * Create command
     *
     * @param subCommand
     * @return Command
     */
    private fun createAutomatorCommand(subCommand: String = ""): String {
        val klass = "'com.charlesmuchene.installer.InstallerAutomator"
        val method = if (subCommand.isBlank()) "'" else "#$subCommand'"
        return "$klass$method"
    }

}