import { useEffect, useState } from "react";
import { api } from "../../services/api";

export default function AdminMenuItems() {
	const [items, setItems] = useState([]);
	const [draft, setDraft] = useState({ name: "", price: 0, description: "", imageUrl: "" });
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const abort = new AbortController();
		(async () => {
			try {
				const data = await api.getMenu({ signal: abort.signal });
				setItems(data ?? []);
			} finally {
				setLoading(false);
			}
		})();
		return () => abort.abort();
	}, []);

	async function addItem() {
		if (!draft.name) return;
		const created = await api.createMenuItem({ ...draft, price: Number(draft.price) });
		setItems(prev => [created, ...prev]);
		setDraft({ name: "", price: 0, description: "", imageUrl: "" });
	}

	async function removeItem(id) {
		await api.deleteMenuItem(id);
		setItems(prev => prev.filter(i => i.id !== id));
	}

	if (loading) return <div style={{ padding: 16 }}>Loading menu items...</div>;

	return (
		<div style={{ padding: 16 }}>
			<h2>Menu Items</h2>
			<div style={{ display: "grid", gap: 8, maxWidth: 520 }}>
				<input placeholder="Name" value={draft.name} onChange={e => setDraft({ ...draft, name: e.target.value })} />
				<input placeholder="Price" type="number" value={draft.price} onChange={e => setDraft({ ...draft, price: e.target.value })} />
				<input placeholder="Image URL" value={draft.imageUrl} onChange={e => setDraft({ ...draft, imageUrl: e.target.value })} />
				<textarea placeholder="Description" value={draft.description} onChange={e => setDraft({ ...draft, description: e.target.value })} />
				<button onClick={addItem}>Add Item</button>
			</div>
			<div style={{ marginTop: 16, display: "grid", gap: 12 }}>
				{items.map(item => (
					<div key={item.id} style={{ border: "1px solid #eee", borderRadius: 8, padding: 12 }}>
						<div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
							<strong>{item.name}</strong>
							<div>
								<button onClick={() => removeItem(item.id)}>Delete</button>
							</div>
						</div>
						<div>${Number(item.price).toFixed(2)}</div>
						<p style={{ color: "#666" }}>{item.description}</p>
					</div>
				))}
			</div>
		</div>
	);
}


