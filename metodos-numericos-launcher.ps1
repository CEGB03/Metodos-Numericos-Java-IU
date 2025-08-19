# Métodos Numéricos - Launcher Standalone para Windows
# Este script descarga y ejecuta automáticamente la última versión desde GitHub
# 
# Uso: Descargar este archivo y ejecutar: powershell -ExecutionPolicy Bypass -File metodos-numericos-launcher.ps1
# Autor: cegb03
# Repositorio: https://github.com/cegb03/Metodos-Numericos-Java-IU

param(
    [switch]$Force  # Forzar descarga aunque ya exista
)

# Configuración
$RepoOwner = "cegb03"
$RepoName = "Metodos-Numericos-Java-IU"
$AppName = "Métodos Numéricos"
$CacheDir = "$env:LOCALAPPDATA\MetodosNumericos"
$JarName = "MetodosNumericos.jar"
$JarPath = "$CacheDir\$JarName"

# Función para mostrar mensajes de error con ventana
function Show-Error {
    param([string]$Message)
    
    Write-Host "❌ Error: $Message" -ForegroundColor Red
    
    # Mostrar ventana de error
    Add-Type -AssemblyName System.Windows.Forms
    [System.Windows.Forms.MessageBox]::Show(
        $Message, 
        "$AppName - Error", 
        [System.Windows.Forms.MessageBoxButtons]::OK, 
        [System.Windows.Forms.MessageBoxIcon]::Error
    ) | Out-Null
}

# Función para mostrar información
function Show-Info {
    param([string]$Message)
    Write-Host "ℹ️  $Message" -ForegroundColor Cyan
}

# Función para mostrar éxito
function Show-Success {
    param([string]$Message)
    Write-Host "✅ $Message" -ForegroundColor Green
}

# Función para mostrar banner
function Show-Banner {
    Write-Host ""
    Write-Host "╔════════════════════════════════════════╗" -ForegroundColor Blue
    Write-Host "║        📊 MÉTODOS NUMÉRICOS 📊        ║" -ForegroundColor Blue
    Write-Host "║                                        ║" -ForegroundColor Blue  
    Write-Host "║      Launcher Automático v1.0          ║" -ForegroundColor Blue
    Write-Host "╚════════════════════════════════════════╝" -ForegroundColor Blue
    Write-Host ""
}

# Función para verificar dependencias
function Test-Dependencies {
    # Verificar Java
    try {
        $javaVersion = java -version 2>&1 | Select-String "version"
        if (-not $javaVersion) {
            throw "Java no encontrado"
        }
        Show-Success "Java encontrado: $($javaVersion.Line)"
        return $true
    }
    catch {
        Show-Error @"
Java no está instalado o no está en el PATH.

Para instalar Java:
• Descargar desde: https://adoptopenjdk.net/
• O desde Microsoft Store: buscar 'OpenJDK'
• Reiniciar el sistema después de la instalación

Java es necesario para ejecutar la aplicación.
"@
        return $false
    }
}

# Función para obtener información de la última release
function Get-LatestReleaseInfo {
    $apiUrl = "https://api.github.com/repos/$RepoOwner/$RepoName/releases/latest"
    
    Show-Info "Consultando última versión disponible..."
    
    try {
        # Configurar TLS para GitHub API
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        
        $releaseInfo = Invoke-RestMethod -Uri $apiUrl -TimeoutSec 30 -ErrorAction Stop
        
        # Buscar archivo JAR en los assets
        $jarAsset = $releaseInfo.assets | Where-Object { $_.name -like "*.jar" } | Select-Object -First 1
        
        if (-not $jarAsset) {
            throw "No se encontró archivo JAR en la release"
        }
        
        return @{
            DownloadUrl = $jarAsset.browser_download_url
            TagName = $releaseInfo.tag_name
            Name = $releaseInfo.name
            Size = [math]::Round($jarAsset.size / 1MB, 2)
        }
    }
    catch {
        $errorMsg = @"
No se pudo conectar con GitHub.

Error: $($_.Exception.Message)

Posibles causas:
• Sin conexión a internet
• GitHub no disponible temporalmente  
• El repositorio es privado o no existe
• Firewall bloqueando la conexión

Repositorio: https://github.com/$RepoOwner/$RepoName
"@
        Show-Error $errorMsg
        return $null
    }
}

