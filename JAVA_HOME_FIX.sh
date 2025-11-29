#!/bin/bash
# Find the JDK installation
JDK_PATH=""
for path in "/usr/lib/jvm/java-21-openjdk-amd64" "/usr/lib/jvm/temurin-21-jdk-amd64" "/opt/java/openjdk" "/usr/java/openjdk-21"; do
    if [ -d "$path" ] && [ -f "$path/bin/javac" ]; then
        JDK_PATH="$path"
        break
    fi
done

if [ -z "$JDK_PATH" ]; then
    echo "No JDK found. Looking for any available JDK..."
    JDK_PATH=$(find /usr -name "javac" 2>/dev/null | head -1 | sed 's|/bin/javac||')
fi

if [ -n "$JDK_PATH" ]; then
    echo "Found JDK at: $JDK_PATH"
    export JAVA_HOME="$JDK_PATH"
    export PATH="$JAVA_HOME/bin:$PATH"
    echo "JAVA_HOME set to: $JAVA_HOME"
    mvn clean test-compile
else
    echo "No JDK found in the system"
    exit 1
fi