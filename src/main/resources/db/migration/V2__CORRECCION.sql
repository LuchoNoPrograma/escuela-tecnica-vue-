-- V2__ids_autoincrement.sql

-- Persona
ALTER TABLE persona
    ALTER COLUMN id_persona
        ADD GENERATED ALWAYS AS IDENTITY;

-- Administrativo
ALTER TABLE administrativo
    ALTER COLUMN id_administrativo
        ADD GENERATED ALWAYS AS IDENTITY;

-- Calificacion
ALTER TABLE calificacion
    ALTER COLUMN id_calificacion
        ADD GENERATED ALWAYS AS IDENTITY;

-- Categoria
ALTER TABLE categoria
    ALTER COLUMN id_categoria
        ADD GENERATED ALWAYS AS IDENTITY;

-- Certificado
ALTER TABLE certificado
    ALTER COLUMN id_certificado
        ADD GENERATED ALWAYS AS IDENTITY;

-- Comprobante_pago
ALTER TABLE comprobante_pago
    ALTER COLUMN id_comprobante
        ADD GENERATED ALWAYS AS IDENTITY;

-- Configuracion_costo
ALTER TABLE configuracion_costo
    ALTER COLUMN id_configuracion_costo
        ADD GENERATED ALWAYS AS IDENTITY;

-- Criterio_eval
ALTER TABLE criterio_eval
    ALTER COLUMN id_criterio_eval
        ADD GENERATED ALWAYS AS IDENTITY;

-- Cronograma_modulo
ALTER TABLE cronograma_modulo
    ALTER COLUMN id_cronograma_mod
        ADD GENERATED ALWAYS AS IDENTITY;

-- Detalle_pago
ALTER TABLE detalle_pago
    ALTER COLUMN id_detalle_pago
        ADD GENERATED ALWAYS AS IDENTITY;

-- Docente
ALTER TABLE docente
    ALTER COLUMN id_docente
        ADD GENERATED ALWAYS AS IDENTITY;

-- Grupo
ALTER TABLE grupo
    ALTER COLUMN id_grupo
        ADD GENERATED ALWAYS AS IDENTITY;

-- Matricula
ALTER TABLE matricula
    ALTER COLUMN cod_matricula
        ADD GENERATED ALWAYS AS IDENTITY;

-- Modalidad
ALTER TABLE modalidad
    ALTER COLUMN id_modalidad
        ADD GENERATED ALWAYS AS IDENTITY;

-- Modulo
ALTER TABLE modulo
    ALTER COLUMN id_modulo
        ADD GENERATED ALWAYS AS IDENTITY;

-- Monografia
ALTER TABLE monografia
    ALTER COLUMN id_monografia
        ADD GENERATED ALWAYS AS IDENTITY;

-- Nivel
ALTER TABLE nivel
    ALTER COLUMN id_nivel
        ADD GENERATED ALWAYS AS IDENTITY;

-- Observacion_monografia
ALTER TABLE observacion_monografia
    ALTER COLUMN id_observacion_monografia
        ADD GENERATED ALWAYS AS IDENTITY;

-- Plan_estudio
ALTER TABLE plan_estudio
    ALTER COLUMN id_plan_estudio
        ADD GENERATED ALWAYS AS IDENTITY;

-- Plan_estudio_detalle
ALTER TABLE plan_estudio_detalle
    ALTER COLUMN id_plan_estudio_detalle
        ADD GENERATED ALWAYS AS IDENTITY;

-- Plan_pago
ALTER TABLE plan_pago
    ALTER COLUMN id_plan_pago
        ADD GENERATED ALWAYS AS IDENTITY;

-- Preinscripcion
ALTER TABLE preinscripcion
    ALTER COLUMN id_preinscripcion
        ADD GENERATED ALWAYS AS IDENTITY;

-- Programa
ALTER TABLE programa
    ALTER COLUMN id_programa
        ADD GENERATED ALWAYS AS IDENTITY;

-- Programacion
ALTER TABLE programacion
    ALTER COLUMN id_programacion
        ADD GENERATED ALWAYS AS IDENTITY;

-- Revision_monografia
ALTER TABLE revision_monografia
    ALTER COLUMN id_revision_monografia
        ADD GENERATED ALWAYS AS IDENTITY;

-- Rol
ALTER TABLE rol
    ALTER COLUMN id_rol
        ADD GENERATED ALWAYS AS IDENTITY;

-- Tarea
ALTER TABLE tarea
    ALTER COLUMN id_tarea
        ADD GENERATED ALWAYS AS IDENTITY;

-- Titulacion
ALTER TABLE titulacion
    ALTER COLUMN id_titulacion
        ADD GENERATED ALWAYS AS IDENTITY;

-- Usuario
ALTER TABLE usuario
    ALTER COLUMN id_usuario
        ADD GENERATED ALWAYS AS IDENTITY;

-- Version (nota: es INTEGER pero también puede ser identity)
ALTER TABLE version
    ALTER COLUMN id_version
        ADD GENERATED ALWAYS AS IDENTITY;
