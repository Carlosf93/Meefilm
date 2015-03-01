--USUARIOS( id,aficiones,ciudad,correo,edad,fdn,hashedAndSalted,login,nombre,parecidos,pelisParecidas,profesion,role,salt,seriesparecidas,usersparecidos)
insert into User values (0,'administrar','madrid','admin@meefilm.com',00,'','b98d0c33982cf14111f7d0e423ca60a9eddf4d0f','admin','','','','admin','b991a2e390e4459479288fe4b831e42d','','');
insert into User values (1,'Pescar','Madrid','carlos@meefilm.com',21,'1993-09-16','c1ddd55cc277edfcf97acd2660160be227e33e7b','carlos','Carlos Fernández','','Pastor','user','c78756428f5796cf38f3734aa4f81149','','');
insert into User values (2,'Cazar','Madrid','victor@meefilm.com',22,'1992-11-15','c1ddd55cc277edfcf97acd2660160be227e33e7b','victor','Victor Delgado','','Carnicero','user','c78756428f5796cf38f3734aa4f81149','','');
insert into User values (3,'Nadar','Madrid','alvaro@meefilm.com',24,'1990-11-30','c1ddd55cc277edfcf97acd2660160be227e33e7b','alvaro','Alvaro Isabel','','Leñador','user','c78756428f5796cf38f3734aa4f81149','','');
--create table Serie (id, IMDbId, creador, genero, nombre, reparto, sinopsis, year )
insert into Serie values (1,'tt2193021','Andrew Kreisberg','Action, Adventure, Crime','Arrow','Stephen Amell','Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.','2012');
insert into Serie values (2,'tt3107288','Geoff Johns','Action, Adventure, Drama','The Flash','Grant Gustin','Barry Allen wakes up 9 months after he was struck by lightning and discovers that the bolt gave him the power of super speed. With his new team and powers, Barry becomes "The Flash" and fights crime in Central City.','2014');
insert into Serie values (3,'tt2375692','Robert Levine','Adventure, Drama','Black Sails', 'Luke Arnold, Zach McGowan, Jessica Parker Kennedy, Louise Barnes', 'Captain Flint and his pirates, twenty years prior to Robert Louis Stevenson classic "Treasure Island".','2014')
insert into Serie values (4,'tt1586680','Paul Abbott', 'Comedy, Drama', 'Shameless (US)','William H. Macy, Emmy Rossum, Jeremy Allen White, Cameron Monaghan','An alcoholic man lives in a perpetual stupor while his six children with whom he lives cope as best they can.','2011')
--Pelicula (id,imdbid,director, genero, nombre, reparto, sinopsis, year)
insert into Pelicula values (1,'tt0167260','Peter Jackson','Adventure, Fantasy','El Señor de los anillos: El retorno del rey','Sean Bean','Gandalf and Aragorn lead the World of Men against Sauron army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.','2003');
insert into Pelicula values (2,'tt2310332','Peter Jackson','Adventure, Fantasy','El hobbit: la batalla de los cinco ejercitos','Martin Freeman','Bilbo and Company are forced to engage in a war against an array of combatants and keep the Lonely Mountain from falling into the hands of a rising darkness.','2014');
insert into Pelicula values (3,'tt0137523','David Fincher','Drama','Fight club','Edward Norton, Brad Pitt, Helena Bonham Carter, Meat Loaf','An insomniac office worker looking for a way to change his life crosses paths with a devil-may-care soap maker and they form an underground fight club that evolves into something much, much more.','1999');

insert into Quedada values (1,'Cinesa Principe pio', 'Madrid', 'Comunidad de Madrid', 'El viernes a las 18:00', 'Birdman')
insert into Quedada_User values (1,1)
insert into User_Quedada values (1,1)

insert into Capitulo values (1,'tt2340185','David Nutter','14 Nov. 2012','Pilot',1,'Stephen Amell, Katie Cassidy, Colin Donnell','Billionaire playboy, Oliver Queen, has been considered dead for five years. Now, he has returned. But something, during those five years, has changed him into a mysterious green hooded archer.',1)
insert into Temporada values (1,1)
insert into Serie_Temporada values (1,1)
insert into Temporada_Capitulo values (1,1)

insert into User_User values (1,3)
insert into User_User values (3,1)

insert into User_Pelicula values (1,1)
insert into Pelicula_User values (1,1)
insert into User_Pelicula values (2,1)
insert into Pelicula_User values (1,2)
insert into User_Pelicula values (1,2)
insert into Pelicula_User values (2,1)

insert into User_Serie values (1,1)
insert into User_Serie values (1,2)
insert into User_Serie values (1,3)
insert into User_Serie values (1,4)
insert into Serie_User values (1,1)
insert into Serie_User values (2,1)
insert into Serie_User values (3,1)
insert into Serie_User values (4,1)
