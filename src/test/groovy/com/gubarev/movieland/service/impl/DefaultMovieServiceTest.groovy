package com.gubarev.movieland.service.impl

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.common.dto.MovieByIdDto
import com.gubarev.movieland.common.dto.MovieDto
import com.gubarev.movieland.common.dto.ReviewDto
import com.gubarev.movieland.common.dto.UserDto
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.entity.Country
import com.gubarev.movieland.entity.Genre
import com.gubarev.movieland.entity.Movie
import com.gubarev.movieland.entity.Poster
import com.gubarev.movieland.entity.Review
import com.gubarev.movieland.entity.User
import com.gubarev.movieland.entity.UserRole
import com.gubarev.movieland.service.MovieService
import com.gubarev.movieland.web.WebApplicationContext
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.transaction.annotation.Transactional

@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"])
class DefaultMovieServiceTest {
    @Autowired
    private MovieService movieService

    @Test
    void "test find movie by id"() {
        def expectedMovie = new MovieByIdDto()
        expectedMovie.setId(1)
        expectedMovie.setNameRussian("Начало")
        expectedMovie.setNameNative("Inception")
        expectedMovie.setDescription("Кобб — талантливый вор.")
        expectedMovie.setYear(2010)
        expectedMovie.setRating(8.6)
        expectedMovie.setPrice(130.00)
        def poster1 = new Poster(1, "http1")
        def poster2 = new Poster(2, "http2")
        List<Poster> posters1 = new ArrayList<>()
        posters1.add(poster1)
        posters1.add(poster2)
        expectedMovie.setPosters(posters1)
        def genre1 = new Genre(1, "драма")
        def genre2 = new Genre(2, "комедия")
        List<Genre> genres = new ArrayList<>()
        genres.add(genre1)
        genres.add(genre2)
        expectedMovie.setGenres(genres)

        def country1 = new Country(1, "США")
        def country2 = new Country(2, "Великобритания")
        List<Country> countries = new ArrayList<>()
        countries.add(country1)
        countries.add(country2)
        expectedMovie.setCountries(countries)
        def user1 = new UserDto(2, "Дарлин Эдвардс")
        def user2 = new UserDto(3, "Габриэль Джексон")

        def review1 = new ReviewDto(1, user1, "Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.")
        def review2 = new ReviewDto(2, user2, "Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.")
        List<ReviewDto> reviews = new ArrayList<>()
        reviews.add(review1)
        reviews.add(review2)
        expectedMovie.setReviews(reviews)

        def actualMovie = movieService.findById(1L)
        actualMovie.posters.sort({ it.id })
        actualMovie.genres.sort({ it.id })
        actualMovie.countries.sort({ it.id })
        actualMovie.reviews.sort({ it.id })

        assert expectedMovie == actualMovie
    }
}
