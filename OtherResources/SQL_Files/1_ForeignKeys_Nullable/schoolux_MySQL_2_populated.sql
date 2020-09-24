
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				MEMENTO DIFFICULTES RENCONTREES
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Remarque : les tables doivent être crées au préalable avant de faire les CONSTRAINT NomContrainte FOREIGN KEY
-- On ne peut pas dire que id_role dans la table users pointe vers la table roles si la table roles n'existe pas encore
-- Donc on crée bien les champs qui sont des foreign keys dans les tables, mais on attend d'avoir créer toutes les tables avant de dire que ce sont des foreign keys
-- Du coup, vu que j'avais écrit toutes les CONSTRAINT FOREIGN KEY au sein de chaque table concernée,
-- il a fallu sortir toutes les CONSTRAINT FOREIGN KEY de ces tables, et les remettre en fin de fichier via des ALTER TABLE ADD
-- Ceci a été réalisé et mis dans le fichier .txt nommé CONSTRAINTS_FK.txt
-- On a ensuite delete les CONSTRAINT FOREIGN KEY dans chaque table, et copié le contenu du CONSTRAINTS_FK.txt à la fin de ce fichier-ci aka CodeSQL_SGBD.txt 
-- avant d'en changer le format de .txt vers du .sql



-- Pq rendre les foreigns keys nullables ?
-- https://stackoverflow.com/questions/7573590/can-a-foreign-key-be-null-and-or-duplicate#:~:text=Short answer%3A Yes%2C it can,table (the parent table).&text=Null by definition is not a value.
-- Ca bloque trop les inserts de raw datas pour faire des tests, faut tjrs que la table-parent d'une foreign key soit peuplée avant de pouvoir indiquer un id pour cette foreign key dans la table-enfant

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				PARAMETRAGE DB + DROP DATABASE AVANT DE LA RECREER + CREATION DES TABLES AVEC LEURS PK - ATTRIBUTS - FOREIGN KEYS - CONTRAINTES SUR PK - CONTRAINTES SUR ATTRIBUTS 
-- 				PAS DE CONTRAINTES SUR LES FOREIGN KEYS ICI, CF "MEMENTO DIFFICULTES RENCONTREES"
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
-- ATTENTION : START TRANSACTION nécessite un COMMIT; en fin de fichier
SET time_zone = "+00:00";


--
-- Nom de notre Base de données :  `schoolux`
--

DROP DATABASE IF EXISTS schoolux;
CREATE DATABASE IF NOT EXISTS schoolux;
USE schoolux;


-- DROP TABLE IF EXISTS countries,
--					   addresses,
--                     cities,                    
--                     etc,
--	 				   etc;
-- On fait pas tous les DROP de tables ici, mais via le DROP TABLE IF EXISTS NomTable avant chaque CREATE TABLE IF NOT EXISTS NomTable qui arrivent dans la section suivante				 




-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				DROP DE CHAQUE TABLE AVANT DE LA RECREER - CREATION DES TABLES AVEC LEURS PK - ATTRIBUTS - FOREIGN KEYS - CONTRAINTES SUR PK - CONTRAINTES SUR ATTRIBUTS 
-- 				PAS DE CONTRAINTES SUR LES FOREIGN KEYS ICI, CF "MEMENTO DIFFICULTES RENCONTREES"
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `phone_number` varchar(25) NULL DEFAULT NULL,
  `birthdate` date NOT NULL,
  `gender` ENUM('Féminin', 'Masculin', 'Neutre', 'Personnalisé') NOT NULL DEFAULT 'Neutre', 
  `email_address` varchar(100) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `inscription_date` timestamp NOT NULL DEFAULT current_timestamp,
  `title` varchar(50) NULL DEFAULT NULL,
  `photo` varchar(255) NULL DEFAULT NULL,

