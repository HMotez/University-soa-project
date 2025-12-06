
import { Outlet } from "react-router-dom";
import SidebarAdmin from "../components/SidebarAdmin";
import Navbar from "../components/Navbar";

export default function AdminLayout() {
  return (
    <div className="flex">
      <SidebarAdmin />
      <div className="flex-1">
        <Navbar title="Admin Panel" />
        <main className="p-6"><Outlet /></main>
      </div>
    </div>
  );
}
