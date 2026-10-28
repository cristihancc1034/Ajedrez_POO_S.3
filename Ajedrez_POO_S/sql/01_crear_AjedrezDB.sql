/* =====================================================================
   AjedrezDB - Proyecto Final POO (Idea 5: Juego de Ajedrez)
   Motor: Microsoft SQL Server (Transact-SQL)
   Convención de nombres: pk_ (clave primaria), fk_ (clave foránea),
   uq_ (única), ck_ (check), df_ (default).
   Ejecutar completo en SQL Server Management Studio (SSMS).
   ===================================================================== */

IF DB_ID('AjedrezDB') IS NULL
    CREATE DATABASE AjedrezDB;
GO

USE AjedrezDB;
GO

/* ---------- Catálogos ---------- */
CREATE TABLE Modalidad (
    id_modalidad TINYINT      NOT NULL,
    nombre       VARCHAR(20)  NOT NULL,
    minutos      SMALLINT     NOT NULL,
    CONSTRAINT pk_modalidad        PRIMARY KEY (id_modalidad),
    CONSTRAINT uq_modalidad_nombre UNIQUE (nombre),
    CONSTRAINT ck_modalidad_minutos CHECK (minutos >= 0)
);
GO

CREATE TABLE TemaTablero (
    id_tema      TINYINT      NOT NULL,
    nombre       VARCHAR(20)  NOT NULL,
    color_claro  CHAR(6)      NOT NULL,
    color_oscuro CHAR(6)      NOT NULL,
    CONSTRAINT pk_tema            PRIMARY KEY (id_tema),
    CONSTRAINT uq_tema_nombre     UNIQUE (nombre),
    CONSTRAINT ck_tema_color_claro  CHECK (color_claro  NOT LIKE '%[^0-9A-Fa-f]%'),
    CONSTRAINT ck_tema_color_oscuro CHECK (color_oscuro NOT LIKE '%[^0-9A-Fa-f]%')
);
GO

/* ---------- Jugadores ---------- */
CREATE TABLE Jugador (
    id_jugador     INT IDENTITY(1,1) NOT NULL,
    nombre         VARCHAR(20)       NOT NULL,
    fecha_registro DATETIME2(0)      NOT NULL
        CONSTRAINT df_jugador_fecha DEFAULT SYSDATETIME(),
    CONSTRAINT pk_jugador        PRIMARY KEY (id_jugador),
    CONSTRAINT uq_jugador_nombre UNIQUE (nombre),
    CONSTRAINT ck_jugador_nombre CHECK (LEN(LTRIM(RTRIM(nombre))) > 0)
);
GO

/* ---------- Partidas ---------- */
CREATE TABLE Partida (
    id_partida         INT IDENTITY(1,1) NOT NULL,
    id_jugador_blancas INT               NOT NULL,
    id_jugador_negras  INT               NOT NULL,
    id_modalidad       TINYINT           NOT NULL,
    id_tema            TINYINT           NOT NULL,
    fecha_inicio       DATETIME2(0)      NOT NULL
        CONSTRAINT df_partida_inicio DEFAULT SYSDATETIME(),
    fecha_fin          DATETIME2(0)      NULL,
    resultado          VARCHAR(10)       NOT NULL
        CONSTRAINT df_partida_resultado DEFAULT 'EN_CURSO',
    motivo_fin         VARCHAR(25)       NULL,
    CONSTRAINT pk_partida PRIMARY KEY (id_partida),
    CONSTRAINT fk_partida_blancas   FOREIGN KEY (id_jugador_blancas) REFERENCES Jugador (id_jugador),
    CONSTRAINT fk_partida_negras    FOREIGN KEY (id_jugador_negras)  REFERENCES Jugador (id_jugador),
    CONSTRAINT fk_partida_modalidad FOREIGN KEY (id_modalidad)       REFERENCES Modalidad (id_modalidad),
    CONSTRAINT fk_partida_tema      FOREIGN KEY (id_tema)            REFERENCES TemaTablero (id_tema),
    CONSTRAINT ck_partida_jugadores_distintos CHECK (id_jugador_blancas <> id_jugador_negras),
    CONSTRAINT ck_partida_fechas    CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio),
    CONSTRAINT ck_partida_resultado CHECK (resultado IN ('EN_CURSO','BLANCAS','NEGRAS','TABLAS')),
    CONSTRAINT ck_partida_motivo    CHECK (motivo_fin IS NULL OR motivo_fin IN
        ('JAQUE_MATE','AHOGADO','INSUFICIENCIA_MATERIAL','TIEMPO','ABANDONO','ACUERDO'))
);
GO

