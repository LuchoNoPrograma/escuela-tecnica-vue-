// src/composables/useAuth.js
import { ref } from 'vue'
import { api } from 'src/boot/axios'

const user = ref(null)

export function useAuth() {
  // --- Login ---
  const login = async ({ nombreUsuario, password }) => {
    const response = await api.post('/authenticate', { nombreUsuario, password })
    localStorage.setItem('access_token', response.data.accessToken)
    user.value = parseJwt(response.data.accessToken)
    return response.data
  }

  // --- Logout ---
  const logout = () => {
    localStorage.removeItem('access_token')
    user.value = null
  }

  // --- Verifica si está logueado ---
  const isLoggedIn = () => {
    const token = localStorage.getItem('access_token')
    if (!token) return false
    const payload = parseJwt(token)
    return payload && payload.exp > Math.floor(Date.now() / 1000)
  }

  // --- Devuelve roles del usuario ---
  const getRoles = () => {
    const token = localStorage.getItem('access_token')
    if (!token) return []
    const payload = parseJwt(token)
    return payload.roles || []
  }

  // --- Util para parsear JWT ---
  function parseJwt(token) {
    try {
      return JSON.parse(atob(token.split('.')[1]))
    } catch (e) {
      console.log(e)
      return null
    }
  }

  return { login, logout, isLoggedIn, getRoles, user }
}
