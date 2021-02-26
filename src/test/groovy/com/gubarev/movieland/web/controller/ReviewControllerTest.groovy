package com.gubarev.movieland.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import com.gubarev.movieland.common.request.AddReviewRequest
import com.gubarev.movieland.config.RootApplicationContext
import com.gubarev.movieland.dao.TestConfiguration
import com.gubarev.movieland.service.security.impl.JwtTokenService
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = [TestConfiguration.class, RootApplicationContext.class, com.gubarev.movieland.web.WebApplicationContext.class])
@DataSet(value = ["movie.xml", "country.xml", "movie_country.xml", "movie_genre.xml", "poster.xml", "user_role.xml",
        "users.xml", "review.xml"], executeStatementsBefore = "SELECT setval('review_id_seq', 3);")
class ReviewControllerTest {

    @Autowired
    private ReviewController reviewController

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsService userDetailsService

    @Autowired
    private WebApplicationContext context

    private MockMvc mockMvc

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    @ExpectedDataSet("add_review.xml")
    void "add review"() {
        //prepare
        def review = new AddReviewRequest(1, "Огонь")
        ObjectMapper mapper = new ObjectMapper()
        String json = mapper.writeValueAsString(review)
        String token = jwtTokenService.generateToken("ronald.reynolds66@example.com")
        UserDetails customUserDetails = userDetailsService.loadUserByUsername("ronald.reynolds66@example.com")
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails,
                null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(post("/review")
                .header("Authorization", token)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
    }

    @Test
    @ExpectedDataSet("review.xml")
    void "add review no authorized"() {
        //prepare
        def review = new AddReviewRequest(1, "Огонь")
        ObjectMapper mapper = new ObjectMapper()
        String json = mapper.writeValueAsString(review)

        mockMvc.perform(post("/review")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isUnauthorized())
    }
}