/* ---------- Movimientos de cada partida ---------- */
CREATE TABLE Movimiento (
    id_movimiento      INT IDENTITY(1,1) NOT NULL,
    id_partida         INT               NOT NULL,
    numero             SMALLINT          NOT NULL,
    color              CHAR(1)           NOT NULL,
    pieza              VARCHAR(10)       NOT NULL,
    casilla_origen     CHAR(2)           NOT NULL,
    casilla_destino    CHAR(2)           NOT NULL,
    pieza_capturada    VARCHAR(10)       NULL,
    notacion           VARCHAR(10)       NOT NULL,
    segundos_empleados INT               NULL,
    CONSTRAINT pk_movimiento PRIMARY KEY (id_movimiento),
    CONSTRAINT fk_movimiento_partida FOREIGN KEY (id_partida)
        REFERENCES Partida (id_partida) ON DELETE CASCADE,
    CONSTRAINT uq_movimiento_partida_numero UNIQUE (id_partida, numero),
    CONSTRAINT ck_movimiento_numero    CHECK (numero > 0),
    CONSTRAINT ck_movimiento_color     CHECK (color IN ('B','N')),
    CONSTRAINT ck_movimiento_pieza     CHECK (pieza IN ('REY','DAMA','TORRE','ALFIL','CABALLO','PEON')),
    CONSTRAINT ck_movimiento_capturada CHECK (pieza_capturada IS NULL OR pieza_capturada IN ('REY','DAMA','TORRE','ALFIL','CABALLO','PEON')),
    CONSTRAINT ck_movimiento_origen    CHECK (casilla_origen  LIKE '[a-h][1-8]'),
    CONSTRAINT ck_movimiento_destino   CHECK (casilla_destino LIKE '[a-h][1-8]'),
    CONSTRAINT ck_movimiento_segundos  CHECK (segundos_empleados IS NULL OR segundos_empleados >= 0)
);
GO

/* ---------- Puntuaciones ---------- */
CREATE TABLE Puntuacion (
    id_partida INT          NOT NULL,
    id_jugador INT          NOT NULL,
    puntos     DECIMAL(2,1) NOT NULL,
    CONSTRAINT pk_puntuacion PRIMARY KEY (id_partida, id_jugador),
    CONSTRAINT fk_puntuacion_partida FOREIGN KEY (id_partida)
        REFERENCES Partida (id_partida) ON DELETE CASCADE,
    CONSTRAINT fk_puntuacion_jugador FOREIGN KEY (id_jugador) REFERENCES Jugador (id_jugador),
    CONSTRAINT ck_puntuacion_puntos CHECK (puntos IN (0, 0.5, 1))
);
GO

/* ---------- Datos iniciales de los catálogos (coinciden con los enum de Java) ---------- */
INSERT INTO Modalidad (id_modalidad, nombre, minutos) VALUES
    (1, 'Normal',    60),
    (2, 'Rápida',    10),
    (3, 'Bala',       1),
    (4, 'Sin reloj',  0);
GO

INSERT INTO TemaTablero (id_tema, nombre, color_claro, color_oscuro) VALUES
    (1, 'Clásico', 'EEEED2', '769656'),
    (2, 'Madera',  'F0D9B5', 'B58863'),
    (3, 'Azul',    'DEE3E6', '8CA2AD'),
    (4, 'Morado',  'E8E0F0', '9B7EBD');
GO
