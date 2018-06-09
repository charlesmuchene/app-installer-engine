package com.charlesmuchene.installer

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Action runner class
 */
class Runner {

    /**
     * Run user action
     *
     * @param action [UserActions] instance
     */
    fun runUserAction(action: UserActions) {
        runProcess(action.getCommand())

        // TODO Display output
    }

    /**
     * Run system action
     *
     * @param action [SystemActions] instance
     */
    fun runSystemAction(action: SystemActions) {
        val (actionOne, actionTwo) = action.getCommands()
        runProcess(actionOne)
        runProcess(actionTwo)

        // TODO Display output
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
            output = reduce.get()
            process.waitFor()
            process.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return output
    }
}