<template>
  <div class="create-note">
    <div class="options" v-if="step == 'NotStarted'">
      <div class="__option">
        <NButton size="large" :disabled="cards.length == 0" type="info" secondary>
          <template #icon> 
            <NIcon>
              <CardIcon/>
            </NIcon>
          </template>
          Use An Existing Group
        </NButton>
      </div>
      <div class="__option">
        <NButton size="large" @click="step = 'CreatingName'" type="success" secondary>
          <template #icon>
            <NIcon>
              <AddIcon />
            </NIcon>
          </template>
          Create A New Group
        </NButton>
      </div>
    </div>
    <div class="card-group" v-if="step == 'CreatingName'">
      <CreateCardNameTags @create="
        (value) => {
          step = 'CreatingCards';
          name = value.title;
          create(value.title, value.tags);
        }
      " />
    </div>
    <div class="card-group" v-if="step == 'CreatingCards'">
      <CreateCards :title="name" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineAsyncComponent, defineComponent, computed } from "vue";
import { CardGroupStep } from "../composables/Card";
import { createMeta } from "../helper";
import { useCardStore } from "../stores/CardStore";
import { NIcon } from "naive-ui";
export default defineComponent({
  name: "CreateNotePage",
  components: {
    NButton: defineAsyncComponent(() => import("naive-ui/lib/button/src/Button")),
    AddIcon: defineAsyncComponent(() => import("@vicons/ionicons5/AddCircleOutline")),
    CardIcon: defineAsyncComponent(() => import('@vicons/ionicons5/CardOutline')),
    NIcon,
    CreateCardNameTags: defineAsyncComponent(() => import("../components/create/CreateCardNameTags.vue")),
    CreateCards: defineAsyncComponent(() => import("../components/create/CreateCards.vue")),
  },
  setup() {
    let cardStore = useCardStore();
    createMeta('FlashNotes ➤ Create Card', [
      {
        property: "og:title",
        content: "FlashNotes ➤ Create Card",
      },
      {
        property: "og:url",
        content: "https://fn.feuer.tech/#/create",
      },
      {
        property: "og:image",
        content: "https://fn.feuer.tech/FlashNotes.png",
      },
      {
        property: "og:description",
        content: "Create your card!",
      },
    ])
    return {
      create: cardStore.createCard,
      cards: computed(() => Array.from(cardStore.cards.values()))
    };
  },
  data() {
    return {
      step: "NotStarted" as CardGroupStep,

      name: "",
    };
  },
});
</script>

<style lang="scss" scoped>
.create-note {
  .options {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    justify-content: center;
    align-items: center;
    gap: 16px;
  }

  .card-group {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    justify-content: center;
    align-items: center;
    gap: 16px;
  }
}
</style>
