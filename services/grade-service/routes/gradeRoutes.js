const express = require("express");
const Grade = require("../models/Grade");
const router = express.Router();

// Add a grade
router.post("/", async (req, res) => {
  try {
    const { studentId, courseId, noteDS, noteExam } = req.body;

    const moyenne = (noteDS * 0.4) + (noteExam * 0.6);
    const etat = moyenne >= 10 ? "ACQUIS" : "RATTRAPAGE";

    const grade = await Grade.create({
      studentId,
      courseId,
      noteDS,
      noteExam,
      moyenne,
      etat
    });

    res.json(grade);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// List grades of a student
router.get("/student/:studentId", async (req, res) => {
  const grades = await Grade.find({ studentId: req.params.studentId });
  res.json(grades);
});

// Average + final state
router.get("/student/:studentId/moyenne", async (req, res) => {
  const grades = await Grade.find({ studentId: req.params.studentId });

  if (!grades.length) return res.json({ message: "No grades found" });

  const moyGlobale =
    grades.reduce((sum, g) => sum + g.moyenne, 0) / grades.length;

  res.json({
    studentId: req.params.studentId,
    moyenne: moyGlobale,
    etat: moyGlobale >= 10 ? "ACQUIS" : "RATTRAPAGE"
  });
});

module.exports = router;
