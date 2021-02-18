package com.gubarev.movieland.web.controller

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.web.controller.GenreController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, com.gubarev.movieland.web.WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"])
class GenreControllerSystemTest {
    @Autowired
    private GenreController genreController

    @Autowired
    private WebApplicationContext context

    private MockMvc mockMvc

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    void "test get all genres from data base"() {
        //then
        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.size()').value(4))
                .andExpect(jsonPath('$[0].id').value(1))
                .andExpect(jsonPath('$[0].genre').value('драма'))

                .andExpect(jsonPath('$[1].id').value(2))
                .andExpect(jsonPath('$[1].genre').value('комедия'))

                .andExpect(jsonPath('$[2].id').value(3))
                .andExpect(jsonPath('$[2].genre').value('боевик'))

                .andExpect(jsonPath('$[3].id').value(4))
                .andExpect(jsonPath('$[3].genre').value('аниме'))
    }
}
