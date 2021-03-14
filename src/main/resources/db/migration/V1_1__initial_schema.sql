create table genre(
 id SERIAL PRIMARY KEY,
 genre VARCHAR(100) NOT NULL
 );

 create table movie (
 id SERIAL PRIMARY KEY,
 nameRussian VARCHAR(500) NOT NULL,
 nameNative VARCHAR(500) NOT NULL,
 year SMALLINT NOT NULL,
 description TEXT,
 rating DECIMAL(2,1) NULL,
 price DECIMAL(7,2) NOT NULL
 );

 create table country (
 id SERIAL PRIMARY KEY,
 country VARCHAR(100)
 );

 create table movie_country (
 movie_id INTEGER NOT NULL,
 country_id INTEGER NOT NULL,
 CONSTRAINT FK_MovieId FOREIGN KEY (movie_id) REFERENCES movie(id),
 CONSTRAINT FK_CountryId FOREIGN KEY (country_id) REFERENCES country(id)
 );

 create table movie_genre (
 movie_id INTEGER NOT NULL,
 genre_id INTEGER NOT NULL,
 CONSTRAINT FK_MovieId FOREIGN KEY (movie_id) REFERENCES movie(id),
 CONSTRAINT FK_GenreId FOREIGN KEY (genre_id) REFERENCES genre(id)
 );

 create table poster(
 id SERIAL PRIMARY KEY,
 movie_id INTEGER NOT NULL,
 link TEXT NOT NULL,
 CONSTRAINT FK_MoviId FOREIGN KEY (movie_id) REFERENCES movie(id)
 );













