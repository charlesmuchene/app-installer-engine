package com.charlesmuchene.installer.ui

import com.charlesmuchene.installer.Runner
import com.charlesmuchene.installer.models.SystemAction
import com.charlesmuchene.installer.models.UserAction
import com.charlesmuchene.installer.utils.lineSeparator
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.awt.*
import javax.swing.*
import kotlinx.coroutines.experimental.javafx.JavaFx as UI


/**
 * Home screen
 */
class HomeScreen(private val runner: Runner, private val screenSize: Dimension = Dimension(800, 600))
    : JFrame("SB Installer Engine") {

    private var output = StringBuilder()

    private val statusLabel: JLabel by lazy {
        JLabel().apply { font = Font("Courier", Font.BOLD, 14) }
    }
    private val nextButton = JButton("Next")
    private val clearButton = JButton("Clear")
    private val previousButton = JButton("Previous")
    private val launchButton = JButton("Launch App")
    private val emailTextField = JTextField("Email")
    private val closeBridgeButton = JButton("Finish")
    private val wifiButton = JButton("Connect Wi-Fi")
    private val accountButton = JButton("Add Account")
    private val passwordTextField = JTextField("Password")
    private val optimizeButton = JButton("Optimize Battery")
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
        nextButton.isEnabled = enable
        wifiButton.isEnabled = enable
        clearButton.isEnabled = enable
        launchButton.isEnabled = enable
        accountButton.isEnabled = enable
        installButton.isEnabled = enable
        optimizeButton.isEnabled = enable
        previousButton.isEnabled = enable
        closeBridgeButton.isEnabled = enable

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
        launch {
            runner.channel.consumeEach { result ->
                withContext(UI) {
                    result?.let(::addOutput)
                }
            }
        }

        closeBridgeButton.addActionListener { performUserAction(UserAction.ResetDeviceBridge) }
        optimizeButton.addActionListener { performUserAction(UserAction.OptimizeBattery) }
        wifiButton.addActionListener { performUserAction(UserAction.ConnectWifi) }
        launchButton.addActionListener { performUserAction(UserAction.LaunchApp) }
        clearButton.addActionListener {
            resetOutput()
            performSystemAction(SystemAction.InitializeBridge)
        }

        accountButton.addActionListener {
            validateInput().let { performUserAction(UserAction.AddGoogleAccount, *it) }
        }

        installButton.addActionListener {
            performSystemAction(SystemAction.InitializeInstaller)
            performSystemAction(SystemAction.InitializeAutomator)
            performSystemAction(SystemAction.InstallApplication)
        }

        nextButton.addActionListener { }
        previousButton.addActionListener {}

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
        runner.runUserAction(action, *values)
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
        runner.runSystemAction(action)
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
        layout.setConstraints(optimizeButton, allButtonConstraints)
        add(optimizeButton)

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
        add(closeBridgeButton)

        val clearButtonConstraints = GridBagConstraints().apply {
            gridx = 3
            gridy = 15
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(clearButton, clearButtonConstraints)
        add(clearButton)

        val emailFieldConstraints = GridBagConstraints().apply {
            gridx = 9
            gridy = 21
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
            gridy = 21
            weightx = 1.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.EAST
        }
        layout.setConstraints(passwordTextField, passwordFieldConstraints)
        add(passwordTextField)

        val statusViewConstraints = GridBagConstraints().apply {
            gridx = 5
            gridy = 21
            weightx = 1.0
            weighty = 0.0
            gridwidth = 3
            gridheight = 2
            insets = Insets(4, 4, 4, 4)
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(statusLabel, statusViewConstraints)
        add(statusLabel)

        val previousButtonConstraints = GridBagConstraints().apply {
            gridx = 9
            gridy = 15
            weightx = 0.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(previousButton, previousButtonConstraints)
        add(previousButton)

        val nextButtonConstraints = GridBagConstraints().apply {
            gridx = 15
            gridy = 15
            weightx = 0.0
            weighty = 0.0
            gridwidth = 5
            gridheight = 2
            fill = GridBagConstraints.BOTH
            anchor = GridBagConstraints.WEST
        }
        layout.setConstraints(nextButton, nextButtonConstraints)
        add(nextButton)

        val outputAreaConstraints = GridBagConstraints().apply {
            gridx = 3
            gridy = 25
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
    private fun addOutput(content: String) {
        output.append(content).append(lineSeparator)
        outputArea.text = output.toString()
    }

    /**
     * Reset output
     */
    private fun resetOutput() {
        output = StringBuilder()
        showBusy(false)
        outputArea.text = ""
        emailTextField.text = "Email"
        passwordTextField.text = "Password"
    }

    /**
     * Display status
     */
    private fun showBusy(busy: Boolean) {
        statusLabel.text = if (busy) "Working..." else "Done"
    }
}