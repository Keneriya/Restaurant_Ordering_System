import { BrowserRouter, Routes, Route } from 'react-router-dom'
import AppLayout from './layouts/AppLayout'
import MenuPage from './pages/Menu'
import CartPage from './pages/Cart'
import OrdersPage from './pages/Orders'
import AdminDashboard from './pages/AdminDashboard'
import { LoginPage, RegisterPage } from './pages/Auth'
import { RequireAdmin, RequireAuth } from './routes/ProtectedRoute'
import AdminMenuItems from './pages/admin/MenuItems'
import AdminOrders from './pages/admin/Orders'
import AdminOrderItems from './pages/admin/OrderItems'
import AdminOrderStatus from './pages/admin/OrderStatus'
import './App.css'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route element={<RequireAuth />}>
          <Route element={<AppLayout />}>
            <Route path="/" element={<MenuPage />} />
            <Route path="/cart" element={<CartPage />} />
            <Route path="/orders" element={<OrdersPage />} />
            <Route element={<RequireAdmin />}>
              <Route path="/admin" element={<AdminDashboard />} />
              <Route path="/admin/menu-items" element={<AdminMenuItems />} />
              <Route path="/admin/orders" element={<AdminOrders />} />
              <Route path="/admin/order-items" element={<AdminOrderItems />} />
              <Route path="/admin/order-status" element={<AdminOrderStatus />} />
            </Route>
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
