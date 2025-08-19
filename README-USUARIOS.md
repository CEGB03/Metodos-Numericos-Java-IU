# 🧮 Métodos Numéricos - Guía de Usuario

Aplicación Java Swing para resolver problemas de métodos numéricos con interfaz gráfica intuitiva.

## 🚀 Instalación Rápida (Recomendada)

### 1. Descargar la última versión

```bash
git clone https://github.com/cegb03/Metodos-Numericos-Java-IU.git
cd Metodos-Numericos-Java-IU
./descargar-release.sh
```

### 2. Ejecutar la aplicación

```bash
./run-metodos-numericos.sh
```

¡Listo! 🎉

## 📋 Requisitos

- **Java 8 o superior** instalado en tu sistema
- **Git** (para clonar el repositorio)
- **Conexión a internet** (para descargar la release)

### 🔍 Verificar Java

```bash
java -version
```

Si no tienes Java instalado:
- **Ubuntu/Debian**: `sudo apt install openjdk-11-jre`
- **Fedora/RedHat**: `sudo dnf install java-11-openjdk`
- **Windows**: Descarga desde [AdoptOpenJDK](https://adoptopenjdk.net/)

## 🖥️ Funcionalidades

### ✅ Disponibles
- **🎯 Localización de Raíces**:
  - Métodos cerrados: Bisección, Regula Falsi
  - Métodos abiertos: Punto Fijo, Newton-Raphson, Tangente
  - Graficación interactiva con JFreeChart

- **📊 Sistemas de Ecuaciones Lineales**:
  - Eliminación Gaussiana
  - Métodos iterativos: Jacobi, Gauss-Seidel

### 🔴 En desarrollo
- **📈 Interpolación** (Lagrange, Polinomial)
- **📉 Regresión** (Lineal, Polinomial)

## 🎮 Uso de la Aplicación

1. **Ejecutar**: `./run-metodos-numericos.sh`
2. **Seleccionar método**: Haz clic en los botones disponibles (verdes)
3. **Configurar parámetros**: Introduce valores en los campos
4. **Ver resultados**: Los gráficos y resultados se muestran automáticamente

### 💡 Consejos
- Los botones **rojos** indican funciones no disponibles aún
- Usa el tooltip (pasar mouse) para más información
- Los colores en "Loc Raíces" indican diferentes tipos de métodos

## 📁 Estructura de Archivos

```
Metodos-Numericos-Java-IU/
├── descargar-release.sh          # Descarga última versión
├── run-metodos-numericos.sh      # Ejecuta la aplicación
├── generar-windows-exe.sh        # Genera .exe para Windows
├── releases/                     # JAR descargado (auto-generado)
│   └── MetodosNumericos.jar
├── MetodosNumericos.desktop      # Acceso directo Linux
└── MetodosNumericos.bat          # Script Windows
```

## 🖼️ Crear Acceso Directo

### Linux (GNOME, KDE, etc.)
```bash
cp MetodosNumericos.desktop ~/.local/share/applications/
```

### Windows
Usar `MetodosNumericos.bat` o generar `.exe`:
```bash
./generar-windows-exe.sh
```

## 🔧 Solución de Problemas

### ❌ "No se encontró ningún archivo JAR ejecutable"
**Solución**: 
```bash
./descargar-release.sh
```

### ❌ "Java no está instalado"
**Solución**: Instala Java 8+ desde [AdoptOpenJDK](https://adoptopenjdk.net/)

### ❌ "Error en la descarga"
**Soluciones**:
1. Verificar conexión a internet
2. Compilar localmente: `mvn clean package`
3. Contactar al desarrollador

### ❌ Los botones aparecen deshabilitados
**Respuesta**: Los botones rojos son funciones en desarrollo. Solo usa los botones verdes/normales.

## 🆕 Actualizaciones

Para obtener la última versión:
```bash
cd Metodos-Numericos-Java-IU
git pull
./descargar-release.sh
```

## 📞 Soporte

- **Issues**: [GitHub Issues](https://github.com/cegb03/Metodos-Numericos-Java-IU/issues)
- **Documentación técnica**: Ver `DISTRIBUCION.md`
- **Código fuente**: `src/main/java/com/cegb03/metodos/`

## 📄 Licencia

Este proyecto está bajo licencia MIT. Ver archivo `LICENSE` para más detalles.

---

**¿Problemas? ¿Sugerencias?** 
¡Abre un [issue](https://github.com/cegb03/Metodos-Numericos-Java-IU/issues) en GitHub!
