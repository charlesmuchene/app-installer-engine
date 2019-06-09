package com.charlesmuchene.installer.models

import com.charlesmuchene.installer.utils.lineSeparator
import com.charlesmuchene.installer.utils.reduce

/**
 * System actions
 */
enum class SystemAction {

    InitializeBridge, InitializeInstaller, InitializeAutomator, InstallApplication;

    /**
     * Get system action commands
     *
     * @return Action commands
     */
    fun getCommands(): Pair<Array<String>, Array<String>> = when (this) {
        InitializeBridge -> Pair(Commands.bridgeVersion, Commands.verifyBridge)
        InitializeInstaller -> Pair(Commands.pushChecker, Commands.installChecker)
        InitializeAutomator -> Pair(Commands.pushAutomator, Commands.installAutomator)
        InstallApplication -> Pair(Commands.pushApp, Commands.installApp)
    }

    override fun toString(): String =
            getCommands().first.reduce().plus(lineSeparator).plus(getCommands().second.reduce())
}