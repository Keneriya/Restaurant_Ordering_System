import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { api } from "../services/api";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
	const [user, setUser] = useState(null);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		(async () => {
			try {
				const token = localStorage.getItem("auth_token");
				if (token) {
					const me = await api.me();
					setUser(me);
				} else {
					setUser(null);
				}
			} catch (_) {
				setUser(null);
			} finally {
				setLoading(false);
			}
		})();
	}, []);

	async function login(credentials) {
		const res = await api.login(credentials);
		try {
			localStorage.setItem("auth_token", res?.token);
		} catch (_) {}
		setUser(res?.user ?? null);
		return res;
	}

	function logout() {
		try {
			localStorage.removeItem("auth_token");
		} catch (_) {}
		setUser(null);
	}

	const value = useMemo(() => ({
		user,
		loading,
		isAuthenticated: !!user,
		isAdmin: !!user && (user.role === "ADMIN" || user.roles?.includes?.("ADMIN")),
		login,
		logout,
	}), [user, loading]);

	return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
	const ctx = useContext(AuthContext);
	if (!ctx) throw new Error("useAuth must be used within AuthProvider");
	return ctx;
}


