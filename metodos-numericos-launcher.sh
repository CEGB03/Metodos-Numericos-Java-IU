#!/bin/bash

# Métodos Numéricos - Launcher Standalone
# Este script descarga y ejecuta automáticamente la última versión desde GitHub
# 
# Uso: Descargar este archivo y ejecutar: bash metodos-numericos-launcher.sh
# Autor: cegb03
# Repositorio: https://github.com/cegb03/Metodos-Numericos-Java-IU
#
# Versión: 2.0
## Requisitos: Java 11 o superior, curl
# Dependencias opcionales: zenity, kdialog, notify-send (para notificaciones gráficas)
## Este script es compatible con la mayoría de distribuciones Linux y puede ejecutarse en terminal

set -e  # Salir en caso de error

# Configuración
REPO_OWNER="CEGB03"
REPO_NAME="Metodos-Numericos-Java-IU"
APP_NAME="Métodos Numéricos"
CACHE_DIR="$(pwd)/metodos-numericos"
JAR_NAME="MetodosNumericos.jar"
JAR_PATH="$CACHE_DIR/$JAR_NAME"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
BOLD='\033[1m'
NC='\033[0m' # No Color

# Función para mostrar mensajes de error con GUI
show_error() {
    local message="$1"
    echo -e "${RED}❌ Error: $message${NC}" >&2
    
    # Intentar mostrar ventana de error gráfica
    if command -v zenity >/dev/null 2>&1; then
        zenity --error --title="$APP_NAME - Error" --text="$message" --width=400 2>/dev/null &
    elif command -v kdialog >/dev/null 2>&1; then
        kdialog --error "$message" --title "$APP_NAME - Error" 2>/dev/null &
    elif command -v notify-send >/dev/null 2>&1; then
        notify-send -u critical "$APP_NAME - Error" "$message" 2>/dev/null &
    fi
}

# Función para mostrar información
show_info() {
    local message="$1"
    echo -e "${BLUE}ℹ️  $message${NC}"
}

# Función para mostrar éxito
show_success() {
    local message="$1"
    echo -e "${GREEN}✅ $message${NC}"
}

# Función para verificar dependencias
check_dependencies() {
    # Verificar Java
    if ! command -v java >/dev/null 2>&1; then
        show_error "Java no está instalado.

Para instalar Java:
• Ubuntu/Debian: sudo apt install openjdk-11-jre
• Fedora/CentOS: sudo dnf install java-11-openjdk
• Arch Linux: sudo pacman -S jre-openjdk
• Manual: https://adoptopenjdk.net/"
        return 1
    fi
    
    # Verificar curl
    if ! command -v curl >/dev/null 2>&1; then
        show_error "curl no está instalado.

Para instalar curl:
• Ubuntu/Debian: sudo apt install curl
• Fedora/CentOS: sudo dnf install curl
• Arch Linux: sudo pacman -S curl"
        return 1
    fi
    
    return 0
}

# Función para obtener información de la última release
get_latest_release_info() {
    local api_url="https://api.github.com/repos/$REPO_OWNER/$REPO_NAME/releases/latest"

    # Mensajes y debug a stderr
    echo "ℹ️  Consultando última versión disponible..." >&2

    local release_info
    release_info=$(curl -s -H "Accept: application/vnd.github.v3+json" --connect-timeout 10 --max-time 30 "$api_url" 2>/dev/null) || release_info=""

    echo "DEBUG: Respuesta de GitHub API (preview):" >&2
    printf '%s\n' "$release_info" | head -n 40 >&2

    if [ -z "$release_info" ]; then
        show_error "No se pudo conectar con GitHub. Revisa tu conexión o que el repositorio exista: https://github.com/$REPO_OWNER/$REPO_NAME"
        return 1
    fi

    # Detectar error en la respuesta JSON (rate limit, not found, etc.)
    if printf '%s\n' "$release_info" | grep -q "\"message\":"; then
        local error_msg
        error_msg=$(printf '%s\n' "$release_info" | sed -n 's/.*"message"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p' | head -n1)
        show_error "Error de GitHub API: ${error_msg:-respuesta inesperada}"
        return 1
    fi

    local download_url tag_name

    if command -v jq >/dev/null 2>&1; then
        download_url=$(printf '%s' "$release_info" | jq -r '.assets[]?.browser_download_url | select(endswith(".jar"))' | head -n1)
        tag_name=$(printf '%s' "$release_info" | jq -r '.tag_name // "desconocida"')
    elif command -v python3 >/dev/null 2>&1; then
        download_url=$(printf '%s' "$release_info" | python3 - <<'PY' 2>/dev/null
import sys, json
try:
    j=json.load(sys.stdin)
    for a in j.get('assets', []):
        url = a.get('browser_download_url', '')
        if url and url.lower().endswith('.jar'):
            print(url)
            break
except:
    pass
PY
)
        tag_name=$(printf '%s' "$release_info" | python3 - <<'PY' 2>/dev/null
import sys,json
try:
    j=json.load(sys.stdin)
    print(j.get('tag_name','desconocida'))
except:
    print('desconocida')
PY
)
    else
        # Fallback con grep/sed (menos fiable)
        download_url=$(printf '%s' "$release_info" | grep -o '"browser_download_url": *"[^"]*\.jar"' | head -n1 | sed 's/.*": *"\(.*\)"/\1/')
        tag_name=$(printf '%s' "$release_info" | grep -o '"tag_name": *"[^"]*"' | head -n1 | sed 's/.*": *"\(.*\)"/\1/')
    fi

    download_url=$(echo -n "$download_url")
    tag_name=${tag_name:-desconocida}

    if [ -z "$download_url" ] || [ "$download_url" = "null" ]; then
        show_error "No se encontró archivo JAR en la última release. Asegúrate de que la release tenga un asset .jar."
        return 1
    fi

    if ! printf '%s\n' "$download_url" | grep -qE '^https?://'; then
        show_error "URL de descarga inválida: $download_url"
        return 1
    fi

    # Salida limpia: URL|TAG por stdout
    printf '%s|%s' "$download_url" "$tag_name"
    return 0
}

