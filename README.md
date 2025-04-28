Acore
=====

[![Build](https://github.com/dkwasny/acore-app/actions/workflows/main.yml/badge.svg)](https://github.com/dkwasny/acore-app/actions/workflows/main.yml)

A project for learning Spring Boot, React, Typescript, and other tech as I go along.

Intended to be a fun little dashboard for the MySQL database backing the [AzerothCore](https://www.azerothcore.org/) WoW server emulator.

### Setup
1. Add a SQL user with read permissions to the three main AzerothCore databases.
    ```
   -- Sample Queries
   create user 'acore-server'@'localhost' identified by 'password';
   grant select on acore_auth.* to 'acore-server'@'localhost';
   grant select on acore_characters.* to 'acore-server'@'localhost';
   grant select on acore_world.* to 'acore-server'@'localhost';
   ```
2. Create a new `acore-server/acore.dev.properties` file using the template.

### How to Run
1. `gradlew bootRun` to start Spring Boot
2. Optional: `gradlew viteRun` in a separate terminal to start the Vite dev UI server
3. Open the app in your web browser based on what UI server you're using
    * Spring Boot: `http://localhost:8080`
    * Vite Dev: `http://localhost:5173`
