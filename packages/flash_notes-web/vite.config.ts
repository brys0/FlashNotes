import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/verify-token': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      }
    },
  }
})
