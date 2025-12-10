# La Buvette de Bel'Air : construire un backend pour la célèbre buvette du festival eXalt. Avec Java, IA, et amour.

Version anglaise : [README.md](README.md)  
Version española : [README_es.md](README_es.md)

>[!note]
> 
> Ce projet fait partie du parcours d'apprentissage eXalt IT augmented engineer, disponible dans son [academy](https://example.com).

Bonjour et bienvenue dans le dépôt du projet La Buvette de Bel'Air !

Ce projet est votre terrain de jeu pour créer un backend robuste de gestion des boissons et snacks !

Vous allez construire le meilleur backend possible en utilisant Java.

Mais plus important encore, votre nouveau meilleur ami : GitHub Copilot, votre canard en caoutchouc / stagiaire trop enthousiaste pour le pair programming !


## Démarrage

D'abord, forkez ce dépôt sur votre propre compte Gitlab : 

![fork](./assets/fork.png)

>[!warning]
> 
> Ne forkez que la branche `main` !

Puis clonez-le sur votre machine locale en utilisant IntelliJ (ou votre terminal si vous voulez vous sentir hacker) :

### IntelliJ

Récupérez l'URL de votre fork sur Gitlab :

![clone](assets/clone.png)

Ensuite dans IntelliJ, allez à `New`, `Project from version control`, cliquez et collez l'URL que vous venez de copier.

![new_project.png](assets/new_project.png)

### Terminal

```bash
git clone <YOUR_FORK git url>
cd belairs-buvette
```

Puis ouvrez le dossier dans IntelliJ (`New` -> `Project from existing sources`).

### Miroir vers GitHub

Pour pouvoir utiliser correctement les fonctionnalités IA avancées avec Copilot, miroir ce dépôt sur votre compte GitHub également.

Créez d'abord un nouveau dépôt vide sur GitHub nommé `belairs-buvette`.

Puis ajoutez la remote GitHub à votre configuration git locale :

```bash
git remote add github  <the URL of your new GitHub repository>
git branch -M main
git push -u github main
```

Vous êtes prêt·e !

Pour compiler le projet, vous pouvez utiliser le wrapper Gradle inclus dans le projet.

```bash
./gradlew build
```

Pour tester le projet :

```bash
./gradlew test
```

## Étapes suivantes

Maintenant que votre environnement de développement est prêt, il est temps de plonger dans le projet.

Commencez par suivre le reste du matériel de formation fourni dans l'[academy](https://example.com).

Vous pouvez aussi consulter le fichier [FEATURES_fr.md](./FEATURES_fr.md) pour avoir une idée des fonctionnalités à implémenter.

Bon codage !
