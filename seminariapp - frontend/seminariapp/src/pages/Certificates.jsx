import { useEffect, useMemo, useState } from 'react'
import { inscripcionService } from '../api/inscripcionService.js'
import { eventoService } from '../api/eventoService.js'
import { certificadoService } from '../api/certificadoService.js'
import { useAuth } from '../context/AuthContext.jsx'

const Certificates = () => {
  const { user } = useAuth()
  const [inscripciones, setInscripciones] = useState([])
  const [eventos, setEventos] = useState([])
  const [certificados, setCertificados] = useState(new Map())
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [saving, setSaving] = useState(false)

  const eventoMap = useMemo(() => {
    const map = new Map()
    eventos.forEach((ev) => map.set(ev.id, ev))
    return map
  }, [eventos])

  useEffect(() => {
    const load = async () => {
      if (!user?.id) return
      try {
        setError(null)
        const [ins, evs] = await Promise.all([
          inscripcionService.listByUsuario(user.id),
          eventoService.list(),
        ])
        setInscripciones(ins || [])
        setEventos(evs || [])

        // Cargar certificados existentes
        const certPairs = await Promise.all(
          (ins || []).map(async (i) => {
            try {
              const cert = await certificadoService.obtenerPorInscripcion(i.id)
              return [i.id, cert]
            } catch {
              return [i.id, null]
            }
          }),
        )
        setCertificados(new Map(certPairs))
      } catch (err) {
        setError('No pudimos cargar tus certificados.')
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [user?.id])

  const generar = async (inscripcionId, download = false) => {
    setSaving(true)
    setError(null)
    try {
      if (download) {
        const pdf = await certificadoService.descargarPdf(inscripcionId)
        const url = window.URL.createObjectURL(new Blob([pdf], { type: 'application/pdf' }))
        const a = document.createElement('a')
        a.href = url
        a.download = 'certificado.pdf'
        a.click()
        window.URL.revokeObjectURL(url)
        const cert = await certificadoService.obtenerPorInscripcion(inscripcionId)
        if (cert) setCertificados((prev) => new Map(prev).set(inscripcionId, cert))
      } else {
        const cert = await certificadoService.generar(inscripcionId)
        setCertificados((prev) => new Map(prev).set(inscripcionId, cert))
      }
    } catch (err) {
      const raw = err.response?.data
      const msg = typeof raw === 'string'
        ? raw
        : (raw?.message || raw?.error || 'No pudimos generar el certificado.')
      setError(msg)
    } finally {
      setSaving(false)
    }
  }

  if (loading) return <div className="loading">Cargando certificados...</div>

  const aprobadas = inscripciones.filter((ins) => ins.estado === 'APROBADO')

  return (
    <div className="grid" style={{ gap: '1rem' }}>
      <section className="card-surface">
        <h1 style={{ marginTop: 0 }}>Certificados</h1>
        <p className="text-muted">Descarga tus certificados de eventos pagados.</p>
        {error && <p className="status-pill status-danger">{error}</p>}
      </section>

      <section className="card-surface">
        {aprobadas.length === 0 ? (
          <p className="text-muted">No tienes inscripciones aprobadas.</p>
        ) : (
          <div className="grid grid-2">
            {aprobadas.map((ins) => {
              const ev = eventoMap.get(ins.eventoId)
              const cert = certificados.get(ins.id)
              return (
                <div key={ins.id} className="card-surface" style={{ padding: '1rem' }}>
                  <h3 style={{ margin: '0 0 0.25rem 0' }}>{ev?.nombre || 'Evento'}</h3>
                  <p className="text-muted" style={{ margin: 0 }}>{ev?.descripcion || ''}</p>
                  <p className="tag" style={{ marginTop: '0.5rem' }}>Estado inscripción: {ins.estado}</p>
                  {cert ? (
                    <div style={{ marginTop: '0.5rem', display: 'flex', gap: '0.5rem', flexDirection: 'column' }}>
                      <span className="status-pill status-success">Certificado generado</span>
                      <div className="tag">Número: {cert.numero}</div>
                      <div className="tag">QR hash: {cert.qrHash}</div>
                      <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                        <a className="btn btn-outline" href={`/certificados/validar/${cert.qrHash}`} target="_blank" rel="noreferrer">
                          Ver/Validar
                        </a>
                        <button className="btn btn-primary" onClick={() => generar(ins.id, true)} disabled={saving}>
                          Descargar PDF
                        </button>
                      </div>
                    </div>
                  ) : (
                    <button className="btn btn-primary" onClick={() => generar(ins.id)} disabled={saving}>
                      {saving ? 'Generando...' : 'Generar certificado'}
                    </button>
                  )}
                </div>
              )
            })}
          </div>
        )}
      </section>
    </div>
  )
}

export default Certificates
