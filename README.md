# Shell

### Overview

Simple Shell implementation.

### Available commands
* assignment
* cat
* echo
* external call
* pwd
* wc

### UML Diagram

![UML](https://github.com/Nikitosh/SPbAU-Software-Design-Course-5th-Term/blob/01-CLI/UML/Shell.png)

### Structure

There are several important classes in my architecture:
* Environment, which holds system's state such as variables' values, current directory etc.
* Lexer, which parses string, given in command line, into lexemes and then substitutes variables' values from environment.
* Parser, which executes given list of lexemes using different classes for each command.
* Command, main interface for all available command, which executes command with given inputStream and arguments and prints it's output to InputStream.

Such structure allows to add new commands really easy and understand written code quickly enough.