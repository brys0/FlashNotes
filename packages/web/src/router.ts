import { createRouter, createWebHistory, NavigationGuardNext, RouteRecordRaw } from 'vue-router';
import { useAuthStore } from './stores/AuthStore';

let routes: RouteRecordRaw[] = [
    {
        path: '/',
        component() {
            return import('./pages/home.vue')
        }
    },
    {
        path: '/create',
        component() {
            return import('./pages/create.vue')
        },
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/fake',
        component() {
            return import('./pages/fake.vue')
        }
    },
    {
        path: '/search',
        component() {
            return import('./pages/search.vue')
        }
    },
    {
        path: '/card/:id',
        props: true,
        component() {
            return import('./pages/card.vue')
        }
    },
    {
        path: '/practice/:id',
        props: true,
        component() {
            return import('./pages/practice.vue');
        }
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next: NavigationGuardNext) => {
    if (to.meta.requireAuth && useAuthStore().user == null) next({ path: '/', query: { highlight: 'login' } });
    else next();
})