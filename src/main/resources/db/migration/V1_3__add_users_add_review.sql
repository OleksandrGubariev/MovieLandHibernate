 create table userrole(
 id SERIAL PRIMARY KEY,
 role VARCHAR(10) NOT NULL
 );

 create table users(
 id SERIAL PRIMARY KEY,
 name VARCHAR(50) NOT NULL,
 email VARCHAR(50) UNIQUE NOT NULL,
 password TEXT NOT NULL,
 userRoleId INTEGER NOT NULL,
 CONSTRAINT FK_UserRole FOREIGN KEY (userRoleId) REFERENCES userrole(id)
 );

 create table review(
 id SERIAL PRIMARY KEY,
 movieId INTEGER NOT NULL,
 userId INTEGER NOT NULL,
 comment TEXT NOT NULL,
 CONSTRAINT FK_MovieId FOREIGN KEY (movieId) REFERENCES movie(id),
 CONSTRAINT FK_UserId FOREIGN KEY (userId) REFERENCES users(id)
 );