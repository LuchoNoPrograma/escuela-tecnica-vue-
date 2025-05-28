<template>
  <q-layout view="hHh lpR fFf">
    <!-- Barra superior -->
    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-toolbar-title>
          Escuela Técnica
        </q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
// No necesitas JS aquí por ahora
</script>

<style scoped>
/* Opcional: altura mínima */
.q-page-container {
  min-height: 100vh;
}
</style>
