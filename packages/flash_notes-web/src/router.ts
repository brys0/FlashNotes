import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';

let routes: RouteRecordRaw[] = [
    {
        path: '/',
        component() {
            return import('./pages/home.vue')
        }
    },
    {
        path: '/fake',
        component() {
            return import('./pages/fake.vue')
        }
    }
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})