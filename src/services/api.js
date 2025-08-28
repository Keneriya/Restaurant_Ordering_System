// Minimal fetch-based API client with auth token support

const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

function getAuthToken() {
	try {
		return localStorage.getItem("auth_token");
	} catch (_) {
		return null;
	}
}

async function request(path, { method = "GET", body, headers = {} } = {}) {
	const token = getAuthToken();
	const response = await fetch(`${BASE_URL}${path}`, {
		method,
		headers: {
			"Content-Type": body instanceof FormData ? undefined : "application/json",
			...(token ? { Authorization: `Bearer ${token}` } : {}),
			...headers,
		},
		body: body instanceof FormData ? body : body ? JSON.stringify(body) : undefined,
		credentials: "include",
	});

	if (!response.ok) {
		let errorBody = null;
		try {
			errorBody = await response.json();
		} catch (_) {}
		const error = new Error(errorBody?.message || `Request failed: ${response.status}`);
		error.status = response.status;
		error.details = errorBody;
		throw error;
	}

	// Try to parse JSON, fallback to text
	const text = await response.text();
	try {
		return text ? JSON.parse(text) : null;
	} catch (_) {
		return text;
	}
}

export const api = {
	// Auth
	login: (credentials) => request("/auth/login", { method: "POST", body: credentials }),
	register: (payload) => request("/auth/register", { method: "POST", body: payload }),
	me: () => request("/auth/me"),

	// Menu
	getMenu: () => request("/menu"),
	createMenuItem: (item) => request("/menu", { method: "POST", body: item }),
	updateMenuItem: (id, item) => request(`/menu/${id}`, { method: "PUT", body: item }),
	deleteMenuItem: (id) => request(`/menu/${id}`, { method: "DELETE" }),

	// Orders
	createOrder: (order) => request("/orders", { method: "POST", body: order }),
	getMyOrders: () => request("/orders/my"),
	getAllOrders: () => request("/orders"),
	updateOrderStatus: (id, status) => request(`/orders/${id}/status`, { method: "PUT", body: { status } }),
};

export default api;


