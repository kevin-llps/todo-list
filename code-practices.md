# Code Practices

- Create unit tests to cover all java methods.
- No comments should be present in the code.
- Repository and service classes must be able to be annotated with stereotype annotations like `@Service`, `@Component`, or `@Repository`.
- `@Autowired` annotations are prohibited in Java code, except for tests.
- Java entities should not be returned directly as responses or received directly in REST endpoints; use DTOs instead.
- All fields must be tested in unit tests.
- In controller tests, verify the response and content type returned by the API.
- Test classes and methods should be non-public.
