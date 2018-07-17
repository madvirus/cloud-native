package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerRestController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class CustomerRestControllerDocumentation {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository repo;

    @Test
    public void apiDoc() throws Exception {
        given(repo.findAll()).willReturn(Arrays.asList(
                new Customer(1L, "이름", "성")
        ));
        mvc.perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andDo(document("customers",
                        responseFields(
                                fieldWithPath("[]").description("고객 목록"),
                                fieldWithPath("[].id").description("ID"),
                                fieldWithPath("[].firstName").description("이름"),
                                fieldWithPath("[].lastName").description("성"))));
    }
}
