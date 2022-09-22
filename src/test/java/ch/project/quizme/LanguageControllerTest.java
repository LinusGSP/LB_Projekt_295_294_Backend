package ch.project.quizme;

import ch.project.quizme.controller.LanguageController;
import ch.project.quizme.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = LanguageController.class)
class LanguageControllerTest {
    @Autowired private LanguageController languageController;
    @MockBean private LanguageRepository languageRepository;
    @Autowired private MockMvc mockMvc;

    /**
     * Class under test: {@link LanguageController}
     */
    @Test
    public void CheckLanguageControllerInject_NotNull() throws Exception {
        assertThat(languageController).isNotNull();
    }

    /**
     * Method under test: {@link LanguageController#getAllLanguages()}
     */
    @Test
    public void CheckGetAllLanguages_isOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/language/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Method under test: {@link LanguageController#getAllLanguageById(Integer)}
     */
    @Test
    public void CheckGetOneLanguageById_isOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/language/{id}", "1"))
                .andDo(res -> System.out.println(res.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    /**
     * Method under test: {@link LanguageController#deleteLanguage(Integer)}
     */
    @Test
    public void CheckDeleteLanguageById_isOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/learnset/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

