package com.charlesmuchene.installer

import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

/**
 * Home screen
 */
class HomeScreen(private val runner: Runner) : JFrame("SB Installer") {

    private val screenSize = Dimension(400, 200)

    private val outputArea = JTextArea()
    private val allButton = JButton("All")
    private val wifiButton = JButton("Connect Wi-Fi")
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
        wifiButton.addActionListener { runner.runUserAction(UserActions.ConnectWifi) }
        accountButton.addActionListener { runner.runUserAction(UserActions.AddAccount) }
        installButton.addActionListener { runner.runUserAction(UserActions.InstallApp) }
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
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            weightx = 1.0
            weighty = 0.0
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(wifiButton, wifiButtonConstraints)
        add(wifiButton)

        val accountButtonConstraints = constraints.apply {
            gridx = 9
            gridy = 3
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            weightx = 1.0
            weighty = 0.0
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(accountButton, accountButtonConstraints)
        add(accountButton)

        val installButtonConstraints = constraints.apply {
            gridx = 15
            gridy = 3
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            weightx = 1.0
            weighty = 0.0
            anchor = GridBagConstraints.EAST
        }
        layout.setConstraints(installButton, installButtonConstraints)
        add(installButton)

        val outputAreaConstraints = constraints.apply {
            gridx = 3
            gridy = 9
            gridwidth = 18
            gridheight = 11
            fill = GridBagConstraints.BOTH
            weightx = 1.0
            weighty = 1.0
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(outputArea, outputAreaConstraints)
        add(outputArea)
    }

}