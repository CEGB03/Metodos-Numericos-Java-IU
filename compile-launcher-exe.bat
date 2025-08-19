@echo off
REM Script para compilar launcher EXE nativo en Windows
REM Requiere .NET Framework SDK o Visual Studio instalado

echo.
echo ================================================================
echo               COMPILADOR DE LAUNCHER EXE
echo                     Metodos Numericos
echo ================================================================
echo.

REM Verificar que existe el archivo fuente
if not exist "MetodosNumericos-Launcher.cs" (
    echo ERROR: No se encuentra MetodosNumericos-Launcher.cs
    echo.
    pause
    exit /b 1
)

REM Buscar compilador C#
echo Buscando compilador C#...

REM Intentar varias ubicaciones comunes del compilador
set CSC_PATH=""

REM .NET Framework 4.x (más común)
if exist "%WINDIR%\Microsoft.NET\Framework64\v4.0.30319\csc.exe" (
    set CSC_PATH="%WINDIR%\Microsoft.NET\Framework64\v4.0.30319\csc.exe"
    goto :found_compiler
)

if exist "%WINDIR%\Microsoft.NET\Framework\v4.0.30319\csc.exe" (
    set CSC_PATH="%WINDIR%\Microsoft.NET\Framework\v4.0.30319\csc.exe"
    goto :found_compiler
)

REM .NET 6/7/8 (más reciente)
where dotnet >nul 2>&1
if %errorlevel%==0 (
    echo Usando dotnet para compilar...
    goto :compile_with_dotnet
)

echo ERROR: No se encontro compilador C#
echo.
echo Para compilar necesitas uno de estos:
echo - .NET Framework SDK
echo - Visual Studio (cualquier version)
echo - .NET 6/7/8 SDK
echo.
echo Descargar desde:
echo - .NET Framework: https://dotnet.microsoft.com/download/dotnet-framework
echo - .NET Core/5+: https://dotnet.microsoft.com/download
echo.
pause
exit /b 1

:found_compiler
echo Compilador encontrado: %CSC_PATH%
echo.
echo Compilando launcher EXE...

REM Compilar con .NET Framework
%CSC_PATH% /target:winexe /out:metodos-numericos-launcher.exe /nologo /optimize+ MetodosNumericos-Launcher.cs /r:System.Windows.Forms.dll /r:System.Net.Http.dll /r:System.Text.Json.dll

goto :check_result

:compile_with_dotnet
echo Creando proyecto temporal...

REM Crear archivo de proyecto temporal
echo ^<Project Sdk="Microsoft.NET.Sdk"^> > temp_project.csproj
echo   ^<PropertyGroup^> >> temp_project.csproj
echo     ^<OutputType^>WinExe^</OutputType^> >> temp_project.csproj
echo     ^<TargetFramework^>net6.0-windows^</TargetFramework^> >> temp_project.csproj
echo     ^<UseWindowsForms^>true^</UseWindowsForms^> >> temp_project.csproj
echo     ^<AssemblyName^>metodos-numericos-launcher^</AssemblyName^> >> temp_project.csproj
echo     ^<PublishSingleFile^>true^</PublishSingleFile^> >> temp_project.csproj
echo     ^<SelfContained^>false^</SelfContained^> >> temp_project.csproj
echo   ^</PropertyGroup^> >> temp_project.csproj
echo ^</Project^> >> temp_project.csproj

REM Renombrar archivo C# para dotnet
copy MetodosNumericos-Launcher.cs Program.cs >nul

REM Compilar con dotnet
dotnet publish temp_project.csproj -c Release -o bin --self-contained false

REM Mover resultado
if exist "bin\metodos-numericos-launcher.exe" (
    copy bin\metodos-numericos-launcher.exe . >nul
    set COMPILATION_SUCCESS=1
) else (
    set COMPILATION_SUCCESS=0
)

REM Limpiar archivos temporales
del temp_project.csproj Program.cs >nul 2>&1
rmdir /s /q bin obj >nul 2>&1

if %COMPILATION_SUCCESS%==1 (
    goto :success
) else (
    goto :failed
)

:check_result
if exist "metodos-numericos-launcher.exe" (
    goto :success
) else (
    goto :failed
)

:success
echo.
echo ================================================================
echo                  COMPILACION EXITOSA!
echo ================================================================
echo.
echo Archivo generado: metodos-numericos-launcher.exe

REM Mostrar informacion del archivo
for %%I in (metodos-numericos-launcher.exe) do (
    echo Tamano: %%~zI bytes
    echo Fecha: %%~tI
)

echo.
echo Caracteristicas del EXE:
echo - Descarga automatica del JAR desde GitHub
echo - Cache inteligente
echo - Ventanas de error graficas
echo - Verificacion de Java
echo - No requiere PowerShell
echo.
echo El usuario puede descargar y ejecutar directamente:
echo   metodos-numericos-launcher.exe
echo.
echo IMPORTANTE: Subir este archivo a GitHub Releases junto con el JAR
echo.
pause
exit /b 0

:failed
echo.
echo ERROR: La compilacion fallo
echo.
echo Posibles soluciones:
echo 1. Verificar que tienes .NET Framework SDK instalado
echo 2. Ejecutar desde "Developer Command Prompt"
echo 3. Instalar Visual Studio Community (gratuito)
echo 4. Instalar .NET 6 SDK
echo.
pause
exit /b 1
