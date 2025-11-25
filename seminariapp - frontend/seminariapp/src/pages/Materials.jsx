import { useEffect, useState } from 'react'
import { materialService } from '../api/materialService.js'
import { eventoService } from '../api/eventoService.js'
import { useAuth } from '../context/AuthContext.jsx'

const MATERIAL_TYPES = [
  { value: 'PDF', label: 'PDF' },
  { value: 'VIDEO', label: 'Video' },
  { value: 'ENLACE', label: 'Enlace' },
]

const Materials = () => {
  const { user } = useAuth()
  const [events, setEvents] = useState([])
  const [materials, setMaterials] = useState([])
  const [selectedEvent, setSelectedEvent] = useState('')
  const [form, setForm] = useState({ titulo: '', tipo: MATERIAL_TYPES[0].value, url: '' })
  const [feedback, setFeedback] = useState(null)
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const load = async () => {
      try {
        const evs = await eventoService.list()
        // Si es ponente, filtrar solo sus eventos asignados
        const filtered =
          user?.rol === 'PONENTE' ? (evs || []).filter((ev) => ev.ponenteId === user.id) : evs || []
        setEvents(filtered)
        if (filtered.length > 0) {
          setSelectedEvent(filtered[0].id)
          const mats = await materialService.listByEvento(filtered[0].id)
          setMaterials(mats || [])
        }
      } catch (err) {
        setError('No pudimos cargar eventos o materiales.')
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [user?.id, user?.rol])

  const handleSelectEvent = async (eventId) => {
    setSelectedEvent(eventId)
    setFeedback(null)
    setError(null)
    try {
      const mats = await materialService.listByEvento(eventId)
      setMaterials(mats || [])
    } catch (err) {
      setError('No pudimos cargar los materiales de este evento.')
    }
  }

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!selectedEvent) {
      setError('Selecciona un evento.')
      return
    }
    setError(null)
    setFeedback(null)
    try {
      const payload = { ...form, eventoId: selectedEvent }
      await materialService.upload(payload)
      setFeedback('Material cargado correctamente.')
      setForm({ titulo: '', tipo: MATERIAL_TYPES[0].value, url: '' })
      const mats = await materialService.listByEvento(selectedEvent)
      setMaterials(mats || [])
    } catch (err) {
      const msg = err.response?.data?.message || 'No pudimos cargar el material.'
      setError(msg)
    }
  }

  if (loading) return <div className="loading">Cargando materiales...</div>

  return (
    <div className="grid" style={{ gap: '1rem' }}>
      <section className="card-surface">
        <h1 style={{ marginTop: 0 }}>Materiales de eventos</h1>
        <p className="text-muted">Sube recursos para tus eventos asignados.</p>
        {error && <p className="status-pill status-danger">{error}</p>}
        {feedback && <p className="status-pill status-success">{feedback}</p>}
      </section>

      <section className="card-surface">
        <div className="form-group">
          <label htmlFor="evento">Evento</label>
          <select
            id="evento"
            value={selectedEvent}
            onChange={(e) => handleSelectEvent(e.target.value)}
          >
            {events.map((ev) => (
              <option key={ev.id} value={ev.id}>
                {ev.nombre} ({ev.tipo})
              </option>
            ))}
          </select>
        </div>
        <form className="grid" style={{ gap: '0.75rem' }} onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="titulo">Título</label>
            <input id="titulo" name="titulo" required value={form.titulo} onChange={handleChange} />
          </div>
          <div className="form-group">
            <label htmlFor="tipo">Tipo</label>
            <select id="tipo" name="tipo" value={form.tipo} onChange={handleChange}>
              {MATERIAL_TYPES.map((t) => (
                <option key={t.value} value={t.value}>
                  {t.label}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="url">URL</label>
            <input
              id="url"
              name="url"
              type="url"
              required
              placeholder="https://..."
              value={form.url}
              onChange={handleChange}
            />
          </div>
          <button className="btn btn-primary" type="submit">
            Subir material
          </button>
        </form>
      </section>

      <section className="card-surface">
        <h2 className="section-title">Materiales del evento</h2>
        {materials.length === 0 ? (
          <p className="text-muted">No hay materiales cargados para este evento.</p>
        ) : (
          <div className="grid">
            {materials.map((mat) => (
              <article key={mat.id} className="card-surface" style={{ background: 'rgba(255,255,255,0.02)' }}>
                <p className="tag" style={{ margin: 0 }}>{mat.tipo}</p>
                <h3 style={{ margin: '0.2rem 0' }}>{mat.titulo}</h3>
                <a className="btn btn-outline" href={mat.url} target="_blank" rel="noreferrer">
                  Abrir
                </a>
              </article>
            ))}
          </div>
        )}
      </section>
    </div>
  )
}

export default Materials
