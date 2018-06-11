package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.models.UserAction
import com.charlesmuchene.installer.utils.lineSeparator
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.Channel
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Action runner class
 */
class Runner {

    val channel = Channel<String?>()

    /**
     * Run user action
     *
     * @param action [UserAction] instance
     */
    fun runUserAction(action: UserAction, vararg values: String) {
        runProcess(action.getCommand(*values))
    }

    /**
     * Run system action
     *
     * @param action [SystemAction] instance
     */
    fun runSystemAction(action: SystemAction) {
        val (actionOne, actionTwo) = action.getCommands()
        runProcess(actionOne)
        runProcess(actionTwo)
    }

    /**
     * Run process
     *
     * @param command Process command
     */
    private fun runProcess(command: Array<String>) {
        launch {
            val builder = ProcessBuilder(*command)
            val output: String?
            try {
                val process = builder.start()
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                val reduce = reader.lines().reduce { a: String?, b: String? -> "$a$lineSeparator$b" }
                output = if (reduce.isPresent) reduce.get() else "SB Installer: Unknown error"
                channel.send(output)
                process.waitFor()
                process.destroy()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}