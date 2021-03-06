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

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto movieToMovieDto(Movie movie);

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
    default Set<Poster> stringToPoster(Set<String> posters) {
        return posters.stream()
                .map(poster -> new Poster(0, poster))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @LongToGenre
    default Set<Genre> longToGenre(Set<Long> genres) {
        return genres.stream()
                .map(genreId -> new Genre(genreId, null))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(Genre::getId))));
    }

    @LongToCountry
    default Set<Country> longToCountry(Set<Long> countries) {
        return countries.stream()
                .map(countryId -> new Country(countryId, null))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(Country::getId))));
    }

    @ReviewToReviewDto
    default Set<ReviewDto> reviewToReviewDto(Set<Review> reviews) {
        Set<ReviewDto> reviewDtoList = new TreeSet<>(Comparator.comparing(ReviewDto::getId));
        for (Review review : reviews) {
            User user = review.getUser();
            UserDto userDto = new UserDto(user.getId(), user.getName());
            ReviewDto reviewDto = new ReviewDto(review.getId(), userDto, review.getComment());
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }
}
