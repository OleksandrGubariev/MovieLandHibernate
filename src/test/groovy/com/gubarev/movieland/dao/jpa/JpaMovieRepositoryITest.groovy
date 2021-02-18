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
    void "test get all movies count select"() {
        //prepare
        def movie1 = new Movie()
        movie1.setId(1)
        movie1.setNameRussian("Начало")
        movie1.setNameNative("Inception")
        movie1.setDescription("Кобб — талантливый вор.")
        movie1.setYear(2010)
        movie1.setRating(8.6)
        movie1.setPrice(130.00)
        def poster1 = new Poster(1, "http1")
        def poster2 = new Poster(2, "http2")
        List<Poster> posters1 = new ArrayList<>()
        posters1.add(poster1)
        posters1.add(poster2)
        movie1.setPosters(posters1)

        def movie2 = new Movie()
        movie2.setId(2)
        movie2.setNameRussian("Жизнь прекрасна")
        movie2.setNameNative("La vita è bella")
        movie2.setDescription("Во время II Мировой войны в Италии в концлагерь были отправлены евреи")
        movie2.setYear(1997)
        movie2.setRating(8.2)
        movie2.setPrice(145.99)
        def poster3 = new Poster(3, "http3")
        def poster4 = new Poster(4, "http4")
        List<Poster> posters2 = new ArrayList<>()
        posters2.add(poster3)
        posters2.add(poster4)
        movie2.setPosters(posters2)

        def movie3 = new Movie()
        movie3.setId(3)
        movie3.setNameRussian("Форрест Гамп")
        movie3.setNameNative("Forrest Gump")
        movie3.setDescription("От лица главного героя Форреста Гампа")
        movie3.setYear(1994)
        movie3.setRating(8.6)
        movie3.setPrice(200.60)
        def poster5 = new Poster(5, "http5")
        List<Poster> posters3 = new ArrayList<>()
        posters3.add(poster5)
        movie3.setPosters(posters3)

        def movie4 = new Movie()
        movie4.setId(4)
        movie4.setNameRussian("Список Шиндлера")
        movie4.setNameNative("Schindler''s List")
        movie4.setDescription("Фильм рассказывает реальную историю загадочного Оскара Шиндлер")
        movie4.setYear(1993)
        movie4.setRating(8.7)
        movie4.setPrice(150.50)
        def poster6 = new Poster(6, "http6")
        List<Poster> posters4 = new ArrayList<>()
        posters4.add(poster6)
        movie4.setPosters(posters4)

        def movie5 = new Movie()
        movie5.setId(5)
        movie5.setNameRussian("1+1")
        movie5.setNameNative("Intouchables")
        movie5.setDescription("Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека")
        movie5.setYear(2011)
        movie5.setRating(8.3)
        movie5.setPrice(120.00)
        def poster7 = new Poster(7, "http7")
        List<Poster> posters5 = new ArrayList<>()
        posters5.add(poster7)
        movie5.setPosters(posters5)

        def expectedMovies = [movie1, movie2, movie3, movie4, movie5]
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

    @Test
    void "test get random movies"() {
        //prepare
        def movie1 = new Movie()
        movie1.setId(1)
        movie1.setNameRussian("Начало")
        movie1.setNameNative("Inception")
        movie1.setDescription("Кобб — талантливый вор.")
        movie1.setYear(2010)
        movie1.setRating(8.6)
        movie1.setPrice(130.00)
        def poster1 = new Poster(1, "http1")
        def poster2 = new Poster(2, "http2")
        List<Poster> posters1 = new ArrayList<>()
        posters1.add(poster1)
        posters1.add(poster2)
        movie1.setPosters(posters1)

        def movie2 = new Movie()
        movie2.setId(2)
        movie2.setNameRussian("Жизнь прекрасна")
        movie2.setNameNative("La vita è bella")
        movie2.setDescription("Во время II Мировой войны в Италии в концлагерь были отправлены евреи")
        movie2.setYear(1997)
        movie2.setRating(8.2)
        movie2.setPrice(145.99)
        def poster3 = new Poster(3, "http3")
        def poster4 = new Poster(4, "http4")
        List<Poster> posters2 = new ArrayList<>()
        posters2.add(poster3)
        posters2.add(poster4)
        movie2.setPosters(posters2)

        def movie3 = new Movie()
        movie3.setId(3)
        movie3.setNameRussian("Форрест Гамп")
        movie3.setNameNative("Forrest Gump")
        movie3.setDescription("От лица главного героя Форреста Гампа")
        movie3.setYear(1994)
        movie3.setRating(8.6)
        movie3.setPrice(200.60)
        def poster5 = new Poster(5, "http5")
        List<Poster> posters3 = new ArrayList<>()
        posters3.add(poster5)
        movie3.setPosters(posters3)

        def movie4 = new Movie()
        movie4.setId(4)
        movie4.setNameRussian("Список Шиндлера")
        movie4.setNameNative("Schindler''s List")
        movie4.setDescription("Фильм рассказывает реальную историю загадочного Оскара Шиндлер")
        movie4.setYear(1993)
        movie4.setRating(8.7)
        movie4.setPrice(150.50)
        def poster6 = new Poster(6, "http6")
        List<Poster> posters4 = new ArrayList<>()
        posters4.add(poster6)
        movie4.setPosters(posters4)

        def movie5 = new Movie()
        movie5.setId(5)
        movie5.setNameRussian("1+1")
        movie5.setNameNative("Intouchables")
        movie5.setDescription("Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека")
        movie5.setYear(2011)
        movie5.setRating(8.3)
        movie5.setPrice(120.00)
        def poster7 = new Poster(7, "http7")
        List<Poster> posters5 = new ArrayList<>()
        posters5.add(poster7)
        movie5.setPosters(posters5)

        def expectedMovies = [movie1, movie2, movie3, movie4, movie5]

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
        def movie1 = new Movie()
        movie1.setId(1)
        movie1.setNameRussian("Начало")
        movie1.setNameNative("Inception")
        movie1.setDescription("Кобб — талантливый вор.")
        movie1.setYear(2010)
        movie1.setRating(8.6)
        movie1.setPrice(130.00)
        def poster1 = new Poster(1, "http1")
        def poster2 = new Poster(2, "http2")
        List<Poster> posters1 = new ArrayList<>()
        posters1.add(poster1)
        posters1.add(poster2)
        movie1.setPosters(posters1)

        def movie3 = new Movie()
        movie3.setId(3)
        movie3.setNameRussian("Форрест Гамп")
        movie3.setNameNative("Forrest Gump")
        movie3.setDescription("От лица главного героя Форреста Гампа")
        movie3.setYear(1994)
        movie3.setRating(8.6)
        movie3.setPrice(200.60)
        def poster5 = new Poster(5, "http5")
        List<Poster> posters3 = new ArrayList<>()
        posters3.add(poster5)
        movie3.setPosters(posters3)

        def expectedMovies = [movie1, movie3]
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

}
