import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AdministrativoListComponent } from './administrativo/administrativo-list.component';
import { AdministrativoAddComponent } from './administrativo/administrativo-add.component';
import { AdministrativoEditComponent } from './administrativo/administrativo-edit.component';
import { CalificacionListComponent } from './calificacion/calificacion-list.component';
import { CalificacionAddComponent } from './calificacion/calificacion-add.component';
import { CalificacionEditComponent } from './calificacion/calificacion-edit.component';
import { CertificadoListComponent } from './certificado/certificado-list.component';
import { CertificadoAddComponent } from './certificado/certificado-add.component';
import { CertificadoEditComponent } from './certificado/certificado-edit.component';
import { CategoriaListComponent } from './categoria/categoria-list.component';
import { CategoriaAddComponent } from './categoria/categoria-add.component';
import { CategoriaEditComponent } from './categoria/categoria-edit.component';
import { ComprobantePagoListComponent } from './comprobante-pago/comprobante-pago-list.component';
import { ComprobantePagoAddComponent } from './comprobante-pago/comprobante-pago-add.component';
import { ComprobantePagoEditComponent } from './comprobante-pago/comprobante-pago-edit.component';
import { ConfiguracionCostoListComponent } from './configuracion-costo/configuracion-costo-list.component';
import { ConfiguracionCostoAddComponent } from './configuracion-costo/configuracion-costo-add.component';
import { ConfiguracionCostoEditComponent } from './configuracion-costo/configuracion-costo-edit.component';
import { CriterioEvalListComponent } from './criterio-eval/criterio-eval-list.component';
import { CriterioEvalAddComponent } from './criterio-eval/criterio-eval-add.component';
import { CriterioEvalEditComponent } from './criterio-eval/criterio-eval-edit.component';
import { CronogramaModuloListComponent } from './cronograma-modulo/cronograma-modulo-list.component';
import { CronogramaModuloAddComponent } from './cronograma-modulo/cronograma-modulo-add.component';
import { CronogramaModuloEditComponent } from './cronograma-modulo/cronograma-modulo-edit.component';
import { DetallePagoListComponent } from './detalle-pago/detalle-pago-list.component';
import { DetallePagoAddComponent } from './detalle-pago/detalle-pago-add.component';
import { DetallePagoEditComponent } from './detalle-pago/detalle-pago-edit.component';
import { DocenteListComponent } from './docente/docente-list.component';
import { DocenteAddComponent } from './docente/docente-add.component';
import { DocenteEditComponent } from './docente/docente-edit.component';
import { GrupoListComponent } from './grupo/grupo-list.component';
import { GrupoAddComponent } from './grupo/grupo-add.component';
import { GrupoEditComponent } from './grupo/grupo-edit.component';
import { MatriculaListComponent } from './matricula/matricula-list.component';
import { MatriculaAddComponent } from './matricula/matricula-add.component';
import { MatriculaEditComponent } from './matricula/matricula-edit.component';
import { ModalidadListComponent } from './modalidad/modalidad-list.component';
import { ModalidadAddComponent } from './modalidad/modalidad-add.component';
import { ModalidadEditComponent } from './modalidad/modalidad-edit.component';
import { ModuloListComponent } from './modulo/modulo-list.component';
import { ModuloAddComponent } from './modulo/modulo-add.component';
import { ModuloEditComponent } from './modulo/modulo-edit.component';
import { MonografiaListComponent } from './monografia/monografia-list.component';
import { MonografiaAddComponent } from './monografia/monografia-add.component';
import { MonografiaEditComponent } from './monografia/monografia-edit.component';
import { NivelListComponent } from './nivel/nivel-list.component';
import { NivelAddComponent } from './nivel/nivel-add.component';
import { NivelEditComponent } from './nivel/nivel-edit.component';
import { ObservacionMonografiaListComponent } from './observacion-monografia/observacion-monografia-list.component';
import { ObservacionMonografiaAddComponent } from './observacion-monografia/observacion-monografia-add.component';
import { ObservacionMonografiaEditComponent } from './observacion-monografia/observacion-monografia-edit.component';
import { OcupaListComponent } from './ocupa/ocupa-list.component';
import { OcupaAddComponent } from './ocupa/ocupa-add.component';
import { OcupaEditComponent } from './ocupa/ocupa-edit.component';
import { PersonaListComponent } from './persona/persona-list.component';
import { PersonaAddComponent } from './persona/persona-add.component';
import { PersonaEditComponent } from './persona/persona-edit.component';
import { PlanEstudioListComponent } from './plan-estudio/plan-estudio-list.component';
import { PlanEstudioAddComponent } from './plan-estudio/plan-estudio-add.component';
import { PlanEstudioEditComponent } from './plan-estudio/plan-estudio-edit.component';
import { PlanEstudioDetalleListComponent } from './plan-estudio-detalle/plan-estudio-detalle-list.component';
import { PlanEstudioDetalleAddComponent } from './plan-estudio-detalle/plan-estudio-detalle-add.component';
import { PlanEstudioDetalleEditComponent } from './plan-estudio-detalle/plan-estudio-detalle-edit.component';
import { PlanPagoListComponent } from './plan-pago/plan-pago-list.component';
import { PlanPagoAddComponent } from './plan-pago/plan-pago-add.component';
import { PlanPagoEditComponent } from './plan-pago/plan-pago-edit.component';
import { ProgramaListComponent } from './programa/programa-list.component';
import { ProgramaAddComponent } from './programa/programa-add.component';
import { ProgramaEditComponent } from './programa/programa-edit.component';
import { RevisionMonografiaListComponent } from './revision-monografia/revision-monografia-list.component';
import { RevisionMonografiaAddComponent } from './revision-monografia/revision-monografia-add.component';
import { RevisionMonografiaEditComponent } from './revision-monografia/revision-monografia-edit.component';
import { ProgramacionListComponent } from './programacion/programacion-list.component';
import { ProgramacionAddComponent } from './programacion/programacion-add.component';
import { ProgramacionEditComponent } from './programacion/programacion-edit.component';
import { RolListComponent } from './rol/rol-list.component';
import { RolAddComponent } from './rol/rol-add.component';
import { RolEditComponent } from './rol/rol-edit.component';
import { TareaListComponent } from './tarea/tarea-list.component';
import { TareaAddComponent } from './tarea/tarea-add.component';
import { TareaEditComponent } from './tarea/tarea-edit.component';
import { TitulacionListComponent } from './titulacion/titulacion-list.component';
import { TitulacionAddComponent } from './titulacion/titulacion-add.component';
import { TitulacionEditComponent } from './titulacion/titulacion-edit.component';
import { UsuarioListComponent } from './usuario/usuario-list.component';
import { UsuarioAddComponent } from './usuario/usuario-add.component';
import { UsuarioEditComponent } from './usuario/usuario-edit.component';
import { PreinscripcionListComponent } from './preinscripcion/preinscripcion-list.component';
import { PreinscripcionAddComponent } from './preinscripcion/preinscripcion-add.component';
import { PreinscripcionEditComponent } from './preinscripcion/preinscripcion-edit.component';
import { InicioMostrarInicioComponent } from './inicio/inicio-mostrar-inicio.component';
import { AuthenticationComponent } from './security/authentication.component';
import { ErrorComponent } from './error/error.component';
import { AuthenticationService, ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO, DOCENTE } from 'app/security/authentication.service';


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: $localize`:@@home.index.headline:Welcome to your new app!`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'administrativos',
    component: AdministrativoListComponent,
    title: $localize`:@@administrativo.list.headline:Administrativoes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'administrativos/add',
    component: AdministrativoAddComponent,
    title: $localize`:@@administrativo.add.headline:Add Administrativo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'administrativos/edit/:idAdministrativo',
    component: AdministrativoEditComponent,
    title: $localize`:@@administrativo.edit.headline:Edit Administrativo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'calificacions',
    component: CalificacionListComponent,
    title: $localize`:@@calificacion.list.headline:Calificacions`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'calificacions/add',
    component: CalificacionAddComponent,
    title: $localize`:@@calificacion.add.headline:Add Calificacion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'calificacions/edit/:idCalificacion',
    component: CalificacionEditComponent,
    title: $localize`:@@calificacion.edit.headline:Edit Calificacion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'certificados',
    component: CertificadoListComponent,
    title: $localize`:@@certificado.list.headline:Certificadoes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'certificados/add',
    component: CertificadoAddComponent,
    title: $localize`:@@certificado.add.headline:Add Certificado`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'certificados/edit/:idCertificado',
    component: CertificadoEditComponent,
    title: $localize`:@@certificado.edit.headline:Edit Certificado`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'categorias',
    component: CategoriaListComponent,
    title: $localize`:@@categoria.list.headline:Categorias`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'categorias/add',
    component: CategoriaAddComponent,
    title: $localize`:@@categoria.add.headline:Add Categoria`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'categorias/edit/:idCategoria',
    component: CategoriaEditComponent,
    title: $localize`:@@categoria.edit.headline:Edit Categoria`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'comprobantePagos',
    component: ComprobantePagoListComponent,
    title: $localize`:@@comprobantePago.list.headline:Comprobante Pagoes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'comprobantePagos/add',
    component: ComprobantePagoAddComponent,
    title: $localize`:@@comprobantePago.add.headline:Add Comprobante Pago`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'comprobantePagos/edit/:idComprobante',
    component: ComprobantePagoEditComponent,
    title: $localize`:@@comprobantePago.edit.headline:Edit Comprobante Pago`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'configuracionCostos',
    component: ConfiguracionCostoListComponent,
    title: $localize`:@@configuracionCosto.list.headline:Configuracion Costoes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'configuracionCostos/add',
    component: ConfiguracionCostoAddComponent,
    title: $localize`:@@configuracionCosto.add.headline:Add Configuracion Costo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'configuracionCostos/edit/:idConfiguracionCosto',
    component: ConfiguracionCostoEditComponent,
    title: $localize`:@@configuracionCosto.edit.headline:Edit Configuracion Costo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'criterioEvals',
    component: CriterioEvalListComponent,
    title: $localize`:@@criterioEval.list.headline:Criterio Evals`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'criterioEvals/add',
    component: CriterioEvalAddComponent,
    title: $localize`:@@criterioEval.add.headline:Add Criterio Eval`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'criterioEvals/edit/:idCriterioEval',
    component: CriterioEvalEditComponent,
    title: $localize`:@@criterioEval.edit.headline:Edit Criterio Eval`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'cronogramaModulos',
    component: CronogramaModuloListComponent,
    title: $localize`:@@cronogramaModulo.list.headline:Cronograma Moduloes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO, DOCENTE]
    }
  },
  {
    path: 'cronogramaModulos/add',
    component: CronogramaModuloAddComponent,
    title: $localize`:@@cronogramaModulo.add.headline:Add Cronograma Modulo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO, DOCENTE]
    }
  },
  {
    path: 'cronogramaModulos/edit/:idCronogramaMod',
    component: CronogramaModuloEditComponent,
    title: $localize`:@@cronogramaModulo.edit.headline:Edit Cronograma Modulo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO, DOCENTE]
    }
  },
  {
    path: 'detallePagos',
    component: DetallePagoListComponent,
    title: $localize`:@@detallePago.list.headline:Detalle Pagoes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'detallePagos/add',
    component: DetallePagoAddComponent,
    title: $localize`:@@detallePago.add.headline:Add Detalle Pago`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'detallePagos/edit/:idDetallePago',
    component: DetallePagoEditComponent,
    title: $localize`:@@detallePago.edit.headline:Edit Detalle Pago`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'docentes',
    component: DocenteListComponent,
    title: $localize`:@@docente.list.headline:Docentes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'docentes/add',
    component: DocenteAddComponent,
    title: $localize`:@@docente.add.headline:Add Docente`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'docentes/edit/:idDocente',
    component: DocenteEditComponent,
    title: $localize`:@@docente.edit.headline:Edit Docente`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'grupos',
    component: GrupoListComponent,
    title: $localize`:@@grupo.list.headline:Grupoes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'grupos/add',
    component: GrupoAddComponent,
    title: $localize`:@@grupo.add.headline:Add Grupo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'grupos/edit/:idGrupo',
    component: GrupoEditComponent,
    title: $localize`:@@grupo.edit.headline:Edit Grupo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'matriculas',
    component: MatriculaListComponent,
    title: $localize`:@@matricula.list.headline:Matriculas`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'matriculas/add',
    component: MatriculaAddComponent,
    title: $localize`:@@matricula.add.headline:Add Matricula`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'matriculas/edit/:codMatricula',
    component: MatriculaEditComponent,
    title: $localize`:@@matricula.edit.headline:Edit Matricula`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'modalidads',
    component: ModalidadListComponent,
    title: $localize`:@@modalidad.list.headline:Modalidads`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'modalidads/add',
    component: ModalidadAddComponent,
    title: $localize`:@@modalidad.add.headline:Add Modalidad`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'modalidads/edit/:idModalidad',
    component: ModalidadEditComponent,
    title: $localize`:@@modalidad.edit.headline:Edit Modalidad`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'modulos',
    component: ModuloListComponent,
    title: $localize`:@@modulo.list.headline:Moduloes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'modulos/add',
    component: ModuloAddComponent,
    title: $localize`:@@modulo.add.headline:Add Modulo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'modulos/edit/:idModulo',
    component: ModuloEditComponent,
    title: $localize`:@@modulo.edit.headline:Edit Modulo`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'monografias',
    component: MonografiaListComponent,
    title: $localize`:@@monografia.list.headline:Monografias`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'monografias/add',
    component: MonografiaAddComponent,
    title: $localize`:@@monografia.add.headline:Add Monografia`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'monografias/edit/:idMonografia',
    component: MonografiaEditComponent,
    title: $localize`:@@monografia.edit.headline:Edit Monografia`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'nivels',
    component: NivelListComponent,
    title: $localize`:@@nivel.list.headline:Nivels`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'nivels/add',
    component: NivelAddComponent,
    title: $localize`:@@nivel.add.headline:Add Nivel`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'nivels/edit/:idNivel',
    component: NivelEditComponent,
    title: $localize`:@@nivel.edit.headline:Edit Nivel`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'observacionMonografias',
    component: ObservacionMonografiaListComponent,
    title: $localize`:@@observacionMonografia.list.headline:Observacion Monografias`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'observacionMonografias/add',
    component: ObservacionMonografiaAddComponent,
    title: $localize`:@@observacionMonografia.add.headline:Add Observacion Monografia`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'observacionMonografias/edit/:idObservacionMonografia',
    component: ObservacionMonografiaEditComponent,
    title: $localize`:@@observacionMonografia.edit.headline:Edit Observacion Monografia`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'ocupas',
    component: OcupaListComponent,
    title: $localize`:@@ocupa.list.headline:Ocupas`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'ocupas/add',
    component: OcupaAddComponent,
    title: $localize`:@@ocupa.add.headline:Add Ocupa`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'ocupas/edit/:estOcupa',
    component: OcupaEditComponent,
    title: $localize`:@@ocupa.edit.headline:Edit Ocupa`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'personas',
    component: PersonaListComponent,
    title: $localize`:@@persona.list.headline:Personae`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'personas/add',
    component: PersonaAddComponent,
    title: $localize`:@@persona.add.headline:Add Persona`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'personas/edit/:idPersona',
    component: PersonaEditComponent,
    title: $localize`:@@persona.edit.headline:Edit Persona`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planEstudios',
    component: PlanEstudioListComponent,
    title: $localize`:@@planEstudio.list.headline:Plan Estudios`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planEstudios/add',
    component: PlanEstudioAddComponent,
    title: $localize`:@@planEstudio.add.headline:Add Plan Estudio`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planEstudios/edit/:idPlanEstudio',
    component: PlanEstudioEditComponent,
    title: $localize`:@@planEstudio.edit.headline:Edit Plan Estudio`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planEstudioDetalles',
    component: PlanEstudioDetalleListComponent,
    title: $localize`:@@planEstudioDetalle.list.headline:Plan Estudio Detalles`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planEstudioDetalles/add',
    component: PlanEstudioDetalleAddComponent,
    title: $localize`:@@planEstudioDetalle.add.headline:Add Plan Estudio Detalle`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planEstudioDetalles/edit/:idPlanEstudioDetalle',
    component: PlanEstudioDetalleEditComponent,
    title: $localize`:@@planEstudioDetalle.edit.headline:Edit Plan Estudio Detalle`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planPagos',
    component: PlanPagoListComponent,
    title: $localize`:@@planPago.list.headline:Plan Pagoes`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planPagos/add',
    component: PlanPagoAddComponent,
    title: $localize`:@@planPago.add.headline:Add Plan Pago`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'planPagos/edit/:idPlanPago',
    component: PlanPagoEditComponent,
    title: $localize`:@@planPago.edit.headline:Edit Plan Pago`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'programas',
    component: ProgramaListComponent,
    title: $localize`:@@programa.list.headline:Programas`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'programas/add',
    component: ProgramaAddComponent,
    title: $localize`:@@programa.add.headline:Add Programa`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'programas/edit/:idPrograma',
    component: ProgramaEditComponent,
    title: $localize`:@@programa.edit.headline:Edit Programa`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'revisionMonografias',
    component: RevisionMonografiaListComponent,
    title: $localize`:@@revisionMonografia.list.headline:Revision Monografias`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'revisionMonografias/add',
    component: RevisionMonografiaAddComponent,
    title: $localize`:@@revisionMonografia.add.headline:Add Revision Monografia`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'revisionMonografias/edit/:idRevisionMonografia',
    component: RevisionMonografiaEditComponent,
    title: $localize`:@@revisionMonografia.edit.headline:Edit Revision Monografia`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'programacions',
    component: ProgramacionListComponent,
    title: $localize`:@@programacion.list.headline:Programacions`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'programacions/add',
    component: ProgramacionAddComponent,
    title: $localize`:@@programacion.add.headline:Add Programacion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'programacions/edit/:idProgramacion',
    component: ProgramacionEditComponent,
    title: $localize`:@@programacion.edit.headline:Edit Programacion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'rols',
    component: RolListComponent,
    title: $localize`:@@rol.list.headline:Rols`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'rols/add',
    component: RolAddComponent,
    title: $localize`:@@rol.add.headline:Add Rol`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'rols/edit/:idPermiso',
    component: RolEditComponent,
    title: $localize`:@@rol.edit.headline:Edit Rol`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'tareas',
    component: TareaListComponent,
    title: $localize`:@@tarea.list.headline:Tareas`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'tareas/add',
    component: TareaAddComponent,
    title: $localize`:@@tarea.add.headline:Add Tarea`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'tareas/edit/:idTarea',
    component: TareaEditComponent,
    title: $localize`:@@tarea.edit.headline:Edit Tarea`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'titulacions',
    component: TitulacionListComponent,
    title: $localize`:@@titulacion.list.headline:Titulacions`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'titulacions/add',
    component: TitulacionAddComponent,
    title: $localize`:@@titulacion.add.headline:Add Titulacion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'titulacions/edit/:idTitulacion',
    component: TitulacionEditComponent,
    title: $localize`:@@titulacion.edit.headline:Edit Titulacion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS, ADMINISTRATIVO]
    }
  },
  {
    path: 'usuarios',
    component: UsuarioListComponent,
    title: $localize`:@@usuario.list.headline:Usuarios`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'usuarios/add',
    component: UsuarioAddComponent,
    title: $localize`:@@usuario.add.headline:Add Usuario`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'usuarios/edit/:idUsuario',
    component: UsuarioEditComponent,
    title: $localize`:@@usuario.edit.headline:Edit Usuario`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'preinscripcions',
    component: PreinscripcionListComponent,
    title: $localize`:@@preinscripcion.list.headline:Preinscripcions`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'preinscripcions/add',
    component: PreinscripcionAddComponent,
    title: $localize`:@@preinscripcion.add.headline:Add Preinscripcion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'preinscripcions/edit/:idPreinscripcion',
    component: PreinscripcionEditComponent,
    title: $localize`:@@preinscripcion.edit.headline:Edit Preinscripcion`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: '',
    component: InicioMostrarInicioComponent,
    title: $localize`:@@inicio.mostrarInicio.headline:Mostrar Inicio`,
    data: {
      roles: [ADMINISTRADOR_SISTEMAS]
    }
  },
  {
    path: 'login',
    component: AuthenticationComponent,
    title: $localize`:@@authentication.login.headline:Login`
  },
  {
    path: 'error',
    component: ErrorComponent,
    title: $localize`:@@error.page.headline:Error`
  },
  {
    path: '**',
    component: ErrorComponent,
    title: $localize`:@@notFound.headline:Page not found`
  }
];

// add authentication check to all routes
for (const route of routes) {
  route.canActivate = [(route: ActivatedRouteSnapshot) => inject(AuthenticationService).checkAccessAllowed(route)];
}
