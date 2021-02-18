package com.gubarev.movieland.dao.jpa

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.ReviewRepository
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.entity.Review
import com.gubarev.movieland.web.WebApplicationContext
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig


@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"], executeStatementsBefore = "SELECT setval('review_id_seq', 3);")
class JpaReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @ExpectedDataSet("add_review.xml")
    void "add review" () {
        //prepare
        def review = new Review(1, 1, "Огонь")

        //when
        reviewRepository.add(review)
    }
}
