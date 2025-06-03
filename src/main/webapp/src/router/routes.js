import AdminRutas from '@/pages/admin/AdminRutas';
import DocenteRutas from '@/pages/docente/DocenteRutas';
import PublicoRoutes from '@/pages/publico/PublicoRoutes';
import AuthRutas from '@/pages/auth/AuthRutas';

const routes = [
  ...AuthRutas,
  ...AdminRutas,
  ...DocenteRutas,
  ...PublicoRoutes
]

export default routes
