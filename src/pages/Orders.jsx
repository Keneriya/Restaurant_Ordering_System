import { useEffect, useState } from "react";
import { api } from "../services/api";

export default function OrdersPage() {
	const [orders, setOrders] = useState([]);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		(async () => {
			try {
				const data = await api.getMyOrders();
				setOrders(data ?? []);
			} finally {
				setLoading(false);
			}
		})();
	}, []);

	if (loading) return <div style={{ padding: 16 }}>Loading orders...</div>;

	return (
		<div style={{ padding: 16 }}>
			<h2>My Orders</h2>
			{orders.length === 0 ? (
				<p>No orders yet.</p>
			) : (
				<div style={{ display: "grid", gap: 12 }}>
					{orders.map(order => (
						<div key={order.id} style={{ border: "1px solid #eee", borderRadius: 8, padding: 12 }}>
							<div style={{ display: "flex", justifyContent: "space-between" }}>
								<strong>Order #{order.id}</strong>
								<span>Status: {order.status}</span>
							</div>
							<ul>
								{order.items?.map(oi => (
									<li key={oi.id}>
										{oi.menuItem?.name ?? oi.menuItemName} x {oi.quantity} - ${Number(oi.price).toFixed(2)}
									</li>
								))}
							</ul>
							<div>Total: ${Number(order.total ?? order.items?.reduce((s, i) => s + i.price * i.quantity, 0) ?? 0).toFixed(2)}</div>
						</div>
					))}
				</div>
			)}
		</div>
	);
}


