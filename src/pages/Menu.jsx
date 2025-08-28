import { useEffect, useMemo, useState } from "react";
import { api } from "../services/api";
import { useCart } from "../contexts/CartContext";

export default function MenuPage() {
	const [menu, setMenu] = useState([]);
	const [loading, setLoading] = useState(true);
	const { addItem } = useCart();

	useEffect(() => {
		const abort = new AbortController();
		(async () => {
			try {
				const data = await api.getMenu({ signal: abort.signal });
				setMenu(data ?? []);
			} finally {
				setLoading(false);
			}
		})();
		return () => abort.abort();
	}, []);

	if (loading) return <div style={{ padding: 16 }}>Loading menu...</div>;

	return (
		<div style={{ padding: 16 }}>
			<h2 style={{ color: "#111827" }}>Menu</h2>
			<div style={{ display: "grid", gap: 16, gridTemplateColumns: "repeat(auto-fill, minmax(240px, 1fr))" }}>
				{menu.map(item => (
					<div key={item.id} style={{ border: "1px solid #e5e7eb", borderRadius: 12, padding: 12, background: "#ffffffcc", backdropFilter: "blur(2px)" }}>
						{item.imageUrl && (
							<img src={item.imageUrl} alt={item.name} style={{ width: "100%", height: 160, objectFit: "cover", borderRadius: 10 }} />
						)}
						<h3 style={{ margin: "10px 0 6px", color: "#111827" }}>{item.name}</h3>
						<p style={{ color: "#374151", minHeight: 44 }}>{item.description}</p>
						<div style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
							<strong style={{ color: "#111827" }}>${Number(item.price).toFixed(2)}</strong>
							<button onClick={() => addItem(item, 1)} style={{ padding: "8px 12px", borderRadius: 8, background: "#7c5cff", color: "#fff", border: 0 }}>Add to cart</button>
						</div>
					</div>
				))}
			</div>
		</div>
	);
}


