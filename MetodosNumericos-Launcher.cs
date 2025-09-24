using System;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Reflection;

// Métodos Numéricos - Launcher EXE Nativo
// Este ejecutable descarga y ejecuta automáticamente la última versión desde GitHub
//
// Compilar con: csc /target:winexe /out:metodos-numericos-launcher.exe MetodosNumericos-Launcher.cs /r:System.Windows.Forms.dll /r:System.Net.Http.dll /r:System.Text.Json.dll
// O usar el script: compile-launcher-exe.bat

namespace MetodosNumericosLauncher 
{
    class Program 
    {
        private const string REPO_OWNER = "cegb03";
        private const string REPO_NAME = "Metodos-Numericos-Java-IU";
        private const string APP_NAME = "Métodos Numéricos";
        private const string JAR_NAME = "MetodosNumericos.jar";
        
        private static readonly string CacheDir = Path.Combine(
            Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location),
            "metodos-numericos"
        );
        private static readonly string JarPath = Path.Combine(CacheDir, JAR_NAME);

        [STAThread]
        static async Task<int> Main(string[] args) 
        {
            try 
            {
                ShowBanner();
                
                // Verificar dependencias
                if (!CheckJava()) 
                {
                    return 1;
                }
                
                // Verificar si necesita descarga
                bool needsDownload = !File.Exists(JarPath) || IsFileOld(JarPath);
                
                if (needsDownload) 
                {
                    Console.WriteLine("📡 Verificando última versión...");
                    
                    var releaseInfo = await GetLatestReleaseInfo();
                    if (releaseInfo != null) 
                    {
                        Console.WriteLine($"📥 Descargando versión {releaseInfo.TagName}...");
                        
                        if (await DownloadJar(releaseInfo.DownloadUrl)) 
                        {
                            Console.WriteLine("✅ Descarga completada");
                        }
                        else if (!File.Exists(JarPath)) 
                        {
                            ShowError("No se pudo descargar la aplicación y no hay versión en caché.");
                            return 1;
                        }
                    }
                    else if (!File.Exists(JarPath)) 
                    {
                        ShowError("No se pudo conectar con GitHub y no hay versión en caché.");
                        return 1;
                    }
                }
                else 
                {
                    Console.WriteLine("📦 Usando versión en caché");
                }
                
                // Ejecutar aplicación
                Console.WriteLine("🚀 Iniciando aplicación...");
                return RunJavaApplication();
            }
            catch (Exception ex) 
            {
                ShowError($"Error inesperado: {ex.Message}");
                return 1;
            }
        }

        private static void ShowBanner() 
        {
            Console.WriteLine();
            Console.WriteLine("╔════════════════════════════════════════╗");
            Console.WriteLine("║        📊 MÉTODOS NUMÉRICOS 📊        ║");
            Console.WriteLine("║                                        ║");
            Console.WriteLine("║      Launcher Automático v1.0          ║");
            Console.WriteLine("╚════════════════════════════════════════╝");
            Console.WriteLine();
        }

