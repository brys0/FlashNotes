<template>
  <div class="slogan">
    <span class="__title"> FlashNotes </span>
    <span class="__slog"> Write Notes In A <span class="neon">Flash</span> </span>
    <div class="__actions">
      <NButton size="large" @click="$router.push('/search')" type="default" text>
        <template #icon>
          <NIcon>
            <SearchIcon />
          </NIcon>
        </template>
        Search
      </NButton>
      <NButton size="large" @click="$router.push('/create')" :disabled="user == null">
        <template #icon>
          <NIcon>
            <ArrowUp />
          </NIcon>
        </template>
        Get Started
      </NButton>
      <NButton size="large" secondary type="error" :disabled="user == null">
        <template #icon>
          <NIcon>
            <RemoveIcon />
          </NIcon>
        </template>
        Delete My Account
      </NButton>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, Transition } from "vue";
import { NButton, NIcon } from "naive-ui";
import { ArrowUpCircleOutline as ArrowUp, PersonRemoveOutline as RemoveIcon, Search as SearchIcon } from "@vicons/ionicons5";
import { useAuthStore } from "../../stores/AuthStore";
export default defineComponent({
  name: "AppSlogan",
  components: { NButton, ArrowUp, NIcon, RemoveIcon, Transition, SearchIcon },
  setup() {
    let authStore = useAuthStore();
    return {
      user: computed(() => authStore.user),
    };
  },
});
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
    font-family: "Metropolis Bold";
  }

  .__slog {
    color: var(--green);
    font-size: 32px;
    text-align: center;
    padding: 10px;
    font-family: "Inter", sans-serif;
  }

  .__actions {
    display: flex;
    flex-direction: column;
    gap: 16px;
    margin-top: auto;
    margin-bottom: auto;
    flex-flow: column;
    width: 75%;
    animation: bringUp 250ms ease-in-out forwards;
  }
}

@media screen and (max-width: 1022px) {
  .slogan {
    padding: 16px;

    .__title {
      font-size: 42px;
    }

    .__slog {
      font-size: 26px;
      padding: 6px;
    }
  }
}
.neon {
  color: white;
  animation: neon 3s infinite ease;
}

@keyframes bringUp {
  0% {
    opacity: 0;
    transform: translateY(120px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
