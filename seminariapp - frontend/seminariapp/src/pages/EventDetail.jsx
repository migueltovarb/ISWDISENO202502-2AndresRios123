import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { eventoService } from '../api/eventoService.js'
import { inscripcionService } from '../api/inscripcionService.js'
import { materialService } from '../api/materialService.js'
import { useAuth } from '../context/AuthContext.jsx'

const EventDetail = () => {
  const { eventoId } = useParams()
  const navigate = useNavigate()
  const { user } = useAuth()
  const [evento, setEvento] = useState(null)
  const [materiales, setMateriales] = useState([])
  const [estadoInscripcion, setEstadoInscripcion] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const loadData = async () => {
      try {
        const ev = await eventoService.getById(eventoId)
        setEvento(ev)

        let ins = null
        if (user?.id) {
          const inscripcionesUsuario = await inscripcionService.listByUsuario(user.id)
          ins = (inscripcionesUsuario || []).find((i) => i.eventoId === eventoId)
        }
        const estado = ins?.estado || null
        setEstadoInscripcion(estado)

        if (estado === 'APROBADO') {
          try {
            const mats = await materialService.listByEvento(eventoId)
            setMateriales(mats || [])
          } catch {
            setMateriales([])
          }
        } else {
          setMateriales([])
        }
      } catch (err) {
        setError('No pudimos cargar el evento.')
      } finally {
        setLoading(false)
      }
    }
    loadData()
  }, [eventoId, user?.id])

  if (loading) return <div className="loading">Cargando evento...</div>
  if (error) return <p className="status-pill status-danger">{error}</p>
  if (!evento) return <p className="text-muted">Evento no encontrado.</p>

  const hasAccess = estadoInscripcion === 'APROBADO'

  return (
    <div className="grid" style={{ gap: '1rem' }}>
      <section className="card-surface">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h1 style={{ margin: 0 }}>{evento.nombre}</h1>
          <button className="btn btn-outline" onClick={() => navigate('/perfil')}>
            Volver al perfil
          </button>
        </div>
        <p className="text-muted">{evento.descripcion}</p>
        <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
          {evento.fechaInicio && <span className="tag">Inicio: {new Date(evento.fechaInicio).toLocaleDateString()}</span>}
          {evento.fechaFin && <span className="tag">Fin: {new Date(evento.fechaFin).toLocaleDateString()}</span>}
          {evento.precio != null && <span className="tag">Precio: ${evento.precio}</span>}
          {evento.tipo && <span className="tag">Tipo: {evento.tipo}</span>}
          {estadoInscripcion && <span className={`status-pill ${estadoInscripcion === 'APROBADO' ? 'status-success' : 'status-warning'}`}>Inscripción: {estadoInscripcion}</span>}
        </div>
      </section>

      <section className="card-surface">
        <h2 className="section-title">Materiales</h2>
        {!hasAccess && <p className="text-muted">Necesitas tener el pago aprobado para ver los materiales.</p>}
        {hasAccess && (
          materiales.length === 0 ? (
            <p className="text-muted">Aún no hay materiales para este evento.</p>
          ) : (
            <ul style={{ listStyle: 'none', padding: 0, margin: 0, display: 'grid', gap: '0.75rem' }}>
              {materiales.map((mat) => (
                <li key={mat.id} className="card-surface" style={{ padding: '0.9rem' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <h4 style={{ margin: '0 0 0.25rem 0' }}>{mat.titulo}</h4>
                      <p className="text-muted" style={{ margin: 0 }}>Tipo: {mat.tipo}</p>
                    </div>
                    <a className="btn btn-primary" href={mat.url} target="_blank" rel="noreferrer">
                      Ver
                    </a>
                  </div>
                </li>
              ))}
            </ul>
          )
        )}
      </section>
    </div>
  )
}

export default EventDetail
