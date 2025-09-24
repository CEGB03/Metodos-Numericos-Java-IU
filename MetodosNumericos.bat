@echo off
REM Métodos Numéricos - Launcher Simple para Windows
REM Este script ejecuta el launcher PowerShell o descarga directamente

echo.
echo ================================================================
echo                   METODOS NUMERICOS
echo                 Launcher Automatico v1.0
echo ================================================================
echo.

REM Verificar que Java está instalado
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo.
    echo Para instalar Java:
    echo - Descargar desde: https://adoptopenjdk.net/
    echo - O desde Microsoft Store: buscar 'OpenJDK'
    echo - Reiniciar el sistema después de la instalación
    echo.
    pause
    exit /b 1
)

REM Intentar ejecutar el launcher PowerShell si existe
if exist "%~dp0metodos-numericos-launcher.ps1" (
    echo Ejecutando launcher PowerShell...
    powershell -ExecutionPolicy Bypass -File "%~dp0metodos-numericos-launcher.ps1"
    goto :end
)

REM Fallback: descargar y ejecutar directamente
echo Descargando launcher PowerShell...
echo.

set CACHE_DIR=%~dp0metodos-numericos
set JAR_PATH=%CACHE_DIR%\MetodosNumericos.jar
if not exist "%CACHE_DIR%" mkdir "%CACHE_DIR%"

REM Descargar launcher PowerShell
powershell -Command "& {Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/cegb03/Metodos-Numericos-Java-IU/main/metodos-numericos-launcher.ps1' -OutFile '%TEMP_DIR%\metodos-numericos-launcher.ps1' -ErrorAction Stop}"

if errorlevel 1 (
    echo ERROR: No se pudo descargar el launcher desde GitHub.
    echo.
    echo Posibles causas:
    echo - Sin conexión a internet
    echo - GitHub no disponible
    echo - Firewall bloqueando la descarga
    echo.
    echo Intenta descargar manualmente desde:
    echo https://github.com/cegb03/Metodos-Numericos-Java-IU
    echo.
    pause
    exit /b 1
)

echo Launcher descargado exitosamente.
echo Ejecutando...
echo.

REM Ejecutar launcher descargado
powershell -ExecutionPolicy Bypass -File "%TEMP_DIR%\metodos-numericos-launcher.ps1"

:end
echo.
echo Launcher finalizado.
pause
