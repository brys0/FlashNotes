<script setup lang="ts">
import { useRouter } from 'vue-router';
import googleOneTap from '@/lib/googleOneTap.js';

const router = useRouter();
const { googleOptions, oneTapSignin, userData, errorData } = googleOneTap();

const loginHovered = ref(false);
const signUpHovered = ref(false);
const loading = ref(false);
const onHoverLogin = () => {
    loginHovered.value = true;
}

const onEndLogin = () => {
    loginHovered.value = false;
}

const onHoverSignup = () => {
    signUpHovered.value = true;
}

const onEndSignup = () => {
    signUpHovered.value = false;
}

const signInWithGoogle = () => {
    oneTapSignin(googleOptions);
}


const navigate = (url: string) => {
    location.href = url;
}
</script>
<template>
    <div class="flex items-center flex-col">
        <div class="inline-flex sm:flex-row flex-col justify-center items-center h-[100%] select-none">
            <section class="container flex flex-auto flex-col flex items-center justify-center px-4 py-8">
                <aside class="sm:mb-6 flex !flex-initial flex-row items-center justify-center px-4 flex-col font-semibold  text-foreground text-center">
                    <Logo class="animate-fade-rotate-scale-in opacity-0" />
                    <h1 class="sm:text-4xl sm:my-5 text-2xl animate-delay-200 animate-fade-rotate-in-lg opacity-0">Log into FlashNotes</h1>
                    <h2 class="sm:text-2xl sm:my-[.15rem] text-xl animate-delay-300 animate-fade-rotate-in opacity-0 text-stone-600 font-medium font-ti">We're currently in closed development</h2>
                </aside>
            </section>
            <section class="container bg-white p-6 sm:rounded-3xl shadow-lg sm:p-10 sm:ml-10 sm:w-auto flex justify-center w-[100vw] opacity-0 animate-fade-in animate-delay-500 animate-duration-500 animate-fill-forwards">
                <form class="flex flex-col gap-6 justify-center w-[340px] items-center" @submit.prevent="">
                    <div class="component w-[98%] h-full shadow-lg">
                        <label class="font-bold font-ti text-xl">Email</label>
                        <div class="container mt-2 border-gray-400 border-2 pb-2 rounded-lg w-full">
                            <input type="email" class="w-full font-ti text-lg px-4 py-0 mt-2 outline-none" placeholder="example@notes.lightable.app" />
                        </div>
                    </div>
                    <div class="component w-[98%] h-full shadow-lg">
                        <label class="font-bold font-ti text-xl">Password</label>
                        <div class="container mt-2 border-gray-400 border-2 pb-2 rounded-lg w-full">
                            <input type="email" class="w-full font-ti text-lg px-4 py-0 mt-2 outline-none" placeholder="••••••••" />
                        </div>
                    </div>
                    
                    <div class="component w-full">
                        <FlashButton color="accent" @mouseover="onHoverLogin" @mouseleave="onEndLogin" :loading="loading" @click="loading = !loading">
                            <template #prefix>
                                <div class="flex flex-row">
                                    <IconsLockSimple weight="fill" color="currentColor" :size="24" class="relative" v-if="!loginHovered" />
                                    <IconsLockSimpleOpen weight="fill" color="currentColor" :size="24" class="relative" v-if="loginHovered" />
                                </div>
                            </template>
                            Sign In
                        </FlashButton>
                    </div>
                    
                    <div class="component w-full">
                        <FlashButton color="dark" @click="navigate('/signup')" @mouseover="onHoverSignup" @mouseleave="onEndSignup">
                            <template #prefix>
                                <IconsUser :size="24" weight="fill" color="currentColor" v-if="!signUpHovered"/>
                                <IconsUserPlus :size="27" weight="fill" color="currentColor" v-if="signUpHovered"/>
                            </template>
                            Sign Up
                        </FlashButton>
                    </div>
                    <div class="component w-full">
                        <FlashButton color="dark" @click="signInWithGoogle()">
                            <template #prefix>
                               <IconsGoogle weight="true" :size="24" color="currentColor"/>
                            </template>
                            Sign In With Google
                        </FlashButton>
                    </div>
                </form>
            </section>
        </div>
    </div>
</template>

<style lang="scss" scoped>

</style>