# La Buvette de Bel'Air : construyendo un backend para la famosa buvette del festival eXalt. Con Java, IA y amor.

Versión inglesa : [README.md](README.md)  
Versión francesa : [README_fr.md](README_fr.md)

>[!note]
> 
> Este proyecto forma parte del camino de aprendizaje eXalt IT augmented engineer, ubicado en su [academy](https://example.com).

¡Hola y bienvenido al repositorio del proyecto La Buvette de Bel'Air!

Este proyecto es tu terreno de juego para crear un backend robusto de gestión de bebidas y snacks !

Vas a construir el mejor backend posible usando Java.

Pero, lo más importante, tu nuevo mejor amigo: GitHub Copilot, tu pato de goma / becario demasiado entusiasta para el pair programming !


## Dónde empezar

Primero haz un fork de este repositorio a tu propia cuenta de Gitlab :

![fork](./assets/fork.png)

>[!warning]
> 
> ¡Solo hacer fork de la rama `main` !

Luego clónalo en tu máquina local usando IntelliJ (o tu terminal si quieres sentirte hacker) :

### IntelliJ

Obtén la URL de tu fork desde Gitlab :

![clone](assets/clone.png)

Luego en IntelliJ, ve a `New`, `Project from version control`, haz clic y pega la URL que acabas de copiar.

![new_project.png](assets/new_project.png)

### Terminal

```bash
git clone <YOUR_FORK git url>
cd belairs-buvette
```

Luego abre la carpeta en IntelliJ (`New` -> `Project from existing sources`).

### Espejar en GitHub

Para poder usar correctamente las funcionalidades IA avanzadas con Copilot, espeja este repositorio en tu cuenta GitHub también.

Primero crea un nuevo repositorio vacío en GitHub llamado `belairs-buvette`.

Luego añade el remote GitHub a tu configuración git local :

```bash
git remote add github  <the URL of your new GitHub repository>
git branch -M main
git push -u github main
```

¡Estás listo!

Para compilar el proyecto, puedes usar el wrapper de Gradle incluido en el proyecto.

```bash
./gradlew build
```

Para ejecutar las pruebas del proyecto :

```bash
./gradlew test
```

## Próximos pasos

Ahora que tu entorno de desarrollo está listo, es hora de sumergirte en el proyecto.

Comienza siguiendo el resto del material de formación proporcionado en la [academy](https://example.com).

También puedes consultar el archivo [FEATURES_es.md](./FEATURES_es.md) para hacerte una idea de las funcionalidades que implementarás.

¡Feliz programación!
