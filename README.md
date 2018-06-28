# chat-websocket-app
A simple chat app based on WebSocket API

You can just type in a username and start chatting with others. 
If no one is available in the chat room, then you can open the app
in two tabs, enter the chat room with 2 different usernames and start chatting.

When a new user joins a chat all users in the chat room are informed.
The same applies when a user leave the chat room (ex. by closing the chat tab).

This simple chat app is deployed online at Heroku. Here is a link to the demo: 
- [LiveChat Demo](https://roundrobine-livechat.herokuapp.com/)

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/roundrobine/chat-websocket-app.git
```

You can run the app directly without packaging it like so -

```bash
mvn spring-boot:run
```
## Testing

Unit and integration tests are created for classes and WebSocket endpoints. 

The overall test coverage is:
* Classes - 100%
* Methods - 94%
* Lines - 95%