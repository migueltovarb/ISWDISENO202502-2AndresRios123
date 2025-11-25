import axiosClient from './axiosClient.js'

export const pagoService = {
  crear: async ({ inscripcionId, monto, medio }) => {
    // POST /pagos?inscripcionId=&monto=&medio=
    const { data } = await axiosClient.post('/pagos', null, {
      params: { inscripcionId, monto, medio },
    })
    return data
  },
  actualizarEstado: async (pagoId, estado) => {
    // PUT /pagos/{pagoId}/estado?estado=APROBADO|RECHAZADO
    const { data } = await axiosClient.put(`/pagos/${pagoId}/estado`, null, {
      params: { estado },
    })
    return data
  },
}
