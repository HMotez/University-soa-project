const mongoose = require("mongoose");

const GradeSchema = new mongoose.Schema({
  studentId: { type: String, required: true },
  courseId: { type: String, required: true },
  noteDS: { type: Number, required: true },
  noteExam: { type: Number, required: true },
  moyenne: { type: Number },
  etat: { type: String, enum: ["ACQUIS", "RATTRAPAGE"] }
});

module.exports = mongoose.model("Grade", GradeSchema);
