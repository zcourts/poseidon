# Poseidon Terminal Emulator and Console
Poseidon is composed of two maven modules. The first is a "console" and the other
a "terminal emulator" which uses the console.

# Intended features
* Cross Platform, I use both Linux and Windows so all the implemented internal commands will work accross OSs
* Eventually the project will implement most or all of Linux's bash internal commands http://linux.about.com/library/cmd/blcmdl1_builtin.htm
* Support for plugins written specifically for Poseidon
* Support for executing any external command available on the user's path

# Feature priority in order of importance
* Cross Platform
* Minimal (subset) of bash's internal commands
* Executing external command
* Plugins