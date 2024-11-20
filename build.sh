#!/bin/bash

set -e

NAME="TimerDebate"

VERSION="33.0.3"
PLATFORM_VERSION="13"
	
AAPT="/opt/android-sdk/build-tools/$VERSION/aapt"
DX="/opt/android-sdk/build-tools/$VERSION/d8"
ZIPALIGN="/opt/android-sdk/build-tools/$VERSION/zipalign"
APKSIGNER="/opt/android-sdk/build-tools/$VERSION/apksigner"
PLATFORM="/opt/android-sdk/platforms/android-$PLATFORM_VERSION/android.jar"


echo "Cleaning..."
rm -rf obj/*
rm -rf src/com/example/helloandroid/R.java

echo "Generating R.java file..."
$AAPT package -f -m -J src -M AndroidManifest.xml -S res -I $PLATFORM

echo "Compiling..."
javac -d obj -source 1.8 -target 1.8 -classpath src -bootclasspath $PLATFORM src/com/example/helloandroid/*.java

echo "Translating in Dalvik bytecode..."
$DX obj/com/example/helloandroid/*class --lib $PLATFORM

echo "Making APK..."
$AAPT package -f -m -F bin/"$NAME".unaligned.apk -M AndroidManifest.xml -S res -I $PLATFORM
$AAPT add bin/"$NAME".unaligned.apk classes.dex

echo "Aligning APK..."
$ZIPALIGN -f 4 bin/"$NAME".unaligned.apk bin/"$NAME".aligned.apk


echo "Signing APK..."
$APKSIGNER sign --ks mykey.keystore --ks-key-alias "mykey"\
    --v1-signing-enabled true \
    --v2-signing-enabled true \
    --v3-signing-enabled true \
    --v4-signing-enabled true \
    bin/"$NAME".aligned.apk


mv bin/"$NAME".aligned.apk bin/"$NAME".apk

echo "Verify..."
$APKSIGNER verify --verbose --print-certs bin/"$NAME".apk

if [ "$1" == "test" ]; then
	echo "Launching..."
	adb install -r bin/$NAME.apk
	adb shell am start -n com.example.helloandroid/.MainActivity
fi
