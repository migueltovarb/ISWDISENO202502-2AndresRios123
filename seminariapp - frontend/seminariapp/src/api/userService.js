import axiosClient from './axiosClient.js'

export const userService = {
  create: async (payload) => {
    // POST /usuarios (registro)
    const { data } = await axiosClient.post('/usuarios', payload)
    return data
  },
  getAll: async () => {
    // GET /usuarios
    const { data } = await axiosClient.get('/usuarios')
    return data
  },
  getByEmail: async (email) => {
    // GET /usuarios/email/{email}
    const { data } = await axiosClient.get(`/usuarios/email/${encodeURIComponent(email)}`)
    return data
  },
  updateRole: async (userId, role) => {
    // PATCH /usuarios/{id}/rol?rol=ADMIN
    const { data } = await axiosClient.patch(`/usuarios/${userId}/rol`, null, { params: { rol: role } })
    return data
  },
  getByRole: async (role) => {
    // GET /usuarios/rol/{rol}
    const { data } = await axiosClient.get(`/usuarios/rol/${role}`)
    return data
  },
}
