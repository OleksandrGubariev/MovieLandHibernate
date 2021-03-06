package com.gubarev.movieland.service.mapper;

import com.gubarev.movieland.common.dto.MovieByIdDto;
import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.common.dto.ReviewDto;
import com.gubarev.movieland.common.dto.UserDto;
import com.gubarev.movieland.common.request.AddMovieRequest;
import com.gubarev.movieland.common.request.EditMovieRequest;
import com.gubarev.movieland.entity.*;
import com.gubarev.movieland.service.mapper.annotation.LongToCountry;
import com.gubarev.movieland.service.mapper.annotation.LongToGenre;
import com.gubarev.movieland.service.mapper.annotation.ReviewToReviewDto;
import com.gubarev.movieland.service.mapper.annotation.StringToPoster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto movieToMovieDto(Movie movie);

    Movie movieJpaToMovie(Movie movie);

    @Mapping(target = "reviews", qualifiedBy = ReviewToReviewDto.class)
    MovieByIdDto movieToMovieByIdDto(Movie movie);

    @Mapping(target = "posters", qualifiedBy = StringToPoster.class)
    @Mapping(target = "genres", qualifiedBy = LongToGenre.class)
    @Mapping(target = "countries", qualifiedBy = LongToCountry.class)
    Movie addMovieDtoToMovie(AddMovieRequest addMovieRequest);

    @Mapping(target = "posters", qualifiedBy = StringToPoster.class)
    @Mapping(target = "genres", qualifiedBy = LongToGenre.class)
    @Mapping(target = "countries", qualifiedBy = LongToCountry.class)
    Movie editMovieDtoToMovie(EditMovieRequest editMovieRequest);

    @StringToPoster
    default List<Poster> stringToPoster(List<String> posters) {
        return posters.stream()
                .map(poster -> new Poster(0, poster))
                .collect(Collectors.toList());
    }

    @LongToGenre
    default List<Genre> longToGenre(List<Long> genres) {
        return genres.stream()
                .map(genreId -> new Genre(genreId, null))
                .collect(Collectors.toList());
    }

    @LongToCountry
    default List<Country> longToCountry(List<Long> countries) {
        return countries.stream()
                .map(countryId -> new Country(countryId, null))
                .collect(Collectors.toList());
    }

    @ReviewToReviewDto
    default List<ReviewDto> reviewToReviewDto(List<Review> reviews) {
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviews) {
            User user = review.getUser();
            UserDto userDto = new UserDto(user.getId(), user.getName());
            ReviewDto reviewDto = new ReviewDto(review.getId(), userDto, review.getComment());
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }
}