-- current_timestamp est la date du jour
  
  
  `id_address` int(11) NULL DEFAULT NULL,
  `id_role` int(11) NULL DEFAULT NULL,
  `id_school` int(11) NULL DEFAULT NULL,
  `id_parent` int(11) NULL DEFAULT NULL,
  
  
  
  CONSTRAINT PK_USERS PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_USERS_email_address UNIQUE (`email_address`),
  CONSTRAINT UNIQUE_CONSTRAINT_USERS_username UNIQUE (`username`)
  
 
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(100) NOT NULL,
  `abbreviation` varchar(10) NOT NULL,
  `description` varchar(2000) NULL DEFAULT NULL,
  
  
  
  CONSTRAINT PK_ROLES PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_ROLES_label UNIQUE (`label`),
  CONSTRAINT UNIQUE_CONSTRAINT_ROLES_abbreviation UNIQUE (`abbreviation`)
  
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(100) NOT NULL,
  `abbreviation` varchar(10) NOT NULL,
  `description` varchar(2000) NULL DEFAULT NULL,
  
  
  
  CONSTRAINT PK_PERMISSIONS PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_PERMISSIONS_label UNIQUE (`label`),
  CONSTRAINT UNIQUE_CONSTRAINT_PERMISSIONS_abbreviation UNIQUE (`abbreviation`)
  
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE IF NOT EXISTS `roles_permissions` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_role` int(11) NULL DEFAULT NULL,
  `id_permission` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_ROLES_PERMISSIONS PRIMARY KEY (`id`),



  CONSTRAINT UNIQUE_CONSTRAINT_ROLES_PERMISSIONS_fkcomposite UNIQUE (`id_role`,`id_permission`)
   
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `adresses`
--

DROP TABLE IF EXISTS `addresses`;
CREATE TABLE IF NOT EXISTS `addresses` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `street` varchar(255) NOT NULL,
  `number` varchar(20) NOT NULL,
  
  
  
  `id_city` int(11) NULL DEFAULT NULL,
  
  
  
  CONSTRAINT PK_ADDRESSES PRIMARY KEY (`id`)
    
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `cities`
--

DROP TABLE IF EXISTS `cities`;
CREATE TABLE IF NOT EXISTS `cities` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `postal_code` varchar(10)NOT NULL,
  
  
  
  `id_country` int(11) NULL DEFAULT NULL,
  
  
  
  CONSTRAINT PK_CITIES PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_CITIES_postal_code UNIQUE (`postal_code`)
 
 
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `countries`
--

DROP TABLE IF EXISTS `countries`;
CREATE TABLE IF NOT EXISTS `countries` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `country_code` varchar(10) NULL DEFAULT NULL,
  `abbreviation` varchar(5) NOT NULL,
  
  
  
  CONSTRAINT PK_COUNTRIES PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_COUNTRIES_abbreviation UNIQUE (`abbreviation`),
-- on peut mettre comme suit une contrainte UNIQUE sur une colonne nullable, les valeurs NULL ne sont pas comparées donc même si toute la colonne est à NULL, le UNIQUE est validé
  CONSTRAINT UNIQUE_CONSTRAINT_COUNTRIES_country_code UNIQUE (`country_code`)

  
    
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `sections`
--

DROP TABLE IF EXISTS `sections`;
CREATE TABLE IF NOT EXISTS `sections` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `scholar_year` YEAR NOT NULL,
 
  
  
  CONSTRAINT PK_SECTIONS PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_KEY_CONSTRAINT_SECTIONS_name_scholar_year UNIQUE key (`name`,`scholar_year`)



) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `medias`
--

DROP TABLE IF EXISTS `medias`;
CREATE TABLE IF NOT EXISTS `medias` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `type` varchar(100) NOT NULL ,
  `date_upload` timestamp NOT NULL DEFAULT current_timestamp,
  `url` varchar(255) NOT NULL,
  `format` varchar(10) NOT NULL,
  
  
  
  CONSTRAINT PK_MEDIAS PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_MEDIAS_url UNIQUE (`url`)
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `documents`
--

DROP TABLE IF EXISTS `documents`;
CREATE TABLE IF NOT EXISTS `documents` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `type` varchar(100) NOT NULL ,
  `date_upload` timestamp NOT NULL DEFAULT current_timestamp,
  `status` ENUM('En attente de validation', 'validé', 'invalidé') NOT NULL DEFAULT 'En attente de validation', 
  `format` varchar(10) NOT NULL,
  `url` varchar(255) NOT NULL,



  CONSTRAINT PK_DOCUMENTS PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_DOCUMENTS_url UNIQUE (`url`)
  
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `events`
--

DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `begin_date` date NOT NULL,
  `end_date` date NOT NULL,
  `begin_hour` time NOT NULL,
  `end_hour` time NOT NULL,
  `description` varchar(2000) NOT NULL,
  `attachment_url` varchar(255) NULL DEFAULT NULL,
  
  
  
  
  CONSTRAINT PK_EVENTS PRIMARY KEY (`id`)
-- pas de constraint UNIQUE sur l'url ici vu qu'on pourrait réutiliser le meme document
  
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `activities`
--

DROP TABLE IF EXISTS `activities`;
CREATE TABLE IF NOT EXISTS `activities` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `departure_date_time` datetime NOT NULL,
  `return_date_time` datetime NOT NULL,
  `description` varchar(2000) NOT NULL,
  `attachment_url` varchar(255) NOT NULL,
  
  
    
  `id_section` int(11) NULL DEFAULT NULL,
    
  
  
  CONSTRAINT PK_ACTIVITIES PRIMARY KEY (`id`)
  
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------


--
-- Structure de la table `schools`
--

DROP TABLE IF EXISTS `schools`;
CREATE TABLE IF NOT EXISTS `schools` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `school_code` varchar(10) NULL DEFAULT NULL,
  `abbreviation` varchar(10) NOT NULL,
  
  
  
  `id_address` int(11) NULL DEFAULT NULL,
  
  
  
  
  CONSTRAINT PK_SCHOOLS PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE_CONSTRAINT_SCHOOLS_abbreviation UNIQUE (`abbreviation`),
-- on peut mettre comme suit une contrainte UNIQUE sur une colonne nullable, les valeurs NULL ne sont pas comparées donc même si toute la colonne est à NULL, le UNIQUE est validé
  CONSTRAINT UNIQUE_CONSTRAINT_SCHOOLS_school_code UNIQUE (`school_code`)

  
  
  
    
	
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `courses`
--

DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `year_grade` ENUM(
  'NonAttribué',
  'Première',
  'Deuxième',
  'Troisième',
  'Quatrième',
  'Cinquième',
  'Sixième',
  'Septième'
  ) NOT NULL DEFAULT 'NonAttribué',
  `cours_code` varchar(10) NOT NULL,
  
    
  
  CONSTRAINT PK_COURSES PRIMARY KEY (`id`)
  
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
CREATE TABLE IF NOT EXISTS `schedules` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `room` varchar(100) NOT NULL,
  `day_of_week` ENUM(
  'NonAttribué',
  'LUNDI',
  'MARDI',
  'MERCREDI',
  'JEUDI',
  'VENDREDI',
  'SAMEDI',
  'DIMANCHE'
  ) NOT NULL DEFAULT 'NonAttribué',
  `begin_date` date NOT NULL,
  `end_date` date NOT NULL,
  `begin_hour` time NOT NULL,
  `end_hour` time NOT NULL,
  
    
	
  `id_class` int(11) NULL DEFAULT NULL,
  `id_planning` int(11) NULL DEFAULT NULL,
  `id_course_user` int(11) NULL DEFAULT NULL,
  
  
  
  CONSTRAINT PK_SCHEDULES PRIMARY KEY (`id`)
  
  
    
  
    
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `plannings`
--

DROP TABLE IF EXISTS `plannings`;
CREATE TABLE IF NOT EXISTS `plannings` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  
  
  
  `id_section` int(11) NULL DEFAULT NULL,
	
	
	
  CONSTRAINT PK_PLANNINGS PRIMARY KEY (`id`)
  
  
  
  


) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
CREATE TABLE IF NOT EXISTS `appointments` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sujet` varchar(255) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `begin_date_time` datetime NOT NULL,
  
  
  
  `id_user_taker` int(11) NULL DEFAULT NULL,
  `id_user_proposer` int(11) NULL DEFAULT NULL,
  `id_planning` int(11) NULL DEFAULT NULL,
  `id_durationType` int(11) NULL DEFAULT NULL,
  
  
  
  CONSTRAINT PK_APPOINTMENTS PRIMARY KEY (`id`)
  
  
  
  
  
 
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `durationTypes`
--

