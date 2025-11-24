# Spécifications techniques – Système Universitaire SOA

## 1. Architecture globale

Le système est composé de plusieurs services indépendants (SOA) :

- **Auth Service** : Spring Boot (REST, JWT)
- **Student Service** : Node.js / Express (REST)
- **Grade Service** : Node.js / Express (REST)
- **Course Service** : Spring Boot (SOAP)
- **Billing Service** : Spring Boot (SOAP)
- **API Gateway** : Spring Cloud Gateway (routage des requêtes)

Tous les services exposent des API accessibles en HTTP via l’API Gateway.

## 2. Modèle de données (vue simplifiée)

### 2.1. Utilisateurs (Auth Service)

- `id` : Long
- `username` : String (unique)
- `password` : String (hashé)
- `email` : String
- `role` : enum { ADMIN, TEACHER, STUDENT }

### 2.2. Étudiants (Student Service)

- `id` : String/ObjectId
- `firstName`
- `lastName`
- `email`
- `filiere`
- `niveau`
- `groupe`
- `cin` ou `matricule`

### 2.3. Cours (Course Service – SOAP)

- `id` : Long
- `code` : String
- `title` : String
- `teacherId` : Long ou String
- `niveau`
- `groupe`
- `horaire` : String (jour + créneau)

### 2.4. Notes (Grade Service)

- `id`
- `studentId`
- `courseId`
- `noteDS`
- `noteExam`
- `moyenne`
- `etat` : ACQUIS / RATTRAPAGE

### 2.5. Facturation (Billing Service)

- `id`
- `studentId`
- `montantInscription`
- `montantScolarite`
- `penalites`
- `total`
- `status` : PAYE / NON_PAYE

## 3. Spécifications des services

### 3.1. Auth Service (Spring Boot – REST)

**Base URL** : `/auth`

Endpoints :

- `POST /auth/register`
  - Body : `{ username, password, email, role }`
  - Fonction : enregistrer un nouvel utilisateur + retourner un JWT.
- `POST /auth/login`
  - Body : `{ username, password }`
  - Fonction : authentifier l’utilisateur + retourner un JWT.
- `GET /auth/me`
  - Header : `Authorization: Bearer <token>`
  - Fonction : renvoyer les infos de l’utilisateur connecté.

**Sécurité** :
- Spring Security + filtre JWT.
- Passwords hashés (BCrypt).

---

### 3.2. Student Service (Node.js – REST)

**Base URL** : `/students`

Endpoints :

- `GET /students` – liste des étudiants.
- `GET /students/:id` – détails d’un étudiant.
- `POST /students` – créer un étudiant.
- `PUT /students/:id` – modifier un étudiant.
- `DELETE /students/:id` – supprimer un étudiant.

Les requêtes sont protégées par un header :
`Authorization: Bearer <token>`.

---

### 3.3. Grade Service (Node.js – REST)

**Base URL** : `/grades`

Endpoints :

- `POST /grades` – ajouter une note.
- `GET /grades/student/:studentId` – lister les notes d’un étudiant.
- `GET /grades/student/:studentId/moyenne` – calculer la moyenne et l’état (ACQUIS / RATTRAPAGE).

La logique métier calcule :
`moyenne = (noteDS * 0.4) + (noteExam * 0.6)` (par exemple).

---

### 3.4. Course Service (Spring Boot – SOAP)

Service SOAP exposé via WSDL, avec opérations :

- `addCourse(Course course)`
- `updateCourse(Course course)`
- `getCourseById(id)`
- `listCourses()`
- `listCoursesByGroup(groupe)`

Le service sera déployé sur une URL du type :
`http://host:port/ws/courses`

---

### 3.5. Billing Service (Spring Boot – SOAP)

Service SOAP pour la facturation :

- `calculateFees(studentId)`
- `getInvoice(studentId)`
- `payInvoice(invoiceId)`

Logique :
- total = inscription + scolarité + pénalités.
- mise à jour du statut (PAYE / NON_PAYE).

---

### 3.6. API Gateway (Spring Cloud Gateway)

Les routes possibles :

- `/api/auth/**` → Auth Service
- `/api/students/**` → Student Service
- `/api/grades/**` → Grade Service
- `/api/courses/**` → Course Service (SOAP)
- `/api/billing/**` → Billing Service (SOAP)

Le Gateway propage le header `Authorization` vers les services internes.

---

## 4. Sécurité (JWT)

- Auth Service génère le JWT.
- Le client stocke le token.
- Chaque requête vers un service REST comporte :
  - `Authorization: Bearer <token>`
- Les services REST vérifient la validité du token.

---

## 5. Déploiement et Docker

Chaque service aura son propre `Dockerfile`.

Un fichier global `docker/docker-compose.yml` permettra de :

- lancer tous les services
- lancer les bases de données nécessaires
- configurer un réseau interne Docker

Commande :

```bash
docker-compose up --build
