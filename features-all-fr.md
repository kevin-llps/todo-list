## Fonctionnalités

### Description
Ces fonctionnalités permettent aux utilisateurs de gérer les éléments Todo dans la liste des Todos.

### Endpoints

- `POST /todos` : Créer un nouvel élément Todo.
Si cela réussit, il doit retourner :
    - Statut HTTP 201
    - Nouveau Todo dans le corps de la réponse
Un élément Todo est créé selon plusieurs règles :
- Les champs obligatoires `title` et `completed` doivent être validés dans l'entrée de l'API.
- Si des champs obligatoires sont manquants, il doit retourner :
    - Statut HTTP 400
    - Un message d'erreur explicite dans le corps de la réponse

- `GET /todos` : Obtenir tous les éléments Todo.
Si cela réussit, il doit retourner :
    - Statut HTTP 200
    - Liste des éléments Todo dans le corps de la réponse : Les éléments Todo doivent être triés par les champs expiryDate, completed, title

- `GET /todos/{id}` : Obtenir un élément Todo par id.
Si cela réussit, il doit retourner :
    - Statut HTTP 200
    - Élément Todo dans le corps de la réponse
Si l'élément Todo n'existe pas, il doit retourner :
    - Statut HTTP 404
    - Un message d'erreur "L'élément Todo n'a pas été trouvé"

- `PATCH /todos/{id}` : Mettre à jour un élément Todo par id.
Si cela réussit, il doit retourner :
    - Statut HTTP 200
    - Élément Todo mis à jour dans le corps de la réponse

- `DELETE /todos/{id}` : Supprimer un élément Todo par id.
Si cela réussit, il doit retourner :
    - Statut HTTP 204
    - Pas de contenu dans le corps de la réponse

### Base de données
- La table `todo` sera utilisée pour stocker les éléments Todo.
