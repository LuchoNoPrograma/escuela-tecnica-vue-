CREATE TABLE administrativo
(
    id_administrativo BIGINT                      NOT NULL,
    id_usu_reg        BIGINT                      NOT NULL,
    id_usu_mod        BIGINT,
    fec_reg           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod           TIMESTAMP WITHOUT TIME ZONE,
    fec_ingreso       TIMESTAMP WITHOUT TIME ZONE,
    fec_salida        TIMESTAMP WITHOUT TIME ZONE,
    habil             BOOLEAN,
    fk_id_persona     BIGINT                      NOT NULL,
    CONSTRAINT pk_administrativo PRIMARY KEY (id_administrativo)
);

CREATE TABLE asigna
(
    fk_id_tarea   BIGINT NOT NULL,
    fk_id_usuario BIGINT NOT NULL,
    CONSTRAINT pk_asigna PRIMARY KEY (fk_id_tarea, fk_id_usuario)
);

CREATE TABLE calificacion
(
    id_calificacion     BIGINT                      NOT NULL,
    id_usu_reg          BIGINT                      NOT NULL,
    id_usu_mod          BIGINT,
    fec_reg             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod             TIMESTAMP WITHOUT TIME ZONE,
    nota                DECIMAL(7, 2),
    fk_id_criterio_eval BIGINT                      NOT NULL,
    fk_id_programacion  BIGINT                      NOT NULL,
    CONSTRAINT pk_calificacion PRIMARY KEY (id_calificacion)
);

CREATE TABLE categoria
(
    id_categoria BIGINT                      NOT NULL,
    id_usu_reg   BIGINT                      NOT NULL,
    id_usu_mod   BIGINT,
    fec_reg      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod      TIMESTAMP WITHOUT TIME ZONE,
    nombre_cat   VARCHAR(100),
    CONSTRAINT pk_categoria PRIMARY KEY (id_categoria)
);

CREATE TABLE certificado
(
    id_certificado       BIGINT                      NOT NULL,
    id_usu_reg           BIGINT                      NOT NULL,
    id_usu_mod           BIGINT,
    fec_reg              TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod              TIMESTAMP WITHOUT TIME ZONE,
    fecha_emision        date,
    est_certificado      VARCHAR(55),
    fk_id_administrativo BIGINT                      NOT NULL,
    fk_cod_matricula     BIGINT,
    CONSTRAINT pk_certificado PRIMARY KEY (id_certificado)
);

CREATE TABLE comprobante_detalle_pago
(
    fk_id_comprobante  BIGINT NOT NULL,
    fk_id_detalle_pago BIGINT NOT NULL,
    CONSTRAINT pk_comprobante_detalle_pago PRIMARY KEY (fk_id_comprobante, fk_id_detalle_pago)
);

CREATE TABLE comprobante_pago
(
    id_comprobante  BIGINT                      NOT NULL,
    id_usu_reg      BIGINT                      NOT NULL,
    id_usu_mod      BIGINT,
    fec_reg         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod         TIMESTAMP WITHOUT TIME ZONE,
    cod_comprobante VARCHAR(25)                 NOT NULL,
    monto           DECIMAL(12, 2)              NOT NULL,
    fec_comprobante TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    tipo_deposito   VARCHAR(35)                 NOT NULL,
    CONSTRAINT pk_comprobantepago PRIMARY KEY (id_comprobante)
);

CREATE TABLE configuracion_costo
(
    id_configuracion_costo BIGINT                      NOT NULL,
    id_usu_reg             BIGINT                      NOT NULL,
    id_usu_mod             BIGINT,
    fec_reg                TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod                TIMESTAMP WITHOUT TIME ZONE,
    monto_config           DECIMAL(12, 2)              NOT NULL,
    fec_inicio_vig         date                        NOT NULL,
    fec_fin_vig            date,
    est_config             BOOLEAN                     NOT NULL,
    concepto               VARCHAR(55)                 NOT NULL,
    fk_id_programa         BIGINT                      NOT NULL,
    CONSTRAINT pk_configuracioncosto PRIMARY KEY (id_configuracion_costo)
);

