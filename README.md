# Usb-Locker

![Java](https://img.shields.io/badge/Java-11%2B-blue)
![License](https://img.shields.io/badge/License-MIT-green)
![Build](https://img.shields.io/badge/Build-Pending-yellow)
![Platform](https://img.shields.io/badge/Platform-Windows%2010%2B-blue)
![GitHub issues](https://img.shields.io/github/issues/N1N0u/Usb-Locker)
![GitHub stars](https://img.shields.io/github/stars/N1N0u/Usb-Locker)

## Live Project
_Java application only_

## Project Overview
Usb-Locker is a Java application that allows you to switch a USB drive into **read-only mode** and back, helping protect your data from accidental writes. The objective of the project is to provide a simple, GUI-based tool for USB security and management.

## Technologies Used

### Frontend / GUI
- Java Swing / FlatLaf  
- KControls library  
- Gradient Icon Font  
- JSVG for SVG rendering  

### Backend / Core
- Java 11+  
- SQLite (optional, for logging USB operations)  

### Dependencies (Required JARs)
- `flatlaf-3.4.jar`  
- `flatlaf-extra-3.4.jar`  
- `kcontrols-2.0.jar`  
- `sqlite-jdbc-3.20.1.jar`  
- `jsvg-1.2.0.jar`  
- `gradient-icon-font.jar`  

## Key Features
- Toggle USB drives between read-only and normal mode  
- Simple and intuitive GUI  
- Command-line option for advanced users  
- Logs USB toggle operations (optional)  

## Project Architecture
- **GUI Layer**: Handles user interface and drive selection  
- **Core Layer**: Executes USB mode toggling commands  
- **Optional DB Layer**: Logs actions to SQLite database  

## Usage
1. Run the JAR file:
2. Select the USB drive and toggle mode  
3. Administrator privileges are required on Windows.  

## Important Notes
- **Do not disable UAC**; run as Administrator instead  
- Read-only mode prevents accidental writes but does **not** prevent malware  
- Tested on Windows 10 only  

👨‍💻 Lead Developer

ALIAT Atef – Responsible for software development and computer vision solutions

