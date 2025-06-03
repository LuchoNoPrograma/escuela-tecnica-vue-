export default [
  {
    component: () => import('@/layouts/MainLayout.vue'),
    meta: { requiresAuth: true, roles: ['DOCENTE'] },
    children: [
      {
        path: '/docente',
        name: 'docente.home',
        component: () => import('@/pages/docente/DocentePage.vue')
      }
    ]
  }
]
