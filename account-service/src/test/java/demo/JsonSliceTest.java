package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class JsonSliceTest {
    @Autowired
    private JacksonTester<Account> json;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void serialize() throws IOException {
        JsonContent<Account> jsonContent = json.write(new Account(1L, "이름", "계좌번호"));
        assertThat(jsonContent).hasJsonPathStringValue("$.username", "이름");
        System.out.println(jsonContent.toString());
    }
}
