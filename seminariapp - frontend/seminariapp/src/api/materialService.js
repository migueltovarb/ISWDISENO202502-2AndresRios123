import axiosClient from './axiosClient.js'

export const materialService = {
  upload: async (payload) => {
    // POST /materiales con cuerpo {titulo, tipo, url, eventoId}
    const { data } = await axiosClient.post('/materiales', payload)
    return data
  },
  listByEvento: async (eventoId) => {
    // GET /materiales/evento/{eventoId}
    const { data } = await axiosClient.get(`/materiales/evento/${eventoId}`)
    return data
  },
}
