# Usb-Locker

Usb-Locker is a Java application that allows you to switch a USB drive into **read-only mode** and back, helping protect your data from accidental writes.

## Developer
**N1N0u** – creator and maintainer

## Features
- Toggle USB drives between read-only and normal mode
- Simple Java GUI and command-line interface

## Installation
1. Install **Java 11** or higher
2. Clone this repository:
3. 
4. Add the required JARs to your project build path:
- `flatlaf-3.4.jar`
- `flatlaf-extra-3.4.jar`
- `kcontrols-2.0.jar`
- `sqlite-jdbc-3.20.1.jar`
- `jsvg-1.2.0.jar`
- `gradient-icon-font.jar`
4. Build with your favorite IDE

## Usage
- Open the GUI or run the JAR from the terminal:
- Select the USB drive and toggle mode

## Important Notes
- **Administrator privileges are required** to toggle USB drive modes.
- On Windows, UAC (User Account Control) may prompt for confirmation. To avoid issues:
- Run the application **as Administrator** (Right-click → Run as Administrator)
- Do **not** disable UAC globally unless you understand the security risks
- This tool may **not work** on restricted accounts or systems with strict group policies
- Tested on Windows 10 only (Linux support coming soon)

## Security & Limitations
- Read-only mode prevents accidental writes but does **not** prevent malware on insertion
- Always back up important data before toggling USB modes

## Contributing
Feel free to open issues or submit pull requests.

## License
MIT License

