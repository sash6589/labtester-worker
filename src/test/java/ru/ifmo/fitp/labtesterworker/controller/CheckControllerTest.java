package ru.ifmo.fitp.labtesterworker.controller;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.ifmo.fitp.labtesterworker.LabtesterWorkerMain;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabtesterWorkerMain.class)
@WebAppConfiguration
public class CheckControllerTest {

//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));
//
//    private MockMvc mockMvc;
//
//    private HttpMessageConverter mappingJackson2HttpMessageConverter;
//
//    @Mock
//    private SubmitService submitServiceMock;
//
//    @InjectMocks
//    private SubmitEndpoint submitEndpoint;
//
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Autowired
//    void setConverters(HttpMessageConverter<?>[] converters) {
//
//        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
//                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
//                .findAny()
//                .orElse(null);
//
//        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
//    }
//
//    @Before
//    public void setup() {
//        initMocks(this);
//
//        this.mockMvc = standaloneSetup(submitEndpoint).build();
//    }
//
//    @Test
//    public void submitCode() throws Exception {
//
//        when(submitServiceMock.process(any(SourceCode.class))).thenReturn(new SubmitReport("5"));
//
//        mockMvc.perform(post("/submit-code")
//                .contentType(contentType)
//                .content(json(new SourceCode(1, "print 5"))))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$.report", is("5")));
//
//        verify(submitServiceMock, times(1)).process(any(SourceCode.class));
//    }
//
//    @SuppressWarnings("unchecked")
//    private String json(Object o) throws IOException {
//        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
//        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
//
//        return mockHttpOutputMessage.getBodyAsString();
//    }
}
