#!/bin/bash

# Script de lanzamiento para Métodos Numéricos
# Prioriza JAR descargado desde GitHub Release, luego el compilado localmente
# Asegúrate de tener Java 8 o superior instalado

# Directorio donde está ubicado este script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Rutas posibles al JAR (en orden de prioridad)
RELEASE_JAR="${SCRIPT_DIR}/releases/MetodosNumericos.jar"
LOCAL_JAR="${SCRIPT_DIR}/target/MetodosNumericos-1.0-SNAPSHOT.jar"

# Función para encontrar el JAR a ejecutar
find_jar() {
    # Prioridad 1: JAR descargado desde release
    if [ -f "$RELEASE_JAR" ]; then
        echo "$RELEASE_JAR"
        return 0
    fi
    
    # Prioridad 2: JAR compilado localmente
    if [ -f "$LOCAL_JAR" ]; then
        echo "$LOCAL_JAR"
        return 0
    fi
    
    # No se encontró ningún JAR
    return 1
}

# Encontrar JAR a ejecutar
JAR_PATH=$(find_jar)

if [ $? -ne 0 ] || [ -z "$JAR_PATH" ]; then
    echo "❌ Error: No se encontró ningún archivo JAR ejecutable"
    echo ""
    echo "💡 Opciones disponibles:"
    echo "   1. Descargar desde GitHub Release: ./descargar-release.sh"
    echo "   2. Compilar localmente: mvn clean package"
    echo ""
    echo "📍 Buscado en:"
    echo "   • $RELEASE_JAR (desde release)"
    echo "   • $LOCAL_JAR (compilado local)"
    exit 1
fi

# Verificar que Java está instalado
if ! command -v java &> /dev/null; then
    echo "Error: Java no está instalado o no está en el PATH"
    echo "Por favor, instala Java 8 o superior."
    exit 1
fi

echo "Iniciando Métodos Numéricos..."
echo "Archivo JAR: $JAR_PATH"

# Ejecutar la aplicación
java -jar "$JAR_PATH"
