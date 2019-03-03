# GameTrace Web Application

### Project Overview

Web Application that follows the [GameTrace Desktop Client](https://github.com/dradisavljevic/GameTrace-Desktop-Client) .

Like the client application, this is part of an old Project that served as my bachelor thesis.

Idea was to create a Steam/Xfire-like web application dashboard where you can access all information about the games you've played.

Application works with OracleSQL. Scripts for database initialization are located inside the [GameTrace SQL](https://github.com/dradisavljevic/GameTrace-SQL) repository.

Before running, replace spring.mail.username and spring.mail.password properties inside application.properties with valid values if you want to register new users.

### ToDo

- [ ] Make the code look more bearable