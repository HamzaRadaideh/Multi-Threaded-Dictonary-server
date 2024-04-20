# Dictonary Server

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This is a simple dictionary server implemented using Java and TCP sockets, capable of handling multiple client connections concurrently through multithreading.

## Usage

- Connect to the server using a TCP client (e.g., telnet, netcat) on port `8888` and the localhost IP `127.0.0.1`.
- Send a word to get its meaning from the server.

## Requirements

- Java
- Gradle

# Build and Run

1. Clone the repository:

```bash
git clone https://github.com/HamzaRadaideh/Multi-Threaded-Dictonary-server
cd dictionary-server
```

2. Run the server:

```bash
gradle run
```

## Notes

> The server listens on port 8888 by default (modifiable in App.java).
> The server is multi-threaded, handling multiple client connections concurrently.

## Structure

- `app/src/main/java/org/example/`
  - `App.java`: Main class containing server logic.
  - `ClientHandler.java`: Handles client connections and dictionary lookups.
  - `JsonReader.java`: Loads dictionary data from a JSON file.
- `app/src/main/resources/league.json`

# License

This project is licensed under the [MIT License](./LICENSE).
