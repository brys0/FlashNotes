<script setup lang="ts">
import { withDefaults } from 'vue';

const props = withDefaults(defineProps<{
    color?: 'accent' | 'dark' | '',
    type?: 'icon-only' | 'link' | '',
    loading?: boolean
}>(), {
    color: '',
    type: '',
    loading: false
});
</script>


<template>
    <button :class="`button-comp ${color} ${type} ${loading ? 'loading' : ''}`">

        <div class="loading flex w-full items-center justify-center whitespace-nowrap">
            <Transition  enterActiveClass="transition-all duration-500" enterFromClass="opacity-0 h-0 translate-up" enterToClass="h-5" leaveActiveClass="transition-all duration-200" leaveFromClass="h-5" leaveToClass="opacity-0 h-0 translate-down" mode="out-in" v-if="loading">
                <IconsSpinner color="#0000" />
            </Transition>
        </div>
        <Transition enterActiveClass="transition-all duration-500" enterFromClass="opacity-0 h-0 translate-up" enterToClass="h-5" leaveActiveClass="transition-all duration-200" leaveFromClass="h-5" leaveToClass="opacity-0 h-0 translate-down" mode="out-in">
            <span class="flex w-full items-center justify-center whitespace-nowrap " v-if="!loading">
                <span class="flex-1 items-center justify-end prefix">
                    <slot name="prefix" />
                </span>
                <span class="px-4">
                    <slot />
                </span>
                <span class="flex flex-1 items-center justify-end suffix">
                    <slot name="suffix" />
                </span>
            </span>
        </Transition>
    </button>
</template>


<style lang="scss" scoped>
.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

.fade-enter-to, .fade-leave-from {
    opacity: 1;
}

.fade-enter-active,
.fade-leave-active {
    transition: all 500ms ease;
}

.translate-up {
    transform: translateY(-10px);
}

.translate-down {
    transform: translateY(10px);
}
</style>