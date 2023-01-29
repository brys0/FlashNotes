// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    modules: ['@pinia/nuxt', 'nuxt-windicss'],
    css: ['@/assets/styles/main.scss'],
    app: {
        head: {
            charset: 'utf-16',
            title: 'FlashNotes',
            link: [{ rel: 'icon', type: 'image/png', href: '/favicon.png' }],
        },
        buildAssetsDir: '_fnpd'
    },
    windicss: {
        analyze: true
    },
    vite: {
        server: {
            proxy: {
                '/api': {
                    target: 'http://localhost:3001',
                    changeOrigin: true,
                    rewrite: path => path.replace(/^\/api/, '')
                },
            }
        },
        css: {
            preprocessorOptions: {
                sass: {
                    additionalData: '@import "@/assets/styles/_variables.sass"',
                },
            },
        },
    },
})
