## Tests unitaires Controller
- Le request body sera valorisé dans un fichier json (Exemple : /json/create-order-request.json)
- Le response body sera valorisé dans un fichier json (Exemple : /json/create-order-response.json)

Exemple : `OrderControllerTest`

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

Exemple : `/json/create-order-request.json`
```
{
  "cart": {
    "items": [],
    "userId": "user123"
  }
}
```

Exemple : `/json/create-order-response.json`
```
{
  "invoiceNumber": 123456,
  "field2": "data2",
  "field3": "data3"
}
```