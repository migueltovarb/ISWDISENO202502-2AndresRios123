import PropTypes from 'prop-types'

const EventCard = ({ item, type, onView, onToggleEnroll, enrolled, inscritos, status, onPay, isNew }) => {
  const startDate = item.fechaInicio
  const endDate = item.fechaFin
  const capacity = item.cupoMaximo

  return (
    <article className="card-surface" style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', gap: '0.75rem' }}>
        <div>
          <p className="tag" style={{ margin: 0, textTransform: 'capitalize' }}>
            {type}
          </p>
          {isNew && <span className="status-pill status-success" style={{ marginRight: '0.35rem' }}>Nuevo</span>}
          <h3 style={{ margin: '0.35rem 0', fontSize: '1.1rem' }}>{item.nombre}</h3>
          <p className="text-muted" style={{ margin: 0 }}>{item.descripcion}</p>
        </div>
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-end', gap: '0.3rem' }}>
          {capacity && <span className="status-pill status-success">{capacity} cupos</span>}
          {typeof inscritos === 'number' && <span className="tag">Inscritos: {inscritos}</span>}
        </div>
      </div>

      <div style={{ display: 'flex', gap: '0.65rem', flexWrap: 'wrap', alignItems: 'center' }}>
        {startDate && <span className="tag">Inicio: {new Date(startDate).toLocaleDateString()}</span>}
        {endDate && <span className="tag">Fin: {new Date(endDate).toLocaleDateString()}</span>}
        {item.precio != null && <span className="tag">Precio: ${item.precio}</span>}
        {status && <span className={`status-pill ${status === 'PENDIENTE' ? 'status-warning' : 'status-success'}`}>{status}</span>}
      </div>

      <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
        <button className="btn btn-outline" onClick={() => onView?.(item)}>
          Ver detalle
        </button>
        {onToggleEnroll && (
          <button className="btn btn-primary" onClick={() => onToggleEnroll(item)}>
            {enrolled ? 'Desinscribirse' : 'Inscribirse'}
          </button>
        )}
        {onPay && status === 'PENDIENTE' && (
          <button className="btn btn-primary" onClick={() => onPay(item)}>
            Pagar ahora
          </button>
        )}
      </div>
    </article>
  )
}

EventCard.propTypes = {
  item: PropTypes.object.isRequired,
  type: PropTypes.string.isRequired,
  onView: PropTypes.func,
  onToggleEnroll: PropTypes.func,
  enrolled: PropTypes.bool,
  inscritos: PropTypes.number,
  status: PropTypes.string,
  onPay: PropTypes.func,
  isNew: PropTypes.bool,
}

export default EventCard
