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

### Architecture

1. Controller : Endpoint `POST /todos`
2. Service : Création du Todo (avec mapping de tous les champs)
3. Repository : Enregistrement en base

### Base de données
- La table `todo` sera utilisée pour stocker les éléments Todo.
