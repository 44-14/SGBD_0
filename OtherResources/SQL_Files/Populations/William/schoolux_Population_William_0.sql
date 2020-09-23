-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 09 août 2020 à 19:46
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `schoolux`
--

--
-- Déchargement des données de la table `permissions`
--

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

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id`, `label`, `abbreviation`, `description`) VALUES
(1, 'Eleve 1', 'ELE-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant qu\'élèves de l\'école'),
(2, 'Parent 1', 'PAR-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que parents d\'élève'),
(3, 'Professeur 1', 'PRO-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que professeurs'),
(4, 'Secretaire 1', 'SEC-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que secrétaires'),
(5, 'Directeur 1', 'DIR-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que directeurs d\'école.'),
(6, 'Administrateur 1', 'ADM-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant qu\'administrateurs.'),
(7, 'Stagiaire 1', 'STA-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que professeurs-stagiaires.'),
(8, 'Stagiaire 2', 'STA-02', 'Ce rôle est attribué aux utilisateurs identifiés en tant que secrétaires-stagiaires.');

--
-- Déchargement des données de la table `roles_permissions`
--

INSERT INTO `roles_permissions` (`id`, `id_role`, `id_permission`) VALUES
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

--
-- Déchargement des données de la table `users`
--

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
