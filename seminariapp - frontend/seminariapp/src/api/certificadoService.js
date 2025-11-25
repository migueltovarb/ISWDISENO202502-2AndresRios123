import axiosClient from './axiosClient.js'

export const certificadoService = {
  generar: async (inscripcionId) => {
    // POST /certificados/generar?inscripcionId=
    const { data } = await axiosClient.post('/certificados/generar', null, { params: { inscripcionId } })
    return data
  },
  obtenerPorInscripcion: async (inscripcionId) => {
    // GET /certificados/inscripcion/{inscripcionId}
    const { data } = await axiosClient.get(`/certificados/inscripcion/${inscripcionId}`)
    return data
  },
  descargarPdf: async (inscripcionId) => {
    // GET /certificados/inscripcion/{id}/pdf
    const { data } = await axiosClient.get(`/certificados/inscripcion/${inscripcionId}/pdf`, {
      responseType: 'arraybuffer',
    })
    return data
  },
}
