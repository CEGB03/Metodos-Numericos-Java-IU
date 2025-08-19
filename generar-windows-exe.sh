#!/bin/bash

# Script para generar ejecutable Windows (.exe) desde Linux
# Requiere que el proyecto ya esté compilado con Maven

echo "=== Generador de ejecutable Windows para Métodos Numéricos ==="

# Función para encontrar el JAR a usar
find_jar() {
    # Prioridad 1: JAR descargado desde release
    if [ -f "releases/MetodosNumericos.jar" ]; then
        echo "releases/MetodosNumericos.jar"
        return 0
    fi
    
    # Prioridad 2: JAR compilado localmente
    if [ -f "target/MetodosNumericos-1.0-SNAPSHOT.jar" ]; then
        echo "target/MetodosNumericos-1.0-SNAPSHOT.jar"
        return 0
    fi
    
    return 1
}

# Verificar que existe un JAR
JAR_FILE=$(find_jar)
if [ $? -ne 0 ] || [ -z "$JAR_FILE" ]; then
    echo "Error: No se encuentra ningún archivo JAR ejecutable"
    echo ""
    echo "Opciones disponibles:"
    echo "   1. Descargar desde GitHub Release: ./descargar-release.sh"
    echo "   2. Compilar localmente: mvn clean package"
    exit 1
fi

# Verificar que existe Launch4j
LAUNCH4J_JAR="tools/launch4j/launch4j.jar"
if [ ! -f "$LAUNCH4J_JAR" ]; then
    echo "Error: No se encuentra Launch4j en: $LAUNCH4J_JAR"
    echo "Descarga Launch4j desde: https://sourceforge.net/projects/launch4j/"
    exit 1
fi

echo "Generando ejecutable Windows..."
echo "JAR fuente: $JAR_FILE"

# Generar configuración dinámica
cd tools
./generar-config-launch4j.sh "$JAR_FILE"

if [ $? -ne 0 ]; then
    echo "Error: No se pudo generar la configuración Launch4j"
    cd ..
    exit 1
fi

CONFIG_FILE="launch4j-config-dynamic.xml"
echo "Configuración: tools/$CONFIG_FILE"

# Ejecutar Launch4j
java -jar launch4j/launch4j.jar "$CONFIG_FILE"

if [ $? -eq 0 ]; then
    cd ..
    if [ -f "MetodosNumericos.exe" ]; then
        echo "✅ Ejecutable Windows creado exitosamente: MetodosNumericos.exe"
        echo "📁 Tamaño: $(du -h MetodosNumericos.exe | cut -f1)"
        echo "🔍 Tipo: $(file MetodosNumericos.exe)"
        echo ""
        echo "📝 Notas importantes:"
        echo "   - Este ejecutable funciona en Windows 7/8/10/11 (32 y 64 bits)"
        echo "   - Requiere Java 8 o superior instalado en el sistema Windows"
        echo "   - Si no hay Java instalado, el usuario será dirigido a descargar desde adoptopenjdk.net"
        echo "   - Para evitar falsos positivos de antivirus, considera firmar el ejecutable"
        echo ""
        echo "🚀 Para distribuir: incluye tanto 'MetodosNumericos.exe' como 'MetodosNumericos.bat'"
    else
        echo "❌ Error: El archivo MetodosNumericos.exe no se creó correctamente"
        exit 1
    fi
else
    cd ..
    echo "❌ Error al ejecutar Launch4j"
    exit 1
fi
