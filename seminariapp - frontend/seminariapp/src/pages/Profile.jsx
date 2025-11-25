import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext.jsx'
import { inscripcionService } from '../api/inscripcionService.js'
import { eventoService } from '../api/eventoService.js'
import EventCard from '../components/EventCard.jsx'

const Profile = () => {
  const navigate = useNavigate()
  const { user, loading: authLoading } = useAuth()
  const [inscripciones, setInscripciones] = useState([])
  const [eventos, setEventos] = useState([])
  const [counts, setCounts] = useState(new Map())
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const eventoMap = useMemo(() => {
    const map = new Map()
    eventos.forEach((ev) => map.set(ev.id, ev))
    return map
  }, [eventos])

  const eventosComoPonente = useMemo(() => {
    if (!user?.id) return []
    return (eventos || []).filter((ev) => ev.ponenteId === user.id)
  }, [eventos, user?.id])

  useEffect(() => {
    const loadData = async () => {
      if (authLoading || !user?.id) return
      try {
        setError(null)
        const [ins, evs] = await Promise.all([
          inscripcionService.listByUsuario(user.id),
          eventoService.list(),
        ])
        setInscripciones(ins || [])
        setEventos(evs || [])
        const countPairs = await Promise.all(
          (evs || []).map(async (ev) => {
            try {
              const list = await inscripcionService.listByEvento(ev.id)
              return [ev.id, list?.length || 0]
            } catch {
              return [ev.id, 0]
            }
          }),
        )
        setCounts(new Map(countPairs))
      } catch (err) {
        setError('No pudimos cargar tus inscripciones.')
      } finally {
        setLoading(false)
      }
    }
    loadData()
  }, [authLoading, user?.id])

  if (authLoading || loading) return <div className="loading">Cargando perfil...</div>

  return (
    <div className="grid" style={{ gap: '1rem' }}>
      <section className="card-surface">
        <h1 style={{ marginTop: 0 }}>Mi perfil</h1>
        <p className="text-muted">Datos actuales y tus inscripciones activas.</p>
        <div className="grid" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '0.75rem' }}>
          <div className="tag">Nombre: {user?.nombre}</div>
          <div className="tag">Correo: {user?.email}</div>
          <div className="tag">Rol: {user?.rol}</div>
          <div className="tag">Teléfono: {user?.telefono}</div>
        </div>
      </section>

      {error && inscripciones.length === 0 && eventos.length === 0 && (
        <p className="status-pill status-danger">{error}</p>
      )}

      <section className="card-surface">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h2 className="section-title">Mis inscripciones</h2>
          <span className="tag">{inscripciones.length}</span>
        </div>
        {inscripciones.length === 0 ? (
          <p className="text-muted">No tienes inscripciones activas.</p>
        ) : (
          <div className="grid grid-2">
            {inscripciones.map((ins) => {
              const ev = eventoMap.get(ins.eventoId)
              if (!ev) return null
              return (
                <EventCard
                  key={ins.id}
                  item={ev}
                  type={ev.tipo?.toLowerCase() || 'evento'}
                  enrolled
                  status={ins.estado}
                  inscritos={counts.get(ev.id)}
                  onView={ins.estado === 'APROBADO' ? () => navigate(`/eventos/${ev.id}`) : null}
                  onToggleEnroll={null}
                  onPay={ins.estado === 'PENDIENTE' ? () => (window.location.href = `/pagos/${ins.id}?evento=${encodeURIComponent(ev.nombre)}&monto=${ev.precio || 0}`) : null}
                />
              )
            })}
          </div>
        )}
      </section>

      {user?.rol === 'PONENTE' && (
        <section className="card-surface">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <h2 className="section-title">Eventos donde soy ponente</h2>
            <span className="tag">{eventosComoPonente.length}</span>
          </div>
          {eventosComoPonente.length === 0 ? (
            <p className="text-muted">No tienes eventos asignados como ponente.</p>
          ) : (
            <div className="grid grid-2">
              {eventosComoPonente.map((ev) => (
                <EventCard
                  key={`ponente-${ev.id}`}
                  item={ev}
                  type={ev.tipo?.toLowerCase() || 'evento'}
                  enrolled
                  status={null}
                  inscritos={counts.get(ev.id)}
                  onView={() => navigate(`/asistencia/${ev.id}`)}
                  onToggleEnroll={null}
                  onPay={null}
                />
              ))}
            </div>
          )}
        </section>
      )}
    </div>
  )
}

export default Profile
