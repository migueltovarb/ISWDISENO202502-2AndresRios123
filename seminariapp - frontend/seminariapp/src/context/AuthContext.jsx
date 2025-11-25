import PropTypes from 'prop-types'
import { createContext, useCallback, useContext, useEffect, useState } from 'react'
import { userService } from '../api/userService.js'
import { clearSession, getAuthHeader, getCredentials, setCredentials } from '../utils/storage.js'

const AuthContext = createContext(null)

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [authHeader, setAuthHeader] = useState(getAuthHeader())
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const fetchCurrentUser = useCallback(async () => {
    const { email } = getCredentials()
    const header = getAuthHeader()
    if (!header || !email) {
      setLoading(false)
      return null
    }
    try {
      const profile = await userService.getByEmail(email)
      setUser(profile)
      setError(null)
      return profile
    } catch (err) {
      clearSession()
      setUser(null)
      setAuthHeader(null)
      const message = err.response?.status === 401 ? 'Credenciales incorrectas.' : 'No pudimos validar la sesión.'
      setError(message)
      throw new Error(message)
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    fetchCurrentUser()
  }, [fetchCurrentUser])

  const login = async (credentials) => {
    setLoading(true)
    try {
      setCredentials(credentials)
      const header = getAuthHeader()
      setAuthHeader(header)
      const profile = await fetchCurrentUser()
      if (!profile) {
        throw new Error('Credenciales incorrectas.')
      }
    } catch (err) {
      setLoading(false)
      const message = err.message || err.response?.data?.message || 'No pudimos iniciar sesión.'
      throw new Error(message)
    }
  }

  const register = async (payload) => {
    setLoading(true)
    try {
      await userService.create(payload)
      setLoading(false)
      return true
    } catch (err) {
      setLoading(false)
      const message = err.response?.data?.message || 'No pudimos registrar al usuario.'
      throw err.response ? { ...err, message } : new Error(message)
    }
  }

  const logout = () => {
    clearSession()
    setUser(null)
    setAuthHeader(null)
  }

  const value = {
    user,
    authHeader,
    isAuthenticated: Boolean(user),
    loading,
    error,
    login,
    register,
    logout,
    refreshUser: fetchCurrentUser,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

AuthProvider.propTypes = {
  children: PropTypes.node,
}

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth debe usarse dentro de AuthProvider')
  }
  return context
}
