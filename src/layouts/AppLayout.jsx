import { Outlet } from "react-router-dom";
import Navbar from "../components/Navbar";

export default function AppLayout() {
	return (
		<div>
			<div className="app-bg" />
			<Navbar />
			<Outlet />
		</div>
	);
}


