# Pratiques de Code

## Général
- Aucun commentaire ne doit être présent dans le code.

## Tests
- Créer des tests unitaires pour couvrir toutes les méthodes Java.
- Les classes et méthodes de test doivent être non publiques.
- Tous les champs doivent être testés dans les tests unitaires.
- Dans les tests de contrôleur, vérifiez la réponse et le type de contenu retournés par l'API.

## Entité Java
- Dans une Java Entity, ajouter une annotation `@Column` sur chaque champ. Les propriétés `name`, `nullable` et `columnDefinition` doivent être renseignés.

## Classes Java
- Utiliser des `record` pour les classes Java sauf s'il s'agit d'une entité.
- Les entités Java ne doivent pas être retournées directement comme réponses ou reçues directement dans les points de terminaison REST ; utilisez des DTO à la place.
- Les classes de repository et de service doivent pouvoir être annotées avec des annotations de stéréotype comme `@Service`, `@Component`, ou `@Repository`.
- Les annotations `@Autowired` sont interdites dans le code Java, sauf pour les tests.