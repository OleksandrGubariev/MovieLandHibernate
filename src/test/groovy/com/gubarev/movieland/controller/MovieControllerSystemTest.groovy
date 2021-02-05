package com.gubarev.movieland.controller

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.web.controller.MovieController
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
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, com.gubarev.movieland.web.WebApplicationContext])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"])
class MovieControllerSystemTest {

    @Autowired
    private MovieController movieController

    @Autowired
    private WebApplicationContext context

    private MockMvc mockMvc

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    void "test get all movie from data base"() {
        //then
        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.size()').value(5))
                .andExpect(jsonPath('$[0].id').value(1))
                .andExpect(jsonPath('$[0].nameRussian').value('Начало'))
                .andExpect(jsonPath('$[0].nameNative').value('Inception'))
                .andExpect(jsonPath('$[0].year').value(2010))
                .andExpect(jsonPath('$[0].rating').value(8.6d))
                .andExpect(jsonPath('$[0].price').value(130.00d))

                .andExpect(jsonPath('$[1].id').value(2))
                .andExpect(jsonPath('$[1].nameRussian').value('Жизнь прекрасна'))
                .andExpect(jsonPath('$[1].nameNative').value('La vita è bella'))
                .andExpect(jsonPath('$[1].year').value(1997))
                .andExpect(jsonPath('$[1].rating').value(8.2d))
                .andExpect(jsonPath('$[1].price').value(145.99d))

                .andExpect(jsonPath('$[2].id').value(3))
                .andExpect(jsonPath('$[2].nameRussian').value('Форрест Гамп'))
                .andExpect(jsonPath('$[2].nameNative').value('Forrest Gump'))
                .andExpect(jsonPath('$[2].year').value(1994))
                .andExpect(jsonPath('$[2].rating').value(8.6d))
                .andExpect(jsonPath('$[2].price').value(200.60d))

                .andExpect(jsonPath('$[3].id').value(4))
                .andExpect(jsonPath('$[3].nameRussian').value('Список Шиндлера'))
                .andExpect(jsonPath('$[3].nameNative').value('Schindler\'\'s List'))
                .andExpect(jsonPath('$[3].year').value(1993))
                .andExpect(jsonPath('$[3].rating').value(8.7d))
                .andExpect(jsonPath('$[3].price').value(150.50d))

                .andExpect(jsonPath('$[4].id').value(5))
                .andExpect(jsonPath('$[4].nameRussian').value('1+1'))
                .andExpect(jsonPath('$[4].nameNative').value('Intouchables'))
                .andExpect(jsonPath('$[4].year').value(2011))
                .andExpect(jsonPath('$[4].rating').value(8.3d))
                .andExpect(jsonPath('$[4].price').value(120.00d))
    }


    @Test
    void "test get movies by genre"() {
        //then
        mockMvc.perform(get("/movies/genre/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.size()').value(2))
                .andExpect(jsonPath('$[0].id').value(1))
                .andExpect(jsonPath('$[0].nameRussian').value('Начало'))
                .andExpect(jsonPath('$[0].nameNative').value('Inception'))
                .andExpect(jsonPath('$[0].year').value(2010))
                .andExpect(jsonPath('$[0].rating').value(8.6d))
                .andExpect(jsonPath('$[0].price').value(130.00d))

                .andExpect(jsonPath('$[1].id').value(3))
                .andExpect(jsonPath('$[1].nameRussian').value('Форрест Гамп'))
                .andExpect(jsonPath('$[1].nameNative').value('Forrest Gump'))
                .andExpect(jsonPath('$[1].year').value(1994))
                .andExpect(jsonPath('$[1].rating').value(8.6d))
                .andExpect(jsonPath('$[1].price').value(200.60d))
    }

    @Test
    void "test get all movies with sort parameter desc for rating"() {
        //then
        this.mockMvc.perform(get("/movies?rating_parameter=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.size()').value(5))
                .andExpect(jsonPath('$[0].id').value(4))
                .andExpect(jsonPath('$[0].nameRussian').value('Список Шиндлера'))
                .andExpect(jsonPath('$[0].nameNative').value('Schindler\'\'s List'))
                .andExpect(jsonPath('$[0].year').value(1993))
                .andExpect(jsonPath('$[0].rating').value(8.7d))
                .andExpect(jsonPath('$[0].price').value(150.50d))

                .andExpect(jsonPath('$[1].id').value(1))
                .andExpect(jsonPath('$[1].nameRussian').value('Начало'))
                .andExpect(jsonPath('$[1].nameNative').value('Inception'))
                .andExpect(jsonPath('$[1].year').value(2010))
                .andExpect(jsonPath('$[1].rating').value(8.6d))
                .andExpect(jsonPath('$[1].price').value(130.00d))

                .andExpect(jsonPath('$[2].id').value(3))
                .andExpect(jsonPath('$[2].nameRussian').value('Форрест Гамп'))
                .andExpect(jsonPath('$[2].nameNative').value('Forrest Gump'))
                .andExpect(jsonPath('$[2].year').value(1994))
                .andExpect(jsonPath('$[2].rating').value(8.6d))
                .andExpect(jsonPath('$[2].price').value(200.60d))

                .andExpect(jsonPath('$[3].id').value(5))
                .andExpect(jsonPath('$[3].nameRussian').value('1+1'))
                .andExpect(jsonPath('$[3].nameNative').value('Intouchables'))
                .andExpect(jsonPath('$[3].year').value(2011))
                .andExpect(jsonPath('$[3].rating').value(8.3d))
                .andExpect(jsonPath('$[3].price').value(120.00d))

                .andExpect(jsonPath('$[4].id').value(2))
                .andExpect(jsonPath('$[4].nameRussian').value('Жизнь прекрасна'))
                .andExpect(jsonPath('$[4].nameNative').value('La vita è bella'))
                .andExpect(jsonPath('$[4].year').value(1997))
                .andExpect(jsonPath('$[4].rating').value(8.2d))
                .andExpect(jsonPath('$[4].price').value(145.99d))

    }

    @Test
    void "test get by genre with sort parameter acs for price"() {
        //then
        mockMvc.perform(get("/movies/genre/{id}?price_parameter=acs", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.size()').value(2))

                .andExpect(jsonPath('$[0].id').value(1))
                .andExpect(jsonPath('$[0].nameRussian').value('Начало'))
                .andExpect(jsonPath('$[0].nameNative').value('Inception'))
                .andExpect(jsonPath('$[0].year').value(2010))
                .andExpect(jsonPath('$[0].rating').value(8.6d))
                .andExpect(jsonPath('$[0].price').value(130.00d))

                .andExpect(jsonPath('$[1].id').value(3))
                .andExpect(jsonPath('$[1].nameRussian').value('Форрест Гамп'))
                .andExpect(jsonPath('$[1].nameNative').value('Forrest Gump'))
                .andExpect(jsonPath('$[1].year').value(1994))
                .andExpect(jsonPath('$[1].rating').value(8.6d))
                .andExpect(jsonPath('$[1].price').value(200.60d))
    }
}

