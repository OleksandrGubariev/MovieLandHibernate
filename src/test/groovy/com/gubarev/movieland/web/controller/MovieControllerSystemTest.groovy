package com.gubarev.movieland.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.common.request.AddMovieRequest
import com.gubarev.movieland.common.request.EditMovieRequest
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.entity.Genre
import com.gubarev.movieland.entity.Poster
import com.gubarev.movieland.service.security.filter.TokenAuthenticateFilter
import com.gubarev.movieland.service.security.impl.JwtTokenService
import com.gubarev.movieland.web.controller.MovieController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, com.gubarev.movieland.web.WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"],
        executeStatementsBefore = "SELECT setval('movie_id_seq', 5);SELECT setval('poster_id_seq', 7);")
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

    @Test
    void "get movie by id"() {
        //then
        mockMvc.perform(get("/movie/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.id').value(1))
                .andExpect(jsonPath('$.nameRussian').value('Начало'))
                .andExpect(jsonPath('$.nameNative').value('Inception'))
                .andExpect(jsonPath('$.year').value(2010))
                .andExpect(jsonPath('$.description').value("Кобб — талантливый вор."))
                .andExpect(jsonPath('$.rating').value(8.6d))
                .andExpect(jsonPath('$.price').value(130.00d))
                .andExpect(jsonPath('$.posters.size()').value(2))
                .andExpect(jsonPath('$.posters[1].id').value(1))
                .andExpect(jsonPath('$.posters[1].link').value('http1'))
                .andExpect(jsonPath('$.posters[0].id').value(2))
                .andExpect(jsonPath('$.posters[0].link').value('http2'))
                .andExpect(jsonPath('$.genres.size()').value(2))
                .andExpect(jsonPath('$.genres[0].id').value(1))
                .andExpect(jsonPath('$.genres[0].genre').value('драма'))
                .andExpect(jsonPath('$.genres[1].id').value(2))
                .andExpect(jsonPath('$.genres[1].genre').value('комедия'))
                .andExpect(jsonPath('$.countries.size()').value(2))
                .andExpect(jsonPath('$.countries[1].id').value(1))
                .andExpect(jsonPath('$.countries[1].country').value('США'))
                .andExpect(jsonPath('$.countries[0].id').value(2))
                .andExpect(jsonPath('$.countries[0].country').value('Великобритания'))
                .andExpect(jsonPath('$.reviews.size()').value(2))
                .andExpect(jsonPath('$.reviews[0].id').value(1))
                .andExpect(jsonPath('$.reviews[0].comment').value('Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.'))
                .andExpect(jsonPath('$.reviews[1].id').value(2))
                .andExpect(jsonPath('$.reviews[1].comment').value('Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.'))
                .andExpect(jsonPath('$.reviews[0].user.id').value(2))
                .andExpect(jsonPath('$.reviews[0].user.name').value('Дарлин Эдвардс'))
                .andExpect(jsonPath('$.reviews[1].user.id').value(3))
                .andExpect(jsonPath('$.reviews[1].user.name').value('Габриэль Джексон'))
    }

    @Test
    @ExpectedDataSet("add_movie.xml, add_movie_genre.xml, add_movie_country.xml, add_poster.xml")
    void "add movie"() {
        //prepare
        def movie = new AddMovieRequest()
        movie.setNameRussian("Бойцовский клуб")
        movie.setNameNative("Fight Club")
        movie.setDescription("Терзаемый хронической бессонницей")
        movie.setYear(1999)
        movie.setPrice(119.99)

        Set<String> posters = new LinkedHashSet<>()
        posters.add("http8")
        posters.add("http9")
        movie.setPosters(posters)

        Set<Long> genres = new LinkedHashSet<>()
        genres.add(1L)
        genres.add(2L)
        movie.setGenres(genres)

        Set<Long> countries = new LinkedHashSet<>()
        countries.add(1L)
        countries.add(2L)
        movie.setCountries(countries)

        ObjectMapper mapper = new ObjectMapper()
        String json = mapper.writeValueAsString(movie)

        mockMvc.perform(post("/movie")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
    }

    @Test
    @ExpectedDataSet("edit_movie.xml, edit_movie_genre.xml, edit_movie_country.xml, edit_poster.xml")
    void "edit movie"() {
        //prepare
        def movie = new EditMovieRequest()
        movie.setNameRussian("Новое Имя")
        movie.setNameNative("New name")

        Set<String> posters = new LinkedHashSet<>()
        posters.add("http3")
        posters.add("http4")
        movie.setPosters(posters)

        Set<Long> genres = new LinkedHashSet<>()
        genres.add(1L)
        genres.add(3L)
        movie.setGenres(genres)

        Set<Long> countries = new LinkedHashSet<>()
        countries.add(1L)
        countries.add(3L)
        movie.setCountries(countries)

        ObjectMapper mapper = new ObjectMapper()
        String json = mapper.writeValueAsString(movie)

        mockMvc.perform(put("/movie/1")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
    }
}

