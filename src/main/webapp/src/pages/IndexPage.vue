<script setup lang="ts">
import { useRouter } from 'vue-router'
const router = useRouter()

function irALogin() {
  router.push('/login')
}
</script>
<template>
  <q-page class="flex flex-center column q-gutter-md" style="min-height: 100vh;">
    <q-btn color="primary" label="Ir a Login" @click="irALogin" />
  </q-page>
</template>
