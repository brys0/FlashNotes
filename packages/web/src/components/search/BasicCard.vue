<template>
  <div class="basic-card" @click="$router.push(`/card/${card?.id}`)">
    <NThing class="basic-card-inner" clickable>
      <template #header>
        <h2>{{ card?.title }}</h2>
      </template>
      <template #description>
        <div class="tags">
          <NTag type="info" v-for="(tag, index) in card?.tags" v-bind:key="index">
            {{ tag }}
          </NTag>
        </div>
      </template>
      <template #footer>
        <span>Created By: {{ card?.created_by }}</span>
      </template>
    </NThing>
  </div>
</template>

<script lang="ts">
import { defineAsyncComponent, defineComponent, PropType } from "vue";
import { Card } from "../../composables/Card";
export default defineComponent({
  name: "BasicCard",
  props: {
    card: {
      type: Object as PropType<Card>,
    },
  },
  components: {
    NThing: defineAsyncComponent(() => import("naive-ui/lib/thing/src/Thing")),
    NTag: defineAsyncComponent(() => import("naive-ui/lib/tag/src/Tag")),
  },
});
</script>

<style lang="scss" scoped>
.basic-card {
    transition: all 250ms ease;
    cursor: pointer;
    &:hover {
        background-color: var(--headerbg);
        border-radius: 8px;
    }
    margin-top: 10px;
    margin-bottom: 10px;
    padding: 5px;
    height: 150px;
}
.tags {
    display: flex;
    flex-direction: row;
    gap: 8px;
}
</style>
