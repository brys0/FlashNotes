<script setup lang="ts">

const reload = () => {
    location.reload();
}
</script>

<template>
    <div class="flex justify-center items-center h-[100%] w-[100%] select-none">
        <section class="container flex flex-auto flex-col flex items-center justify-center px-4 py-8">
            <aside class="sm:mb-6 flex !flex-initial flex-grow items-center justify-center px-4 flex-col font-semibold  text-foreground text-center">
                <Logo class="animate-fade-rotate-scale-in opacity-0"/>
                <h1 class="sm:text-4xl sm:my-5 text-2xl animate-delay-200 animate-fade-rotate-in-lg opacity-0">Pardon the interuption</h1>
                <h2 class="sm:text-2xl sm:my-[.15rem] text-xl animate-delay-300 animate-fade-rotate-in opacity-0">Check back later</h2>
            </aside>
            <div class="card animate-delay-[1s] animate-duration-700 opacity-0 animate-fill-forwards animate-fade-in-up">
                <FlashButton color="accent" @click="reload()">
                    Reload
                    <template #suffix>
                        <IconsReload weight="bold" :size="18" color="currentColor"/>
                    </template>
                </FlashButton>
            </div>
        </section>
    </div>
</template>