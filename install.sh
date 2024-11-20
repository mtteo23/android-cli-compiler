echo "Starting installation"

VERSION="33.0.3"
PLATFORM_VERSION="13"

echo "Installing Java"
sudo apt-get install openjdk-8-jdk-headless

echo "Installing sdktools"
wget https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip
mkdir -p /opt/android-sdk
unzip sdk-tools-linux-3859397.zip -d /opt/android-sdk
rm sdk-tools-linux-3859397.zip

echo "Installing tools for build and platform"
sudo sdkmanager --install "build-tools;$VERSION" "platform;$PLATFORM_VERSION"

echo "Installing android debugger"
sudo apt-get install adb

echo "Generating keys"
keytool -genkeypair -validity 365 -keystore mykey.keystore -keyalg RSA -keysize 2048