CREATE TABLE criterio_eval
(
    id_criterio_eval     BIGINT                      NOT NULL,
    id_usu_reg           BIGINT                      NOT NULL,
    id_usu_mod           BIGINT,
    fec_reg              TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod              TIMESTAMP WITHOUT TIME ZONE,
    nombre_crit          VARCHAR(25)                 NOT NULL,
    descripcion          VARCHAR(155),
    ponderacion          INTEGER                     NOT NULL,
    orden                INTEGER                     NOT NULL,
    fk_id_cronograma_mod BIGINT                      NOT NULL,
    CONSTRAINT pk_criterioeval PRIMARY KEY (id_criterio_eval)
);

CREATE TABLE cronograma_modulo
(
    id_cronograma_mod          BIGINT                      NOT NULL,
    id_usu_reg                 BIGINT                      NOT NULL,
    id_usu_mod                 BIGINT,
    fec_reg                    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod                    TIMESTAMP WITHOUT TIME ZONE,
    fec_inicio                 date,
    fec_fin                    date,
    estado                     VARCHAR(35)                 NOT NULL,
    fk_id_plan_estudio_detalle BIGINT                      NOT NULL,
    fk_id_grupo                BIGINT                      NOT NULL,
    fk_id_docente              BIGINT,
    CONSTRAINT pk_cronogramamodulo PRIMARY KEY (id_cronograma_mod)
);

CREATE TABLE detalle_pago
(
    id_detalle_pago      BIGINT                      NOT NULL,
    id_usu_reg           BIGINT                      NOT NULL,
    id_usu_mod           BIGINT,
    fec_reg              TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod              TIMESTAMP WITHOUT TIME ZONE,
    monto_pagado_detalle DECIMAL(12, 2),
    fk_id_plan_pago      BIGINT                      NOT NULL,
    CONSTRAINT pk_detallepago PRIMARY KEY (id_detalle_pago)
);

CREATE TABLE docente
(
    id_docente          BIGINT                      NOT NULL,
    id_usu_reg          BIGINT                      NOT NULL,
    id_usu_mod          BIGINT,
    fec_reg             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod             TIMESTAMP WITHOUT TIME ZONE,
    nro_resolucion      INTEGER,
    fec_inicio_contrato date                        NOT NULL,
    fec_fin_contrato    date,
    fk_id_persona       BIGINT                      NOT NULL,
    CONSTRAINT pk_docente PRIMARY KEY (id_docente)
);

CREATE TABLE grupo
(
    id_grupo       BIGINT                      NOT NULL,
    id_usu_reg     BIGINT                      NOT NULL,
    id_usu_mod     BIGINT,
    fec_reg        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod        TIMESTAMP WITHOUT TIME ZONE,
    nombre_grupo   VARCHAR(100)                NOT NULL,
    est_grupo      VARCHAR(35)                 NOT NULL,
    fk_id_version  INTEGER,
    fk_id_programa BIGINT                      NOT NULL,
    CONSTRAINT pk_grupo PRIMARY KEY (id_grupo)
);

CREATE TABLE matricula
(
    cod_matricula     BIGINT                      NOT NULL,
    id_usu_reg        BIGINT                      NOT NULL,
    id_usu_mod        BIGINT,
    fec_reg           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod           TIMESTAMP WITHOUT TIME ZONE,
    fec_matriculacion date,
    est_matricula     VARCHAR(35)                 NOT NULL,
    fk_id_grupo       BIGINT                      NOT NULL,
    fk_id_persona     BIGINT                      NOT NULL,
    CONSTRAINT pk_matricula PRIMARY KEY (cod_matricula)
);

CREATE TABLE modalidad
(
    id_modalidad BIGINT                      NOT NULL,
    id_usu_reg   BIGINT                      NOT NULL,
    id_usu_mod   BIGINT,
    fec_reg      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod      TIMESTAMP WITHOUT TIME ZONE,
    nombre       VARCHAR(50),
    CONSTRAINT pk_modalidad PRIMARY KEY (id_modalidad)
);

