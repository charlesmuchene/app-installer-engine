package com.charlesmuchene.installer.ui

import com.charlesmuchene.installer.Runner
import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.models.UserAction
import com.charlesmuchene.installer.utils.lineSeparator
import java.awt.*
import javax.swing.*


/**
 * Home screen
 */
class HomeScreen(private val runner: Runner, private val screenSize: Dimension = Dimension(800, 600))
    : JFrame("SB Installer Engine") {

    private var output = StringBuilder()

    private val statusLabel: JLabel by lazy {
        JLabel().apply { font = Font("Courier", Font.BOLD, 14) }
    }
    private val closeBridgeButton = JButton("Finish")
    private val emailTextField = JTextField("Email")
    private val launchButton = JButton("Launch SB")
    private val wifiButton = JButton("Connect Wi-Fi")
    private val allButton = JButton("All in Sequence")
    private val accountButton = JButton("Add Account")
    private val passwordTextField = JTextField("Password")
    private val installButton = JButton("Install Artifacts")

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
        wifiButton.isEnabled = enable
        closeBridgeButton.isEnabled = enable
        accountButton.isEnabled = enable
        installButton.isEnabled = enable
        showBusy(!enable)
    }

    /**
     * Show screen
     */
    fun showScreen() {
        size = screenSize
        minimumSize = screenSize
        setLocationRelativeTo(null)
        showBusy(true)
        isVisible = true
    }

    /**
     * Set up listeners
     */
    private fun setUpListeners() {
        closeBridgeButton.addActionListener { performUserAction(UserAction.ResetDeviceBridge) }
        wifiButton.addActionListener { performUserAction(UserAction.ConnectWifi) }

        accountButton.addActionListener {
            validateInput().let { performUserAction(UserAction.AddGoogleAccount, *it) }
        }

        installButton.addActionListener {
            performSystemAction(SystemAction.InitializeInstaller)
            performSystemAction(SystemAction.InitializeAutomator)
            performSystemAction(SystemAction.InstallApplication)
        }

        // TODO Add implementation
        allButton.addActionListener {}
        launchButton.addActionListener { }
    }

    /**
     * Validate user input
     *
     * @return [Pair] of email and password
     */
    private fun validateInput(): Array<String> {
        val email = emailTextField.text
        if (email.isBlank()) {
            addOutput("Add a valid email address")
            return emptyArray()
        }
        val password = passwordTextField.text
        if (password.isBlank()) {
            addOutput("Add a valid password")
            return emptyArray()
        }
        return arrayOf(email, password)
    }

    /**
     * Perform user action
     *
     * @param action [UserAction] to perform
     */
    private fun performUserAction(action: UserAction, vararg values: String) {
        showBusy(true)
        addOutput("Installer Running: $action")
        runner.runUserAction(action, *values)?.let(::addOutput)
        showBusy(false)
    }

    /**
     * Perform system action
     *
     * @param action [SystemAction] to perform
     */
    private fun performSystemAction(action: SystemAction) {
        showBusy(true)
        addOutput("Installer Running: $action")
        runner.runSystemAction(action)?.let(::addOutput)
        showBusy(false)
    }

    /**
     * Layout ui
     */
    private fun layoutUI() {
        val layout = GridBagLayout()

        val installButtonConstraints = GridBagConstraints().apply {
            gridx = 3
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

        val launchButtonConstraints = GridBagConstraints().apply {
            gridx = 9
            gridy = 3
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }

        layout.setConstraints(launchButton, launchButtonConstraints)
        launchButton.isEnabled = false
        add(launchButton)

        val allButtonConstraints = GridBagConstraints().apply {
            gridx = 15
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

        val wifiButtonConstraints = GridBagConstraints().apply {
            gridx = 3
            gridy = 7
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(wifiButton, wifiButtonConstraints)
        add(wifiButton)

        val accountButtonConstraints = GridBagConstraints().apply {
            gridx = 9
            gridy = 7
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(accountButton, accountButtonConstraints)
        add(accountButton)

        val closeButtonConstraints = GridBagConstraints().apply {
            gridx = 15
            gridy = 7
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.EAST
        }
        layout.setConstraints(closeBridgeButton, closeButtonConstraints)
        closeBridgeButton.isEnabled = false
        add(closeBridgeButton)

        val statusViewConstraints = GridBagConstraints().apply {
            gridx = 5
            gridy = 15
            weightx = 1.0
            weighty = 0.0
            gridwidth = 3
            gridheight = 2
            insets = Insets(4, 4, 4, 4)
            fill = GridBagConstraints.NONE
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(statusLabel, statusViewConstraints)
        add(statusLabel)

        val emailFieldConstraints = GridBagConstraints().apply {
            gridx = 9
            gridy = 15
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(emailTextField, emailFieldConstraints)
        add(emailTextField)

        val passwordFieldConstraints = GridBagConstraints().apply {
            gridx = 15
            gridy = 15
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.EAST
        }
        layout.setConstraints(passwordTextField, passwordFieldConstraints)

        add(passwordTextField)

        val outputAreaConstraints = GridBagConstraints().apply {
            gridx = 3
            gridy = 21
            weightx = 1.0
            weighty = 1.0
            gridwidth = 32
            gridheight = 11
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.CENTER
        }
        layout.setConstraints(outputAreaScrollPane, outputAreaConstraints)
        add(outputAreaScrollPane)

        contentPane.layout = layout
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

    /**
     * Display status
     */
    private fun showBusy(busy: Boolean) {
        statusLabel.text = if (busy) "Working..." else "Done"
    }
}