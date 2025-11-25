import PropTypes from 'prop-types'
import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext.jsx'

const RoleRoute = ({ roles, children }) => {
  const { user, loading } = useAuth()
  const role = user?.rol || user?.role

  if (loading) return <div className="loading">Validando permisos...</div>
  if (!roles.includes(role)) {
    return <Navigate to="/" replace />
  }
  return children
}

RoleRoute.propTypes = {
  roles: PropTypes.arrayOf(PropTypes.string).isRequired,
  children: PropTypes.node,
}

export default RoleRoute
