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

### Parsing command line parameters

```JCommander``` framework was chosen for parsing command line parameters as the simplest tool for our task.
It takes just one line for adding a new parameter in parsing, 
allows to create unnamed parameters and your own validators for parameters (for example, ```NonNegativeInteger``` in my implementation).

It was one more library, which could be used ```Apache Commons CLI```. 
It's a little bit harder to use and has more features, but in this assignment I don't need them, so I decided to used ```JCommander```.