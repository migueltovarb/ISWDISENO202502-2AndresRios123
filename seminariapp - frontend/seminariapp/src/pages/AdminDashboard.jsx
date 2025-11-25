import { useEffect, useState } from 'react'
import { userService } from '../api/userService.js'
import { ROLES, roleLabels } from '../utils/roles.js'

const AdminDashboard = () => {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [feedback, setFeedback] = useState(null)

  const loadUsers = async () => {
    try {
      const data = await userService.getAll()
      setUsers(data || [])
      setError(null)
    } catch (err) {
      setError('No pudimos cargar usuarios.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadUsers()
  }, [])

  const handleRoleChange = async (userId, role) => {
    try {
      await userService.updateRole(userId, role)
      setUsers((prev) => prev.map((u) => (u.id === userId ? { ...u, rol: role, role } : u)))
      setFeedback('Rol actualizado correctamente.')
      setTimeout(() => setFeedback(null), 2500)
    } catch (err) {
      setError('No pudimos actualizar el rol.')
    }
  }

  if (loading) return <div className="loading">Cargando usuarios...</div>

  return (
    <div className="grid" style={{ gap: '1rem' }}>
      <section className="card-surface">
        <h1 style={{ marginTop: 0 }}>Dashboard Admin</h1>
        <p className="text-muted">Gestiona roles y monitorea la base de usuarios.</p>
        <div className="grid" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(180px, 1fr))' }}>
          <div className="tag">Usuarios: {users.length}</div>
          <div className="tag">
            Admins:{' '}
            {users.filter((u) => u.rol === ROLES.ADMIN || u.role === ROLES.ADMIN).length}
          </div>
        </div>
        {feedback && <p className="status-pill status-success">{feedback}</p>}
        {error && <p className="status-pill status-danger">{error}</p>}
      </section>

      <section className="card-surface">
        <div style={{ overflowX: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
              <tr style={{ textAlign: 'left' }}>
                <th style={{ padding: '0.75rem 0.5rem' }}>Nombre</th>
                <th style={{ padding: '0.75rem 0.5rem' }}>Correo</th>
                <th style={{ padding: '0.75rem 0.5rem' }}>Rol</th>
              </tr>
            </thead>
            <tbody>
              {users.map((u) => (
                <tr key={u.id} style={{ borderTop: '1px solid rgba(255,255,255,0.08)' }}>
                  <td style={{ padding: '0.65rem 0.5rem' }}>{u.nombre || u.name}</td>
                  <td style={{ padding: '0.65rem 0.5rem' }}>{u.email}</td>
                  <td style={{ padding: '0.65rem 0.5rem' }}>
                    <select
                      value={u.rol || u.role}
                      onChange={(e) => handleRoleChange(u.id, e.target.value)}
                      style={{ maxWidth: 220 }}
                    >
                      {Object.values(ROLES).map((role) => (
                        <option key={role} value={role}>
                          {roleLabels[role]}
                        </option>
                      ))}
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </div>
  )
}

export default AdminDashboard
