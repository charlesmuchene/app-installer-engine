package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.models.UserAction
import com.charlesmuchene.installer.utils.lineSeparator
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Action runner class
 */
class Runner {

    /**
     * Run user action
     *
     * @param action [UserAction] instance
     * @return Output
     */
    fun runUserAction(action: UserAction, vararg values: String): String? = runProcess(action.getCommand(*values))

    /**
     * Run system action
     *
     * @param action [SystemAction] instance
     * @return Output
     */
    fun runSystemAction(action: SystemAction): String? {
        val (actionOne, actionTwo) = action.getCommands()
        val resultOne = runProcess(actionOne)
        val resultTwo = runProcess(actionTwo)
        return "$resultOne$lineSeparator$resultTwo"
    }

    /**
     * Run process
     *
     * @param command Process command
     * @return Output of running process command
     */
    private fun runProcess(command: Array<String>): String? {
        val builder = ProcessBuilder(*command)
        var output: String? = null
        try {
            val process = builder.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val reduce = reader.lines().reduce { a: String?, b: String? -> "$a$lineSeparator$b" }
            output = if (reduce.isPresent) reduce.get() else "SB Installer: Unknown error"
            process.waitFor()
            process.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return output
    }
}