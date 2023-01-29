<script setup lang="ts">
const props = defineProps<{
    error: any
}>()
</script>


<template>
    <div class="error-page h-[100vh] w-[100vw]">
        <div class="flex justify-center items-center h-[100%] w-[100%] select-none">
            <section class="container flex flex-auto flex-col flex items-center justify-center px-4 py-8">
                <aside class="sm:mb-6 flex !flex-initial flex-grow items-center justify-center px-4 flex-col font-semibold  text-foreground text-center">
                    <h1 class=" text-[128px] animate-delay-200 animate-fade-rotate-in-lg opacity-0 font-alba">{{ error.statusCode }}</h1>
                    <h2 class="sm:text-4xl sm:my-[5rem] text-xl animate-delay-300 animate-fade-rotate-in opacity-0 my-2">{{ error.statusMessage }}</h2>
                </aside>
                <div class="card animate-delay-[1s] animate-duration-700 opacity-0 animate-fill-forwards animate-fade-in-up">
                    <FlashButton color="accent" @click="$router.push('/')">
                        <template #prefix>
                            <IconsHouse weight="duotone" color="currentColor" :size="24" />
                        </template>
                        Return to home
                        <template #suffix>
                            <IconsHouse weight="duotone" color="currentColor" :size="24" />
                        </template>
                    </FlashButton>
                </div>
            </section>
        </div>
    </div>
</template>