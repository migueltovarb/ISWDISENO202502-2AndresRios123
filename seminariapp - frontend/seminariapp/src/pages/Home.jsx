import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import EventCard from '../components/EventCard.jsx'
import { eventoService } from '../api/eventoService.js'
import { inscripcionService } from '../api/inscripcionService.js'
import { useAuth } from '../context/AuthContext.jsx'

const Home = () => {
  const navigate = useNavigate()
  const { isAuthenticated, user } = useAuth()
  const [eventos, setEventos] = useState([])
  const [inscripciones, setInscripciones] = useState([])
  const [counts, setCounts] = useState(new Map())
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [selected, setSelected] = useState(null)
  const [search, setSearch] = useState('')
  const [typeFilter, setTypeFilter] = useState('TODOS')
  const [sortMode, setSortMode] = useState('NUEVOS') // NUEVOS o PROXIMOS

  const inscripcionMap = useMemo(() => {
    const map = new Map()
    inscripciones.forEach((ins) => map.set(ins.eventoId, ins))
    return map
  }, [inscripciones])

  useEffect(() => {
    const loadData = async () => {
      try {
        const data = await eventoService.list()
        setEventos(data || [])
        if (isAuthenticated && user?.id) {
          const ins = await inscripcionService.listByUsuario(user.id)
          setInscripciones(ins || [])
        }
        // contar inscritos por evento
        const countsPairs = await Promise.all(
          (data || []).map(async (ev) => {
            try {
              const list = await inscripcionService.listByEvento(ev.id)
              return [ev.id, list?.length || 0]
            } catch {
              return [ev.id, 0]
            }
          }),
        )
        setCounts(new Map(countsPairs))
      } catch (err) {
        setError('No pudimos cargar los eventos.')
      } finally {
        setLoading(false)
      }
    }
    loadData()
  }, [isAuthenticated, user?.id])

  const toggleEnrollment = async (evento) => {
    if (!isAuthenticated || !user?.id) {
      navigate('/login')
      return
    }
    try {
      const existingInscripcion = inscripcionMap.get(evento.id)
      if (existingInscripcion) {
        await inscripcionService.cancelar(existingInscripcion.id)
        setInscripciones((prev) => prev.filter((ins) => ins.id !== existingInscripcion.id))
      } else {
        const created = await inscripcionService.inscribir({ usuarioId: user.id, eventoId: evento.id })
        setInscripciones((prev) => [...prev, created])
      }
    } catch (err) {
      setError('No pudimos actualizar tu inscripcion.')
    }
  }

  const handlePayNow = (evento) => {
    const ins = inscripcionMap.get(evento.id)
    if (ins) {
      navigate(`/pagos/${ins.id}?evento=${encodeURIComponent(evento.nombre)}&monto=${evento.precio || 0}`)
    }
  }

  if (loading) return <div className="loading">Cargando eventos...</div>

  const normalizedSearch = search.trim().toLowerCase()
  const filteredEventos = eventos.filter((e) => {
    const matchesType = typeFilter === 'TODOS' || e.tipo === typeFilter
    const matchesSearch =
      !normalizedSearch ||
      e.nombre?.toLowerCase().includes(normalizedSearch) ||
      e.descripcion?.toLowerCase().includes(normalizedSearch)
    return matchesType && matchesSearch
  })

  const sortedEventos = [...filteredEventos].sort((a, b) => {
    const aDate = a.fechaInicio ? new Date(a.fechaInicio).getTime() : 0
    const bDate = b.fechaInicio ? new Date(b.fechaInicio).getTime() : 0
    if (sortMode === 'PROXIMOS') {
      return aDate - bDate
    }
    return bDate - aDate
  })

  const nuevosIds = new Set(sortedEventos.slice(0, 3).map((ev) => ev.id))

  const talleres = sortedEventos.filter((e) => e.tipo === 'TALLER')
  const seminarios = sortedEventos.filter((e) => e.tipo === 'SEMINARIO')
  const sections = [
    { key: 'taller', label: 'Talleres', type: 'taller', items: talleres },
    { key: 'seminario', label: 'Seminarios', type: 'seminario', items: seminarios },
  ].sort((a, b) => b.items.length - a.items.length)

  return (
    <div className="grid" style={{ gap: '1.5rem' }}>
      <section className="card-surface" style={{ padding: '1.5rem' }}>
        <h1 style={{ margin: 0 }}>Explora eventos</h1>
        <p className="text-muted" style={{ marginTop: '0.35rem' }}>
          Talleres y seminarios disponibles. Inscribete con un clic.
        </p>
        {error && <p className="status-pill status-danger">{error}</p>}
      </section>

      <section className="card-surface">
        <div className="grid" style={{ gridTemplateColumns: '1.2fr 0.8fr 0.8fr', gap: '0.75rem' }}>
          <div className="form-group" style={{ margin: 0 }}>
            <label htmlFor="search">Buscar</label>
            <input
              id="search"
              placeholder="Nombre o descripcion"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
          </div>
          <div className="form-group" style={{ margin: 0 }}>
            <label htmlFor="tipo">Tipo</label>
            <select id="tipo" value={typeFilter} onChange={(e) => setTypeFilter(e.target.value)}>
              <option value="TODOS">Todos</option>
              <option value="TALLER">Taller</option>
              <option value="SEMINARIO">Seminario</option>
            </select>
          </div>
          <div className="form-group" style={{ margin: 0 }}>
            <label htmlFor="orden">Orden</label>
            <select id="orden" value={sortMode} onChange={(e) => setSortMode(e.target.value)}>
              <option value="NUEVOS">Mas nuevos</option>
              <option value="PROXIMOS">Mas proximos (fecha inicio)</option>
            </select>
          </div>
        </div>
      </section>

      {sections.map((section) => (
        <section key={section.key} className="card-surface">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.75rem' }}>
            <h2 className="section-title">{section.label}</h2>
            <span className="tag">
              {section.items.length} {section.label.toLowerCase()}
            </span>
          </div>
          {section.items.length === 0 ? (
            <p className="text-muted">No hay {section.label.toLowerCase()}.</p>
          ) : (
            <div className="grid grid-2">
              {section.items.map((ev) => (
                <EventCard
                  key={`${section.key}-${ev.id}`}
                  item={ev}
                  type={section.type}
                  isNew={nuevosIds.has(ev.id)}
                  enrolled={inscripcionMap.has(ev.id)}
                  status={inscripcionMap.get(ev.id)?.estado}
                  inscritos={counts.get(ev.id)}
                  onPay={inscripcionMap.get(ev.id) ? () => handlePayNow(ev) : null}
                  onView={setSelected}
                  onToggleEnroll={() => toggleEnrollment(ev)}
                />
              ))}
            </div>
          )}
        </section>
      ))}

      {selected && (
        <section className="card-surface">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
            <div>
              <h3 style={{ marginTop: 0 }}>{selected.nombre}</h3>
              <p className="text-muted">{selected.descripcion}</p>
              {selected.precio != null && <p className="tag">Precio: ${selected.precio}</p>}
            </div>
            <button className="btn btn-outline" onClick={() => setSelected(null)}>
              Cerrar
            </button>
          </div>
        </section>
      )}
    </div>
  )
}

export default Home
