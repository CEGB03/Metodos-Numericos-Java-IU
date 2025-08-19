# Distribución de Métodos Numéricos

Este proyecto Java Swing para métodos numéricos se puede distribuir de varias formas:

## 1. JAR desde GitHub Release (Recomendado para usuarios)

### Descarga automática:
```bash
./descargar-release.sh
```

Esto descargará la última versión estable desde GitHub Releases a `releases/MetodosNumericos.jar`.

### Ejecución directa:
```bash
java -jar releases/MetodosNumericos.jar
```

## 2. JAR Ejecutable Local (Para desarrollo)

El JAR ejecutable compilado localmente está ubicado en:
```
target/MetodosNumericos-1.0-SNAPSHOT.jar
```

### Compilación:
```bash
mvn clean package
```

### Ejecución directa:
```bash
java -jar target/MetodosNumericos-1.0-SNAPSHOT.jar
```

### Script de lanzamiento:
```bash
./run-metodos-numericos.sh
```

## 2. Aplicación Nativa (jpackage)

Se ha creado una imagen de aplicación nativa usando jpackage:

### Directorio de la aplicación:
```
MetodosNumericos/
├── bin/
│   └── MetodosNumericos    # Ejecutable nativo
└── lib/
    ├── runtime/           # Runtime de Java personalizado
    └── app/              # JARs de la aplicación
```

### Generación de la aplicación nativa:
```bash
jpackage --type app-image --input target/ --main-jar MetodosNumericos-1.0-SNAPSHOT.jar --name MetodosNumericos --app-version 1.0 --main-class com.cegb03.metodos.MetodosNumericos
```

### Ejecución:
```bash
./MetodosNumericos/bin/MetodosNumericos
```

**Nota**: El directorio `MetodosNumericos/` no se incluye en el repositorio debido a su gran tamaño (200+ MB). Se regenera automáticamente con el comando anterior.

## 3. Paquete RPM

Se ha creado un paquete RPM para distribución en sistemas basados en RPM:
```
metodosnumericos-1.0-1.x86_64.rpm
```

### Instalación:
```bash
sudo rpm -ivh metodosnumericos-1.0-1.x86_64.rpm
```

### Desinstalación:
```bash
sudo rpm -e metodosnumericos
```

## 4. Acceso directo del escritorio

Se proporciona un archivo `.desktop` para crear accesos directos:
```
MetodosNumericos.desktop
```

### Instalación del acceso directo (usuario actual):
```bash
cp MetodosNumericos.desktop ~/.local/share/applications/
```

### Instalación del acceso directo (sistema):
```bash
sudo cp MetodosNumericos.desktop /usr/share/applications/
```

## Requisitos del Sistema

- Java 8 o superior (para el JAR ejecutable)
- Linux x86_64 (para la aplicación nativa y el RPM)

## Compilación desde el código fuente

```bash
mvn clean package
```

## 5. Ejecutable Windows (.exe)

Se ha creado un ejecutable Windows usando Launch4j:
```
MetodosNumericos.exe
```

### Generación automática:
```bash
./generar-windows-exe.sh
```

### Características del ejecutable Windows:
- Compatible con Windows 7/8/10/11 (32 y 64 bits)
- Detecta automáticamente si Java está instalado
- Redirige al usuario para descargar Java si no está disponible
- Incluye información de versión y metadatos
- Tamaño: ~42 MB (incluye el JAR completo)

### Archivos para Windows:
- `MetodosNumericos.exe`: Ejecutable nativo Windows
- `MetodosNumericos.bat`: Script alternativo para Windows

## Notas técnicas

- La aplicación nativa incluye un runtime de Java personalizado, no requiere Java instalado
- El JAR ejecutable incluye todas las dependencias (JFreeChart, etc.)
- Se utiliza el Look & Feel del sistema por defecto
- Los colores personalizados están configurados para preservarse independientemente del tema

## Estructura del proyecto

- `src/main/java/com/cegb03/metodos/`: Código fuente principal
  - `MetodosNumericos.java`: Clase principal
  - `Inicio.java`: Ventana principal
  - `LocRaices/`: Módulo de localización de raíces
  - `SisEcuaciones/`: Módulo de sistemas de ecuaciones
  - `Regresion/`: Módulo de regresión
  - `Interpolacion/`: Módulo de interpolación
