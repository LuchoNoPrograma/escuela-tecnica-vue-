#!/usr/bin/env bash
set -euo pipefail

# ── RUTA AL PROPERTIES ────────────────────────────────────────────────
PROPERTIES="$(dirname "$0")/src/main/resources/application-dev.properties"

if [[ ! -f "$PROPERTIES" ]]; then
  echo "❌ No se encontró ${PROPERTIES}. Ajusta la ruta en el script."
  exit 1
fi

# ── FUNCIÓN PARA EXTRAER PROPIEDADES ──────────────────────────────────
getProperty() {
  local raw
  raw=$(grep -E "^$1=" "$PROPERTIES" | cut -d'=' -f2- | tr -d '\r')
  # Extrae valor por defecto si viene así: ${VAR:valor}
  if [[ "$raw" =~ \$\{[^:]+:(.*)\} ]]; then
    echo "${BASH_REMATCH[1]}"
  else
    echo "$raw"
  fi
}

# ── EXTRAEMOS URL, USUARIO Y CONTRA ───────────────────────────────────
URL=$(getProperty 'spring.datasource.url')
USER=$(getProperty 'spring.datasource.username')
PASS=$(getProperty 'spring.datasource.password')

# ── PARSEAMOS EL JDBC ─────────────────────────────────────────────────
# Ejemplo: jdbc:postgresql://localhost:5432/escuela-tecnica
stripped=${URL#jdbc:postgresql://}
host_and_rest=${stripped%%/*}      # localhost:5432
DB_NAME=${stripped#*/}             # escuela-tecnica
HOST=${host_and_rest%%:*}          # localhost
PORT=${host_and_rest#*:}           # 5432 o igual que host

if [[ "$PORT" == "$HOST" ]]; then
  PORT=5432
fi

# ── EXPORTAMOS LA CONTRA ──────────────────────────────────────────────
export PGPASSWORD="$PASS"

# ── OPERACIÓN DROP Y CREATE ───────────────────────────────────────────
echo "🗑  Conectando a $HOST:$PORT como $USER, dropeando BD '$DB_NAME'…"
psql -h "$HOST" -p "$PORT" -U "$USER" -d postgres -c "DROP DATABASE IF EXISTS \"$DB_NAME\";"

echo "✨ Creando BD '$DB_NAME'…"
psql -h "$HOST" -p "$PORT" -U "$USER" -d postgres -c "CREATE DATABASE \"$DB_NAME\";"

echo "✅ ¡Base de datos reiniciada exitosamente! 💾"
