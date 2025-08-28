import { useEffect, useState } from "react";
import { api } from "../../services/api";

export default function AdminOrders() {
	const [orders, setOrders] = useState([]);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const abort = new AbortController();
		(async () => {
			try {
				const data = await api.getAllOrders({ signal: abort.signal });
				setOrders(data ?? []);
			} finally {
				setLoading(false);
			}
		})();
		return () => abort.abort();
	}, []);

	async function updateStatus(id, status) {
		const updated = await api.updateOrderStatus(id, status);
		setOrders(prev => prev.map(o => o.id === id ? updated : o));
	}

	if (loading) return <div style={{ padding: 16 }}>Loading orders...</div>;

	return (
		<div style={{ padding: 16 }}>
			<h2>Orders</h2>
			<div style={{ display: "grid", gap: 12 }}>
				{orders.map(order => (
					<div key={order.id} style={{ border: "1px solid #eee", borderRadius: 8, padding: 12 }}>
						<div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
							<strong>Order #{order.id}</strong>
							<select value={order.status} onChange={e => updateStatus(order.id, e.target.value)}>
								{["PENDING", "PREPARING", "COMPLETED"].map(s => <option key={s} value={s}>{s}</option>)}
							</select>
						</div>
						<ul>
							{order.items?.map(oi => (
								<li key={oi.id}>{oi.menuItem?.name ?? oi.menuItemName} x {oi.quantity}</li>
							))}
						</ul>
					</div>
				))}
			</div>
		</div>
	);
}


