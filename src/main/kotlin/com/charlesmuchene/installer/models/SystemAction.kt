package com.charlesmuchene.installer.models

/**
 * System actions
 */
enum class SystemAction {

    InitializeInstaller, InitializeAutomator, InstallApplication;

    /**
     * Get system action commands
     *
     * @return Action commands
     */
    fun getCommands(): Pair<Array<String>, Array<String>> = when (this) {
        InitializeInstaller -> Pair(Commands.pushChecker, Commands.installChecker)
        InitializeAutomator -> Pair(Commands.pushAutomator, Commands.installAutomator)
        InstallApplication -> Pair(Commands.pushSBDriverApp, Commands.installSBDriverApp)
    }
}