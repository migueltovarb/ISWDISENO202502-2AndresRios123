import axiosClient from './axiosClient.js'

export const inscripcionService = {
  inscribir: async ({ usuarioId, eventoId }) => {
    // POST /inscripciones?usuarioId=&eventoId=
    const { data } = await axiosClient.post('/inscripciones', null, { params: { usuarioId, eventoId } })
    return data
  },
  cancelar: async (inscripcionId) => {
    // PUT /inscripciones/{id}/cancelar
    const { data } = await axiosClient.put(`/inscripciones/${inscripcionId}/cancelar`)
    return data
  },
  listByUsuario: async (usuarioId) => {
    // GET /inscripciones/usuario/{usuarioId}
    const { data } = await axiosClient.get(`/inscripciones/usuario/${usuarioId}`)
    return data
  },
  listByEvento: async (eventoId) => {
    // GET /inscripciones/evento/{eventoId}
    const { data } = await axiosClient.get(`/inscripciones/evento/${eventoId}`)
    return data
  },
  listAprobadasDetalle: async (eventoId) => {
    // GET /inscripciones/evento/{eventoId}/aprobadas
    const { data } = await axiosClient.get(`/inscripciones/evento/${eventoId}/aprobadas`)
    return data
  },
  marcarAsistencia: async (inscripcionId, sesion, presente = true) => {
    // PATCH /inscripciones/{id}/asistencia?sesion=&presente=
    const { data } = await axiosClient.patch(`/inscripciones/${inscripcionId}/asistencia`, null, {
      params: { sesion, presente },
    })
    return data
  },
}
