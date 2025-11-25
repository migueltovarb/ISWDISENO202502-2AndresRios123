import axiosClient from './axiosClient.js'

export const eventoService = {
  list: async () => {
    // GET /eventos
    const { data } = await axiosClient.get('/eventos')
    return data
  },
  getById: async (id) => {
    // GET /eventos/{id}
    const { data } = await axiosClient.get(`/eventos/${id}`)
    return data
  },
  create: async (payload) => {
    // POST /eventos
    const { data } = await axiosClient.post('/eventos', payload)
    return data
  },
  update: async (id, payload) => {
    // PUT /eventos/{id}
    const { data } = await axiosClient.put(`/eventos/${id}`, payload)
    return data
  },
  remove: async (id) => {
    // DELETE /eventos/{id}
    const { data } = await axiosClient.delete(`/eventos/${id}`)
    return data
  },
  assignPonente: async (eventoId, ponenteId) => {
    // PUT /eventos/{eventoId}/asignar-ponente/{ponenteId}
    const { data } = await axiosClient.put(`/eventos/${eventoId}/asignar-ponente/${ponenteId}`)
    return data
  },
}
