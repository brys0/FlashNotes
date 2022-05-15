import { createPinia } from 'pinia'
import { createApp } from 'vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './App.vue'
import { router } from './router';
let vueApp = createApp(App);
let pinia = createPinia();

pinia.use(piniaPluginPersistedstate)
vueApp.use(pinia);
vueApp.use(router)
vueApp.mount('#app');
