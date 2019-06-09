package com.charlesmuchene.installer.models

import com.charlesmuchene.installer.utils.*
import java.io.File

/**
 * Installer/Automator commands
 */
object Commands {

    private const val androidJunitRunner = "com.charlesmuchene.installer.test/com.charlesmuchene.installer.InstallerRunner"
    private val automatorRunner = arrayOf("shell", "am", "instrument", "-w", "-r", "-e", "class")

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

    val pushChecker = arrayOf("push", File(properties.getProperty(CHECKER_PATH_KEY)).absolutePath,
            "/data/local/tmp/com.charlesmuchene.installer")
    val installChecker = arrayOf("shell", "pm", "install", "-t", "-r",
            "\"/data/local/tmp/com.charlesmuchene.installer\"")

    val pushAutomator = arrayOf("push", File(properties.getProperty(AUTOMATOR_PATH_KEY)).absolutePath,
            "/data/local/tmp/com.charlesmuchene.installer.test")
    val installAutomator = arrayOf("shell", "pm", "install", "-t", "-r",
            "\"/data/local/tmp/com.charlesmuchene.installer.test\"")

    val pushApp = arrayOf("push", File(properties.getProperty(APP_PATH_KEY)).absolutePath,
            "/data/local/tmp/<full-package-name>")
    val installApp = arrayOf("shell", "pm", "install", "-t", "-r", "-g",
            "\"/data/local/tmp/<full-package-name>\"")

    val bridgeVersion = arrayOf("version")
    val verifyBridge = arrayOf("devices")


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