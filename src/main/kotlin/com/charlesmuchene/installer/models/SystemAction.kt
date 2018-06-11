package com.charlesmuchene.installer.models

import com.charlesmuchene.installer.utils.lineSeparator

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
        InstallApplication -> Pair(Commands.pushSBDriverApp, Commands.installSBDriverApp)
    }

    /**
     * To string
     */
    override fun toString(): String {
        return getCommands().first.reduce { a, b -> "$a $b" }
                .plus(lineSeparator)
                .plus(getCommands().second.reduce { a, b -> "$a $b" })
    }
}