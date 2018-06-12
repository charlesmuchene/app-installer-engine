package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.models.UserAction
import com.charlesmuchene.installer.utils.lineSeparator
import com.charlesmuchene.installer.utils.reduce
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
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
        val command = action.getCommand(*values)
        launch { channel.send(command.reduce()) }
        runProcess(command)
    }

    /**
     * Run system action
     *
     * @param action [SystemAction] instance
     */
    fun runSystemAction(action: SystemAction) {
        val (actionOne, actionTwo) = action.getCommands()
        launch { channel.send("Installer Running: ${actionOne.reduce()}") }
        runProcess(actionOne)
        launch { channel.send("Installer Running: ${actionTwo.reduce()}") }
        runProcess(actionTwo)
    }

    /**
     * Run process
     *
     * @param command Process command
     */
    private fun runProcess(command: Array<String>) {
        launch {
            val builder = ProcessBuilder(*command).apply { redirectErrorStream() }
            val output: String?
            try {
                val process = builder.start()
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                val reduce = reader.lines().reduce { a: String?, b: String? -> "$a$lineSeparator$b" }
                output = if (reduce.isPresent) reduce.get() else "Installer Engine: Error"
                channel.send(output)
                process.waitFor()
                process.destroy()
            } catch (e: Exception) {
                val message = "Installer Error: ${e.localizedMessage}"
                channel.send(message)
            } finally {
                channel.send("Installer Finished: ${command.reduce()}")
            }
        }
    }

}