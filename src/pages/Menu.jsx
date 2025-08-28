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
		<div style={{ display: "grid", gap: 16, padding: 16, gridTemplateColumns: "repeat(auto-fill, minmax(220px, 1fr))" }}>
			{menu.map(item => (
				<div key={item.id} style={{ border: "1px solid #eee", borderRadius: 8, padding: 12 }}>
					{item.imageUrl && (
						<img src={item.imageUrl} alt={item.name} style={{ width: "100%", height: 140, objectFit: "cover", borderRadius: 6 }} />
					)}
					<h3 style={{ margin: "8px 0" }}>{item.name}</h3>
					<p style={{ color: "#666", minHeight: 40 }}>{item.description}</p>
					<div style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
						<strong>${Number(item.price).toFixed(2)}</strong>
						<button onClick={() => addItem(item, 1)}>Add</button>
					</div>
				</div>
			))}
		</div>
	);
}


