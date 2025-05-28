import { ref } from 'vue'
import { api } from 'boot/axios'

export function useQuasarTablePagination(tablaInicial = 'rol') {
  const tabla = ref(tablaInicial)
  const rows = ref([])
  const columns = ref([])
  const loading = ref(false)
  const totalElements = ref(0)
  const pagination = ref({
    page: 1,
    rowsPerPage: 10,
    rowsNumber: 0,
    filter: ''
  })

  async function fetchData({ pagination: p = pagination.value } = {}) {
    loading.value = true
    try {
      const page = (p.page || 1) - 1
      const size = p.rowsPerPage || 10
      const q = p.filter || ''
      const { data } = await api.get(`/api/crud/page/${tabla.value}`, {
        params: { q, page, size }
      })
      rows.value = data.content || []
      totalElements.value = data.totalElements || 0
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

  return {
    tabla,
    rows,
    columns,
    loading,
    pagination,
    totalElements,
    fetchData
  }
}
