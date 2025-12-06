import { Routes, Route } from "react-router-dom";
import Register from "../pages/Register.jsx";
import DashboardAdmin from "../pages/DashboardAdmin.jsx";
import DashboardTeacher from "../pages/teacher/DashboardTeacher.jsx";
import Students from "../pages/admin/students/Students.jsx";

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Register />} />
      <Route path="/dashboard-admin" element={<DashboardAdmin />} />
      <Route path="/dashboard-teacher" element={<DashboardTeacher />} />
      <Route path="/students" element={<Students />} />
    </Routes>
  );
}