# Función para descargar el JAR
function Get-JarFile {
    param(
        [string]$DownloadUrl,
        [string]$Version,
        [double]$SizeMB
    )
    
    # Crear directorio caché si no existe
    if (-not (Test-Path $CacheDir)) {
        New-Item -ItemType Directory -Path $CacheDir -Force | Out-Null
    }
    
    Show-Info "Descargando $AppName versión $Version..."
    Show-Info "Tamaño: $SizeMB MB"
    Show-Info "Desde: $DownloadUrl"
    Show-Info "Hacia: $JarPath"
    
    try {
        # Descargar archivo con barra de progreso
        $tempPath = "$JarPath.tmp"
        
        # Configurar cliente web
        $webClient = New-Object System.Net.WebClient
        
        # Evento para mostrar progreso
        $webClient.add_DownloadProgressChanged({
            param($sender, $e)
            $percent = $e.ProgressPercentage
            if ($percent -gt 0) {
                Write-Progress -Activity "Descargando $AppName" -Status "$percent% completado" -PercentComplete $percent
            }
        })
        
        # Descargar archivo
        $webClient.DownloadFile($DownloadUrl, $tempPath)
        $webClient.Dispose()
        
        # Limpiar barra de progreso
        Write-Progress -Activity "Descargando $AppName" -Completed
        
        # Verificar que es un archivo válido
        if ((Get-Item $tempPath).Length -lt 1KB) {
            Remove-Item $tempPath -Force
            throw "Archivo descargado demasiado pequeño"
        }
        
        # Mover archivo temporal al destino final
        Move-Item $tempPath $JarPath -Force
        
        $fileSize = [math]::Round((Get-Item $JarPath).Length / 1MB, 2)
        Show-Success "Descarga completada ($fileSize MB)"
        
        return $true
    }
    catch {
        # Limpiar archivos temporales
        if (Test-Path "$JarPath.tmp") {
            Remove-Item "$JarPath.tmp" -Force -ErrorAction SilentlyContinue
        }
        
        Show-Error @"
Error durante la descarga.

Detalles: $($_.Exception.Message)

Posibles causas:
• Conexión interrumpida
• Espacio en disco insuficiente
• Permisos de escritura en: $CacheDir
• Antivirus bloqueando descarga
"@
        return $false
    }
}

# Función para verificar si necesita actualización
function Test-NeedsUpdate {
    if (-not (Test-Path $JarPath)) {
        return $true
    }
    
    # Si el archivo tiene más de 1 día, verificar actualización
    $fileAge = (Get-Date) - (Get-Item $JarPath).LastWriteTime
    if ($fileAge.TotalDays -gt 1) {
        return $true
    }
    
    return $false
}

# Función para ejecutar la aplicación
function Start-Application {
    Show-Info "Iniciando $AppName..."
    
    try {
        # Cambiar al directorio del JAR para evitar problemas de rutas
        Push-Location (Split-Path $JarPath)
        
        # Ejecutar Java con el JAR
        $process = Start-Process -FilePath "java" -ArgumentList @("-jar", "`"$JarPath`"") -Wait -PassThru
        
        Pop-Location
        
        if ($process.ExitCode -eq 0) {
            Show-Success "Aplicación cerrada correctamente"
        } else {
            throw "Código de salida: $($process.ExitCode)"
        }
        
        return $true
    }
    catch {
        Show-Error @"
Error al ejecutar la aplicación.

Detalles: $($_.Exception.Message)

Posibles soluciones:
• Verificar que Java está correctamente instalado
• Eliminar archivo corrupto: $JarPath
• Ejecutar como administrador
• Verificar antivirus no esté bloqueando Java
"@
        return $false
    }
}

# Función principal
function Main {
    Show-Banner
    
    # Verificar dependencias
    if (-not (Test-Dependencies)) {
        Read-Host "Presiona Enter para salir"
        exit 1
    }
    
    # Verificar si necesita descarga/actualización
    $shouldDownload = $Force.IsPresent -or (Test-NeedsUpdate)
    
    if (-not $shouldDownload) {
        Show-Info "Usando versión en caché: $JarPath"
    } else {
        if (Test-Path $JarPath) {
            Show-Info "Verificando actualizaciones..."
        } else {
            Show-Info "Primera ejecución - descargando aplicación..."
        }
        
        # Obtener información de la release
        $releaseInfo = Get-LatestReleaseInfo
        
        if ($null -eq $releaseInfo) {
            # Si hay error pero existe caché, usar caché
            if (Test-Path $JarPath) {
                Show-Info "Usando versión en caché debido a error de conexión"
            } else {
                Read-Host "Presiona Enter para salir"
                exit 1
            }
        } else {
            Show-Info "Última versión disponible: $($releaseInfo.TagName)"
            if ($releaseInfo.Name) {
                Show-Info "Release: $($releaseInfo.Name)"
            }
            
            # Descargar
            if (-not (Get-JarFile $releaseInfo.DownloadUrl $releaseInfo.TagName $releaseInfo.Size)) {
                # Si hay error pero existe caché, usar caché
                if (Test-Path $JarPath) {
                    Show-Info "Usando versión en caché debido a error de descarga"
                } else {
                    Read-Host "Presiona Enter para salir"
                    exit 1
                }
            }
        }
    }
    
    # Ejecutar aplicación
    Write-Host ""
    if (-not (Start-Application)) {
        Read-Host "Presiona Enter para salir"
        exit 1
    }
}

# Ejecutar función principal
Main
