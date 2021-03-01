package com.gubarev.movieland.web.mapper;

import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto movieToMovieDto(Movie movie);
}
