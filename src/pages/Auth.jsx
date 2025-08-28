import { useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import { Link, Navigate, useLocation, useNavigate } from "react-router-dom";
import "../styles/auth.css";

export function LoginPage() {
	const { isAuthenticated, login } = useAuth();
	const location = useLocation();
	const navigate = useNavigate();
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");
	const [loading, setLoading] = useState(false);

	if (isAuthenticated) {
		const from = location.state?.from?.pathname || "/";
		return <Navigate to={from} replace />;
	}

	async function handleSubmit(e) {
		e.preventDefault();
		setError("");
		setLoading(true);
		try {
			const res = await login({ email, password });
			const role = res?.user?.role || (res?.user?.roles?.includes?.("ADMIN") ? "ADMIN" : "USER");
			if (role === "ADMIN") navigate("/admin", { replace: true }); else navigate("/", { replace: true });
		} catch (err) {
			setError(err?.message || "Invalid email or password");
		} finally {
			setLoading(false);
		}
	}

	return (
		<div className="auth-wrapper">
			<div className="auth-card">
				<div className="auth-brand">Smart Restaurant</div>
				<h2>Welcome back</h2>
				<p className="auth-sub">Sign in to continue to your dashboard</p>
				<form onSubmit={handleSubmit} className="auth-form">
					<label>Email</label>
					<input placeholder="you@example.com" value={email} onChange={e => setEmail(e.target.value)} />
					<label>Password</label>
					<input placeholder="••••••••" type="password" value={password} onChange={e => setPassword(e.target.value)} />
					{error && <div className="auth-error">{error}</div>}
					<div className="auth-actions">
						<button type="submit" className="auth-primary" disabled={loading}>{loading ? "Signing in..." : "Sign in"}</button>
						<Link to="/register" className="auth-secondary">Create account</Link>
					</div>
				</form>
				<div className="auth-footer">
					New here? <Link to="/register">Create an account</Link>
				</div>
			</div>
		</div>
	);
}

export function RegisterPage() {
	const navigate = useNavigate();
	const { login } = useAuth();
	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");
	const [loading, setLoading] = useState(false);

	async function handleRegister(e) {
		e.preventDefault();
		setError("");
		setLoading(true);
		try {
			const res = await fetch((import.meta.env.VITE_API_URL || "http://localhost:8080/api") + "/auth/register", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ name, email, password })
			});
			if (!res.ok) throw new Error("Registration failed");
			const loginRes = await login({ email, password });
			const role = loginRes?.user?.role || (loginRes?.user?.roles?.includes?.("ADMIN") ? "ADMIN" : "USER");
			if (role === "ADMIN") navigate("/admin", { replace: true }); else navigate("/", { replace: true });
		} catch (err) {
			setError(err?.message || "Registration failed");
		} finally {
			setLoading(false);
		}
	}

	return (
		<div className="auth-wrapper">
			<div className="auth-card">
				<div className="auth-brand">Smart Restaurant</div>
				<h2>Create your account</h2>
				<p className="auth-sub">Start ordering in minutes</p>
				<form onSubmit={handleRegister} className="auth-form">
					<label>Name</label>
					<input placeholder="Jane Doe" value={name} onChange={e => setName(e.target.value)} />
					<label>Email</label>
					<input placeholder="you@example.com" value={email} onChange={e => setEmail(e.target.value)} />
					<label>Password</label>
					<input placeholder="Create a password" type="password" value={password} onChange={e => setPassword(e.target.value)} />
					{error && <div className="auth-error">{error}</div>}
					<div className="auth-actions">
						<button type="submit" className="auth-primary" disabled={loading}>{loading ? "Creating..." : "Sign up"}</button>
						<Link to="/login" className="auth-secondary">Sign in instead</Link>
					</div>
				</form>
				<div className="auth-footer">
					Already have an account? <Link to="/login">Login</Link>
				</div>
			</div>
		</div>
	);
}


