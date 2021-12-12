JAVA_FILES=($(find . -name \*.java))
GIF_FILES=($(cd src; find . -name \*.gif))
javac -d bin $FILES
for F in $GIF_FILES
do
  cp src/$F bin/$F
done
jpackage -p bin -m greenhouse/greenhouse.Main --name Greenhouse --type app-image
codesign --remove-signature Greenhouse.app
codesign --remove-signature Greenhouse.app/Contents/runtime
zip -r Greenhouse-macos.zip Greenhouse.app
