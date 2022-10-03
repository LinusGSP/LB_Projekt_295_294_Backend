package ch.project.quizme;

import ch.project.quizme.controller.LanguageController;
import ch.project.quizme.controller.LearnWordController;
import ch.project.quizme.databases.Language;
import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.databases.LearnWord;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**This class is for Junit testing the languageController class
 *
 * @author Linus
 * @version 1.0
 * @since 2022-10-03
 */
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
        when(languageRepository.findById(anyInt())).thenReturn(Optional.of(new Language()));

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
        when(languageRepository.findById(anyInt())).thenReturn(Optional.of(new Language()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/language/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