CREATE TABLE modulo
(
    id_modulo   BIGINT                      NOT NULL,
    id_usu_reg  BIGINT                      NOT NULL,
    id_usu_mod  BIGINT,
    fec_reg     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod     TIMESTAMP WITHOUT TIME ZONE,
    nombre_mod  VARCHAR(100)                NOT NULL,
    competencia TEXT,
    CONSTRAINT pk_modulo PRIMARY KEY (id_modulo)
);

CREATE TABLE monografia
(
    id_monografia    BIGINT                      NOT NULL,
    id_usu_reg       BIGINT                      NOT NULL,
    id_usu_mod       BIGINT,
    fec_reg          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod          TIMESTAMP WITHOUT TIME ZONE,
    titulo           VARCHAR(255)                NOT NULL,
    est_monografia   VARCHAR(35)                 NOT NULL,
    nota_final       INTEGER                     NOT NULL,
    fk_cod_matricula BIGINT,
    CONSTRAINT pk_monografia PRIMARY KEY (id_monografia)
);

CREATE TABLE nivel
(
    id_nivel     BIGINT                      NOT NULL,
    id_usu_reg   BIGINT                      NOT NULL,
    id_usu_mod   BIGINT,
    fec_reg      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod      TIMESTAMP WITHOUT TIME ZONE,
    nombre_nivel VARCHAR(100)                NOT NULL,
    CONSTRAINT pk_nivel PRIMARY KEY (id_nivel)
);

CREATE TABLE observacion_monografia
(
    id_observacion_monografia BIGINT                      NOT NULL,
    id_usu_reg                BIGINT                      NOT NULL,
    id_usu_mod                BIGINT,
    fec_reg                   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod                   TIMESTAMP WITHOUT TIME ZONE,
    descripcion               TEXT                        NOT NULL,
    fk_id_revision_monografia BIGINT                      NOT NULL,
    CONSTRAINT pk_observacionmonografia PRIMARY KEY (id_observacion_monografia)
);

CREATE TABLE ocupa
(
    est_ocupa     VARCHAR(25)                 NOT NULL,
    id_usu_reg    BIGINT                      NOT NULL,
    id_usu_mod    BIGINT,
    fec_reg       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod       TIMESTAMP WITHOUT TIME ZONE,
    fk_id_rol     BIGINT                      NOT NULL,
    fk_id_usuario BIGINT                      NOT NULL,
    CONSTRAINT pk_ocupa PRIMARY KEY (est_ocupa)
);

CREATE TABLE persona
(
    id_persona  BIGINT                      NOT NULL,
    id_usu_reg  BIGINT                      NOT NULL,
    id_usu_mod  BIGINT,
    fec_reg     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod     TIMESTAMP WITHOUT TIME ZONE,
    nombre      VARCHAR(35)                 NOT NULL,
    ap_paterno  VARCHAR(55)                 NOT NULL,
    ap_materno  VARCHAR(55),
    ci          VARCHAR(20)                 NOT NULL,
    nro_celular VARCHAR(20)                 NOT NULL,
    correo      VARCHAR(55),
    CONSTRAINT pk_persona PRIMARY KEY (id_persona)
);

CREATE TABLE plan_estudio
(
    id_plan_estudio BIGINT                      NOT NULL,
    id_usu_reg      BIGINT                      NOT NULL,
    id_usu_mod      BIGINT,
    fec_reg         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod         TIMESTAMP WITHOUT TIME ZONE,
    anho            INTEGER                     NOT NULL,
    vigente         BOOLEAN                     NOT NULL,
    fk_id_programa  BIGINT                      NOT NULL,
    CONSTRAINT pk_planestudio PRIMARY KEY (id_plan_estudio)
);

