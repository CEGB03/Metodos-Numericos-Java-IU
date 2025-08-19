#!/bin/bash

# Script para descargar la última release de Métodos Numéricos desde GitHub
# Autor: cegb03

REPO_OWNER="cegb03"  # Cambia esto por tu usuario de GitHub
REPO_NAME="Metodos-Numericos-Java-IU"
RELEASE_DIR="releases"
JAR_NAME="MetodosNumericos.jar"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Descargador de Métodos Numéricos ===${NC}"
echo ""

# Crear directorio de releases si no existe
mkdir -p "$RELEASE_DIR"

# Función para obtener la URL de descarga de la última release
get_latest_release_url() {
    echo "🔍 Buscando la última release..."
    
    # Usar GitHub API para obtener la última release
    local api_url="https://api.github.com/repos/$REPO_OWNER/$REPO_NAME/releases/latest"
    local download_url
    
    # Intentar obtener la URL de descarga del JAR
    download_url=$(curl -s "$api_url" | grep -o "https://github.com/$REPO_OWNER/$REPO_NAME/releases/download/[^\"]*\.jar" | head -1)
    
    if [ -z "$download_url" ]; then
        echo -e "${RED}❌ Error: No se pudo encontrar un archivo JAR en la última release${NC}"
        echo -e "${YELLOW}💡 Asegúrate de que:${NC}"
        echo "   1. El repositorio existe: https://github.com/$REPO_OWNER/$REPO_NAME"
        echo "   2. Hay una release publicada con un archivo .jar"
        echo "   3. La release es pública"
        return 1
    fi
    
    echo "$download_url"
}

# Función para descargar el archivo
download_jar() {
    local url=$1
    local file_path="$RELEASE_DIR/$JAR_NAME"
    
    echo "📥 Descargando desde: $url"
    echo "💾 Guardando en: $file_path"
    
    # Descargar con curl mostrando progreso
    if curl -L --progress-bar -o "$file_path" "$url"; then
        echo -e "${GREEN}✅ Descarga completada exitosamente${NC}"
        
        # Verificar que el archivo descargado es un JAR válido
        if file "$file_path" | grep -q "Java archive data"; then
            echo -e "${GREEN}✅ Archivo JAR válido${NC}"
            
            # Mostrar información del archivo
            echo ""
            echo "📋 Información del archivo:"
            echo "   📁 Ubicación: $file_path"
            echo "   📏 Tamaño: $(du -h "$file_path" | cut -f1)"
            echo "   🕐 Fecha: $(date)"
            
            return 0
        else
            echo -e "${RED}❌ Error: El archivo descargado no es un JAR válido${NC}"
            rm -f "$file_path"
            return 1
        fi
    else
        echo -e "${RED}❌ Error en la descarga${NC}"
        return 1
    fi
}

# Función principal
main() {
    local jar_path="$RELEASE_DIR/$JAR_NAME"
    
    # Verificar si ya existe una versión descargada
    if [ -f "$jar_path" ]; then
        echo -e "${YELLOW}⚠️  Ya existe un archivo JAR descargado en: $jar_path${NC}"
        echo "¿Deseas descargarlo nuevamente? (s/N): "
        read -r response
        if [[ ! "$response" =~ ^[sS]$ ]]; then
            echo "ℹ️  Usando archivo existente"
            echo "   Para forzar descarga, elimina: $jar_path"
            exit 0
        fi
        echo "🗑️  Eliminando archivo anterior..."
        rm -f "$jar_path"
    fi
    
    # Obtener URL de descarga
    local download_url
    download_url=$(get_latest_release_url)
    
    if [ $? -ne 0 ]; then
        exit 1
    fi
    
    # Descargar archivo
    if download_jar "$download_url"; then
        echo ""
        echo -e "${GREEN}🎉 ¡Descarga completada!${NC}"
        echo ""
        echo -e "${BLUE}📝 Próximos pasos:${NC}"
        echo "   • Ejecutar aplicación: ./run-metodos-numericos.sh"
        echo "   • Generar .exe Windows: ./generar-windows-exe.sh"
        echo "   • Compilar desde código: mvn clean package"
        echo ""
    else
        exit 1
    fi
}

# Ejecutar función principal
main "$@"
