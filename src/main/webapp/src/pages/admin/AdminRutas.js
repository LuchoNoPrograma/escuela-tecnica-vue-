const AdminRutas = [
  {
    path: '/admin',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR_SISTEMAS'] },
    children: [
      {
        path: '',
        name: 'admin.home',
        component: () => import('@/pages/admin/AdminPage.vue')
      }
    ]
  }
]

export default AdminRutas;