CREATE TABLE plan_estudio_detalle
(
    id_plan_estudio_detalle BIGINT                      NOT NULL,
    id_usu_reg              BIGINT                      NOT NULL,
    id_usu_mod              BIGINT,
    fec_reg                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod                 TIMESTAMP WITHOUT TIME ZONE,
    carga_horaria           INTEGER                     NOT NULL,
    creditos                DECIMAL(8, 2)               NOT NULL,
    orden                   INTEGER                     NOT NULL,
    sigla                   VARCHAR(10)                 NOT NULL,
    fk_id_nivel             BIGINT                      NOT NULL,
    fk_id_modulo            BIGINT                      NOT NULL,
    fk_id_plan_estudio      BIGINT                      NOT NULL,
    CONSTRAINT pk_planestudiodetalle PRIMARY KEY (id_plan_estudio_detalle)
);

CREATE TABLE plan_pago
(
    id_plan_pago     BIGINT                      NOT NULL,
    id_usu_reg       BIGINT                      NOT NULL,
    id_usu_mod       BIGINT,
    fec_reg          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod          TIMESTAMP WITHOUT TIME ZONE,
    deuda_total      DECIMAL(12, 2)              NOT NULL,
    pagado_total     DECIMAL(12, 2),
    est_plan_pago    VARCHAR(35)                 NOT NULL,
    concepto         VARCHAR(155)                NOT NULL,
    fk_cod_matricula BIGINT                      NOT NULL,
    CONSTRAINT pk_planpago PRIMARY KEY (id_plan_pago)
);

CREATE TABLE preinscripcion
(
    id_preinscripcion BIGINT                      NOT NULL,
    id_usu_reg        BIGINT                      NOT NULL,
    id_usu_mod        BIGINT,
    fec_reg           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod           TIMESTAMP WITHOUT TIME ZONE,
    nombres           VARCHAR(55)                 NOT NULL,
    paterno           VARCHAR(35)                 NOT NULL,
    materno           VARCHAR(35),
    ci                VARCHAR(255),
    celular           VARCHAR(15),
    fk_id_programa    BIGINT                      NOT NULL,
    CONSTRAINT pk_preinscripcion PRIMARY KEY (id_preinscripcion)
);

CREATE TABLE programa
(
    id_programa     BIGINT                      NOT NULL,
    id_usu_reg      BIGINT                      NOT NULL,
    id_usu_mod      BIGINT,
    fec_reg         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod         TIMESTAMP WITHOUT TIME ZONE,
    nombre_programa VARCHAR(100)                NOT NULL,
    est_programa    VARCHAR(35)                 NOT NULL,
    fk_id_modalidad BIGINT                      NOT NULL,
    fk_id_categoria BIGINT                      NOT NULL,
    CONSTRAINT pk_programa PRIMARY KEY (id_programa)
);

CREATE TABLE programacion
(
    id_programacion         BIGINT                      NOT NULL,
    id_usu_reg              BIGINT                      NOT NULL,
    id_usu_mod              BIGINT,
    fec_reg                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod                 TIMESTAMP WITHOUT TIME ZONE,
    observacion             VARCHAR(255),
    fec_programacion        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    est_calificacion        VARCHAR(35)                 NOT NULL,
    tipo_programacion       VARCHAR(35)                 NOT NULL,
    calificacion_final      INTEGER,
    fk_id_cronograma_modulo BIGINT                      NOT NULL,
    fk_cod_matricula        BIGINT                      NOT NULL,
    CONSTRAINT pk_programacion PRIMARY KEY (id_programacion)
);

CREATE TABLE revision_monografia
(
    id_revision_monografia BIGINT                      NOT NULL,
    id_usu_reg             BIGINT                      NOT NULL,
    id_usu_mod             BIGINT,
    fec_reg                TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod                TIMESTAMP WITHOUT TIME ZONE,
    fec_hora_designacion   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    es_aprobador_final     BOOLEAN,
    fec_hora_revision      TIMESTAMP WITHOUT TIME ZONE,
    est_revision_moografia VARCHAR(35)                 NOT NULL,
    fk_id_monografia       BIGINT                      NOT NULL,
    fk_id_administrativo   BIGINT,
    fk_id_docente          BIGINT,
    CONSTRAINT pk_revisionmonografia PRIMARY KEY (id_revision_monografia)
);

