import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export function RequireAuth() {
	const { isAuthenticated, loading } = useAuth();
	const location = useLocation();
	if (loading) return <div style={{ padding: 16 }}>Checking session...</div>;
	if (!isAuthenticated) {
		return <Navigate to="/login" replace state={{ from: location }} />;
	}
	return <Outlet />;
}

export function RequireAdmin() {
	const { isAuthenticated, isAdmin, loading } = useAuth();
	const location = useLocation();
	if (loading) return <div style={{ padding: 16 }}>Checking session...</div>;
	if (!isAuthenticated || !isAdmin) {
		return <Navigate to="/" replace state={{ from: location }} />;
	}
	return <Outlet />;
}


