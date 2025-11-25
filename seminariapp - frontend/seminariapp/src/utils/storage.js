const AUTH_KEY = 'seminariapp_basic'
const EMAIL_KEY = 'seminariapp_email'

export const getCredentials = () => {
  const email = localStorage.getItem(EMAIL_KEY)
  const basic = localStorage.getItem(AUTH_KEY)
  return { email, basic }
}

export const setCredentials = ({ email, password }) => {
  const basic = `Basic ${btoa(`${email}:${password}`)}`
  localStorage.setItem(EMAIL_KEY, email)
  localStorage.setItem(AUTH_KEY, basic)
}

export const getAuthHeader = () => {
  return localStorage.getItem(AUTH_KEY)
}

export const clearSession = () => {
  localStorage.removeItem(AUTH_KEY)
  localStorage.removeItem(EMAIL_KEY)
}
