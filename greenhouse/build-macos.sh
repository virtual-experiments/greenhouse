GIF_FILES=($(cd src; find . -name \*.gif))
javac -d bin --module-source-path greenhouse=src -m greenhouse
for F in $GIF_FILES
do
  cp src/$F bin/greenhouse/$F
done
jpackage -p bin -m greenhouse/greenhouse.Main --name Greenhouse --type app-image --mac-sign
zip -r Greenhouse-macos-unstapled.zip Greenhouse.app
xcrun notarytool submit Greenhouse-macos-unstapled.zip --keychain-profile APPLE_ID_PASSWORD --wait
xcrun stapler staple Greenhouse.app
zip -r Greenhouse-macos.zip Greenhouse.app
