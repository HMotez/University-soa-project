
import { Outlet } from "react-router-dom";
import SidebarStudent from "../components/SidebarStudent";
import Navbar from "../components/Navbar";

export default function StudentLayout() {
  return (
    <div className="flex">
      <SidebarStudent />
      <div className="flex-1">
        <Navbar title="Student Panel" />
        <main className="p-6"><Outlet /></main>
      </div>
    </div>
  );
}
