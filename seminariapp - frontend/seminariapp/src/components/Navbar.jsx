import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext.jsx'
import { ROLES } from '../utils/roles.js'

const Navbar = () => {
  const navigate = useNavigate()
  const { user, isAuthenticated, logout } = useAuth()
  const role = user?.rol || user?.role

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <header
      className="card-surface"
      style={{
        borderRadius: 0,
        marginBottom: '1rem',
        position: 'sticky',
        top: 0,
        zIndex: 10,
        backdropFilter: 'blur(10px)',
      }}
    >
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', gap: '1rem' }}>
        <Link to="/" style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', fontWeight: 800, fontSize: '1.1rem' }}>
          <span
            style={{
              width: 38,
              height: 38,
              display: 'grid',
              placeItems: 'center',
              borderRadius: 12,
              background: 'linear-gradient(135deg, #1fa2ff, #12d8fa 60%, #1c66ff)',
              color: '#0a0f1a',
              fontWeight: 800,
            }}
          >
            S
          </span>
          Seminariapp
        </Link>

        <nav style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', flexWrap: 'wrap' }}>
          <Link to="/" className="tag nav-link">
            Inicio
          </Link>
          {isAuthenticated && (
            <Link to="/perfil" className="tag nav-link">
              Perfil
            </Link>
          )}
          {isAuthenticated && (
            <Link to="/certificados" className="tag nav-link">
              Certificados
            </Link>
          )}
          {(role === ROLES.PONENTE || role === ROLES.COORDINADOR || role === ROLES.ADMIN) && (
            <Link to="/materiales" className="tag nav-link">
              Materiales
            </Link>
          )}
          {(role === ROLES.ADMIN || role === ROLES.COORDINADOR) && (
            <>
              <Link to="/admin/eventos" className="tag nav-link">
                Gestionar Eventos
              </Link>
              {role === ROLES.ADMIN && (
                <Link to="/admin" className="tag nav-link">
                  Dashboard
                </Link>
              )}
            </>
          )}
        </nav>

        <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          {isAuthenticated ? (
            <>
              <span className="tag" style={{ background: 'rgba(255,255,255,0.05)' }}>
                {user?.nombre || user?.name || user?.email}
              </span>
              <button className="btn btn-outline" onClick={handleLogout}>
                Cerrar sesión
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="btn btn-outline">
                Ingresar
              </Link>
              <Link to="/registro" className="btn btn-primary">
                Registrarse
              </Link>
            </>
          )}
        </div>
      </div>
    </header>
  )
}

export default Navbar
