package com.charlesmuchene.installer

import com.charlesmuchene.installer.models.SystemActions
import com.charlesmuchene.installer.models.UserAction
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
    fun runUserAction(action: UserAction): String? = runProcess(action.getCommand())

    /**
     * Run system action
     *
     * @param action [SystemActions] instance
     * @return Output
     */
    fun runSystemAction(action: SystemActions): String? {
        val (actionOne, actionTwo) = action.getCommands()
        val resultOne = runProcess(actionOne)
        val resultTwo = runProcess(actionTwo)
        return resultOne + resultTwo
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
            val reduce = reader.lines().reduce { t: String?, u: String? -> "$t\n$u" }
            output = if (reduce.isPresent) reduce.get() else "SB Installer: Unknown error"
            process.waitFor()
            process.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return output
    }
}