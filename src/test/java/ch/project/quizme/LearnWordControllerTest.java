package ch.project.quizme;

import ch.project.quizme.controller.LearnWordController;
import ch.project.quizme.databases.LearnSet;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**This class is for Junit testing the LearnWordController class
 *
 * @author Linus
 * @version 1.0
 * @since 2022-10-03
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = LearnWordController.class)
public class LearnWordControllerTest {
    @MockBean private LearnWordRepository learnWordRepository;
    @MockBean private LanguageRepository languageRepository;
    @MockBean private LearnSetRepository learnSetRepository;

    @Autowired
    private LearnWordController learnWordController;
    @Autowired private MockMvc mockMvc;

    /**
     * Class under test: {@link LearnWordController}
     */
    @Test
    public void CheckLearnWordControllerInject_NotNull() throws Exception {
        assertThat(learnWordController).isNotNull();
    }

    /**
     * Method under test: {@link LearnWordController#getAllWords()}
     */
    @Test
    public void CheckGetAllLearnWords_isOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/word/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Method under test: {@link LearnWordController#getWord(Integer)}
     */
    @Test
    public void CheckGetLearnWordById_isOk() throws Exception{
        when(learnWordRepository.findById(anyInt())).thenReturn(Optional.of(new LearnWord()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/word/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Method under test: {@link LearnWordController#getWord(Integer)}
     */
    @Test
    public void CheckGetLearnSetWordsByLearnSetId_isOk() throws Exception{
        when(learnSetRepository.findById(anyInt())).thenReturn(Optional.of(new LearnSet()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/word/set/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Method under test: {@link LearnWordController#getWord(Integer)}
     */
    @Test
    public void CheckDeleteLearnWordById_isOk() throws Exception{
        when(learnWordRepository.findById(anyInt())).thenReturn(Optional.of(new LearnWord()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/word/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
