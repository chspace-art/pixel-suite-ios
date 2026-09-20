# Capture the desktop preview window (Compose target) to a PNG.
# Pure ASCII on purpose: PowerShell 5.1 needs a BOM to read non-ASCII .ps1 correctly.
param(
    [Parameter(Mandatory = $true)][string]$Out,
    [string]$ProcessName = "java"
)
$ErrorActionPreference = "Stop"
Add-Type -AssemblyName System.Drawing

Add-Type @"
using System;
using System.Runtime.InteropServices;
public class WinCap {
    [DllImport("user32.dll")] public static extern bool SetProcessDPIAware();
    [DllImport("user32.dll")] public static extern bool GetWindowRect(IntPtr hWnd, out RECT lpRect);
    [DllImport("user32.dll")] public static extern bool SetForegroundWindow(IntPtr hWnd);
    [StructLayout(LayoutKind.Sequential)]
    public struct RECT { public int Left; public int Top; public int Right; public int Bottom; }
}
"@

[void][WinCap]::SetProcessDPIAware()

$proc = Get-Process -Name $ProcessName |
    Where-Object { $_.MainWindowHandle -ne 0 -and $_.MainWindowTitle -ne "" } |
    Select-Object -First 1
if (-not $proc) { throw "no visible window for process '$ProcessName'" }

[void][WinCap]::SetForegroundWindow($proc.MainWindowHandle)
Start-Sleep -Milliseconds 900

$r = New-Object WinCap+RECT
if (-not [WinCap]::GetWindowRect($proc.MainWindowHandle, [ref]$r)) { throw "GetWindowRect failed" }
$w = $r.Right - $r.Left
$h = $r.Bottom - $r.Top

$bmp = New-Object System.Drawing.Bitmap($w, $h)
$gfx = [System.Drawing.Graphics]::FromImage($bmp)
$gfx.CopyFromScreen($r.Left, $r.Top, 0, 0, $bmp.Size)

$dir = Split-Path -Parent $Out
if (-not (Test-Path $dir)) { New-Item -ItemType Directory -Force -Path $dir | Out-Null }
$bmp.Save($Out, [System.Drawing.Imaging.ImageFormat]::Png)

$gfx.Dispose()
$bmp.Dispose()
Write-Output "saved $Out ($w x $h) title='$($proc.MainWindowTitle)'"
