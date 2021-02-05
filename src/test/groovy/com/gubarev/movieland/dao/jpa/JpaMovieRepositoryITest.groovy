package com.gubarev.movieland.dao.jpa

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.MovieRepository
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.entity.Movie
import com.gubarev.movieland.common.MovieRequest
import com.gubarev.movieland.entity.Poster
import com.gubarev.movieland.web.WebApplicationContext
import com.vladmihalcea.sql.SQLStatementCountValidator
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig

@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"])
class JpaMovieRepositoryITest {
    @Autowired
    private MovieRepository movieRepository

    @Test
    void "test get all movies"() {
        //prepare
        def expectedMovies = [
                Movie.builder()
                        .id(1)
                        .nameRussian("Начало")
                        .nameNative("Inception")
                        .description("Кобб — талантливый вор.")
                        .year(2010)
                        .rating(8.6)
                        .price(130.00)
                        .posters([
                                Poster.builder()
                                        .id(1)
                                        .link("http1").build(),
                                Poster.builder()
                                        .id(2)
                                        .link("http2").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(2)
                        .nameRussian("Жизнь прекрасна")
                        .nameNative("La vita è bella")
                        .description("Во время II Мировой войны в Италии в концлагерь были отправлены евреи")
                        .year(1997)
                        .rating(8.2)
                        .price(145.99)
                        .posters([
                                Poster.builder()
                                        .id(3)
                                        .link("http3").build(),
                                Poster.builder()
                                        .id(4)
                                        .link("http4").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(3)
                        .nameRussian("Форрест Гамп")
                        .nameNative("Forrest Gump")
                        .description("От лица главного героя Форреста Гампа")
                        .year(1994)
                        .rating(8.6)
                        .price(200.60)
                        .posters([
                                Poster.builder()
                                        .id(5)
                                        .link("http5").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(4)
                        .nameRussian("Список Шиндлера")
                        .nameNative("Schindler''s List")
                        .description("Фильм рассказывает реальную историю загадочного Оскара Шиндлер")
                        .year(1993)
                        .rating(8.7)
                        .price(150.50)
                        .posters([
                                Poster.builder()
                                        .id(6)
                                        .link("http6").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(5)
                        .nameRussian("1+1")
                        .nameNative("Intouchables")
                        .description("Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека")
                        .year(2011)
                        .rating(8.3)
                        .price(120.00)
                        .posters([
                                Poster.builder()
                                        .id(7)
                                        .link("http7").build()
                        ]
                        ).build()
        ]
        def sortParameter = new MovieRequest()

        //when
        def actualMovies = movieRepository.findAll(sortParameter)

        //then
        assert actualMovies.size() == 5

        for (movie in actualMovies) {
            expectedMovies.removeIf(x -> x == movie)
        }

        assert expectedMovies.size() == 0
    }

    @Test
    void "test get random movies"() {

        //prepare
        def expectedMovies = [
                Movie.builder()
                        .id(1)
                        .nameRussian("Начало")
                        .nameNative("Inception")
                        .description("Кобб — талантливый вор.")
                        .year(2010)
                        .rating(8.6)
                        .price(130.00)
                        .posters([
                                Poster.builder()
                                        .id(1)
                                        .link("http1").build(),
                                Poster.builder()
                                        .id(2)
                                        .link("http2").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(2)
                        .nameRussian("Жизнь прекрасна")
                        .nameNative("La vita è bella")
                        .description("Во время II Мировой войны в Италии в концлагерь были отправлены евреи")
                        .year(1997)
                        .rating(8.2)
                        .price(145.99)
                        .posters([
                                Poster.builder()
                                        .id(3)
                                        .link("http3").build(),
                                Poster.builder()
                                        .id(4)
                                        .link("http4").build()

                        ]
                        ).build(),

                Movie.builder()
                        .id(3)
                        .nameRussian("Форрест Гамп")
                        .nameNative("Forrest Gump")
                        .description("От лица главного героя Форреста Гампа")
                        .year(1994)
                        .rating(8.6)
                        .price(200.60)
                        .posters([
                                Poster.builder()
                                        .id(5)
                                        .link("http5").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(4)
                        .nameRussian("Список Шиндлера")
                        .nameNative("Schindler''s List")
                        .description("Фильм рассказывает реальную историю загадочного Оскара Шиндлер")
                        .year(1993)
                        .rating(8.7)
                        .price(150.50)
                        .posters([
                                Poster.builder()
                                        .id(6)
                                        .link("http6").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(5)
                        .nameRussian("1+1")
                        .nameNative("Intouchables")
                        .description("Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека")
                        .year(2011)
                        .rating(8.3)
                        .price(120.00)
                        .posters([
                                Poster.builder()
                                        .id(7)
                                        .link("http7").build()
                        ]
                        ).build()
        ]

        //when
        def actualMovies = movieRepository.findRandom()

        //then
        assert actualMovies.size() == 3
        for (movie in actualMovies) {
            movie.posters.sort({ it.id })
            expectedMovies.removeIf(x -> x == movie)
        }

        assert expectedMovies.size() == 2
    }

    @Test
    void "test get movies by genre"() {

        //prepare
        def expectedMovies = [
                Movie.builder()
                        .id(1)
                        .nameRussian("Начало")
                        .nameNative("Inception")
                        .description("Кобб — талантливый вор.")
                        .year(2010)
                        .rating(8.6)
                        .price(130.00)
                        .posters([
                                Poster.builder()
                                        .id(1)
                                        .link("http1").build(),
                                Poster.builder()
                                        .id(2)
                                        .link("http2").build()
                        ]
                        ).build(),
                Movie.builder()
                        .id(3)
                        .nameRussian("Форрест Гамп")
                        .nameNative("Forrest Gump")
                        .description("От лица главного героя Форреста Гампа")
                        .year(1994)
                        .rating(8.6)
                        .price(200.60)
                        .posters([
                                Poster.builder()
                                        .id(5)
                                        .link("http5").build()
                        ]
                        ).build()
        ]
        def sortParameter = new MovieRequest()

        //when
        def actualMovies = movieRepository.findByGenre(1, sortParameter)

        //then
        assert actualMovies.size() == 2

        for (movie in actualMovies) {
            expectedMovies.removeIf(x -> x == movie)
        }

        assert expectedMovies.size() == 0
    }

    @Test
    void "test get all movies count select"() {
        //prepare
        def expectedMovies = [
                Movie.builder()
                        .id(1)
                        .nameRussian("Начало")
                        .nameNative("Inception")
                        .description("Кобб — талантливый вор.")
                        .year(2010)
                        .rating(8.6)
                        .price(130.00)
                        .posters([
                                Poster.builder()
                                        .id(1)
                                        .link("http1").build(),
                                Poster.builder()
                                        .id(2)
                                        .link("http2").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(2)
                        .nameRussian("Жизнь прекрасна")
                        .nameNative("La vita è bella")
                        .description("Во время II Мировой войны в Италии в концлагерь были отправлены евреи")
                        .year(1997)
                        .rating(8.2)
                        .price(145.99)
                        .posters([
                                Poster.builder()
                                        .id(3)
                                        .link("http3").build(),
                                Poster.builder()
                                        .id(4)
                                        .link("http4").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(3)
                        .nameRussian("Форрест Гамп")
                        .nameNative("Forrest Gump")
                        .description("От лица главного героя Форреста Гампа")
                        .year(1994)
                        .rating(8.6)
                        .price(200.60)
                        .posters([
                                Poster.builder()
                                        .id(5)
                                        .link("http5").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(4)
                        .nameRussian("Список Шиндлера")
                        .nameNative("Schindler''s List")
                        .description("Фильм рассказывает реальную историю загадочного Оскара Шиндлер")
                        .year(1993)
                        .rating(8.7)
                        .price(150.50)
                        .posters([
                                Poster.builder()
                                        .id(6)
                                        .link("http6").build()
                        ]
                        ).build(),

                Movie.builder()
                        .id(5)
                        .nameRussian("1+1")
                        .nameNative("Intouchables")
                        .description("Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека")
                        .year(2011)
                        .rating(8.3)
                        .price(120.00)
                        .posters([
                                Poster.builder()
                                        .id(7)
                                        .link("http7").build()
                        ]
                        ).build()
        ]
        def sortParameter = new MovieRequest()
        SQLStatementCountValidator.reset()
        //when
        def actualMovies = movieRepository.findAll(sortParameter)

        //then
        assert actualMovies.size() == 5
        for (movie in actualMovies) {
            expectedMovies.removeIf(x -> x == movie)
        }

        assert expectedMovies.size() == 0
        SQLStatementCountValidator.assertSelectCount(1)
    }

}
