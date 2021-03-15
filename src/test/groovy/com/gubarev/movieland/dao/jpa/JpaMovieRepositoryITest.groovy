package com.gubarev.movieland.dao.jpa

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.common.MovieRequest
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.MovieRepository
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.entity.*
import com.gubarev.movieland.web.WebApplicationContext
import com.vladmihalcea.sql.SQLStatementCountValidator
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.transaction.annotation.Transactional

@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"],
        executeStatementsBefore = "SELECT setval('movie_id_seq', 5);SELECT setval('poster_id_seq', 7);")
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
        Set<Poster> posters1 = new LinkedHashSet<>()
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
        Set<Poster> posters2 = new LinkedHashSet<>()
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
        Set<Poster> posters3 = new LinkedHashSet<>()
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
        Set<Poster> posters4 = new LinkedHashSet<>()
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
        Set<Poster> posters5 = new LinkedHashSet<>()
        posters5.add(poster7)
        movie5.setPosters(posters5)

        def expectedMovies = [movie1, movie2, movie3, movie4, movie5]
        def sortParameter = new MovieRequest()
        SQLStatementCountValidator.reset()

        //when
        def actualMovies = movieRepository.findAll(sortParameter)

        //then
        assert actualMovies.size() == 5

        actualMovies.sort({ it.id })

        for (int i = 0; i < 5; i++) {
            def actualMovie = actualMovies.get(i)
            def expectedMovie = expectedMovies.get(i)

            assert actualMovie.getId() == expectedMovie.getId()

            assert actualMovie.getNameRussian() == expectedMovie.getNameRussian()

            assert actualMovie.getNameNative() == expectedMovie.getNameNative()

            assert actualMovie.getDescription() == expectedMovie.getDescription()

            assert actualMovie.getPrice() == expectedMovie.getPrice()

            assert actualMovie.getYear() == expectedMovie.getYear()

            assert actualMovie.getRating() == expectedMovie.getRating()

            assert actualMovie.getPosters() == expectedMovie.getPosters()
        }

        SQLStatementCountValidator.assertSelectCount(1)
    }

    @Test
    void "test get random movies"() {
        //when
        def actualMovies = movieRepository.findRandom()

        //then
        assert actualMovies.size() == 3
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
        Set<Poster> posters1 = new LinkedHashSet<>()
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
        Set<Poster> posters3 = new LinkedHashSet<>()
        posters3.add(poster5)
        movie3.setPosters(posters3)

        def expectedMovies = [movie1, movie3]
        def sortParameter = new MovieRequest()

        //when
        def actualMovies = movieRepository.findByGenre(1, sortParameter)

        //then
        assert actualMovies.size() == 2

        actualMovies.sort({ it.id })

        for (int i = 0; i < 2; i++) {
            def actualMovie = actualMovies.get(i)
            def expectedMovie = expectedMovies.get(i)

            assert actualMovie.getId() == expectedMovie.getId()

            assert actualMovie.getNameRussian() == expectedMovie.getNameRussian()

            assert actualMovie.getNameNative() == expectedMovie.getNameNative()

            assert actualMovie.getDescription() == expectedMovie.getDescription()

            assert actualMovie.getPrice() == expectedMovie.getPrice()

            assert actualMovie.getYear() == expectedMovie.getYear()

            assert actualMovie.getRating() == expectedMovie.getRating()

            assert actualMovie.getPosters() == expectedMovie.getPosters()
        }
    }

    @Test
    @Transactional
    void "test get movie by id"() {
        def expectedMovie = new Movie()
        expectedMovie.setId(1)
        expectedMovie.setNameRussian("Начало")
        expectedMovie.setNameNative("Inception")
        expectedMovie.setDescription("Кобб — талантливый вор.")
        expectedMovie.setYear(2010)
        expectedMovie.setRating(8.6)
        expectedMovie.setPrice(130.00)
        def poster1 = new Poster(1, "http1")
        def poster2 = new Poster(2, "http2")
        Set<Poster> posters1 = new LinkedHashSet<>()
        posters1.add(poster1)
        posters1.add(poster2)
        expectedMovie.setPosters(posters1)
        def genre1 = new Genre(1, "драма")
        def genre2 = new Genre(2, "комедия")
        Set<Genre> genres = new LinkedHashSet<>()
        genres.add(genre1)
        genres.add(genre2)
        expectedMovie.setGenres(genres)

        def country1 = new Country(1, "США")
        def country2 = new Country(2, "Великобритания")
        Set<Country> countries = new LinkedHashSet<>()
        countries.add(country1)
        countries.add(country2)
        expectedMovie.setCountries(countries)
        User user1 = new User(2, "Дарлин Эдвардс", "darlene.edwards15@example.com",
                "\$2a\$10\$eSYOdTUlWcHsq0FKyu8JL.lBIEEDL60nXTZB/jyVluryGTqWTjWs6", UserRole.USER)
                User user2 = new User(3, "Габриэль Джексон", "gabriel.jackson91@example.com",
                        "\$2a\$10\$OvEnkjbh4h9iPnrxG8T9Y.F5DgB//pp/iSdBUc30W/uTpK500nZPK", UserRole.USER)

        def review1 = new Review(1, 1, user1, "Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.")
        def review2 = new Review(2, 1, user2, "Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.")
        Set<Review> reviews = new LinkedHashSet<>()
        reviews.add(review1)
        reviews.add(review2)
        expectedMovie.setReviews(reviews)

        def actualMovie = movieRepository.findById(1L)
        actualMovie.posters.sort({ it.id })
        actualMovie.genres.sort({ it.id })
        actualMovie.countries.sort({ it.id })
        actualMovie.reviews.sort({ it.id })

        assert expectedMovie == actualMovie
    }

    @Test
    @ExpectedDataSet("add_movie.xml, add_movie_genre.xml, add_movie_country.xml")
    void "add movie"() {
        //prepare
        def movie = new Movie()
        movie.setNameRussian("Бойцовский клуб")
        movie.setNameNative("Fight Club")
        movie.setDescription("Терзаемый хронической бессонницей")
        movie.setYear(1999)
        movie.setPrice(119.99)

        def poster1 = new Poster()
        poster1.setLink("http8")
        def poster2 = new Poster()
        poster2.setLink("http9")
        Set<Poster> posters1 = new LinkedHashSet<>()
        posters1.add(poster1)
        posters1.add(poster2)
        movie.setPosters(posters1)

        def genre1 = new Genre()
        genre1.setId(1)
        def genre2 = new Genre()
        genre2.setId(2)
        Set<Genre> genres = new LinkedHashSet<>()
        genres.add(genre1)
        genres.add(genre2)
        movie.setGenres(genres)

        def country1 = new Country()
        country1.setId(1)
        def country2 = new Country()
        country2.setId(2)
        Set<Country> countries = new LinkedHashSet<>()
        countries.add(country1)
        countries.add(country2)
        movie.setCountries(countries)

        //when
        movieRepository.addMovie(movie)
    }

    @Test
    @ExpectedDataSet("edit_movie.xml, edit_movie_genre.xml, edit_movie_country.xml, edit_poster.xml, review.xml")
    void "edit movie"() {
        //prepare
        def movie = new Movie()
        movie.setId(1)
        movie.setNameRussian("Новое Имя")
        movie.setNameNative("New name")

        def poster1 = new Poster()
        poster1.setLink("http3")
        def poster2 = new Poster()
        poster2.setLink("http4")
        Set<Poster> posters = new LinkedHashSet<>()
        posters.add(poster1)
        posters.add(poster2)
        movie.setPosters(posters)

        def genre1 = new Genre()
        genre1.setId(1)
        def genre2 = new Genre()
        genre2.setId(3)
        Set<Genre> genres = new LinkedHashSet<>()
        genres.add(genre1)
        genres.add(genre2)
        movie.setGenres(genres)

        def country1 = new Country()
        country1.setId(1)
        def country2 = new Country()
        country2.setId(3)
        Set<Country> countries = new LinkedHashSet<>()
        countries.add(country1)
        countries.add(country2)
        movie.setCountries(countries)

        //when
        movieRepository.editMovie(movie)
    }

}
