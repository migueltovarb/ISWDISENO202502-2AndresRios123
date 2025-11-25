import { useEffect, useMemo, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { eventoService } from '../api/eventoService.js'
import { inscripcionService } from '../api/inscripcionService.js'

const SESSIONS = [1, 2]

const Attendance = () => {
  const { eventoId } = useParams()
  const navigate = useNavigate()
  const [evento, setEvento] = useState(null)
  const [inscripciones, setInscripciones] = useState([])
  const [sesion, setSesion] = useState(SESSIONS[0])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [saving, setSaving] = useState(false)

  const cumplimiento = useMemo(() => {
    return inscripciones.map((ins) => {
      const totalSesiones = SESSIONS.length
      const asist = ins.asistencias || {}
      const presentes = Object.entries(asist).filter(([k, v]) => SESSIONS.includes(Number(k)) && v === true).length
      const porcentaje = totalSesiones > 0 ? Math.round((presentes / totalSesiones) * 100) : 0
      return { ...ins, porcentaje }
    })
  }, [inscripciones])

  useEffect(() => {
    const load = async () => {
      try {
        const [ev, ins] = await Promise.all([
          eventoService.getById(eventoId),
          inscripcionService.listAprobadasDetalle(eventoId),
        ])
        setEvento(ev)
        setInscripciones(ins || [])
      } catch (err) {
        setError('No pudimos cargar la asistencia.')
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [eventoId])

  const togglePresente = async (inscripcionId, presenteActual) => {
    setSaving(true)
    try {
      const updated = await inscripcionService.marcarAsistencia(inscripcionId, sesion, !presenteActual)
      setInscripciones((prev) =>
        prev.map((ins) => (ins.inscripcionId === inscripcionId ? { ...ins, asistencias: updated.asistencias } : ins)),
      )
    } catch (err) {
      setError('No pudimos marcar la asistencia.')
    } finally {
      setSaving(false)
    }
  }

  if (loading) return <div className="loading">Cargando asistencia...</div>
  if (error) return <p className="status-pill status-danger">{error}</p>

  return (
    <div className="grid" style={{ gap: '1rem' }}>
      <section className="card-surface">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h1 style={{ margin: 0 }}>Registro de asistencia</h1>
            {evento && <p className="text-muted" style={{ margin: 0 }}>{evento.nombre}</p>}
          </div>
          <button className="btn btn-outline" onClick={() => navigate('/admin')}>
            Volver
          </button>
        </div>
      </section>

      <section className="card-surface">
        <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
          {SESSIONS.map((s) => (
            <button
              key={s}
              className={`btn ${sesion === s ? 'btn-primary' : 'btn-outline'}`}
              onClick={() => setSesion(s)}
            >
              Sesión {s}
            </button>
          ))}
        </div>
      </section>

      <section className="card-surface">
        <h2 className="section-title">Participantes</h2>
        {inscripciones.length === 0 ? (
          <p className="text-muted">No hay inscritos aprobados.</p>
        ) : (
          <div className="grid" style={{ gap: '0.75rem' }}>
            {inscripciones.map((ins) => {
              const presente = ins.asistencias?.[sesion] === true
              return (
                <div key={ins.inscripcionId} className="card-surface" style={{ padding: '0.85rem' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', gap: '0.5rem' }}>
                    <div>
                      <h4 style={{ margin: 0 }}>{ins.nombre}</h4>
                      <p className="text-muted" style={{ margin: 0, fontSize: '0.9rem' }}>{ins.email}</p>
                    </div>
                    <button
                      className={`btn ${presente ? 'btn-primary' : 'btn-outline'}`}
                      onClick={() => togglePresente(ins.inscripcionId, presente)}
                      disabled={saving}
                    >
                      {presente ? 'Presente' : 'Marcar'}
                    </button>
                  </div>
                </div>
              )
            })}
          </div>
        )}
      </section>

      <section className="card-surface">
        <h2 className="section-title">Resumen</h2>
        {cumplimiento.length === 0 ? (
          <p className="text-muted">Sin datos.</p>
        ) : (
          <div className="grid grid-2">
            {cumplimiento.map((c) => (
              <div key={c.inscripcionId} className="card-surface" style={{ padding: '0.75rem' }}>
                <h4 style={{ margin: '0 0 0.25rem 0' }}>{c.nombre}</h4>
                <p className="text-muted" style={{ margin: 0 }}>Cumplimiento: {c.porcentaje}%</p>
                <p className={c.porcentaje >= 80 ? 'status-pill status-success' : 'status-pill status-warning'}>
                  {c.porcentaje >= 80 ? 'Cumple' : 'No cumple'}
                </p>
              </div>
            ))}
          </div>
        )}
      </section>
    </div>
  )
}

export default Attendance