CREATE TABLE rol
(
    id_rol            BIGINT                      NOT NULL,
    id_usu_reg        BIGINT                      NOT NULL,
    id_usu_mod        BIGINT,
    fec_reg           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod           TIMESTAMP WITHOUT TIME ZONE,
    nombre            VARCHAR(50)                 NOT NULL,
    est_cargo         VARCHAR(35)                 NOT NULL,
    descripcion_cargo TEXT,
    CONSTRAINT pk_rol PRIMARY KEY (id_rol)
);

CREATE TABLE tarea
(
    id_tarea      BIGINT                      NOT NULL,
    id_usu_reg    BIGINT                      NOT NULL,
    id_usu_mod    BIGINT,
    fec_reg       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod       TIMESTAMP WITHOUT TIME ZONE,
    nombre_tarea  VARCHAR(50)                 NOT NULL,
    descrip_tarea TEXT,
    est_tarea     BOOLEAN                     NOT NULL,
    fk_id_rol     BIGINT                      NOT NULL,
    CONSTRAINT pk_tarea PRIMARY KEY (id_tarea)
);

CREATE TABLE titulacion
(
    id_titulacion    BIGINT                      NOT NULL,
    id_usu_reg       BIGINT                      NOT NULL,
    id_usu_mod       BIGINT,
    fec_reg          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod          TIMESTAMP WITHOUT TIME ZONE,
    fec_emision      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    cod_titulo       VARCHAR(25)                 NOT NULL,
    url_doc          TEXT                        NOT NULL,
    fk_cod_matricula BIGINT                      NOT NULL,
    CONSTRAINT pk_titulacion PRIMARY KEY (id_titulacion)
);

CREATE TABLE usuario
(
    id_usuario      BIGINT                      NOT NULL,
    id_usu_reg      BIGINT                      NOT NULL,
    id_usu_mod      BIGINT,
    fec_reg         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod         TIMESTAMP WITHOUT TIME ZONE,
    nombre_usuario  VARCHAR(50),
    contrasena_hash VARCHAR(255),
    est_usuario     VARCHAR(35),
    fk_id_persona   BIGINT                      NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id_usuario)
);

CREATE TABLE version
(
    id_version          INTEGER                     NOT NULL,
    id_usu_reg          BIGINT                      NOT NULL,
    id_usu_mod          BIGINT,
    fec_reg             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fec_mod             TIMESTAMP WITHOUT TIME ZONE,
    cod_version         VARCHAR(15)                 NOT NULL,
    fec_inicio_vigencia date                        NOT NULL,
    fec_fin_vigencia    date,
    fk_id_programa      BIGINT                      NOT NULL,
    CONSTRAINT pk_version PRIMARY KEY (id_version)
);

ALTER TABLE titulacion
    ADD CONSTRAINT uc_titulacion_fk_cod_matricula UNIQUE (fk_cod_matricula);

ALTER TABLE usuario
    ADD CONSTRAINT uc_usuario_fk_id_persona UNIQUE (fk_id_persona);

ALTER TABLE administrativo
    ADD CONSTRAINT FK_ADMINISTRATIVO_ON_FK_ID_PERSONA FOREIGN KEY (fk_id_persona) REFERENCES persona (id_persona);

ALTER TABLE calificacion
    ADD CONSTRAINT FK_CALIFICACION_ON_FK_ID_CRITERIO_EVAL FOREIGN KEY (fk_id_criterio_eval) REFERENCES criterio_eval (id_criterio_eval);

ALTER TABLE calificacion
    ADD CONSTRAINT FK_CALIFICACION_ON_FK_ID_PROGRAMACION FOREIGN KEY (fk_id_programacion) REFERENCES programacion (id_programacion);

ALTER TABLE certificado
    ADD CONSTRAINT FK_CERTIFICADO_ON_FK_COD_MATRICULA FOREIGN KEY (fk_cod_matricula) REFERENCES matricula (cod_matricula);

ALTER TABLE certificado
    ADD CONSTRAINT FK_CERTIFICADO_ON_FK_ID_ADMINISTRATIVO FOREIGN KEY (fk_id_administrativo) REFERENCES administrativo (id_administrativo);

