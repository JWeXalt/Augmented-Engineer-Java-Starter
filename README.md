# The Bel'Air's Buvette : building a backend for eXalt famous festival drinks and snacks bar. With Java, AI, and love.

Version française : [README_fr.md](README_fr.md)  
Version española : [README_es.md](README_es.md)

>[!note]
> 
> This project is part of the eXalt IT augmented engineer learning path, located in its [academy](https://academy.exalt-company.com/paths/699c49f3a1dffef24c46c739/home).

Hello there and welcome to the Bel'Air's Buvette project repository!

This project is your playground to create a robust backend system for managing the drinks and snacks!

You will build the most fantastic backend using Java.

But more importantly, your new best friend: Github Copilot, your new rubber ducky / overenthusiastic intern pair programmer buddy!

## Project Structure

```
belairs-buvette/
 domain/           # Business logic and domain model
 application/      # Use cases and application services
 infrastructure/   # Adapters, persistence, external integrations
```

## Installing the Toolchain

| Tool | Version | Documentation |
|------|---------|---------------|
| Java | 21+ | [adoptium.net](https://adoptium.net/) |
| Git | latest | [git-scm.com](https://git-scm.com/downloads) |

> The Gradle wrapper (`gradlew` / `gradlew.bat`) is included — no need to install Gradle separately.

## Getting Started

### Prerequisites

- Java 21+
- Git

### Fork & Clone

Fork this repository to your own Gitlab account (main branch only), then clone it:

```bash
git clone <YOUR_FORK_URL>
cd belairs-buvette
```

Then open the folder in VS Code or any IDE of your choice.

### Mirror to GitHub

To properly use advanced AI features with Copilot, mirror this repository to your GitHub account:

```bash
git remote add github <the URL of your new GitHub repository>
git branch -M main
git push -u github main
```

### Build

```bash
./gradlew build
```

### Run the tests

```bash
./gradlew test
```

## Next Steps

Start by following the formation material in the [academy](https://academy.exalt-company.com/paths/699c49f3a1dffef24c46c739/home).

Read [FEATURES.md](./FEATURES.md) for the list of user stories and acceptance criteria.

Happy coding!
