import { useEffect, useMemo, useState } from "react";
import { api } from "../services/api";

function StatusSelector({ value, onChange }) {
	const options = ["PENDING", "PREPARING", "COMPLETED"];
	return (
		<select value={value} onChange={e => onChange(e.target.value)}>
			{options.map(o => (
				<option key={o} value={o}>{o}</option>
			))}
		</select>
	);
}

export default function AdminDashboard() {
	const [menu, setMenu] = useState([]);
	const [orders, setOrders] = useState([]);
	const [loading, setLoading] = useState(true);

	const emptyItem = useMemo(() => ({ name: "", price: 0, description: "", imageUrl: "" }), []);
	const [draft, setDraft] = useState(emptyItem);

	useEffect(() => {
		const abort = new AbortController();
		(async () => {
			try {
				const [m, o] = await Promise.all([
					api.getMenu({ signal: abort.signal }),
					api.getAllOrders({ signal: abort.signal }),
				]);
				setMenu(m ?? []);
				setOrders(o ?? []);
			} finally {
				setLoading(false);
			}
		})();
		return () => abort.abort();
	}, [emptyItem]);

	async function saveItem() {
		if (!draft.name) return;
		const created = await api.createMenuItem({ ...draft, price: Number(draft.price) });
		setMenu(prev => [created, ...prev]);
		setDraft(emptyItem);
	}

	async function updateItem(id, changes) {
		const updated = await api.updateMenuItem(id, changes);
		setMenu(prev => prev.map(i => i.id === id ? updated : i));
	}

	async function deleteItem(id) {
		await api.deleteMenuItem(id);
		setMenu(prev => prev.filter(i => i.id !== id));
	}

	async function changeOrderStatus(id, status) {
		const updated = await api.updateOrderStatus(id, status);
		setOrders(prev => prev.map(o => o.id === id ? updated : o));
	}

	if (loading) return <div style={{ padding: 16 }}>Loading admin data...</div>;

	return (
		<div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 24, padding: 16 }}>
			<section>
				<h2>Menu Management</h2>
				<div style={{ display: "grid", gap: 8, maxWidth: 520 }}>
					<input placeholder="Name" value={draft.name} onChange={e => setDraft({ ...draft, name: e.target.value })} />
					<input placeholder="Price" type="number" value={draft.price} onChange={e => setDraft({ ...draft, price: e.target.value })} />
					<input placeholder="Image URL" value={draft.imageUrl} onChange={e => setDraft({ ...draft, imageUrl: e.target.value })} />
					<textarea placeholder="Description" value={draft.description} onChange={e => setDraft({ ...draft, description: e.target.value })} />
					<button onClick={saveItem}>Add Item</button>
				</div>
				<div style={{ marginTop: 16, display: "grid", gap: 12 }}>
					{menu.map(item => (
						<div key={item.id} style={{ border: "1px solid #eee", borderRadius: 8, padding: 12 }}>
							<div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
								<strong>{item.name}</strong>
								<div>
									<button onClick={() => updateItem(item.id, { price: Number(item.price) + 1 })}>+ $1</button>
									<button onClick={() => deleteItem(item.id)} style={{ marginLeft: 8 }}>Delete</button>
								</div>
							</div>
							<div>${Number(item.price).toFixed(2)}</div>
							<p style={{ color: "#666" }}>{item.description}</p>
						</div>
					))}
				</div>
			</section>

			<section>
				<h2>Orders</h2>
				<div style={{ display: "grid", gap: 12 }}>
					{orders.map(order => (
						<div key={order.id} style={{ border: "1px solid #eee", borderRadius: 8, padding: 12 }}>
							<div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
								<strong>Order #{order.id}</strong>
								<StatusSelector value={order.status} onChange={(s) => changeOrderStatus(order.id, s)} />
							</div>
							<ul>
								{order.items?.map(oi => (
									<li key={oi.id}>{oi.menuItem?.name ?? oi.menuItemName} x {oi.quantity}</li>
								))}
							</ul>
						</div>
					))}
				</div>
			</section>
		</div>
	);
}


