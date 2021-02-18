package com.gubarev.movieland.dao.jpa

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.dao.UserRepository
import com.gubarev.movieland.entity.User
import com.gubarev.movieland.entity.UserRole
import com.gubarev.movieland.web.WebApplicationContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"])
class JpaUserRepositoryITest {
    @Autowired
    private UserRepository userRepository

    @Test
    void "login test"() {
        //prepare
        def expectedUser = new User(1, "Рональд Рейнольдс", "ronald.reynolds66@example.com",
                "\$2a\$10\$EAPxJZoDOUwxvCilsSl39.Y844zPaSljhNhlbzoSQGSF/Z3ey.q16",
                UserRole.USER)

        //when
        def actualUser = userRepository.findByLogin("ronald.reynolds66@example.com")

        //then
        assert actualUser == expectedUser
    }
}
