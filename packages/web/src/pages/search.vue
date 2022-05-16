<template>
  <div class="search">
    <div class="top">
      <h1>Search Cards</h1>
      <div class="bring-down">
        <SearchComponent @searched="(card) => search(card)" />
      </div>
      <div class="cards" v-if="cards.size != 0">
        <BasicCard v-for="(card, index) in cards" :card="card[1]" v-bind:key="card[0]" />
      </div>
      <div class="cards" v-if="cards.size == 0"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineAsyncComponent, defineComponent } from "vue";
import { createMeta } from "../helper";
import { ICardSearch } from "../composables/Card";
import { useCardStore } from "../stores/CardStore";
import { useLoadingBar } from "naive-ui";
export default defineComponent({
  name: "SearchPage",
  setup() {
    let cardStore = useCardStore();
    let loadingBar = useLoadingBar();
    createMeta("FlashNotes ➤ Search Cards", [
      {
        property: "og:title",
        content: "FlashNotes ➤ Search",
      },
      {
        property: "og:url",
        content: "https://fn.feuer.tech/#/search",
      },
      {
        property: "og:image",
        content: "https://fn.feuer.tech/FlashNotes.png",
      },
      {
        property: "og:description",
        content: "Search for cards",
      },
    ]);
    cardStore.browseCards();
    return {
      cards: computed(() => cardStore.searchCards),
      startLoading: loadingBar.start,
      finishLoadingBar: loadingBar.finish,
    };
  },
  components: {
    SearchComponent: defineAsyncComponent(() => import("../components/search/SearchComponent.vue")),
    BasicCard: defineAsyncComponent(() => import("../components/search/BasicCard.vue")),
  },
  methods: {
    search(card: ICardSearch) {
      this.startLoading();
      useCardStore()
        .searchForCards(card.type, card.value)
        .then(() => this.finishLoadingBar());
    },
  },
});
</script>

<style lang="scss" scoped>
.search {
  display: flex;
  flex-direction: column;
  align-items: center;
  .top {
    h1 {
      font-family: Oswald;
      font-size: 70px;
      color: var(--shadow-color);
    }
    height: 100%;
  }
  width: 100%;
  overflow: hidden;
}
.cards {
  overflow-y: auto;
  max-height: calc(100% - 350px);
  height: calc(100% - 350px);
  .__loading {
    color: var(--shadow-color);
  }
}
</style>