ALTER TABLE configuracion_costo
    ADD CONSTRAINT FK_CONFIGURACIONCOSTO_ON_FK_ID_PROGRAMA FOREIGN KEY (fk_id_programa) REFERENCES programa (id_programa);

ALTER TABLE criterio_eval
    ADD CONSTRAINT FK_CRITERIOEVAL_ON_FK_ID_CRONOGRAMA_MOD FOREIGN KEY (fk_id_cronograma_mod) REFERENCES cronograma_modulo (id_cronograma_mod);

ALTER TABLE cronograma_modulo
    ADD CONSTRAINT FK_CRONOGRAMAMODULO_ON_FK_ID_DOCENTE FOREIGN KEY (fk_id_docente) REFERENCES docente (id_docente);

ALTER TABLE cronograma_modulo
    ADD CONSTRAINT FK_CRONOGRAMAMODULO_ON_FK_ID_GRUPO FOREIGN KEY (fk_id_grupo) REFERENCES grupo (id_grupo);

ALTER TABLE cronograma_modulo
    ADD CONSTRAINT FK_CRONOGRAMAMODULO_ON_FK_ID_PLAN_ESTUDIO_DETALLE FOREIGN KEY (fk_id_plan_estudio_detalle) REFERENCES plan_estudio_detalle (id_plan_estudio_detalle);

ALTER TABLE detalle_pago
    ADD CONSTRAINT FK_DETALLEPAGO_ON_FK_ID_PLAN_PAGO FOREIGN KEY (fk_id_plan_pago) REFERENCES plan_pago (id_plan_pago);

ALTER TABLE docente
    ADD CONSTRAINT FK_DOCENTE_ON_FK_ID_PERSONA FOREIGN KEY (fk_id_persona) REFERENCES persona (id_persona);

ALTER TABLE grupo
    ADD CONSTRAINT FK_GRUPO_ON_FK_ID_PROGRAMA FOREIGN KEY (fk_id_programa) REFERENCES programa (id_programa);

ALTER TABLE grupo
    ADD CONSTRAINT FK_GRUPO_ON_FK_ID_VERSION FOREIGN KEY (fk_id_version) REFERENCES version (id_version);

ALTER TABLE matricula
    ADD CONSTRAINT FK_MATRICULA_ON_FK_ID_GRUPO FOREIGN KEY (fk_id_grupo) REFERENCES grupo (id_grupo);

ALTER TABLE matricula
    ADD CONSTRAINT FK_MATRICULA_ON_FK_ID_PERSONA FOREIGN KEY (fk_id_persona) REFERENCES persona (id_persona);

ALTER TABLE monografia
    ADD CONSTRAINT FK_MONOGRAFIA_ON_FK_COD_MATRICULA FOREIGN KEY (fk_cod_matricula) REFERENCES matricula (cod_matricula);

ALTER TABLE observacion_monografia
    ADD CONSTRAINT FK_OBSERVACIONMONOGRAFIA_ON_FK_ID_REVISION_MONOGRAFIA FOREIGN KEY (fk_id_revision_monografia) REFERENCES revision_monografia (id_revision_monografia);

ALTER TABLE ocupa
    ADD CONSTRAINT FK_OCUPA_ON_FK_ID_ROL FOREIGN KEY (fk_id_rol) REFERENCES rol (id_rol);

