// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    modules: ['@pinia/nuxt', 'nuxt-windicss'],
    css: ['@/assets/styles/main.scss'],
    windicss: {
        analyze: true
    },
    vite: {
        css: {
            preprocessorOptions: {
                sass: {
                    additionalData: '@import "@/assets/styles/_variables.sass"',
                },
            },
        },
    },
})
