# Poseidon Terminal Emulator and Console

Poseidon is composed of two maven modules. The first is a "console" and the other
a "terminal emulator" which uses the console.

# Intended features

* Cross Platform, I use both Linux and Windows so all the implemented internal commands will work accross OSs
* Eventually the project will implement most or all of Linux's bash internal commands http://linux.about.com/library/cmd/blcmdl1_builtin.htm
* Support for the ANSI colour output (see http://en.wikipedia.org/wiki/ANSI_escape_code or http://pueblo.sourceforge.net/doc/manual/ansi_color_codes.html)
* Support for plugins written specifically for Poseidon
* Support for executing any external command available on the user's path
* Built in SSH (Possibly using http://www.jcraft.com/jsch/ or https://github.com/shikhar/sshj (possibly http://mina.apache.org/sshd/ ?) pending investigation)

# Feature priority in order of importance

* Cross Platform
* Minimal (subset) of bash's internal commands
* Executing external command
* Plugins
* ANSI colouring support
* SSH

# Rough Road Map

Features to implement in the order given

* CD
* export
* improve input from external process
* implement output to external process
* Implement plugin system
* Implement cross platform system access as a plugin using http://www.hyperic.com/products/sigar or any available alternative