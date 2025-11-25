import axios from 'axios'
import { getAuthHeader, clearSession } from '../utils/storage.js'

const baseURL = import.meta.env.VITE_API_URL || 'http://localhost:8080'

const axiosClient = axios.create({
  baseURL,
})

axiosClient.interceptors.request.use((config) => {
  // No enviar Authorization en registro abierto
  const isOpenRegistration = config.method?.toLowerCase() === 'post' && config.url?.startsWith('/usuarios')
  if (!isOpenRegistration) {
    const basic = getAuthHeader()
    if (basic) {
      config.headers.Authorization = basic
    }
  }
  return config
})

axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      clearSession()
    }
    return Promise.reject(error)
  },
)

export default axiosClient
