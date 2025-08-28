import { createContext, useContext, useMemo, useState } from "react";

const CartContext = createContext(null);

export function CartProvider({ children }) {
	const [items, setItems] = useState(() => {
		try {
			const stored = localStorage.getItem("cart_items");
			return stored ? JSON.parse(stored) : [];
		} catch (_) {
			return [];
		}
	});

	function persist(next) {
		setItems(next);
		try {
			localStorage.setItem("cart_items", JSON.stringify(next));
		} catch (_) {}
	}

	function addItem(menuItem, quantity = 1) {
		persist((prev => {
			const existing = items.find(i => i.id === menuItem.id);
			if (existing) {
				return items.map(i => i.id === menuItem.id ? { ...i, quantity: i.quantity + quantity } : i);
			}
			return [...items, { ...menuItem, quantity }];
		})(items));
	}

	function updateQuantity(id, quantity) {
		persist(items.map(i => i.id === id ? { ...i, quantity } : i));
	}

	function removeItem(id) {
		persist(items.filter(i => i.id !== id));
	}

	function clear() {
		persist([]);
	}

	const total = items.reduce((sum, i) => sum + (i.price ?? 0) * (i.quantity ?? 0), 0);

	const value = useMemo(() => ({ items, addItem, updateQuantity, removeItem, clear, total }), [items, total]);

	return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export function useCart() {
	const ctx = useContext(CartContext);
	if (!ctx) throw new Error("useCart must be used within CartProvider");
	return ctx;
}


