# 🧮 Métodos Numéricos - Descarga Directa

¡Ejecuta **Métodos Numéricos** sin compilar nada! Solo descarga y ejecuta.

## 🚀 Descarga Rápida por Sistema Operativo

### 🐧 **Linux/macOS**
Descarga este archivo y ejecútalo:

**📥 [metodos-numericos-launcher.sh](https://raw.githubusercontent.com/cegb03/Metodos-Numericos-Java-IU/main/metodos-numericos-launcher.sh)**

```bash
# Descargar
curl -O https://raw.githubusercontent.com/cegb03/Metodos-Numericos-Java-IU/main/metodos-numericos-launcher.sh

# Dar permisos y ejecutar
chmod +x metodos-numericos-launcher.sh
./metodos-numericos-launcher.sh
```

### 🪟 **Windows**

#### Opción 1: Script PowerShell (Recomendado)
**📥 [metodos-numericos-launcher.ps1](https://raw.githubusercontent.com/cegb03/Metodos-Numericos-Java-IU/main/metodos-numericos-launcher.ps1)**

1. Descargar el archivo `.ps1`
2. Clic derecho → "Ejecutar con PowerShell"
3. Si aparece mensaje de seguridad, escribir `S` y presionar Enter

#### Opción 2: Ejecutable Nativo EXE
**📍 [metodos-numericos-launcher.exe](https://github.com/cegb03/Metodos-Numericos-Java-IU/releases/latest)**

1. Descargar el archivo `.exe` desde Releases
2. Doble clic para ejecutar
3. **No requiere PowerShell** - funciona en cualquier Windows

#### Opción 3: Script Batch  
**📍 [MetodosNumericos.bat](https://raw.githubusercontent.com/cegb03/Metodos-Numericos-Java-IU/main/MetodosNumericos.bat)**

1. Descargar el archivo `.bat`  
2. Doble clic para ejecutar

## 📋 Requisitos

- **☕ Java 8 o superior** (se verifica automáticamente)
- **🌐 Conexión a internet** (solo para primera descarga)

### Instalar Java si no lo tienes:

| Sistema | Comando/Enlace |
|---------|----------------|
| **Ubuntu/Debian** | `sudo apt install openjdk-11-jre` |
| **Fedora/CentOS** | `sudo dnf install java-11-openjdk` |
| **Arch Linux** | `sudo pacman -S jre-openjdk` |
| **Windows** | [AdoptOpenJDK](https://adoptopenjdk.net/) o Microsoft Store |
| **macOS** | `brew install openjdk` o [AdoptOpenJDK](https://adoptopenjdk.net/) |

## 🔧 ¿Cómo Funciona?

1. **🔍 Verifica** que Java esté instalado
2. **📱 Consulta** GitHub para la última versión
3. **📥 Descarga** el archivo JAR automáticamente
4. **💾 Guarda** en caché para futuras ejecuciones  
5. **🚀 Ejecuta** la aplicación

### 📁 Ubicaciones de Caché:
- **Linux**: `~/.cache/metodos-numericos/`
- **Windows**: `%LOCALAPPDATA%\MetodosNumericos\`
- **macOS**: `~/.cache/metodos-numericos/`

## ✨ Funcionalidades

### ✅ **Disponibles**
- **🎯 Localización de Raíces**
  - Bisección, Regula Falsi
  - Punto Fijo, Newton-Raphson, Tangente
  - Gráficos interactivos
  
- **📊 Sistemas de Ecuaciones Lineales**
  - Eliminación Gaussiana
  - Jacobi, Gauss-Seidel

### 🔴 **En Desarrollo** 
- Interpolación (Lagrange, Polinomial)
- Regresión (Lineal, Polinomial)

## 🆘 Solución de Problemas

### ❌ "Java no está instalado"
**Solución**: Instala Java desde los enlaces de arriba y reinicia la terminal/cmd.

### ❌ "No se pudo conectar con GitHub"
**Soluciones**:
1. Verificar conexión a internet
2. Verificar que no haya firewall bloqueando
3. Intentar más tarde (GitHub puede estar ocupado)

### ❌ "PowerShell bloqueado por política"
**Windows - Solución**:
1. Abrir PowerShell como Administrador
2. Ejecutar: `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser`
3. Escribir `S` y presionar Enter
4. Reintentar

### ❌ "Error al ejecutar la aplicación"  
**Soluciones**:
1. Eliminar caché: 
   - Linux: `rm -rf ~/.cache/metodos-numericos/`
   - Windows: Eliminar `%LOCALAPPDATA%\MetodosNumericos\`
2. Volver a ejecutar el launcher
3. Verificar antivirus no esté bloqueando Java

## 🔄 Actualizaciones

Los launchers verifican automáticamente nuevas versiones cada 24 horas. Para forzar actualización:

- **Linux**: `./metodos-numericos-launcher.sh` (se actualiza automáticamente)
- **Windows**: Ejecutar PowerShell con parámetro: `.\metodos-numericos-launcher.ps1 -Force`

## 📞 Soporte y Reportar Problemas

- **🐛 Issues**: [GitHub Issues](https://github.com/cegb03/Metodos-Numericos-Java-IU/issues)
- **📧 Email**: Crear issue en GitHub (más rápido)
- **📱 Telegram**: @cegb03 (solo para urgencias)

## 🎉 ¡Disfruta Calculando!

Una vez que la aplicación esté ejecutándose:

1. **🎯 Loc Raíces**: Para encontrar raíces de ecuaciones
2. **📊 Sis Ecu Lin**: Para resolver sistemas de ecuaciones
3. **🔴 Botones rojos**: Funciones en desarrollo (próximamente)

---

**💡 Tip**: Los launchers son inteligentes - solo descargan cuando es necesario y muestran ventanas de error gráficas si algo falla.
