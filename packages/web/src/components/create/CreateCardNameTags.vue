<template>
  <div class="create-card-name">
    <span class="__info grouptitle">Whats the name of this group?</span>
    <NInput placeholder="Group Name" size="large" show-count :maxlength="32" v-model:value="cardTitle" />
    <div class="tags section">
      <h2 class="__info ic">What tags does this group have?</h2>
      <NDynamicTags v-on:update-value="onTag" :max="8" size="large" closable />
    </div>
    <div class="__actions">
      <NButton type="primary" :disabled="cardTitle.length < 1" @click="$emit('create', {title: cardTitle as string, tags: cardTags as []})" icon-placement="right" :currentlyActive="cardTitle.length >= 1" class="save-btn">
        <template #icon>
          <NIcon>
            <ArrowIcon />
          </NIcon>
        </template>
        Next
      </NButton>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, defineAsyncComponent } from "vue";
import { NIcon } from "naive-ui";
export default defineComponent({
  name: "CreateCardName",
  components: {
    NInput: defineAsyncComponent(() => import("naive-ui/lib/input/src/Input")),
    NButton: defineAsyncComponent(() => import("naive-ui/lib/button/src/Button")),
    NIcon,
    ArrowIcon: defineAsyncComponent(() => import("@vicons/ionicons5/ChevronForward")),
    NDynamicTags: defineAsyncComponent(() => import("naive-ui/lib/dynamic-tags/src/DynamicTags")),
  },
  data() {
    return {
      cardTitle: "",
      cardTags: [] as any,
    };
  },
  methods: {
    onTag(value: any) {
      this.cardTags = value;
    },
  },
});
</script>

<style lang="scss" scoped>
.create-card-name {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.__info {
  color: var(--green);
}
.ic {
  color: #70c0e8;
}
.grouptitle {
  font-size: 32px;
  font-weight: bold;
}
@media screen and (max-width: 1022px) {
  .grouptitle {
    font-size: 22px;
  }
  .create-card-name {
    width: 85%;
  }
}
.__actions {
  width: 100%;
  margin-top: 17px;
  .save-btn {
    width: 100%;
  }
}
</style>
