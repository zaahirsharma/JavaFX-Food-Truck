#!/bin/bash

# Set the paths
JAVA_FX_PATH="/Applications/JavaFX/javafx-sdk-24.0.1/lib"
SRC_PATH="src"
OUT_PATH="out"
STYLE_PATH="src/style"
VIEW_PATH="src/view"

# Print a message indicating what the script is doing
echo "Compiling Java files..."

# Compile Java files with JavaFX modules
javac --module-path "$JAVA_FX_PATH" --add-modules javafx.controls,javafx.fxml -d "$OUT_PATH" "$SRC_PATH"/app/*.java

# Print message for copying resources
echo "Copying FXML and CSS files..."

# Create necessary directories in the out directory
mkdir -p "$OUT_PATH"/view
mkdir -p "$OUT_PATH"/resources
mkdir -p "$OUT_PATH"/style

# Copy the FXML files to the output directory
cp "$VIEW_PATH"/*.fxml "$OUT_PATH"/view/

# Copy the image and CSS files to the output directory
cp "$SRC_PATH"/resources/* "$OUT_PATH"/resources/
cp "$STYLE_PATH"/*.css "$OUT_PATH"/style/

# Print message indicating that we are ready to run the application
echo "Running the JavaFX application..."

# Run the application
java --module-path "$JAVA_FX_PATH" --add-modules javafx.controls,javafx.fxml -cp "$OUT_PATH":"$OUT_PATH"/resources:"$OUT_PATH"/style app.App
