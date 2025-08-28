import { useCart } from "../contexts/CartContext";
import { api } from "../services/api";

export default function CartPage() {
	const { items, updateQuantity, removeItem, clear, total } = useCart();

	async function placeOrder() {
		if (!items.length) return;
		const order = {
			items: items.map(i => ({ menuItemId: i.id, quantity: i.quantity, price: i.price })),
		};
		await api.createOrder(order);
		clear();
		alert("Order placed! Pending status.");
	}

	return (
		<div style={{ padding: 16 }}>
			<h2>Cart</h2>
			{items.length === 0 ? (
				<p>No items in cart.</p>
			) : (
				<>
					{items.map(item => (
						<div key={item.id} style={{ display: "flex", gap: 12, alignItems: "center", borderBottom: "1px solid #eee", padding: "8px 0" }}>
							<div style={{ flex: 1 }}>
								<strong>{item.name}</strong> - ${Number(item.price).toFixed(2)}
							</div>
							<input type="number" min={1} value={item.quantity} onChange={e => updateQuantity(item.id, Number(e.target.value))} style={{ width: 64 }} />
							<button onClick={() => removeItem(item.id)}>Remove</button>
						</div>
					))}
					<div style={{ display: "flex", justifyContent: "space-between", marginTop: 12 }}>
						<strong>Total: ${total.toFixed(2)}</strong>
						<button onClick={placeOrder}>Place Order</button>
					</div>
				</>
			)}
		</div>
	);
}


