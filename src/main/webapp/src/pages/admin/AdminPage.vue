<script setup>
import {ref, watch, onMounted} from 'vue'
import { api } from 'boot/axios.js'

const tablas = [
  { label: 'Roles', value: 'rol' },
  { label: 'Tareas', value: 'tarea' },
  { label: 'Categoria', value: 'categoria' },
  { label: 'Personas', value: 'persona' }
]
const tabla = ref(tablas[0].value)
const rows = ref([])
const columns = ref([])
const loading = ref(false)
const totalElements = ref(0)

// Paginación controlada
const pagination = ref({
  page: 1,
  rowsPerPage: 10,
  rowsNumber: 0
})
const search = ref('')

async function fetchData(props = {}) {
  loading.value = true
  const pag = props.pagination ?? pagination.value
  const page = (pag.page ?? 1) - 1
  const size = pag.rowsPerPage ?? 10
  const q = props.filter ?? search.value

  try {
    const { data } = await api.get(`/api/crud/page/${tabla.value}`, {
      params: { q, page, size }
    })
    rows.value = data.content
    totalElements.value = data.totalElements
    columns.value = rows.value.length
      ? Object.keys(rows.value[0]).map(key => ({
        name: key, label: key, field: key, align: 'left', sortable: true
      }))
      : []
    pagination.value.page = (data.number ?? 0) + 1
    pagination.value.rowsPerPage = data.size ?? size
    pagination.value.rowsNumber = data.totalElements ?? 0
  } finally {
    loading.value = false
  }
}

// Reactividad
watch(tabla, () => fetchData({ pagination: pagination.value }))
onMounted(() => fetchData({ pagination: pagination.value }))

// Métodos para custom paginador
function onRowsPerPageChange(val) {
  pagination.value.rowsPerPage = val
  pagination.value.page = 1
  fetchData({ pagination: pagination.value })
}
function onPageChange(val) {
  pagination.value.page = val
  fetchData({ pagination: pagination.value })
}
function onBuscar() {
  pagination.value.page = 1
  fetchData({ pagination: pagination.value })
}
</script>

<template>
  <q-page class="q-pa-md q-pt-lg">
    <q-card class="q-pa-none">
      <div style="min-height: 80vh;">
        <!-- Controles arriba -->
        <div class="row items-center q-gutter-md full-width q-pa-md">
          <q-select
            v-model="tabla"
            :options="tablas"
            dense
            label="Tabla"
            emit-value
            map-options
            style="width: 200px"
            outlined
          />
          <q-input
            v-model="search"
            label="Buscar"
            dense
            debounce="300"
            clearable
            outlined
            @keyup.enter="onBuscar"
          />
          <q-btn label="Buscar" color="primary" @click="onBuscar" :loading="loading"/>
          <q-select
            v-model="pagination.rowsPerPage"
            :options="[5, 10, 20, 50, 100, 500]"
            label="Filas por página"
            dense
            style="width: 160px"
            outlined
            @update:model-value="onRowsPerPageChange"
          />
        </div>

        <!-- Tabla -->
        <q-table
          :rows="rows"
          :columns="columns"
          :loading="loading"
          row-key="id"
          :pagination="pagination"
          @request="fetchData"
          :rows-per-page-options="[5, 10, 20, 50, 100, 500]"
          :rows-number="totalElements"
          dense
          flat
          separator="cell"
          striped
          :no-data-label="'Sin registros.'"
          :no-results-label="'No se encontraron resultados.'"
        >
          <!-- Forzar celdas a la izquierda -->
          <template v-slot:body-cell="props">
            <q-td :props="props" class="text-left">
              {{ props.value }}
            </q-td>
          </template>
        </q-table>

        <!-- Paginador custom SIEMPRE abajo -->
        <div class="q-mt-auto q-pt-lg">
          <div class="row justify-center">
            <q-pagination
              v-model="pagination.page"
              :max="Math.max(1, Math.ceil(totalElements / pagination.rowsPerPage))"
              input
              boundary-numbers
              boundary-links
              direction-links
              :max-pages="7"
              color="primary"
              size="lg"
              unelevated
              @update:model-value="onPageChange"
            />
          </div>
          <div class="row justify-center q-mt-sm">
            <div class="text-grey-7" v-if="totalElements > 0">
              Página <b>{{ pagination.page }}</b> de <b>{{ Math.max(1, Math.ceil(totalElements / pagination.rowsPerPage)) }}</b>
              (Total registros: <b>{{ totalElements }}</b>)
            </div>
          </div>
        </div>
      </div>
    </q-card>
  </q-page>
</template>

<style>
</style>
