import { useEffect, useState } from 'react'
import { eventoService } from '../api/eventoService.js'
import { userService } from '../api/userService.js'
import { ROLES } from '../utils/roles.js'

const AdminEvents = () => {
  const [eventos, setEventos] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [feedback, setFeedback] = useState(null)
  const [ponentes, setPonentes] = useState([])
  const [editingId, setEditingId] = useState(null)
  const [form, setForm] = useState({
    tipo: 'SEMINARIO',
    nombre: '',
    descripcion: '',
    fechaInicio: '',
    fechaFin: '',
    cupoMaximo: '',
    precio: '',
    ponente: '',
  })

  const loadData = async () => {
    try {
      const [evData, ponData] = await Promise.all([
        eventoService.list(),
        userService.getByRole(ROLES.PONENTE),
      ])
      setEventos(evData || [])
      setPonentes(ponData || [])
      setError(null)
    } catch (err) {
      setError('No pudimos cargar los eventos para administración.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadData()
  }, [])

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const resetForm = () => {
    setForm({
      tipo: 'SEMINARIO',
      nombre: '',
      descripcion: '',
      fechaInicio: '',
      fechaFin: '',
      cupoMaximo: '',
      precio: '',
      ponenteId: '',
    })
    setEditingId(null)
  }

  const handleEdit = (item, tipo) => {
    setEditingId(item.id)
    setForm({
      tipo,
      nombre: item.nombre,
      descripcion: item.descripcion || '',
      fechaInicio: item.fechaInicio ? item.fechaInicio.split('T')[0] : '',
      fechaFin: item.fechaFin ? item.fechaFin.split('T')[0] : '',
      cupoMaximo: item.cupoMaximo ?? '',
      precio: item.precio ?? '',
      ponenteId: item.ponenteId || '',
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    const payload = {
      nombre: form.nombre,
      descripcion: form.descripcion,
      fechaInicio: form.fechaInicio,
      fechaFin: form.fechaFin,
      cupoMaximo: Number(form.cupoMaximo) || 0,
      precio: Number(form.precio) || 0,
      tipo: form.tipo,
      ponenteId: form.ponenteId || null,
    }

    try {
      if (editingId) await eventoService.update(editingId, payload)
      else await eventoService.create(payload)
      setFeedback('Guardado correctamente.')
      resetForm()
      loadData()
      setTimeout(() => setFeedback(null), 2500)
    } catch (err) {
      setError('No pudimos guardar el evento, revisa los datos.')
    }
  }

  const handleDelete = async (item) => {
    try {
      await eventoService.remove(item.id)
      loadData()
    } catch (err) {
      setError('No pudimos eliminar el evento.')
    }
  }

  if (loading) return <div className="loading">Cargando gestión...</div>

  return (
    <div className="grid" style={{ gap: '1rem' }}>
      <section className="card-surface">
        <h1 style={{ marginTop: 0 }}>Administrar eventos</h1>
        <p className="text-muted">Crea, edita y asigna ponentes.</p>
        {feedback && <p className="status-pill status-success">{feedback}</p>}
        {error && <p className="status-pill status-danger">{error}</p>}
      </section>

      <section className="card-surface">
        <h2 className="section-title">{editingId ? 'Editar evento' : 'Nuevo evento'}</h2>
        <form className="grid" style={{ gap: '0.75rem' }} onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="tipo">Tipo</label>
            <select id="tipo" name="tipo" value={form.tipo} onChange={handleChange}>
              <option value="SEMINARIO">Seminario</option>
              <option value="TALLER">Taller</option>
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="nombre">Nombre</label>
            <input id="nombre" name="nombre" required value={form.nombre} onChange={handleChange} />
          </div>
          <div className="form-group">
            <label htmlFor="descripcion">Descripción</label>
            <textarea
              id="descripcion"
              name="descripcion"
              rows={3}
              required
              value={form.descripcion}
              onChange={handleChange}
            />
          </div>
          <div className="grid" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '0.75rem' }}>
            <div className="form-group">
              <label htmlFor="fechaInicio">Fecha inicio</label>
              <input id="fechaInicio" name="fechaInicio" type="date" value={form.fechaInicio} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label htmlFor="fechaFin">Fecha fin</label>
              <input id="fechaFin" name="fechaFin" type="date" value={form.fechaFin} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label htmlFor="cupoMaximo">Cupo máximo</label>
              <input id="cupoMaximo" name="cupoMaximo" type="number" min="0" value={form.cupoMaximo} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label htmlFor="precio">Precio</label>
              <input id="precio" name="precio" type="number" min="0" value={form.precio} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label htmlFor="ponenteId">Ponente</label>
              <select id="ponenteId" name="ponenteId" value={form.ponenteId || ''} onChange={handleChange}>
                <option value="">Sin ponente</option>
                {ponentes.map((p) => (
                  <option key={p.id} value={p.id}>
                    {p.nombre} ({p.email})
                  </option>
                ))}
              </select>
            </div>
          </div>
          <div style={{ display: 'flex', gap: '0.5rem' }}>
            <button className="btn btn-primary" type="submit">
              {editingId ? 'Actualizar' : 'Crear'}
            </button>
            {editingId && (
              <button className="btn btn-outline" type="button" onClick={resetForm}>
                Cancelar edición
              </button>
            )}
          </div>
          <small className="text-muted">Las rutas usadas son las de tu backend: /eventos, /eventos/{'{id}'}, /eventos/{'{eventoId}'}/asignar-ponente/{'{ponenteId}'}</small>
        </form>
      </section>

      <section className="card-surface">
        <h2 className="section-title">Listado de eventos</h2>
        {eventos.length === 0 ? (
          <p className="text-muted">No hay eventos creados.</p>
        ) : (
          <div className="grid grid-2">
            {eventos.map((ev) => (
              <article key={ev.id} className="card-surface" style={{ background: 'rgba(255,255,255,0.02)' }}>
                <p className="tag" style={{ margin: 0 }}>{ev.tipo}</p>
                <h3 style={{ marginTop: 0 }}>{ev.nombre}</h3>
                <p className="text-muted">{ev.descripcion}</p>
                <div className="tag">Cupo: {ev.cupoMaximo}</div>
                <div className="tag">Precio: ${ev.precio}</div>
                <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap', marginTop: '0.5rem' }}>
                  <button className="btn btn-outline" onClick={() => handleEdit(ev, ev.tipo)}>
                    Editar
                  </button>
                  <button className="btn btn-outline" onClick={() => handleDelete(ev)}>
                    Eliminar
                  </button>
                  {ev.ponenteId && <span className="tag">Ponente asignado</span>}
                </div>
              </article>
            ))}
          </div>
        )}
      </section>
    </div>
  )
}

export default AdminEvents
