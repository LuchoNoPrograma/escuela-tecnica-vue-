<script setup lang="ts">
import LoginForm from "components/LoginForm.vue";
</script>

<template>
  <q-page class="login-page">
    <div class="floating-cubes"></div>
    <div class="right-login-box">
      <div class="branding">
        <!-- Aquí va tu logo o un icono SVG lindo -->
        <img src="/logo-cpeyfcp.png" alt="Logo CPEyFC" class="login-logo" />
        <div class="branding-title">CPEyFC</div>
        <div class="branding-sub">Centro de Proyectos Especiales y Formación Continua</div>
      </div>
      <login-form />
    </div>
  </q-page>
</template>

<style scoped lang="scss">
@import 'src/css/quasar.variables';

.login-page {
  width: 100vw;
  height: 100vh;
  min-height: 100vh;
  background: #1d1f27;
  display: flex;
  justify-content: flex-end;
  align-items: stretch;
  position: relative;
  font-family: 'DM Sans', sans-serif;
  overflow: hidden;
}

.floating-cubes {
  position: absolute;
  width: 100vw;
  height: 100vh;
  left: 0;
  top: 0;
  background-image: radial-gradient(rgba(255,255,255,0.07) 1.5px, transparent 1.5px), radial-gradient(rgba(255,255,255,0.05) 1px, transparent 1px);
  background-size: 52px 52px, 18px 18px;
  z-index: 1;
  animation: moveBackground 50s linear infinite;
  pointer-events: none;
}
@keyframes moveBackground {
  from { background-position: 0 0, 0 0; }
  to { background-position: 100px 100px, 30px 30px; }
}

.right-login-box {
  background: rgba(255,255,255,0.96);
  height: 100vh;
  min-height: 100vh;
  max-width: 420px;
  width: 100%;
  box-shadow: -10px 0 48px 0 rgba(0,0,0,0.18);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 3rem 2.4rem 1.6rem 2.4rem;
  z-index: 2;
  margin-right: 0;
}

.branding {
  text-align: center;
  margin-bottom: 1.8rem;
}
.login-logo {
  width: 68px;
  margin-bottom: 0.5rem;
}
.branding-title {
  font-size: 2rem;
  font-weight: 700;
  color: $primary;
  letter-spacing: 2px;
}
.branding-sub {
  font-size: 1rem;
  color: #5a5c60;
  opacity: 0.7;
  font-weight: 500;
  margin-bottom: 1.2rem;
}
</style>
