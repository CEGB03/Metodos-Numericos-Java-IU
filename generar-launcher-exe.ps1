# Script para generar launcher EXE desde PowerShell
# Requiere ps2exe: Install-Module ps2exe -Scope CurrentUser

param(
    [string]$OutputPath = "metodos-numericos-launcher.exe"
)

Write-Host "🔧 Generando launcher EXE para Windows..." -ForegroundColor Blue
Write-Host ""

# Verificar que ps2exe esté instalado
try {
    Get-Command ps2exe -ErrorAction Stop | Out-Null
    Write-Host "✅ ps2exe encontrado" -ForegroundColor Green
}
catch {
    Write-Host "❌ ps2exe no está instalado" -ForegroundColor Red
    Write-Host ""
    Write-Host "Para instalar ps2exe:" -ForegroundColor Yellow
    Write-Host "1. Abrir PowerShell como Administrador"
    Write-Host "2. Ejecutar: Install-Module ps2exe -Scope CurrentUser"
    Write-Host "3. Confirmar con 'Y' si pregunta"
    Write-Host ""
    exit 1
}

# Verificar que existe el script PowerShell
$psScript = "metodos-numericos-launcher.ps1"
if (-not (Test-Path $psScript)) {
    Write-Host "❌ No se encuentra: $psScript" -ForegroundColor Red
    exit 1
}

Write-Host "📝 Archivo fuente: $psScript" -ForegroundColor Cyan
Write-Host "🎯 Archivo destino: $OutputPath" -ForegroundColor Cyan
Write-Host ""

try {
    # Generar EXE con ps2exe
    ps2exe -inputFile $psScript -outputFile $OutputPath -noConsole -title "Métodos Numéricos Launcher" -description "Launcher automático para Métodos Numéricos" -company "CEGB03" -product "Métodos Numéricos" -version "1.0.0.0" -requireAdmin:$false
    
    if (Test-Path $OutputPath) {
        $fileSize = [math]::Round((Get-Item $OutputPath).Length / 1KB, 2)
        Write-Host "✅ EXE generado exitosamente!" -ForegroundColor Green
        Write-Host "📁 Ubicación: $OutputPath" -ForegroundColor Green
        Write-Host "📏 Tamaño: $fileSize KB" -ForegroundColor Green
        Write-Host ""
        Write-Host "🚀 El usuario puede ahora descargar y ejecutar directamente:" -ForegroundColor Yellow
        Write-Host "   $OutputPath" -ForegroundColor White
        Write-Host ""
        Write-Host "💡 Características del EXE:" -ForegroundColor Blue
        Write-Host "   • No requiere PowerShell visible" -ForegroundColor Gray
        Write-Host "   • Descarga automática del JAR" -ForegroundColor Gray
        Write-Host "   • Ventanas de error gráficas" -ForegroundColor Gray
        Write-Host "   • Caché inteligente" -ForegroundColor Gray
        Write-Host "   • Verificación de Java" -ForegroundColor Gray
    }
    else {
        throw "El archivo EXE no se generó"
    }
}
catch {
    Write-Host "❌ Error al generar EXE: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}
