package pl.pjm77.weightliftinglog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void submitRegistrationPasswordNotValid() throws Exception {
        this.mockMvc.perform(post("/saveuser").with(csrf())
                .param("id", "0")
                .param("name", "passwordConstraintTestUser")
                .param("password", "invalid password")
                .param("confirmPassword", "invalid password")
                .param("email", "emai@email.com")
                .param("realEmail", "false")
                .param("firstName", "Firstname")
                .param("lastName", "Lastname")
                .param("age", "55")
                .param("gender", "true")
                .param("role", "USER"))
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("user", "password", "confirmPassword"))
        .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationSuccess() throws Exception {
        this.mockMvc.perform(post("/saveuser").with(csrf())
                .param("id", "0")
                .param("name", "passwordConstraintTestUser")
                .param("password", "password")
                .param("confirmPassword", "password")
                .param("email", "emai@email.com")
                .param("realEmail", "false")
                .param("firstName", "Firstname")
                .param("lastName", "Lastname")
                .param("age", "55")
                .param("gender", "false")
                .param("role", "USER"))
        .andExpect(model().hasNoErrors());
//        .andExpect(redirectedUrl("/registration?success"))
//        .andExpect(status().is3xxRedirection());
    }
}