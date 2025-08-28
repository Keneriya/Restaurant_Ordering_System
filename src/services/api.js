// Minimal fetch-based API client with auth token support

const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

function getAuthToken() {
	try {
		return localStorage.getItem("auth_token");
	} catch (_) {
		return null;
	}
}

async function request(path, { method = "GET", body, headers = {}, signal } = {}) {
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
		signal,
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
	login: (credentials, opts) => request("/auth/login", { method: "POST", body: credentials, ...(opts || {}) }),
	register: (payload, opts) => request("/auth/register", { method: "POST", body: payload, ...(opts || {}) }),
	me: (opts) => request("/auth/me", { ...(opts || {}) }),

	// Menu
	getMenu: (opts) => request("/menu", { ...(opts || {}) }),
	createMenuItem: (item, opts) => request("/menu", { method: "POST", body: item, ...(opts || {}) }),
	updateMenuItem: (id, item, opts) => request(`/menu/${id}`, { method: "PUT", body: item, ...(opts || {}) }),
	deleteMenuItem: (id, opts) => request(`/menu/${id}`, { method: "DELETE", ...(opts || {}) }),

	// Orders
	createOrder: (order, opts) => request("/orders", { method: "POST", body: order, ...(opts || {}) }),
	getMyOrders: (opts) => request("/orders/my", { ...(opts || {}) }),
	getAllOrders: (opts) => request("/orders", { ...(opts || {}) }),
	updateOrderStatus: (id, status, opts) => request(`/orders/${id}/status`, { method: "PUT", body: { status }, ...(opts || {}) }),
};

export default api;


