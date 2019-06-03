# Timetable

## Table of Content

- [Screenshots](#screenshots) <br>
  - [Homescreen Dark](#homescreen-dark) <br>
  - [Homescreen Light](#homescreen-light) <br>
- [Information](#information) <br>
  - [Dependencies](#dependencies) <br>
  - [Features](#features) <br> 
  - [Visions](#visions) <br>
  - [Notes](#notes) <br>
- [Shortcuts](#shortcuts)
  - [General](#general) <br>
  - [Subject](#subject) <br>

## Screenshots
### Homescreen Dark
![Homescreen dark](https://github.com/Saecki/Timetable/blob/master/Timetable-home-dark.png)
### Homescreen Light
![Homescreen light](https://github.com/Saecki/Timetable/blob/master/Timetable-home-light.png)

## Information
### Dependencies
- JDK 11
- JavaFX 11
- [JFoenix](https://github.com/jfoenixadmin/JFoenix) Design Library
- [json-simple](https://github.com/fangyidong/json-simple) JSON API

### Features
- Alternating Week Support
- Autocompletion
- Intuitive Interface
- Drag and Drop Subjects
- Theming

### Visions
- Google Drive Support
- Configurable Pauses

### Notes
- There are still some bugs when changing colors but these are caused by the JFoenix Components and will be resolved in their next release. In the meantime a restart of the application will fix most of them.

## Shortcuts
### General
| Shortcut                          | Function |
| ---                               | --- |
| CTRL + Tab                        | Switches between the A and B week |
| CTRL + N                          | Adds a new timetable |
| CTRL + M                          | Opens the sidebar menu |
| CTRL + S                          | Opens the settings |
| CTRL + D                          | Toggles the darkmode |
| CTRL + H or <br> ESC              | Hides all menus |
| CTRL + Space or <br> CTRL + Enter | Opens a contextmenu if available |

### Subject
| Shortcut                          | Function |
| ---                               | --- |
| CTRL + C                          | Copies the currently focused subject |
| CTRL + X                          | Cuts the currently focused subject |
| CTRL + V                          | Pastes the currently focused subject |
| CTRL + E                          | Clears the currently focused subject |
| CTRL + R or <br> DELETE           | Deletes the currently focused subject |
| CTRL + PLUS                       | Adds a new subject below |
| CTRL + SHIFT + PLUS               | Adds a new subject above |
| CTRL + ARROWKEY                   | Moves a subject in the arrow direction | 
