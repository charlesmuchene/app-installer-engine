package com.charlesmuchene.installer.ui

import com.charlesmuchene.installer.Runner
import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.models.UserAction
import com.charlesmuchene.installer.utils.lineSeparator
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

/**
 * Home screen
 */
class HomeScreen(private val runner: Runner, private val screenSize: Dimension = Dimension(600, 400))
    : JFrame("SB Installer Engine") {

    private var output = StringBuilder()

    private val closeButton = JButton("Close")
    private val wifiButton = JButton("Connect Wi-Fi")
    private val allButton = JButton("All in Sequence")
    private val accountButton = JButton("Add Account")
    private val installButton = JButton("Install SB App")

    private val outputArea: JTextArea by lazy {
        JTextArea().apply {
            margin = Insets(4, 4, 4, 4)
            isEditable = false
        }
    }

    private val outputAreaScrollPane: JScrollPane by lazy {
        JScrollPane(outputArea).apply {
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        }
    }

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        layoutUI()
        setUpListeners()
        enableUI(false)
    }

    /**
     * Enable/Disable UI
     *
     * @param enable Boolean
     */
    fun enableUI(enable: Boolean) {
        allButton.isEnabled = enable
        wifiButton.isEnabled = enable
        closeButton.isEnabled = enable
        accountButton.isEnabled = enable
        installButton.isEnabled = enable
    }

    /**
     * Show screen
     */
    fun showScreen() {
        size = screenSize
        minimumSize = screenSize
        setLocationRelativeTo(null)
        isVisible = true
    }

    /**
     * Set up listeners
     */
    private fun setUpListeners() {
        wifiButton.addActionListener { performUserAction(UserAction.ConnectWifi) }
        closeButton.addActionListener { performUserAction(UserAction.ResetDeviceBridge) }
        accountButton.addActionListener { performUserAction(UserAction.AddGoogleAccount) }
        installButton.addActionListener { performSystemAction(SystemAction.InstallApplication) }
    }

    /**
     * Perform user action
     *
     * @param action [UserAction] to perform
     */
    private fun performUserAction(action: UserAction) {
        addOutput("Installer Running: $action")
        runner.runUserAction(action)?.let(::addOutput)
    }

    /**
     * Perform system action
     *
     * @param action [SystemAction] to perform
     */
    private fun performSystemAction(action: SystemAction) {
        addOutput("Installer Running: $action")
        runner.runSystemAction(action)?.let(::addOutput)
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

        val closeButtonConstraints = constraints.apply {
            gridx = 26
            gridy = 3
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.EAST
        }
        layout.setConstraints(closeButton, closeButtonConstraints)
        closeButton.isEnabled = false
        add(closeButton)

        val outputAreaConstraints = constraints.apply {
            gridx = 3
            gridy = 15
            weightx = 1.0
            weighty = 1.0
            gridwidth = 30
            gridheight = 11
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(outputAreaScrollPane, outputAreaConstraints)
        add(outputAreaScrollPane)
    }

    /**
     * Display output
     *
     * @param content Content to show
     */
    fun addOutput(content: String) {
        output.append(content).append(lineSeparator)
        outputArea.text = output.toString()
    }
}