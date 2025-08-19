#!/bin/bash

# Script para limpiar todos los artefactos de distribución
# Útil antes de hacer commit o para liberar espacio

echo "🧹 Limpiando artefactos de distribución..."

# Eliminar JAR descargado desde release
if [ -f "releases/MetodosNumericos.jar" ]; then
    echo "  - Eliminando JAR de release: releases/MetodosNumericos.jar"
    rm -f releases/MetodosNumericos.jar
    # Eliminar directorio si queda vacío
    [ -d "releases" ] && rmdir releases 2>/dev/null
fi

# Eliminar aplicación nativa de jpackage
if [ -d "MetodosNumericos" ]; then
    echo "  - Eliminando aplicación nativa: MetodosNumericos/"
    rm -rf MetodosNumericos/
fi

# Eliminar ejecutables Windows
if ls *.exe 1> /dev/null 2>&1; then
    echo "  - Eliminando ejecutables Windows: *.exe"
    rm -f *.exe
fi

# Eliminar paquetes RPM/DEB
if ls *.rpm 1> /dev/null 2>&1; then
    echo "  - Eliminando paquetes RPM: *.rpm"
    rm -f *.rpm
fi

if ls *.deb 1> /dev/null 2>&1; then
    echo "  - Eliminando paquetes DEB: *.deb"
    rm -f *.deb
fi

# Eliminar herramientas descargadas
if [ -d "tools/launch4j" ]; then
    echo "  - Eliminando Launch4j descargado: tools/launch4j/"
    rm -rf tools/launch4j/
fi

if ls tools/*.tgz 1> /dev/null 2>&1; then
    echo "  - Eliminando archivos de herramientas: tools/*.tgz"
    rm -f tools/*.tgz
fi

# Limpiar Maven
if [ -d "target" ]; then
    echo "  - Limpiando target/ de Maven..."
    mvn clean > /dev/null 2>&1 || rm -rf target/
fi

echo "✅ Limpieza completada"
echo ""
echo "📝 Para regenerar los artefactos:"
echo "   1. Compilar: mvn clean package"
echo "   2. Ejecutable Windows: ./generar-windows-exe.sh"
echo "   3. Aplicación Linux: jpackage --type app-image ..."
echo "   4. Paquete RPM: jpackage --type rpm ..."
