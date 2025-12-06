import { createContext, useContext, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

export const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  // ======================
  // REGISTER FUNCTION
  // ======================
  const register = async (username, email, password, role) => {
    try {
      const res = await api.post(
        "/auth/register",
        { username, email, password, role },
        {
          headers: {
            Authorization: "", // IMPORTANT: REMOVE TOKEN
          },
        }
      );

      alert("Registration successful!");
      navigate("/"); // go to login page
    } catch (error) {
      console.error("REGISTER ERROR:", error);
      alert("Registration failed");
    }
  };

  // ======================
  // LOGIN FUNCTION
  // ======================
  const login = async (username, password) => {
    try {
      const res = await api.post(
        "/auth/login",
        { username, password },
        {
          headers: {
            Authorization: "", // login must not send token
          },
        }
      );

      const token = res.data.accessToken;
      localStorage.setItem("token", token);
      setUser({ username });

      alert("Login successful!");
      navigate("/admin"); // temporary route

    } catch (error) {
      console.error("LOGIN ERROR:", error);
      alert("Login failed");
    }
  };

  return (
    <AuthContext.Provider value={{ user, login, register }}>
      {children}
    </AuthContext.Provider>
  );
};
