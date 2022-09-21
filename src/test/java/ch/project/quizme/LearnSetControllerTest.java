package ch.project.quizme;

import ch.project.quizme.controller.LanguageController;
import ch.project.quizme.controller.LearnSetController;
import ch.project.quizme.databases.LearnWord;
import ch.project.quizme.repository.LanguageRepository;
import ch.project.quizme.repository.LearnSetRepository;
import ch.project.quizme.repository.LearnWordRepository;
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
@ContextConfiguration(classes = LearnSetController.class)
public class LearnSetControllerTest {
    @MockBean private LearnWordRepository learnWordRepository;
    @MockBean private LanguageRepository languageRepository;
    @MockBean private LearnSetRepository learnSetRepository;
    @MockBean private LanguageController languageController;
    @Autowired private LearnSetController learnSetController;
    @Autowired private MockMvc mockMvc;

    /**
     * Class under test: {@link LearnSetController}
     */
    @Test
    public void CheckLearnSetControllerInject_NotNull() throws Exception {
        assertThat(learnSetController).isNotNull();
    }

    /**
     * Method under test: {@link LearnSetController#getAllLearnSets()}
     */
    @Test
    public void CheckGetAllLearnSets_isOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/learnset/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Method under test: {@link LearnSetController#getLearnSetById(Integer)}
     */
    @Test
    public void CheckGetLearnSetById_isOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/learnset/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Method under test: {@link LearnSetController#createLearnSet(String, Integer, Integer)}
     */
    @Test
    public void CheckCreateLearnSetWithCorrectInput_isOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/learnset")
                        .param("name", "foo")
                        .param("language1_id" , "1")
                        .param("language2_id", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link LearnSetController#createLearnSet(String, Integer, Integer)}
     */
    @Test
    public void CheckCreateLearnSetWithBadInput_isOk() throws Exception{
        // same language twice is not allowed
        mockMvc.perform(MockMvcRequestBuilders.post("/api/learnset")
                        .param("name", "foo")
                        .param("language1_id" , "1")
                        .param("language2_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/learnset")
                        .param("name", "foo")
                        .param("language1_id" , "1")
                        .param("language2_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

