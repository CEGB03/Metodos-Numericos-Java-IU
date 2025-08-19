# 🔧 Cómo Generar EXE Launcher - Guía Completa

Tienes **3 opciones** para generar un launcher `.exe` nativo para Windows:

## 🚀 Opción 1: PowerShell a EXE (Más Fácil)

### En Windows con PowerShell:

```powershell
# 1. Instalar ps2exe (solo una vez)
Install-Module ps2exe -Scope CurrentUser

# 2. Generar EXE desde PowerShell
.\generar-launcher-exe.ps1
```

**Resultado**: `metodos-numericos-launcher.exe` (~500KB)

### Características:
- ✅ Más fácil de generar
- ✅ Funciona igual que el .ps1
- ✅ No requiere .NET Framework adicional
- ⚠️ Requiere PowerShell en el sistema

---

## 🛠️ Opción 2: Compilar C# Nativo (Más Robusto)

### En Windows con .NET:

```batch
# Ejecutar el compilador batch
compile-launcher-exe.bat
```

**Resultado**: `metodos-numericos-launcher.exe` (~50-200KB)

### Características:
- ✅ EXE nativo completamente independiente
- ✅ No requiere PowerShell
- ✅ Más rápido en ejecución
- ✅ Ventanas de error con MessageBox
- ⚠️ Requiere .NET Framework en Windows

---

## 🌐 Opción 3: Herramientas Online (Sin Instalar Nada)

### 3.1 Usar .NET Fiddle (Online C# Compiler)
1. Ir a: https://dotnetfiddle.net/
2. Seleccionar "Console Application"
3. Pegar el código de `MetodosNumericos-Launcher.cs`
4. **Problema**: No puede generar EXE directamente

### 3.2 Usar OneCompiler (Limitado)
1. Ir a: https://onecompiler.com/csharp
2. Similar limitación

### 3.3 Usar GitHub Actions (Automático)
Crear workflow para compilar automáticamente:

```yaml
# .github/workflows/build-launcher.yml
name: Build Launcher EXE
on: [push, release]
jobs:
  build-exe:
    runs-on: windows-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup .NET
        uses: actions/setup-dotnet@v3
        with:
          dotnet-version: '6.0'
      - name: Build EXE
        run: |
          csc /target:winexe /out:metodos-numericos-launcher.exe MetodosNumericos-Launcher.cs /r:System.Windows.Forms.dll
      - name: Upload artifact
        uses: actions/upload-artifact@v3
        with:
          name: launcher-exe
          path: metodos-numericos-launcher.exe
```

---

## 📊 Comparación de Opciones

| Opción | Facilidad | Tamaño EXE | Dependencias | Velocidad |
|---------|-----------|------------|--------------|-----------|
| **ps2exe** | ⭐⭐⭐⭐⭐ | ~500KB | PowerShell | Media |
| **C# Nativo** | ⭐⭐⭐ | ~50KB | .NET Framework | Rápida |
| **GitHub Actions** | ⭐⭐⭐⭐ | ~50KB | Ninguna (user) | Rápida |

---

## 🎯 Recomendación por Uso

### 👤 **Para Usuarios Finales** (más compatibilidad):
1. **Opción 1**: `metodos-numericos-launcher.ps1` (PowerShell)
2. **Opción 2**: `MetodosNumericos.bat` (Batch simple)  
3. **Opción 3**: `metodos-numericos-launcher.exe` (Si existe)

### 👨‍💻 **Para Desarrollador** (distribución profesional):
1. **Primera elección**: C# Nativo (`compile-launcher-exe.bat`)
2. **Alternativa fácil**: ps2exe (`generar-launcher-exe.ps1`)
3. **Automatización**: GitHub Actions

---

## 📁 Estructura Final de Distribución

```
GitHub Release:
├── MetodosNumericos.jar                    # Aplicación principal
├── metodos-numericos-launcher.sh           # Linux/macOS
├── metodos-numericos-launcher.ps1          # Windows PowerShell  
├── MetodosNumericos.bat                    # Windows Batch
└── metodos-numericos-launcher.exe          # Windows EXE (opcional)
```

---

## 🚀 Próximos Pasos

1. **Elegir opción** según tus preferencias/herramientas
2. **Generar EXE** usando el método seleccionado
3. **Probar** el EXE generado
4. **Subir a GitHub Release** junto con el JAR
5. **Actualizar documentación** con enlace de descarga

---

## 💡 Consejos

- **El .exe es opcional**: Los scripts .ps1 y .bat ya funcionan perfectamente
- **Tamaño vs Compatibilidad**: EXE nativo es más pequeño, ps2exe más compatible
- **Testing**: Probar en Windows sin .NET/PowerShell para verificar dependencias
- **Firma digital**: Para distribución profesional, considera firmar el EXE

**¡Con cualquiera de estas opciones tendrás distribución profesional completa!** 🎉
