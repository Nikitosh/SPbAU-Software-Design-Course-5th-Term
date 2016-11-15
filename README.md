# Chat

### Overview

Simple chat with peer-to-peer connection between users.

### Message protocol

Protocol consists only of two strings, author's name and message's text.

### Structure

There are several important classes in my architecture:
* Client, which can connect to server and disconnect from it, and it's implementation ClientImpl.
* Server, which can be launched to accept clients and can be stopped, and it's implementation ServerImpl.
* Settings, singleton class, which holds all users settings (name, server IP, user's server port and port to connect).
* Controller, which provides ability to communicate with another user (send and receive messages) and which is used for interaction between Model and UI.
* Message, which holds information about message (it's author and text).

### UI

For implementing UI I used standard Swing library.

### Porting to another platforms

Model code doesn't depend on UI at all, so it's easy to port application on another platforms, for example Android.

