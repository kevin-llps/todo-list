# Pratiques de Code

Les règles suivantes doivent être appliquées dans le code.

## Général
- Aucun commentaire ne doit être présent dans le code.

## Classes Java
- Utiliser des `record` pour les classes Java sauf s'il s'agit d'une entité.
- Les entités Java ne doivent pas être retournées directement comme réponses ou reçues directement dans les points de terminaison REST ; utilisez des DTO à la place.
- Les classes de repository et de service doivent pouvoir être annotées avec des annotations de stéréotype comme `@Service`, `@Component`, ou `@Repository`.
- Les annotations `@Autowired` sont interdites dans le code Java, sauf pour les tests.

Exemple : 

```
@Service
public class OrderService {
    private final TransactionService transactionService;
    private final InvoiceService invoiceService
    
    public OrderService(PurchaseTransactionService purchaseTransactionService,
                       InvoiceService invoiceService) {
        this.transactionService = transactionService;
        this.invoiceService = invoiceService;
    }
    
    public Invoice validateOrder(Cart cart) {
        PurchaseTransactionDetails purchaseTransactionDetails = purchaseTransactionService.validate(cart);
        
        return invoiceService.create(purchaseTransactionDetails);
    }
}
```

## Entité Java
- Dans une Java Entity, ajouter une annotation `@Column` sur chaque champ. Les propriétés `name`, `nullable` et `columnDefinition` doivent être renseignés.

## Tests unitaires
- Créer des tests unitaires pour couvrir toutes les méthodes Java.
- Les classes et méthodes de test doivent être non publiques.
- Tous les champs doivent être testés dans les tests unitaires.
- Dans les tests de contrôleur, vérifiez la réponse et le type de contenu retournés par l'API.

Exemple pour un test unitaire :

```
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    
    @Mock
    private TransactionService transactionService;
    
    @Mock
    private InvoiceService invoiceService;
    
    @Test 
    void shouldValidateOrder() {
        Cart cart = new Cart();
        PurchaseTransactionDetails purchaseTransactionDetails = new PurchaseTransactionDetails("data1", "data2", "data3");
        Invoice expected = new Invoice(123456, purchaseTransactionDetails);
        
        when(purchaseTransactionService.validate(cart)).thenReturn(purchaseTransactionDetails);
        when(invoiceService.create(purchaseTransactionDetails)).thenReturn(expected);
        
        Invoice actual = orderService.validateOrder(cart);
        
        assertThat(actual).isEqualTo(expected);
        
        verify(purchaseTransactionService).validate(cart);
        verify(invoiceService).create(purchaseTransactionDetails);
        
        verifyNoMoreInteractions(transactionService, invoiceService);
    }
    
}
```

Exemple pour un test unitaire de contrôleur :

```
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;
    
    @MockitoBean
    private InvoiceMapper invoiceMapper;

    @Value("classpath:/json/create-order-request.json")
    private Resource createOrderRequest;
    
    @Value("classpath:/json/create-order-response.json")
    private Resource createOrderResponse;

    @Test
    void shouldCreateOrder() throws Exception {
        InvoiceDto invoiceDto = new Invoice(123456, "data2", "data3");
        PurchaseTransactionDetails purchaseTransactionDetails = new PurchaseTransactionDetails("data1", "data2", "data3");
        Invoice invoice = new Invoice(123456, purchaseTransactionDetails);
        Cart cart = new Cart();

        when(orderService.validateOrder(cart)).thenReturn(invoice);
        when(invoiceMapper.mapToDto(invoice)).thenReturn(invoiceDto);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderRequest.getContentAsString(Charset.defaultCharset())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createOrderResponse.getContentAsString(Charset.defaultCharset())));

        verify(orderService).validateOrder(cart);
        verify(invoiceMapper).mapToDto(invoice);
        verifyNoMoreInteractions(orderService, invoiceMapper);
    }
```

