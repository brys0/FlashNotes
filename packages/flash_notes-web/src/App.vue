<script setup lang="ts">
import AppHeader from './components/layout/AppHeader.vue';
</script>

<template>
  <NConfigProvider :theme="darkTheme">
    <NDialogProvider>
      <AppHeader />
      <router-view v-slot="{ Component }">
        <transition name="fade">
          <component :is="Component" />
        </transition>
      </router-view>
    </NDialogProvider>
  </NConfigProvider>
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import { darkTheme, lightTheme, NConfigProvider, NDialogProvider } from 'naive-ui'

export default defineComponent({
  setup() {
    return {
      darkTheme,
      lightTheme
    }
  },
  mounted() {
    console.log(import.meta.env.VITE_CLIENT_ID);
  },
  components: { NConfigProvider }
})
</script>
<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Fira+Mono:wght@500&family=Inter:wght@200;500&family=Montserrat&display=swap');
@import url('./assets/fonts/metropolis/style.css');

:root {
  --appbg: #101014;
  --headerbg: #0a0a0a;
  --green: #63e2b7;

  &[theme="dark"] {
    --appbg: #101014;
    --headerbg: #0a0a0a;
  }

  &[theme="light"] {
    --appbg: #FAFAFA;
    --headerbg: #E7E7E8;
  }

  user-select: none;
}

#app,
html,
body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: var(--appbg);
  height: 100vh;
  width: 100%;
  overflow: hidden;
}

[border-bottom-left-round] {
  border-bottom-left-radius: 4px;
}

[border-bottom-right-round] {
  border-bottom-right-radius: 4px;
}

.slide-up-enter-active {
  transition: all .2s ease;
}

.slide-up-leave-active {
  transition: all .2s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-up-enter,
.slide-fade-leave-to {
  transform: translateY(100vh);
  opacity: 0;
}

.v-enter-active,
.v-leave-active {
  transition: all 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
  transform: translateY(120px);
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 200ms linear;
}


.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
