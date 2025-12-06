
import { Outlet } from "react-router-dom";
import SidebarTeacher from "../components/SidebarTeacher";
import Navbar from "../components/Navbar";

export default function TeacherLayout() {
  return (
    <div className="flex">
      <SidebarTeacher />
      <div className="flex-1">
        <Navbar title="Teacher Panel" />
        <main className="p-6"><Outlet /></main>
      </div>
    </div>
  );
}
