#!/bin/bash

# Script para generar configuración Launch4j dinámica
# Recibe como parámetro la ruta al JAR a usar

if [ $# -ne 1 ]; then
    echo "Uso: $0 <ruta-al-jar>"
    exit 1
fi

JAR_PATH="$1"
CONFIG_FILE="launch4j-config-dynamic.xml"

# Generar configuración XML
cat > "$CONFIG_FILE" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<launch4jConfig>
  <dontWrapJar>false</dontWrapJar>
  <headerType>gui</headerType>
  <jar>../$JAR_PATH</jar>
  <outfile>../MetodosNumericos.exe</outfile>
  <errTitle>Métodos Numéricos - Error</errTitle>
  <cmdLine></cmdLine>
  <chdir>.</chdir>
  <priority>normal</priority>
  <downloadUrl>https://adoptopenjdk.net/</downloadUrl>
  <supportUrl>https://github.com/cegb03/Metodos-Numericos-Java-IU</supportUrl>
  <stayAlive>false</stayAlive>
  <restartOnCrash>false</restartOnCrash>
  <manifest></manifest>
  <icon></icon>
  <jre>
    <path></path>
    <bundledJre64Bit>false</bundledJre64Bit>
    <bundledJreAsFallback>false</bundledJreAsFallback>
    <minVersion>1.8.0</minVersion>
    <maxVersion></maxVersion>
    <jdkPreference>preferJre</jdkPreference>
    <runtimeBits>64/32</runtimeBits>
    <maxHeapSize>1024</maxHeapSize>
    <maxHeapPercent>25</maxHeapPercent>
  </jre>
  <versionInfo>
    <fileVersion>1.0.0.0</fileVersion>
    <txtFileVersion>1.0</txtFileVersion>
    <fileDescription>Aplicación Java para Métodos Numéricos</fileDescription>
    <copyright>2024</copyright>
    <productVersion>1.0.0.0</productVersion>
    <txtProductVersion>1.0</txtProductVersion>
    <productName>Métodos Numéricos</productName>
    <companyName>CEGB03</companyName>
    <internalName>MetodosNumericos</internalName>
    <originalFilename>MetodosNumericos.exe</originalFilename>
  </versionInfo>
</launch4jConfig>
EOF

echo "Configuración generada: $CONFIG_FILE"
echo "JAR configurado: $JAR_PATH"