        private static bool CheckJava() 
        {
            try 
            {
                var process = new Process 
                {
                    StartInfo = new ProcessStartInfo 
                    {
                        FileName = "java",
                        Arguments = "-version",
                        UseShellExecute = false,
                        RedirectStandardError = true,
                        CreateNoWindow = true
                    }
                };
                
                process.Start();
                string output = process.StandardError.ReadToEnd();
                process.WaitForExit();
                
                if (process.ExitCode == 0 && output.Contains("version")) 
                {
                    Console.WriteLine("✅ Java encontrado");
                    return true;
                }
            }
            catch { }
            
            ShowError(@"Java no está instalado o no está en el PATH.

Para instalar Java:
• Descargar desde: https://adoptopenjdk.net/
• O desde Microsoft Store: buscar 'OpenJDK'
• Reiniciar el sistema después de la instalación

Java es necesario para ejecutar la aplicación.");
            
            return false;
        }

        private static async Task<ReleaseInfo> GetLatestReleaseInfo() 
        {
            try 
            {
                using (var client = new HttpClient()) 
                {
                    client.DefaultRequestHeaders.Add("User-Agent", "MetodosNumericos-Launcher/1.0");
                    client.Timeout = TimeSpan.FromSeconds(30);
                    
                    string apiUrl = $"https://api.github.com/repos/{REPO_OWNER}/{REPO_NAME}/releases/latest";
                    string jsonResponse = await client.GetStringAsync(apiUrl);
                    
                    using (JsonDocument doc = JsonDocument.Parse(jsonResponse)) 
                    {
                        var root = doc.RootElement;
                        string tagName = root.GetProperty("tag_name").GetString();
                        
                        var assets = root.GetProperty("assets");
                        foreach (var asset in assets.EnumerateArray()) 
                        {
                            string assetName = asset.GetProperty("name").GetString();
                            if (assetName.EndsWith(".jar")) 
                            {
                                return new ReleaseInfo 
                                {
                                    TagName = tagName,
                                    DownloadUrl = asset.GetProperty("browser_download_url").GetString()
                                };
                            }
                        }
                    }
                }
            }
            catch (Exception ex) 
            {
                Console.WriteLine($"⚠️ Error al consultar GitHub: {ex.Message}");
            }
            
            return null;
        }

        private static async Task<bool> DownloadJar(string downloadUrl) 
        {
            try 
            {
                Directory.CreateDirectory(CacheDir);
                string tempPath = JarPath + ".tmp";
                
                using (var client = new HttpClient()) 
                {
                    client.Timeout = TimeSpan.FromSeconds(300);
                    
                    using (var response = await client.GetAsync(downloadUrl))
                    using (var fileStream = File.Create(tempPath)) 
                    {
                        await response.Content.CopyToAsync(fileStream);
                    }
                }
                
                // Verificar que es un archivo válido
                var fileInfo = new FileInfo(tempPath);
                if (fileInfo.Length < 1024) 
                {
                    File.Delete(tempPath);
                    return false;
                }
                
                // Mover archivo temporal al destino final
                if (File.Exists(JarPath)) 
                {
                    File.Delete(JarPath);
                }
                File.Move(tempPath, JarPath);
                
                return true;
            }
            catch (Exception ex) 
            {
                Console.WriteLine($"❌ Error durante la descarga: {ex.Message}");
                return false;
            }
        }

        private static bool IsFileOld(string filePath) 
        {
            try 
            {
                var fileAge = DateTime.Now - File.GetLastWriteTime(filePath);
                return fileAge.TotalDays > 1;
            }
            catch 
            {
                return true;
            }
        }

        private static int RunJavaApplication() 
        {
            try 
            {
                var process = new Process 
                {
                    StartInfo = new ProcessStartInfo 
                    {
                        FileName = "java",
                        Arguments = $"-jar \"{JarPath}\"",
                        UseShellExecute = false,
                        WorkingDirectory = Path.GetDirectoryName(JarPath)
                    }
                };
                
                process.Start();
                process.WaitForExit();
                
                if (process.ExitCode == 0) 
                {
                    Console.WriteLine("✅ Aplicación cerrada correctamente");
                }
                
                return process.ExitCode;
            }
            catch (Exception ex) 
            {
                ShowError($"Error al ejecutar la aplicación: {ex.Message}");
                return 1;
            }
        }

        private static void ShowError(string message) 
        {
            Console.WriteLine($"❌ Error: {message}");
            
            try 
            {
                MessageBox.Show(message, $"{APP_NAME} - Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch 
            {
                // Si falla MessageBox, solo mostrar en consola
                Console.WriteLine("Presiona cualquier tecla para continuar...");
                Console.ReadKey();
            }
        }

        private class ReleaseInfo 
        {
            public string TagName { get; set; }
            public string DownloadUrl { get; set; }
        }
    }
}
