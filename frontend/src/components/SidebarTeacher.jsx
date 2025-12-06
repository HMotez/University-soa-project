
import { Link } from "react-router-dom";

export default function SidebarTeacher() {
  return (
    <aside className="w-64 h-screen bg-white dark:bg-gray-800 shadow">
      <h2 className="p-4 text-xl font-bold text-green-600 dark:text-green-400">Teacher</h2>
      <nav className="px-4 space-y-2">
        <Link to="/teacher" className="block hover:bg-gray-200 dark:hover:bg-gray-700 p-2 rounded">Dashboard</Link>
      </nav>
    </aside>
  );
}
