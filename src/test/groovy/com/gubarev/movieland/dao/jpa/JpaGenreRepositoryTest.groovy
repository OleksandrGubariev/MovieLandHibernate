package com.gubarev.movieland.dao.jpa

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.GenreRepository
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.entity.Genre
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
class JpaGenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository

    @Test
    void "test get all genres"() {
        //prepare
        def expected = [new Genre(1, "драма"),
                        new Genre(2, "комедия"),
                        new Genre(3, "боевик"),
                        new Genre(4, "аниме")
        ]

        SQLStatementCountValidator.reset()

        //when
        def actual = genreRepository.findAll()

        //then
        assert actual.size() == 4

        for (genre in expected) {
            actual.removeIf(x -> x == genre)
        }

        assert actual.size() == 0

        actual = genreRepository.findAll()
        assert actual.size() == 4

        for (genre in expected) {
            actual.removeIf(x -> x == genre)
        }

        assert actual.size() == 0
        SQLStatementCountValidator.assertSelectCount(1)
    }
}