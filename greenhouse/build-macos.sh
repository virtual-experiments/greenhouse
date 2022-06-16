#!/bin/bash

set -e

GIF_FILES=($(cd src; find . -name \*.gif))
javac -d bin --module-source-path greenhouse=src -m greenhouse
for F in $GIF_FILES
do
  cp src/$F bin/greenhouse/$F
done
jpackage -p bin -m greenhouse/greenhouse.ui.ExperimentView --name Greenhouse --type app-image --mac-sign
ditto -c -k --keepParent --sequesterRsrc Greenhouse.app Greenhouse-macos-unstapled.zip
xcrun notarytool submit Greenhouse-macos-unstapled.zip --keychain-profile APPLE_ID_PASSWORD --wait
rm Greenhouse-macos-unstapled.zip
xcrun stapler staple Greenhouse.app
ditto -c -k --keepParent --sequesterRsrc Greenhouse.app Greenhouse-macos.zip
