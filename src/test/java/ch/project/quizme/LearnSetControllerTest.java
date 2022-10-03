package ch.project.quizme;

import ch.project.quizme.controller.LanguageController;
import ch.project.quizme.controller.LearnSetController;
import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.repository.LanguageRepository;
import ch.project.quizme.repository.LearnSetRepository;
import ch.project.quizme.repository.LearnWordRepository;
import org.hibernate.query.criteria.internal.expression.SimpleCaseExpression;
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

/**This class is for Junit testing the learnSetController class
 *
 * @author Linus
 * @version 1.0
 * @since 2022-10-03
 */
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

        when(learnSetRepository.findById(anyInt())).thenReturn(Optional.of(new LearnSet()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/learnset/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


}

