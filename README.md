# Automated app Installer

Automates phone configuration and installation of the desired app.

## Instructions
Plug in your new device and run the installer program.

NB: Ensure you have the *artifacts* directory setup with the apks and the *installer.properties* file with the correct
setup information e.g. Wi-Fi SSID and password etc.

## Artifacts
The required artifacts are: 
* platform-dependent adb [download here](https://developer.android.com/studio/releases/platform-tools.html)
* users.csv (email,password)
* install-automator.apk (see companion repo)
* install-checker.apk (see companion repo)
* app.apk

NB: The program expects the artifacts folder to be in the same root directory as the executable.

## Installer Properties
The required fields in the *installer.properties* are:
* adb_path=artifacts/adb
* network_ssid=networkssid
* network_password="networkpassword"
* app_path=artifacts/app.apk
* users_path=artifacts/users.csv
* checker_path=artifacts/install-checker.apk
* automator_path=artifacts/install-automator.apk