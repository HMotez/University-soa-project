
import ThemeToggle from "./ThemeToggle";

export default function Navbar({ title }) {
  return (
    <header className="bg-white dark:bg-gray-800 p-4 shadow flex justify-between">
      <h1 className="text-xl font-semibold dark:text-white">{title}</h1>
      <ThemeToggle />
    </header>
  );
}
