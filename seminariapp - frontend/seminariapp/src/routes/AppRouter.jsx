import { Route, Routes } from 'react-router-dom'
import AppLayout from '../App.jsx'
import ProtectedRoute from '../components/ProtectedRoute.jsx'
import RoleRoute from '../components/RoleRoute.jsx'
import { ROLES } from '../utils/roles.js'
import Home from '../pages/Home.jsx'
import Login from '../pages/Login.jsx'
import Register from '../pages/Register.jsx'
import Profile from '../pages/Profile.jsx'
import AdminDashboard from '../pages/AdminDashboard.jsx'
import AdminEvents from '../pages/AdminEvents.jsx'
import Payment from '../pages/Payment.jsx'
import Materials from '../pages/Materials.jsx'
import EventDetail from '../pages/EventDetail.jsx'
import Attendance from '../pages/Attendance.jsx'
import Certificates from '../pages/Certificates.jsx'

const AppRouter = () => {
  return (
    <AppLayout>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/registro" element={<Register />} />
        <Route
          path="/perfil"
          element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              <RoleRoute roles={[ROLES.ADMIN]}>
                <AdminDashboard />
              </RoleRoute>
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/eventos"
          element={
            <ProtectedRoute>
              <RoleRoute roles={[ROLES.ADMIN, ROLES.COORDINADOR]}>
                <AdminEvents />
              </RoleRoute>
            </ProtectedRoute>
          }
        />
        <Route
          path="/pagos/:inscripcionId"
          element={
            <ProtectedRoute>
              <Payment />
            </ProtectedRoute>
          }
        />
        <Route
          path="/eventos/:eventoId"
          element={
            <ProtectedRoute>
              <EventDetail />
            </ProtectedRoute>
          }
        />
        <Route
          path="/materiales"
          element={
            <ProtectedRoute>
              <RoleRoute roles={[ROLES.PONENTE, ROLES.COORDINADOR, ROLES.ADMIN]}>
                <Materials />
              </RoleRoute>
            </ProtectedRoute>
          }
        />
        <Route
          path="/asistencia/:eventoId"
          element={
            <ProtectedRoute>
              <RoleRoute roles={[ROLES.PONENTE, ROLES.COORDINADOR, ROLES.ADMIN]}>
                <Attendance />
              </RoleRoute>
            </ProtectedRoute>
          }
        />
        <Route
          path="/certificados"
          element={
            <ProtectedRoute>
              <Certificates />
            </ProtectedRoute>
          }
        />
        <Route path="*" element={<Home />} />
      </Routes>
    </AppLayout>
  )
}

export default AppRouter
