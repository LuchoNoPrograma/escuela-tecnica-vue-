export default [
  {
    path: '/login',
    component: () => import('@/layouts/AuthLayout.vue'),
    children: [
      {
        path: '',
        name: 'auth.login',
        component: () => import('@/pages/LoginPage.vue')
      }
    ]
  }
]
