<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuth } from 'src/composables/useAuth.js'

const nombreUsuario = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()
const { login } = useAuth()

async function handleLogin() {
  error.value = ''
  try {
    await login({ nombreUsuario: nombreUsuario.value, password: password.value })
    router.push('/admin')
  } catch (err) {
    console.log(err)
    error.value = 'Usuario o contraseña incorrectos.'
  }
}
</script>

<template>
  <q-card class="login-form-card q-pa-md q-mb-none">
    <q-card-section>
      <div class="text-h6 text-center text-weight-bold q-mb-xs" style="color:#222;">
        Iniciar Sesión
      </div>
      <div class="text-caption text-center q-mb-md" style="color: #838383;">
        Acceda a la plataforma CPEyFC
      </div>
      <q-input
        v-model="nombreUsuario"
        label="Usuario"
        dense
        outlined
        class="q-mb-md"
        :rules="[val => !!val || 'Requerido']"
        autocomplete="username"
      />
      <q-input
        v-model="password"
        label="Contraseña"
        type="password"
        dense
        outlined
        class="q-mb-md"
        :rules="[val => !!val || 'Requerido']"
        autocomplete="current-password"
      />
      <q-btn
        label="Iniciar sesión"
        color="primary"
        class="full-width q-mt-md login-btn"
        @click="handleLogin"
        no-caps
      />
      <q-banner v-if="error" class="bg-red-1 text-red-8 q-mt-md text-center">
        {{ error }}
      </q-banner>
    </q-card-section>
  </q-card>
</template>

<style scoped>
.login-form-card {
  box-shadow: 0 8px 32px 0 rgba(31,38,135,0.13);
  border: 1.5px solid #eee;
  background: #fafbff;
  min-width: 310px;
  max-width: 100%;
}
.login-btn {
  font-weight: 600;
  letter-spacing: 1px;
  transition: 0.2s;
  box-shadow: 0 2px 10px 0 rgba(25, 118, 210, 0.08);
}
.login-btn:hover {
  filter: brightness(1.1);
  box-shadow: 0 4px 22px 0 rgba(25, 118, 210, 0.19);
}
</style>
