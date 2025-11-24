const express = require('express');
const cors = require('cors');

const app = express();

// Middleware pour JSON
app.use(cors());
app.use(express.json());

// --- Données en mémoire pour commencer (pas de base de données) ---
let students = []; // tableau d'étudiants en mémoire
let nextId = 1;

// Route de test
app.get('/', (req, res) => {
  res.json({ message: 'Student service is running' });
});

// GET /students -> liste de tous les étudiants
app.get('/students', (req, res) => {
  res.json(students);
});

// GET /students/:id -> un étudiant par id
app.get('/students/:id', (req, res) => {
  const id = parseInt(req.params.id);
  const student = students.find(s => s.id === id);

  if (!student) {
    return res.status(404).json({ error: 'Student not found' });
  }

  res.json(student);
});

// POST /students -> ajouter un étudiant
app.post('/students', (req, res) => {
  const { firstName, lastName, email, filiere, niveau, groupe, matricule } = req.body;

  if (!firstName || !lastName || !email) {
    return res.status(400).json({ error: 'firstName, lastName and email are required' });
  }

  const newStudent = {
    id: nextId++,
    firstName,
    lastName,
    email,
    filiere: filiere || '',
    niveau: niveau || '',
    groupe: groupe || '',
    matricule: matricule || ''
  };

  students.push(newStudent);
  res.status(201).json(newStudent);
});

// PUT /students/:id -> modifier un étudiant
app.put('/students/:id', (req, res) => {
  const id = parseInt(req.params.id);
  const student = students.find(s => s.id === id);

  if (!student) {
    return res.status(404).json({ error: 'Student not found' });
  }

  const { firstName, lastName, email, filiere, niveau, groupe, matricule } = req.body;

  student.firstName = firstName ?? student.firstName;
  student.lastName  = lastName  ?? student.lastName;
  student.email     = email     ?? student.email;
  student.filiere   = filiere   ?? student.filiere;
  student.niveau    = niveau    ?? student.niveau;
  student.groupe    = groupe    ?? student.groupe;
  student.matricule = matricule ?? student.matricule;

  res.json(student);
});

// DELETE /students/:id -> supprimer un étudiant
app.delete('/students/:id', (req, res) => {
  const id = parseInt(req.params.id);
  const index = students.findIndex(s => s.id === id);

  if (index === -1) {
    return res.status(404).json({ error: 'Student not found' });
  }

  students.splice(index, 1);
  res.json({ message: 'Student deleted successfully' });
});

// Lancer le serveur
const PORT = process.env.PORT || 3001;
app.listen(PORT, () => {
  console.log(` Student service running on port ${PORT}`);
});
