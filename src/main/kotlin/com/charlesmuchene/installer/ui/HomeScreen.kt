package com.charlesmuchene.installer.ui

import com.charlesmuchene.installer.Runner
import com.charlesmuchene.installer.models.UserAction
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

/**
 * Home screen
 */
class HomeScreen(private val runner: Runner) : JFrame("SB Installer") {

    private val screenSize = Dimension(600, 400)
    private var output = StringBuilder()

    private val outputArea = JTextArea()
    private val wifiButton = JButton("Connect Wi-Fi")
    private val allButton = JButton("All in Sequence")
    private val accountButton = JButton("Add Account")
    private val installButton = JButton("Install SB app")

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        layoutUI()
        setUpListeners()
        showFrame()
    }

    /**
     * Show frame
     */
    private fun showFrame() {
        size = screenSize
        minimumSize = screenSize
        setLocationRelativeTo(null)
        isVisible = true
    }

    /**
     * Set up listeners
     */
    private fun setUpListeners() {
        wifiButton.addActionListener { performAction(UserAction.ConnectWifi) }
        accountButton.addActionListener { performAction(UserAction.AddAccount) }
        installButton.addActionListener { performAction(UserAction.InstallApp) }
    }

    /**
     * Perform user action
     *
     * @param action [UserAction] to perform
     */
    private fun performAction(action: UserAction) {
        runner.runUserAction(action)?.let {
            output.append(it).append("\n")
            outputArea.text = output.toString()
        }
    }

    /**
     * Layout ui
     */
    private fun layoutUI() {
        val layout = GridBagLayout()
        val constraints = GridBagConstraints()
        contentPane.layout = layout
        val wifiButtonConstraints = constraints.apply {
            gridx = 3
            gridy = 3
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(wifiButton, wifiButtonConstraints)
        add(wifiButton)

        val accountButtonConstraints = constraints.apply {
            gridx = 9
            gridy = 3
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(accountButton, accountButtonConstraints)
        add(accountButton)

        val installButtonConstraints = constraints.apply {
            gridx = 15
            gridy = 3
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(installButton, installButtonConstraints)
        add(installButton)

        val allButtonConstraints = constraints.apply {
            gridx = 21
            gridy = 3
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.EAST
        }
        layout.setConstraints(allButton, allButtonConstraints)
        allButton.isEnabled = false
        add(allButton)

        val outputAreaConstraints = constraints.apply {
            gridx = 3
            gridy = 15
            weightx = 1.0
            weighty = 1.0
            gridwidth = 24
            gridheight = 11
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(outputArea, outputAreaConstraints)
        outputArea.margin = Insets(4, 4, 4, 4)
        add(outputArea)
    }

}