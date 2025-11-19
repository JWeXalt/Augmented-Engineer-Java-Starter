# The Bel'Air's Buvette : building a backend for eXalt famous festival drinks and snacks bar. With Java, AI, and love.

Version française : [README_fr.md](README_fr.md)  
Version española : [README_es.md](README_es.md)

>  [!NOTE]
> This project is part of the eXalt IT augmented engineer learning path, located in its [academy](https://example.com).

Hello there and welcome to the Bel'Air's Buvette project repository ! 

This project is your playground to create a robust backend system for managing the drinks and snacks ! 

You will build the most fantastic backend using Java, the stack of your choice.

But more importantly, your new best friend : Github Copilot, your new rubber ducky / overenthusiastic intern pair programmer buddy ! 


## Getting Started

First thing first, fork this repository to your own Gitlab account : 

![fork](./assets/fork.png)

>  [!WARNING]
> Only fork the main branch !

Then clone it to your local machine using IntelliJ (or your terminal if you want to feel like a hacker) :

### IntelliJ

Get the URL of your forked repository from Gitlab :

![clone](assets/clone.png)

Then in IntelliJ, go to `New`, `Project from version control`, click and paste the URL you just copied into the from.

![new_project.png](assets/new_project.png)

### Terminal

```bash
git clone <YOUR_FORK git url>
cd belairs-buvette
```
Then open the folder in Intellij (`New` -> `Project from existing sources`).

### Mirror to GitHub

To be able to properly use the more advanced AI features using Copilot, mirror this repository to your GitHub account as well.

First create a new empty repository on GitHub called `belairs-buvette`.

Then add the GitHub remote to your local git configuration :

```bash
git remote add github  <the URL of your new GitHub repository>
git branch -M main
git push -u github main
```

You are all set ! 

To build the project, you can use the Gradle wrapper included in the project.

```bash
./gradlew build
```

To test the project, you can use the following command :

```bash
./gradlew test
```

Happy coding !
