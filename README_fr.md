# La Buvette de Bel'Air : construire un backend pour le bar de boissons et snacks du célèbre festival eXalt. Avec du Java, de l'IA et de l'amour.

Version originale : [README.md](README.md)

>  [!note]
> 
> Ce projet fait partie du parcours d'apprentissage d'ingénieur augmenté eXalt IT, disponible dans son [academie](https://example.com).

Bonjour et bienvenue dans le dépôt du projet La Buvette de Bel'Air !

Ce projet est votre terrain de jeu pour créer un système backend robuste pour gérer les boissons et les snacks !

Vous allez construire le backend le plus fantastique en utilisant Java, la stack de votre choix.

Mais surtout, votre nouveau meilleur ami : GitHub Copilot, votre nouveau canard en caoutchouc / copain-stagiaire survitaminé de pair programming !


## Pour commencer

D'abord, forkez ce dépôt vers votre propre compte Gitlab :

![fork](./assets/fork.png)

>  [!warning]
> 
> Ne forkez que la branche main !

Ensuite clonez-le sur votre machine locale en utilisant IntelliJ (ou votre terminal si vous sentez l'âme d'un hacker) :

### IntelliJ

Récupérez l'URL de votre dépôt forké depuis Gitlab :

![clone](assets/clone.png)

Puis dans IntelliJ, allez dans `New`, `Project from version control`, cliquez et collez l'URL que vous venez de copier dans le champ prévu à cet effet.

![new_project.png](assets/new_project.png)

### Terminal

```bash
git clone <URL_DE_VOTRE_FORK_git>
cd belairs-buvette
```

Puis ouvrez le dossier dans IntelliJ (`New` -> `Project from existing sources`).

### Miroir vers GitHub

Pour pouvoir utiliser correctement les fonctionnalités IA avancées avec Copilot, miroirisez aussi ce dépôt sur votre compte GitHub.

Créez d'abord un nouveau dépôt vide sur GitHub appelé `belairs-buvette`.

Puis ajoutez la remote GitHub à votre configuration git locale :

```bash
git remote add github  <l'URL_de_votre_nouveau_dépôt_GitHub>
git branch -M main
git push -u github main
```

Vous êtes prêt !

Pour construire le projet, vous pouvez utiliser le wrapper Gradle inclus dans le projet.

```bash
./gradlew build
```

Pour tester le projet, vous pouvez utiliser la commande suivante :

```bash
./gradlew test
```

Bon codage !

