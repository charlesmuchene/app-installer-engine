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

    val launchApp = automatorRunner
            .plus(createAutomatorCommand("launchApp"))
            .plus(androidJunitRunner)

    val optimizeBattery = automatorRunner
            .plus(createAutomatorCommand("optimizeBattery"))
            .plus(androidJunitRunner)

    val resetDeviceBridge = automatorRunner
            .plus(createAutomatorCommand("resetDeviceBridge"))
            .plus(androidJunitRunner)

    val connectToWifi = automatorRunner
            .plus(createAutomatorCommand("connectToWifi"))
            .plus(Argument.NETWORK_PASSWORD.getArgument(properties))
            .plus(Argument.NETWORK_SSID.getArgument(properties))
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

    val bridgeVersion = arrayOf("adb", "version")
    val verifyBridge = arrayOf("adb", "devices", "-l")


    /**
     * Get Google account command
     *
     * @param email Email address
     * @param password Password
     */
    fun getGoogleAccountCommand(email: String, password: String) = automatorRunner
            .plus(createAutomatorCommand("addGoogleAccount"))
            .plus(Argument.ACCOUNT_PASSWORD.getArgument(password))
            .plus(Argument.ACCOUNT_EMAIL.getArgument(email))
            .plus(androidJunitRunner)

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