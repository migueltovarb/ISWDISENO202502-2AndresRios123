import { useEffect, useState } from 'react'
import { useNavigate, useParams, useSearchParams } from 'react-router-dom'
import { pagoService } from '../api/pagoService.js'

const PAYMENT_METHODS = [
  { value: 'TARJETA', label: 'Tarjeta' },
  { value: 'PSE', label: 'PSE' },
  { value: 'TRANSFERENCIA', label: 'Transferencia' },
]

const Payment = () => {
  const { inscripcionId } = useParams()
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()
  const [monto, setMonto] = useState(Number(searchParams.get('monto')) || 0)
  const evento = searchParams.get('evento') || 'Evento'
  const [medio, setMedio] = useState(PAYMENT_METHODS[0].value)
  const [statusMsg, setStatusMsg] = useState(null)
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    setError(null)
    setStatusMsg(null)
  }, [inscripcionId])

  const handlePay = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError(null)
    try {
      const pago = await pagoService.crear({ inscripcionId, monto, medio })
      setStatusMsg(`Pago creado (${pago.estado || 'PENDIENTE'})`)
      setTimeout(() => navigate('/perfil'), 1200)
    } catch (err) {
      const msg = err.response?.data?.message || 'No pudimos registrar el pago.'
      setError(msg)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="card-surface" style={{ maxWidth: 520, margin: '0 auto' }}>
      <h1 style={{ marginTop: 0 }}>Pago de inscripción</h1>
      <p className="text-muted">Evento: {evento}</p>
      {statusMsg && <p className="status-pill status-success">{statusMsg}</p>}
      {error && <p className="status-pill status-danger">{error}</p>}
      <form className="grid" style={{ gap: '0.75rem' }} onSubmit={handlePay}>
        <div className="form-group">
          <label htmlFor="monto">Monto</label>
          <input
            id="monto"
            type="number"
            min="0"
            value={monto}
            onChange={(e) => setMonto(Number(e.target.value) || 0)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="medio">Medio de pago</label>
          <select id="medio" value={medio} onChange={(e) => setMedio(e.target.value)}>
            {PAYMENT_METHODS.map((m) => (
              <option key={m.value} value={m.value}>
                {m.label}
              </option>
            ))}
          </select>
        </div>
        <button className="btn btn-primary" type="submit" disabled={loading}>
          {loading ? 'Procesando...' : 'Pagar'}
        </button>
      </form>
    </div>
  )
}

export default Payment
