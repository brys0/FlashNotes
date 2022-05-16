<template>
  <NConfigProvider :theme="darkTheme">
    <NLoadingBarProvider>
      <NDialogProvider>
        <AppHeader />
        <router-view v-slot="{ Component }" class="currentpage">
          <transition name="fade">
            <component :is="Component" />
          </transition>
        </router-view>
      </NDialogProvider>
    </NLoadingBarProvider>
  </NConfigProvider>
</template>
<script lang="ts">
import { defineAsyncComponent, defineComponent } from "vue";
import { darkTheme, lightTheme, NDialogProvider, NLoadingBarProvider } from "naive-ui";
import { useCardStore } from "./stores/CardStore";
export default defineComponent({
  setup() {
    return {
      darkTheme,
      lightTheme,
    };
  },
  mounted() {
    useCardStore().getAllTags();
    useCardStore().getMyGroups();
    console.log(import.meta.env.VITE_CLIENT_ID);
  },
  components: {
    NConfigProvider: defineAsyncComponent(() => import("naive-ui/lib/config-provider/src/ConfigProvider")),
    NDialogProvider,
    NLoadingBarProvider,
    AppHeader: defineAsyncComponent(() => import("./components/layout/AppHeader.vue")),
  },
});
</script>
<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;600&family=JetBrains+Mono&family=Montserrat:wght@500&family=Oswald&display=swap");
@import url("./assets/fonts/metropolis/style.css");

:root {
  --appbg: #101014;
  --headerbg: #0a0a0a;
  --green: #63e2b7;
  --shadow-color: #9ed8ff;
  --shadow-color-light: white;
  &[theme="dark"] {
    --appbg: #101014;
    --headerbg: #0a0a0a;
  }

  &[theme="light"] {
    --appbg: #fafafa;
    --headerbg: #e7e7e8;
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
  transition: all 0.2s ease;
}

.slide-up-leave-active {
  transition: all 0.2s cubic-bezier(1, 0.5, 0.8, 1);
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
@keyframes neon {
  0% {
    text-shadow: -1px -1px 1px var(--shadow-color-light), -1px 1px 1px var(--shadow-color-light), 1px -1px 1px var(--shadow-color-light), 1px 1px 1px var(--shadow-color-light), 0 0 3px var(--shadow-color-light), 0 0 10px var(--shadow-color-light), 0 0 20px var(--shadow-color-light), 0 0 30px var(--shadow-color), 0 0 40px var(--shadow-color), 0 0 50px var(--shadow-color), 0 0 70px var(--shadow-color), 0 0 100px var(--shadow-color), 0 0 200px var(--shadow-color);
  }
  50% {
    text-shadow: -1px -1px 1px var(--shadow-color-light), -1px 1px 1px var(--shadow-color-light), 1px -1px 1px var(--shadow-color-light), 1px 1px 1px var(--shadow-color-light), 0 0 5px var(--shadow-color-light), 0 0 15px var(--shadow-color-light), 0 0 25px var(--shadow-color-light), 0 0 40px var(--shadow-color), 0 0 50px var(--shadow-color), 0 0 60px var(--shadow-color), 0 0 80px var(--shadow-color), 0 0 110px var(--shadow-color), 0 0 210px var(--shadow-color);
  }
  100% {
    text-shadow: -1px -1px 1px var(--shadow-color-light), -1px 1px 1px var(--shadow-color-light), 1px -1px 1px var(--shadow-color-light), 1px 1px 1px var(--shadow-color-light), 0 0 3px var(--shadow-color-light), 0 0 10px var(--shadow-color-light), 0 0 20px var(--shadow-color-light), 0 0 30px var(--shadow-color), 0 0 40px var(--shadow-color), 0 0 50px var(--shadow-color), 0 0 70px var(--shadow-color), 0 0 100px var(--shadow-color), 0 0 200px var(--shadow-color);
  }
}
.currentpage {
  height: calc(100vh - 46.4px);
}

::-webkit-scrollbar {
  width: 12px;
  background-color: transparent;
}
::-webkit-scrollbar-button {
  display: none;
  width: 0;
  height: 0;
}
::-webkit-scrollbar-thumb {
  background-color: #4a4d52;
  border: 2px solid #282a2d;
  border-radius: 10px;
}
::-webkit-scrollbar-corner {
  display: none;
}
</style>
