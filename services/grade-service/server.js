const express = require("express");
const cors = require("cors");
const dotenv = require("dotenv");
const connectDB = require("./config/db");

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());

// connect DB
connectDB();

// routes
app.use("/grades", require("./routes/gradeRoutes"));

const PORT = process.env.PORT || 5002;
app.listen(PORT, () => console.log(`Grade Service running on port ${PORT}`));
