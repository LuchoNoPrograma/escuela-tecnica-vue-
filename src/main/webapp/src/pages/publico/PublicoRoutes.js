export default [
  {
    path: '/',
    component: () => import('@/layouts/BlankLayout.vue'),
    children: [
      {
        path: '',
        name: 'publico.index',
        component: () => import('@/pages/IndexPage.vue')
      }
    ]
  },
  {
    path: '/error',
    component: () => import('@/layouts/AuthLayout.vue'),
    children: [
      { path: '', name: 'error.notfound', component: () => import('@/pages/ErrorNotFound.vue') }
    ]
  },
  {
    path: '/:catchAll(.*)*',
    component: () => import('@/layouts/AuthLayout.vue'),
    children: [
      { path: '', name: 'error.404', component: () => import('@/pages/ErrorNotFound.vue') }
    ]
  }
]
