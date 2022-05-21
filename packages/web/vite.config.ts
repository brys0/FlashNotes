import { defineConfig, splitVendorChunkPlugin } from 'vite';
import htmlMinimize from '@sergeymakinen/vite-plugin-html-minimize';
import visualizer from 'rollup-plugin-visualizer';
import vue from '@vitejs/plugin-vue';
import { VitePWA } from 'vite-plugin-pwa';
// https://vitejs.dev/config/

export default defineConfig({
  plugins: [vue(), splitVendorChunkPlugin(), htmlMinimize(),  VitePWA({
    filename: 'sw.ts',
    includeAssets: ['*.png'],
    injectRegister: false,
    includeManifestIcons: false,
    srcDir: 'src/service-worker',
    strategies: 'injectManifest',
    manifest: {
      name: 'Flash Notes',
      short_name: 'flash_notes',
      description: 'Create note in a flash!',
      start_url: '/',
      background_color: '#101014',
      theme_color: '#63e2b7',
      lang: 'en',
      icons: [
        {
          src: 'FlashNotes.png',
          sizes: '512x512',
          type: 'image/png',
          purpose: 'maskable'
        },
        {
          src: 'FlashNotes.png',
          sizes: '512x512',
          type: 'image/png',
        }
      ]
    },
    minify: true,
  })],
  build: {
    rollupOptions: {
      plugins: [
        visualizer({
          filename: 'bundle-analysis.html',
          open: true
        })
      ],
        // manualChunks: (id) => {
        //   if (id.includes('naive-ui')) {
        //     if (id.includes('naive-ui/lib/dynamic-input')) {
        //       return "vendor_naive_dynin";
        //     } else if (id.includes('naive-ui/lib/button')) {
        //       return "vendor_naive_btn";
        //     } else if (id.includes('naive-ui/lib/thing')) {
        //       return "vendor_naive_thing";
        //     } else if (id.includes('naive-ui/lib/input')) {
        //       return "vendor_naive_input";
        //     }
        //     console.log('VENDOR -> NAIVE UI ELEMENTS', id);
        //     return `vendor_naive`;
        //   } else if (id.includes('xicons')) {
        //     return "vendor_icons"
        //   } else if (id.includes("vue")) {
        //     return "vendor_vue";
        //   }
        //   return "vendor-[hash]";
        // }
    }
  },
  server: {
    proxy: {
      '/verify-token': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      },
      '/create': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      },
      '/tags': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      },
      '/browse': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      },
      '/filter': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      },
      '/mygroups': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      },
      '/card': {
        target: 'http://127.0.0.1:3001/',
        secure: false,
        ws: true,
        ignorePath: false,
        prependPath: true
      }
    },
  }
})