DROP TABLE IF EXISTS `durationTypes`;
CREATE TABLE IF NOT EXISTS `durationTypes` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `duration` int(11) NOT NULL,
  `label` varchar(100) NOT NULL, 
  
	
	
  CONSTRAINT PK_DURATIONTYPES PRIMARY KEY (`id`)
  



) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `classes`
--

DROP TABLE IF EXISTS `classes`;
CREATE TABLE IF NOT EXISTS `classes` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  
  
  
  `id_section` int(11) NULL DEFAULT NULL,
	
	
	
  CONSTRAINT PK_CLASSES PRIMARY KEY (`id`)
  
  
  
  


) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `courses_sections`
--

DROP TABLE IF EXISTS `courses_sections`;
CREATE TABLE IF NOT EXISTS `courses_sections` (

-- artificial id dans les tables intermediaires pour simplifier java
  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_course` int(11) NULL DEFAULT NULL,
  `id_section` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_COURSES_SECTIONS PRIMARY KEY (`id`),
   


  CONSTRAINT UNIQUE_CONSTRAINT_COURSES_PERMISSIONS_fkcomposite UNIQUE (`id_course`,`id_section`)
  
  
    
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `medias_users`
--

DROP TABLE IF EXISTS `medias_users`;
CREATE TABLE IF NOT EXISTS `medias_users` (

-- artificial id dans les tables intermediaires pour simplifier java
  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_media` int(11) NULL DEFAULT NULL,
  `id_user` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_MEDIAS_USERS PRIMARY KEY (`id`),
   


  CONSTRAINT UNIQUE_CONSTRAINT_MEDIAS_USERS_fkcomposite UNIQUE (`id_media`,`id_user`)
  
    
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `events_users`
--

DROP TABLE IF EXISTS `events_users`;
CREATE TABLE IF NOT EXISTS `events_users` (

-- artificial id dans les tables intermediaires pour simplifier java
  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_event` int(11) NULL DEFAULT NULL,
  `id_user` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_EVENTS_USERS PRIMARY KEY (`id`),
   


  CONSTRAINT UNIQUE_CONSTRAINT_EVENTS_USERS_fkcomposite UNIQUE (`id_event`,`id_user`)
  
  
    
    
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `activities_plannings`
--

DROP TABLE IF EXISTS `activities_plannings`;
CREATE TABLE IF NOT EXISTS `activities_plannings` (

-- artificial id dans les tables intermediaires pour simplifier java
  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_activity` int(11) NULL DEFAULT NULL,
  `id_planning` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_ACTIVITIES_PLANNINGS PRIMARY KEY (`id`),
   
  
  
  CONSTRAINT UNIQUE_CONSTRAINT_ACTIVITIES_PLANNINGS_fkcomposite UNIQUE (`id_activity`,`id_planning`)

  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `activities_users`
--

DROP TABLE IF EXISTS `activities_users`;
CREATE TABLE IF NOT EXISTS `activities_users` (

-- artificial id dans les tables intermediaires pour simplifier java
  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_activity` int(11) NULL DEFAULT NULL,
  `id_user` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_ACTIVITIES_USERS PRIMARY KEY (`id`),
   
  

  CONSTRAINT UNIQUE_CONSTRAINT_ACTIVITIES_USERS_fkcomposite UNIQUE (`id_activity`,`id_user`)
    
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `courses_users`
--
-- Attention il ne s agit pas d'une table intermédiaire même si la structure  y ressemble pour java

DROP TABLE IF EXISTS `courses_users`;
CREATE TABLE IF NOT EXISTS `courses_users` (

  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_course` int(11) NULL DEFAULT NULL,
  `id_user` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_COURSESUSERS PRIMARY KEY (`id`),
   
  
  
  CONSTRAINT UNIQUE_CONSTRAINT_COURSES_USERS_fkcomposite UNIQUE (`id_course`,`id_user`)
    
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `sections_users`
--

DROP TABLE IF EXISTS `sections_users`;
CREATE TABLE IF NOT EXISTS `sections_users` (

-- artificial id dans les tables intermediaires pour simplifier java
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL DEFAULT 1,
	


  `id_section` int(11) NULL DEFAULT NULL,
  `id_user` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_SECTIONS_USERS PRIMARY KEY (`id`),
   

  
  CONSTRAINT UNIQUE_CONSTRAINT_SECTIONS_USERS_fkcomposite UNIQUE (`id_section`,`id_user`)
    
  
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `documents_users`
--

DROP TABLE IF EXISTS `documents_users`;
CREATE TABLE IF NOT EXISTS `documents_users` (

-- artificial id dans les tables intermediaires pour simplifier java
  `id` int(11) NOT NULL AUTO_INCREMENT,



  `id_document` int(11) NULL DEFAULT NULL,
  `id_user` int(11) NULL DEFAULT NULL,
  
  

  CONSTRAINT PK_DOCUMENTS_USERS PRIMARY KEY (`id`),
   
  
  
  CONSTRAINT UNIQUE_CONSTRAINT_DOCUMENTS_USERS_fkcomposite UNIQUE (`id_document`,`id_user`)
  

  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;













-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				AJOUT DES CONTRAINTES DE FOREIGN KEYS POUR CHAQUE TABLE EN POSSEDANT
--			
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



-- On réalise ceci via des ALTER TABLE comme suit
-- ALTER TABLE1 nomTableEnfant ADD CONSTRAINT ConstraintName (column ID dansTableEnfant) REFERENCES TableParent (columnId dans TableParent) ON DELETE NO ACTION ON UPDATE CASCADE


-- ALTER TABLE NomTable   
-- pas de parenthèses 
-- écrire ADD qu'une seule fois puis ouvrir parenthèse
-- séparer les CONSTRAINT par des virgules
-- fermer parenthèse
-- écrire un ; qui terminera l'instruction d'alter table 






ALTER TABLE users 	

	ADD(
	  CONSTRAINT FK_USERS_ADDRESSES FOREIGN KEY (`id_address`) REFERENCES `addresses` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_USERS_ROLES FOREIGN KEY (`id_role`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_USERS_SCHOOLS FOREIGN KEY (`id_school`) REFERENCES `schools` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_USERS_USERS FOREIGN KEY (`id_parent`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




-- Le on delete cascade => si on supprime un role de la table roles, ca supprimera aussi les records dans roles_permissions où id_role = id du role qui vient d etre supprimé
-- Sans ce on delete cascade => si on supprime un role de la table role, alors les records dans roles_permissions où id_role était l id du role supprimé vont désormais pointer vers une valeur inexistante
-- Hors une clé référentielle ne pointe jamais vers un null, ca s'appelle la contrainte d'intégrité référentielle


ALTER TABLE roles_permissions 	

	ADD(
		CONSTRAINT FK_ROLESPERMISSIONS_ROLES FOREIGN KEY (`id_role`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
		CONSTRAINT FK_ROLESPERMISSIONS_PERMISSIONS FOREIGN KEY (`id_permission`) REFERENCES `permissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
	);	
	
	
	
	
	
	
	
	
	
	ALTER TABLE addresses 	

	ADD(
		CONSTRAINT FK_ADRESSES_CITIES FOREIGN KEY (`id_city`) REFERENCES `cities` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);	
	
	
	
	
	
	ALTER TABLE cities 	

	ADD(
		CONSTRAINT FK_CITIES_COUNTRIES FOREIGN KEY (`id_country`) REFERENCES `countries` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);	
	
	
	
	
	
	
	
	
	ALTER TABLE activities

	ADD(
		CONSTRAINT FK_ACTIVITIES_SECTIONS FOREIGN KEY (`id_section`) REFERENCES `sections` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);			
	
	
	
	
	
	
	
	ALTER TABLE schools

	ADD(
		CONSTRAINT FK_SCHOOLS_ADDRESSES FOREIGN KEY (`id_address`) REFERENCES `addresses` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);	
		
	
	
	ALTER TABLE schedules
	
	ADD(
		CONSTRAINT FK_SCHEDULES_CLASSES FOREIGN KEY (`id_class`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_SCHEDULES_PLANNINGS FOREIGN KEY (`id_class`) REFERENCES `plannings` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_SCHEDULES_COURSES_USERS FOREIGN KEY (`id_course_user`) REFERENCES `courses_users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);	
	
	
	
	
	
	
	
	
	ALTER TABLE plannings

	ADD(
		CONSTRAINT FK_PLANNINGS_SECTIONS FOREIGN KEY (`id_section`) REFERENCES `sections` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);		
	
	
	
	
	
	ALTER TABLE appointments

	ADD(
		CONSTRAINT FK_APPOINTMENTS_USERS_0_taker FOREIGN KEY (`id_user_taker`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_APPOINTMENTS_USERS_1_proposer FOREIGN KEY (`id_user_proposer`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_APPOINTMENTS_PLANNINGS FOREIGN KEY (`id_planning`) REFERENCES `plannings` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_APPOINTMENTS_DURATIONTYPES FOREIGN KEY (`id_durationType`) REFERENCES `durationTypes` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);		
	
	
	
	ALTER TABLE classes

	ADD(
		CONSTRAINT FK_CLASSES_SECTIONS FOREIGN KEY (`id_section`) REFERENCES `sections` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);		
	
	
	
	
	
	
	
	
	ALTER TABLE courses_sections

	ADD(
		CONSTRAINT FK_COURSESSECTIONS_COURSES FOREIGN KEY (`id_course`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_COURSESSECTIONS_SECTIONS FOREIGN KEY (`id_section`) REFERENCES `sections` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);			
	
	
	
	
	
	
	
	
	
	ALTER TABLE medias_users

	ADD(
		CONSTRAINT FK_MEDIASUSERS_MEDIAS FOREIGN KEY (`id_media`) REFERENCES `medias` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_MEDIASUSERS_USERS FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);			
		
	
	
	
	ALTER TABLE events_users

	ADD(
		CONSTRAINT FK_EVENTSUSERS_EVENTS FOREIGN KEY (`id_event`) REFERENCES `events` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_EVENTSUSERS_USERS FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);	
	
	
	
	ALTER TABLE activities_plannings

	ADD(
		CONSTRAINT FK_ACTIVITIESPLANNINGS_ACTIVITIES FOREIGN KEY (`id_activity`) REFERENCES `activities` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_ACTIVITIESPLANNINGS_PLANNINGS FOREIGN KEY (`id_planning`) REFERENCES `plannings` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);		
	
	
	
	
	
	
	ALTER TABLE activities_users

	ADD(
		CONSTRAINT FK_ACTIVITIESUSERS_ACTIVITIES FOREIGN KEY (`id_activity`) REFERENCES `activities` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_ACTIVITIESUSERS_USERS FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);			
	
	
	
	ALTER TABLE courses_users

	ADD(
		CONSTRAINT FK_COURSESUSERS_COURSES FOREIGN KEY (`id_course`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_COURSESUSERS_USERS FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




	ALTER TABLE sections_users

	ADD(
		CONSTRAINT FK_SECTIONSUSERS_SECTIONS FOREIGN KEY (`id_section`) REFERENCES `sections` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_SECTIONSUSERS_USERS FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);
	
	
	
	
	
	
	ALTER TABLE documents_users

	ADD(
		CONSTRAINT FK_DOCUMENTSUSERS_DOCUMENTS FOREIGN KEY (`id_document`) REFERENCES `documents` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_DOCUMENTSUSERS_USERS FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);	
	
	
	
	
	
	

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				AJOUT DE DATA RECORDS = POPULATING TABLES
--              ATTENTION NE PAS OUBLIER LE COMMIT; Á LA FIN
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



INSERT INTO `permissions` (`id`, `label`, `abbreviation`, `description`) VALUES
(1, 'DeleteAnyUser', 'D-USER-01', 'Cette permission permet de supprimer n\'importe lequel des comptes-utilisateurs.'),
(2, 'DeleteOwnUser', 'D-USER-02', 'Cette permission permet de supprimer son propre compte-utilisateur.'),
(3, 'DeleteAnyRole', 'D-ROLE-01', 'Cette permission permet de supprimer un rôle.'),
(4, 'CreateNewRole', 'C-ROLE-01', 'Cette permission permet de créer un nouveau rôle et de lui attribuer des permissions.'),
(5, 'ReadAnyRole', 'R-ROLE-01', 'Cette permission permet de lire la liste des rôles.'),
(6, 'CreateNewUser', 'C-USER-01', 'Cette permission permet de créer un nouveau compte utilisateur'),
(7, 'ReadAnyPermission', 'R-PERM-01', 'Cette permission permet de lire la liste des permissions'),
(8, 'DeleteAnyPermission', 'D-PERM-01', 'Cette permission permet de supprimer une permission'),
(9, 'CreateNewPermission', 'C-PERM-01', 'Cette permission permet de créer une nouvelle permission'),
(10, 'UpdateAnyPermission', 'U-PERM-01', 'Cette permission permet de mettre à jour n\'importe laquelle des permissions.'),
(11, 'UpdateAnyRole', 'U-ROLE-01', 'Cette permission permet de mettre à jour n\'importe lequel des rôles'),
(12, 'UpdateAnyUser', 'U-USER-01', 'Cette permission permet de mettre à jour n\'importe lequel des comptes utilisateurs'),
(13, 'ReadAnyUser', 'R-USER-01', 'Cette permission permet de lire la liste des utilisateurs');



INSERT INTO `roles` (`id`, `label`, `abbreviation`, `description`) VALUES
(1, 'Eleve 1', 'ELE-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant qu\'élèves de l\'école'),
(2, 'Parent 1', 'PAR-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que parents d\'élève'),
(3, 'Professeur 1', 'PRO-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que professeurs'),
(4, 'Secretaire 1', 'SEC-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que secrétaires'),
(5, 'Directeur 1', 'DIR-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que directeurs d\'école.'),
(6, 'Administrateur 1', 'ADM-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant qu\'administrateurs.'),
(7, 'Stagiaire 1', 'STA-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que professeurs-stagiaires.'),
(8, 'Stagiaire 2', 'STA-02', 'Ce rôle est attribué aux utilisateurs identifiés en tant que secrétaires-stagiaires.');



INSERT INTO `roles_permissions` (`id`, `id_role`, `id_permission`) VALUES
(30, 1, 2),
(32, 1, 5),
(31, 1, 13),
(14, 5, 1),
(15, 5, 2),
(16, 5, 3),
(17, 5, 4),
(18, 5, 5),
(19, 5, 6),
(20, 5, 7),
(21, 5, 8),
(22, 5, 9),
(23, 5, 10),
(24, 5, 11),
(25, 5, 12),
(26, 5, 13),
(6, 6, 1),
(7, 6, 2),
(5, 6, 3),
(2, 6, 4),
(9, 6, 5),
(3, 6, 6),
(8, 6, 7),
(4, 6, 8),
(1, 6, 9),
(11, 6, 10),
(12, 6, 11),
(13, 6, 12),
(10, 6, 13);



INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `password`, `phone_number`, `birthdate`, `gender`, `email_address`, `active`, `inscription_date`, `title`, `photo`, `id_address`, `id_role`, `id_school`, `id_parent`) VALUES
(1, 'Viktor', 'Ganise', 'ladministrateur01', 'mdp', NULL, '1991-01-01', 'Neutre', 'viktor.ganise@gmail.com', 1, '2020-08-09 17:46:03', 'Mr. l\'Administrateur', NULL, NULL, 6, NULL, NULL),
(2, 'Jean', 'Seigne', 'leprofesseur01', 'mdp', NULL, '1990-01-01', 'Masculin', 'jean.seigne@gmail.com', 1, '2020-08-09 17:46:03', '', NULL, NULL, 3, NULL, NULL),
(3, 'Arya', 'Secret', 'lasecretaire01', 'mdp', NULL, '1994-01-01', 'Féminin', 'arya.secret@gmail.com', 1, '2020-08-09 17:46:03', 'Mme la Secrétaire', NULL, NULL, 4, NULL, NULL),
(4, 'Moundir', 'Ecteur', 'ledirecteur01', 'mdp', NULL, '1985-02-02', 'Masculin', 'moundir.ecteur@gmail.com', 1, '2020-08-09 17:46:03', 'Mr. le Directeur', NULL, NULL, 5, NULL, NULL),
(5, 'Gaspard', 'Ent', 'leparent01', 'mdp', NULL, '1970-03-03', 'Masculin', 'gaspard.ent@gmail.com', 1, '2020-08-09 17:46:03', 'Mr. le Parent', NULL, NULL, 2, NULL, NULL),
(6, 'Michael', 'Eve', 'leleve01', 'mdp', NULL, '2005-05-05', 'Masculin', 'michael.eve@gmail.com', 1, '2020-08-09 17:46:03', 'L\'Élève', NULL, NULL, 1, NULL, NULL),
(7, 'Callista', 'Giaire', 'lastagiaire01', 'mdp', NULL, '2002-02-02', 'Féminin', 'callista.giaire@gmail.com', 1, '2020-08-09 17:46:03', 'Mme la Stagiaire-professeure', NULL, NULL, 7, NULL, NULL),
(8, 'Kosta', 'Giaire', 'lestagiaire01', 'mdp', NULL, '2001-01-01', 'Masculin', 'kosta.giaire@gmail.com', 1, '2020-08-09 17:46:03', 'Mr le Stagiaire-secrétaire', NULL, NULL, 8, NULL, NULL),
(9, 'Enf', 'Ent', 'lenfant01', 'mdp', NULL, '2004-04-04', 'Masculin', 'enf.ent@gmail.com', 1, '2020-08-09 17:49:31', 'Mr.', NULL, NULL, 1, NULL, 5);


COMMIT;















