# Cahier des charges – Système Universitaire SOA

## 1. Contexte

L’université souhaite moderniser son système d’information en adoptant une architecture orientée services (SOA).
L’objectif est de séparer les fonctionnalités en plusieurs services indépendants (REST et SOAP) afin de faciliter :
- l’évolutivité,
- la maintenabilité,
- l’interopérabilité entre différentes technologies. 

Ce mini-projet est réalisé dans le cadre du module **Architecture SOA et Services Web**, encadré par Mme **Ghada Feki**.

## 2. Objectifs du projet

- Mettre en place une **architecture SOA complète** pour un système de gestion universitaire.
- Développer plusieurs **services web** hétérogènes (REST + SOAP).
- Assurer la **sécurité** via un service d’authentification (JWT).
- Permettre l’**interopérabilité** entre différentes technologies (Spring Boot, Node.js, etc.).
- Préparer le projet au **déploiement conteneurisé** (Docker / Docker Compose).
- Fournir une **documentation technique** et un **manuel d’utilisation**.
- Présenter une **soutenance** avec démonstration fonctionnelle.

## 3. Périmètre fonctionnel

Le système couvre les domaines suivants :

### 3.1 Gestion des utilisateurs & authentification

- Création de comptes (admin, enseignant, étudiant).
- Authentification (login/mot de passe).
- Génération d’un jeton **JWT**.
- Gestion des rôles et des droits d’accès.

### 3.2 Gestion des étudiants

- Créer, modifier, supprimer un étudiant.
- Consulter les informations d’un étudiant.
- Lister les étudiants par filière, niveau, groupe.

### 3.3 Gestion des cours et emplois du temps

- Créer un cours (code, nom, enseignant, niveau).
- Modifier / supprimer un cours.
- Associer un cours à un groupe et un horaire.
- Consulter l’emploi du temps.

### 3.4 Gestion des notes

- Saisir les notes d’un étudiant pour un cours.
- Calculer la moyenne.
- Déterminer l’état : **ACQUIS** / **RATTRAPAGE**.

### 3.5 Gestion de la facturation

- Calcul des frais universitaires (inscription, scolarité, pénalités…).
- Gestion des paiements.
- Génération de justificatifs / reçus.

### 3.6 API Gateway

- Fournir un point d’entrée unique pour les clients (front-end, Postman).
- Router les requêtes vers les services internes.
- Option d’agrégation de données (ex : fiche étudiant = infos + notes + facturation).

## 4. Périmètre non fonctionnel

- **Performance** : temps de réponse acceptable en environnement local.
- **Sécurité** :
  - Authentification par JWT.
  - Protection des endpoints sensibles.
- **Interopérabilité** :
  - Services développés avec différentes technologies backend.
- **Scalabilité** :
  - Services découplés pouvant être déployés séparément.
- **Déploiement** :
  - Utilisation de Docker et docker-compose.

## 5. Acteurs

- **Administrateur** : gère les utilisateurs, les cours, la facturation.
- **Enseignant** : saisit les notes.
- **Étudiant** : consulte ses informations (notes, cours, factures).



