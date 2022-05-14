
<template>
    <div class="header" border-bottom-left-round border-bottom-right-round>
        <div class="logo">
            <span class="__text">FlashNotes</span>
        </div>
        <div class="actions">
            <div class="__action source">
                <NButton type="info" secondary @click="window.open('https://github.com/brys0/FlashNotes', '_blank')">
                    <template #icon>
                        <NIcon>
                            <CodeIcon />
                        </NIcon>
                    </template>
                    Source
                </NButton>
            </div>
            <div class="__action ifLoggedOut" v-if="user == null">
                <NButton type="primary" :loading="loading" @click="() => { handleSignIn(); }">
                    <template #icon>
                        <NIcon>
                            <LoginIcon />
                        </NIcon>
                    </template>
                    Login
                </NButton>
            </div>
            <div class="__action ifLoggedIn" v-if="user != null">
                <NButton type="error" @click="deleteUser()">
                    <template #icon>
                        <NIcon>
                            <div class="flip">
                                <LogOutIcon />
                            </div>
                        </NIcon>
                    </template>
                    Logout
                </NButton>
            </div>

        </div>
    </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, watch } from "vue";
import { NButton, NIcon, NAvatar } from 'naive-ui';
import { LogInOutline as LoginIcon, LogOutOutline as LogOutIcon, CodeOutline as CodeIcon } from '@vicons/ionicons5';
import { IUser, useAuthStore } from "../../stores/AuthStore";
import googleOneTapSignin from '../../composables/googleOneTap.js';
export default defineComponent({
    name: 'AppHeader',
    components: { NButton, LoginIcon, NIcon, LogOutIcon, NAvatar, CodeIcon },
    setup() {
        let authStore = useAuthStore();
        return {
            user: computed(() => authStore.user as IUser),
            createUser: authStore.setAuthedUser,
            deleteUser: authStore.deleteUser,
            loading: ref(false),
            window: window
        }
    },
    methods: {
        async handleSignIn() {
            this.loading = true;
            const { googleOptions, oneTapSignin, userData } = googleOneTapSignin();
            oneTapSignin(googleOptions);
            watch(userData, () => {
                this.loading = false;
                // @ts-ignore
                this.createUser({ name: userData.value.name, email: userData.value.email, avatar: userData.value.picture })
                console.log(userData.value);
            })
        }
    }
});

</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Fira+Mono:wght@500&family=Inter:wght@200;500&display=swap');

.header {
    padding: 8px;
    display: flex;
    flex-direction: row;
    background-color: var(--headerbg);

    .logo {
        .__text {
            font-size: 1.5rem;
            color: #70c0e8;
            font-weight: bold;
            justify-self: flex-start;
            font-family: 'Metropolis Regular';
        }
    }

    .actions {
        margin-left: auto;
        display: flex;
        flex-direction: row;

        .__action {
            margin-left: 4px;
            margin-right: 4px;
        }
    }
}

.flip {
    transform: rotate(90deg);
}


</style>