# Función para descargar el JAR
download_jar() {
    local download_url="$1"
    local version="$2"
    
    # Crear directorio caché si no existe
    mkdir -p "$CACHE_DIR"
    
    show_info "Descargando $APP_NAME versión $version..."
    show_info "Desde: $download_url"
    show_info "Hacia: $JAR_PATH"
    
    # Descargar con curl mostrando progreso
    if curl -L --progress-bar --connect-timeout 30 --retry 3 --retry-delay 2 -o "$JAR_PATH.tmp" "$download_url"; then
        # Verificar que es un JAR válido
        if file "$JAR_PATH.tmp" 2>/dev/null | grep -q "Java archive\|Zip archive"; then
            mv "$JAR_PATH.tmp" "$JAR_PATH"
            show_success "Descarga completada ($(du -h "$JAR_PATH" | cut -f1))"
            return 0
        else
            rm -f "$JAR_PATH.tmp"
            show_error "El archivo descargado no es un JAR válido."
            return 1
        fi
    else
        rm -f "$JAR_PATH.tmp"
        show_error "Error durante la descarga.\n\nPosibles causas:\n• Conexión interrumpida\n• Espacio en disco insuficiente\n• Permisos de escritura en: $CACHE_DIR"
        return 1
    fi
}

# Función para verificar si necesita actualización
needs_update() {
    [ ! -f "$JAR_PATH" ] && return 0
    
    # Si el archivo tiene más de 1 día, verificar actualización
    if [ "$(find "$JAR_PATH" -mtime +1 2>/dev/null)" ]; then
        return 0
    fi
    
    return 1
}

# Función para ejecutar la aplicación
run_application() {
    show_info "Iniciando $APP_NAME..."
    
    # Ejecutar Java con el JAR
    if java -jar "$JAR_PATH"; then
        show_success "Aplicación cerrada correctamente"
    else
        show_error "Error al ejecutar la aplicación.

El archivo JAR puede estar corrupto.
Elimina: $JAR_PATH
y vuelve a ejecutar este script."
        return 1
    fi
}

# Función principal
main() {
    echo -e "${BOLD}${BLUE}"
    echo "╔════════════════════════════════════════╗"
    echo "║        📊 MÉTODOS NUMÉRICOS 📊        ║"
    echo "║                                        ║"
    echo "║      Launcher Automático v1.0          ║"
    echo "╚════════════════════════════════════════╝"
    echo -e "${NC}"
    echo ""

    # Verificar dependencias
    if ! check_dependencies; then
        exit 1
    fi

    # Verificar si necesita descarga/actualización
    local should_download=false
    if [ ! -f "$JAR_PATH" ]; then
        show_info "Primera ejecución - descargando aplicación..."
        should_download=true
    elif needs_update; then
        show_info "Verificando actualizaciones..."
        should_download=true
    else
        show_info "Usando versión en caché: $JAR_PATH"
    fi

    # Descargar si es necesario
    if [ "$should_download" = true ]; then
        # Obtener información de la release de forma segura
        local info_out
        if ! info_out=$(get_latest_release_info); then
            # Si falla y hay caché, usar caché; si no, salir
            if [ -f "$JAR_PATH" ]; then
                show_info "Usando versión en caché debido a error al consultar GitHub"
            else
                show_error "No se pudo obtener información de la release y no existe caché."
                exit 1
            fi
        else
            IFS='|' read -r download_url tag_name <<< "$info_out"
            tag_name=${tag_name:-desconocida}
            show_info "Última versión disponible: $tag_name"

            # Validar URL antes de intentar descargar
            if [ -z "$download_url" ] || ! echo "$download_url" | grep -qE '^https?://'; then
                show_error "URL de descarga inválida: '$download_url'"
                if [ -f "$JAR_PATH" ]; then
                    show_info "Usando versión en caché debido a URL inválida"
                else
                    exit 1
                fi
            else
                if ! download_jar "$download_url" "$tag_name"; then
                    if [ -f "$JAR_PATH" ]; then
                        show_info "Usando versión en caché debido a error de descarga"
                    else
                        show_error "Fallo la descarga y no existe versión en caché."
                        exit 1
                    fi
                fi
            fi
        fi
    fi

    # Ejecutar aplicación
    echo ""
    run_application
}

# Ejecutar función principal
main "$@"
