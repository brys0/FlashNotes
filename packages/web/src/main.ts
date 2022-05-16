import { createPinia } from 'pinia'
import { createApp } from 'vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './App.vue'
import { router } from './router';
import { createMetaManager } from 'vue-meta';


let vueApp = createApp(App);
vueApp.use(createMetaManager(false));
let pinia = createPinia();

pinia.use(piniaPluginPersistedstate)
vueApp.use(pinia);
vueApp.use(router);

vueApp.mount('#app');
