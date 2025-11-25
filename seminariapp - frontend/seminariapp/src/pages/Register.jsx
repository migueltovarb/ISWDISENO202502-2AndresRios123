import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext.jsx'
import { ROLES, roleLabels } from '../utils/roles.js'

const Register = () => {
  const navigate = useNavigate()
  const { register, loading } = useAuth()
  const [form, setForm] = useState({ nombre: '', email: '', telefono: '', password: '', rol: ROLES.ESTUDIANTE })
  const [error, setError] = useState(null)

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      const payload = {
        nombre: form.nombre.trim(),
        email: form.email.trim(),
        telefono: Number(form.telefono) || 0,
        password: form.password,
        rol: form.rol || undefined,
      }
      await register(payload)
      navigate('/login', { replace: true })
    } catch (err) {
      const msg = err.response?.data?.message || err.message || 'No pudimos registrar al usuario.'
      setError(msg)
    }
  }

  return (
    <div className="card-surface" style={{ maxWidth: 520, margin: '0 auto' }}>
      <h1 style={{ marginTop: 0 }}>Crear cuenta</h1>
      <p className="text-muted">Accede a todos los seminarios y talleres al registrarte.</p>
      {error && <p className="status-pill status-danger">{error}</p>}

      <form className="grid" style={{ gap: '1rem' }} onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="nombre">Nombre completo</label>
          <input id="nombre" name="nombre" required value={form.nombre} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label htmlFor="email">Correo</label>
          <input id="email" name="email" type="email" required value={form.email} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label htmlFor="telefono">Teléfono</label>
          <input id="telefono" name="telefono" type="number" min="0" required value={form.telefono} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label htmlFor="password">Contraseña</label>
          <input
            id="password"
            name="password"
            type="password"
            minLength={6}
            required
            value={form.password}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="rol">Tipo de rol al registrarse</label>
          <select id="rol" name="rol" value={form.rol} onChange={handleChange}>
            {[ROLES.ESTUDIANTE, ROLES.PONENTE].map((role) => (
              <option key={role} value={role}>
                {roleLabels[role]}
              </option>
            ))}
          </select>
          <small className="text-muted">El rol ADMIN solo se asigna desde el dashboard.</small>
        </div>
        <button className="btn btn-primary" type="submit" disabled={loading}>
          {loading ? 'Creando cuenta...' : 'Crear cuenta'}
        </button>
      </form>

      <p className="text-muted" style={{ marginTop: '1rem' }}>
        ¿Ya tienes cuenta? <Link to="/login">Inicia sesión</Link>
      </p>
    </div>
  )
}

export default Register
