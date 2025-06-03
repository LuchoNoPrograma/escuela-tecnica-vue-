import { defineRouter } from '#q-app/wrappers'
import { createRouter, createMemoryHistory, createWebHistory, createWebHashHistory } from 'vue-router'
import routes from './routes.js'
import { useAuth } from 'src/composables/useAuth.js'

export default defineRouter(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory)

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,
    history: createHistory(process.env.VUE_ROUTER_BASE)
  })

  // --- Aquí va tu guard de autenticación ---
  Router.beforeEach((to, from, next) => {
    const { isLoggedIn, getRoles } = useAuth()
    if (to.meta.requiresAuth && !isLoggedIn()) {
      next('/login')
    } else if (to.meta.roles && !to.meta.roles.some(role => getRoles().includes(role))) {
      next('/error')
    } else {
      next()
    }
  })

  return Router
})
