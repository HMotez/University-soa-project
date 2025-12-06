import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Interceptor — attach token ONLY when needed
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  // Do NOT send token for login or register
  if (
    config.url.includes("/auth/login") ||
    config.url.includes("/auth/register")
  ) {
    return config;
  }

  // For all other endpoints, attach token
  if (token) config.headers.Authorization = `Bearer ${token}`;

  return config;
});

export default api;
