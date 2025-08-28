import { Link, NavLink } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { useCart } from "../contexts/CartContext";

export default function Navbar() {
	const { isAuthenticated, isAdmin, logout } = useAuth();
	const { items } = useCart();
	const cartCount = items.reduce((sum, i) => sum + (i.quantity ?? 0), 0);

	const linkStyle = ({ isActive }) => ({ textDecoration: "none", padding: "6px 8px", borderRadius: 8, background: isActive ? "#f3f4f6" : "transparent" });

	return (
		<nav style={{ display: "flex", gap: 8, padding: 12, borderBottom: "1px solid #eee", alignItems: "center", flexWrap: "wrap" }}>
			<NavLink to="/" style={linkStyle}>Menu</NavLink>
			<NavLink to="/cart" style={linkStyle}>Cart ({cartCount})</NavLink>
			{isAuthenticated && <NavLink to="/orders" style={linkStyle}>My Orders</NavLink>}
			{isAdmin && (
				<>
					<NavLink to="/admin" style={linkStyle}>Admin Home</NavLink>
				</>
			)}
			<span style={{ flex: 1 }} />
			{isAuthenticated ? (
				<button onClick={logout} style={{ padding: "8px 12px", borderRadius: 8 }}>Logout</button>
			) : (
				<>
					<NavLink to="/login" style={linkStyle}>Login</NavLink>
					<NavLink to="/register" style={linkStyle}>Register</NavLink>
				</>
			)}
		</nav>
	);
}


