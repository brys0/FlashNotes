<template>
    <div class="slogan">
        <span class="__title">
            FlashNotes
        </span>
        <span class="__slog">
            Write Notes In A Flash
        </span>
        <Transition>
            <div class="__actions" v-if="user != null">
                <NButton size="large" @click="$router.push('/fake')">
                    <template #icon>
                        <NIcon>
                            <ArrowUp />
                        </NIcon>
                    </template>
                    Why don't we get started {{ user.name }}?
                </NButton>
                <NButton size="large" secondary type="error">
                    <template #icon>
                        <NIcon>
                            <RemoveIcon />
                        </NIcon>
                    </template>
                    Delete My Account
                </NButton>
            </div>
        </Transition>
    </div>
</template>

<script lang="ts">
import { computed, defineComponent, Transition } from "vue";
import { NButton, NIcon } from "naive-ui";
import { ArrowUpCircleOutline as ArrowUp, PersonRemoveOutline as RemoveIcon } from "@vicons/ionicons5";
import { useAuthStore } from "../../stores/AuthStore";
export default defineComponent({
    name: 'AppSlogan',
    components: { NButton, ArrowUp, NIcon, RemoveIcon, Transition },
    setup() {
        let authStore = useAuthStore();
        return {
            user: computed(() => authStore.user)
        }
    }
})
</script>

<style lang="scss" scoped>
.slogan {
    display: flex;
    flex-direction: row;
    flex-flow: column;
    justify-content: center;
    align-items: center;
    transition: all 200ms ease;
    height: 600px;
    .__title {
        font-size: 64px;
        font-weight: bold;
        color: rgba(255, 255, 255, 0.9);
        font-family: 'Metropolis Bold';
    }

    .__slog {
        color: var(--green);
        font-size: 32px;
        text-align: center;
        padding: 10px;
        font-family: 'Inter', sans-serif;
    }

    .__actions {
        display: flex;
        flex-direction: column;
        gap: 16px;
        flex-flow: column;
    }
}

@media screen and (max-width: 1022px) {
    .slogan {
        justify-content: flex-start;
        padding: 16px;

        .__title {
            font-size: 42px;
        }

        .__slog {
            font-size: 26px;
            text-align: left;
            padding: 6px;

        }
    }
}

</style>