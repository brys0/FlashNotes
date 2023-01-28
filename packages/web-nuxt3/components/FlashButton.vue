<script setup lang="ts">
import {withDefaults} from 'vue';

const props = withDefaults(defineProps<{
    color?: 'accent' | 'dark' | '',
    type?: 'icon-only' | 'link' | ''
}>(), {
    color: '',
    type: ''
});
</script>


<template>
    <button :class="`button-comp ${color} ${type}`">
        <span class="fles w-full items-center justify-center whitespace-nowrap">
            <span class="flex-1 items-center justify-end prefix">
                <slot name="prefix" />
            </span>
            <slot />
            <span class="flex flex-1 items-center justify-end suffix">
                <slot name="suffix" />
            </span>
        </span>
    </button>
</template>