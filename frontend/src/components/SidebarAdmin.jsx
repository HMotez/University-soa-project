
import { Link } from "react-router-dom";

export default function SidebarAdmin() {
  return (
    <aside className="w-64 h-screen bg-white dark:bg-gray-800 shadow">
      <h2 className="p-4 text-xl font-bold text-blue-600 dark:text-blue-400">Admin</h2>
      <nav className="px-4 space-y-2">
        <Link to="/admin" className="block hover:bg-gray-200 dark:hover:bg-gray-700 p-2 rounded">Dashboard</Link>
        <Link to="/admin/students" className="block hover:bg-gray-200 dark:hover:bg-gray-700 p-2 rounded">Students</Link>
      </nav>
    </aside>
  );
}
