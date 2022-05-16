<template>
  <div class="home">
    <div class="__center">
      <AppSlogan />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, defineAsyncComponent } from "vue";
import { createMeta } from "../helper";
export default defineComponent({
  name: "FlashNotesHome",
  components: {
    AppSlogan: defineAsyncComponent(() => import("../components/home/AppSlogan.vue")),
  },
  setup() {
    createMeta("FlashNotes ➤ Home", [
      {
        property: "og:title",
        content: "FlashNotes ➤ Home",
      },
      {
        property: "og:url",
        content: "https://fn.feuer.tech",
      },
      {
        property: "og:image",
        content: "https://fn.feuer.tech/FlashNotes.png",
      },
      {
        property: "og:description",
        content: "Write notes in a flash",
      },
    ]);
  },
});
</script>

<style lang="scss" scoped>
.__center {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
}
// @media screen and (max-width: 1022px) {
//    .__center {
//        justify-content: flex-start;
//    }
// }
</style>