ALTER TABLE ocupa
    ADD CONSTRAINT FK_OCUPA_ON_FK_ID_USUARIO FOREIGN KEY (fk_id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE plan_estudio_detalle
    ADD CONSTRAINT FK_PLANESTUDIODETALLE_ON_FK_ID_MODULO FOREIGN KEY (fk_id_modulo) REFERENCES modulo (id_modulo);

ALTER TABLE plan_estudio_detalle
    ADD CONSTRAINT FK_PLANESTUDIODETALLE_ON_FK_ID_NIVEL FOREIGN KEY (fk_id_nivel) REFERENCES nivel (id_nivel);

ALTER TABLE plan_estudio_detalle
    ADD CONSTRAINT FK_PLANESTUDIODETALLE_ON_FK_ID_PLAN_ESTUDIO FOREIGN KEY (fk_id_plan_estudio) REFERENCES plan_estudio (id_plan_estudio);

ALTER TABLE plan_estudio
    ADD CONSTRAINT FK_PLANESTUDIO_ON_FK_ID_PROGRAMA FOREIGN KEY (fk_id_programa) REFERENCES programa (id_programa);

ALTER TABLE plan_pago
    ADD CONSTRAINT FK_PLANPAGO_ON_FK_COD_MATRICULA FOREIGN KEY (fk_cod_matricula) REFERENCES matricula (cod_matricula);

ALTER TABLE preinscripcion
    ADD CONSTRAINT FK_PREINSCRIPCION_ON_FK_ID_PROGRAMA FOREIGN KEY (fk_id_programa) REFERENCES programa (id_programa);

ALTER TABLE programacion
    ADD CONSTRAINT FK_PROGRAMACION_ON_FK_COD_MATRICULA FOREIGN KEY (fk_cod_matricula) REFERENCES matricula (cod_matricula);

ALTER TABLE programacion
    ADD CONSTRAINT FK_PROGRAMACION_ON_FK_ID_CRONOGRAMA_MODULO FOREIGN KEY (fk_id_cronograma_modulo) REFERENCES cronograma_modulo (id_cronograma_mod);

ALTER TABLE programa
    ADD CONSTRAINT FK_PROGRAMA_ON_FK_ID_CATEGORIA FOREIGN KEY (fk_id_categoria) REFERENCES categoria (id_categoria);

ALTER TABLE programa
    ADD CONSTRAINT FK_PROGRAMA_ON_FK_ID_MODALIDAD FOREIGN KEY (fk_id_modalidad) REFERENCES modalidad (id_modalidad);

ALTER TABLE revision_monografia
    ADD CONSTRAINT FK_REVISIONMONOGRAFIA_ON_FK_ID_ADMINISTRATIVO FOREIGN KEY (fk_id_administrativo) REFERENCES administrativo (id_administrativo);

ALTER TABLE revision_monografia
    ADD CONSTRAINT FK_REVISIONMONOGRAFIA_ON_FK_ID_DOCENTE FOREIGN KEY (fk_id_docente) REFERENCES docente (id_docente);

ALTER TABLE revision_monografia
    ADD CONSTRAINT FK_REVISIONMONOGRAFIA_ON_FK_ID_MONOGRAFIA FOREIGN KEY (fk_id_monografia) REFERENCES monografia (id_monografia);

ALTER TABLE tarea
    ADD CONSTRAINT FK_TAREA_ON_FK_ID_ROL FOREIGN KEY (fk_id_rol) REFERENCES rol (id_rol);

ALTER TABLE titulacion
    ADD CONSTRAINT FK_TITULACION_ON_FK_COD_MATRICULA FOREIGN KEY (fk_cod_matricula) REFERENCES matricula (cod_matricula);

ALTER TABLE usuario
    ADD CONSTRAINT FK_USUARIO_ON_FK_ID_PERSONA FOREIGN KEY (fk_id_persona) REFERENCES persona (id_persona);

ALTER TABLE version
    ADD CONSTRAINT FK_VERSION_ON_FK_ID_PROGRAMA FOREIGN KEY (fk_id_programa) REFERENCES programa (id_programa);

ALTER TABLE asigna
    ADD CONSTRAINT fk_asigna_on_tarea FOREIGN KEY (fk_id_tarea) REFERENCES tarea (id_tarea);

ALTER TABLE asigna
    ADD CONSTRAINT fk_asigna_on_usuario FOREIGN KEY (fk_id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE comprobante_detalle_pago
    ADD CONSTRAINT fk_comdetpag_on_comprobante_pago FOREIGN KEY (fk_id_comprobante) REFERENCES comprobante_pago (id_comprobante);

ALTER TABLE comprobante_detalle_pago
    ADD CONSTRAINT fk_comdetpag_on_detalle_pago FOREIGN KEY (fk_id_detalle_pago) REFERENCES detalle_pago (id_detalle_pago);
