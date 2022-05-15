<template>
  <div class="create-note">
    <div class="options" v-if="step == 'NotStarted'">
      <div class="__option" v-if="false">
        <NButton size="large">Use An Existing Group</NButton>
      </div>
      <div class="__option">
        <NButton size="large" @click="step = 'CreatingName'">
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
      <CreateCardNameTags
        @create="
          (value) => {
            step = 'CreatingCards';
            name = value.title;
            create(value.title, value.tags);
          }
        "
      />
    </div>
    <div class="card-group" v-if="step == 'CreatingCards'">
        <CreateCards :title="name"/>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { NButton, NIcon } from "naive-ui";
import { AddCircleOutline as AddIcon } from "@vicons/ionicons5";
import { CardGroupStep } from "../composables/Card";
import CreateCardNameTags from "../components/create/CreateCardNameTags.vue";
import CreateCards from "../components/create/CreateCards.vue";
import { useCardStore } from "../stores/CardStore";
import { useAuthStore } from "../stores/AuthStore";
export default defineComponent({
  name: "CreateNotePage",
  components: { NButton, AddIcon, NIcon, CreateCardNameTags, CreateCards },
  setup() {
      let cardStore = useCardStore();
      return {
          create: cardStore.createCard
      }
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
