<template>
  <div class="card">
    <NThing class="card-inner">
      <template #header>
        <h1>{{ title }}</h1>
      </template>
      <template #description>
        <div class="tags">
          <NTag v-for="(tag, index) in currentCard?.tags" v-bind:key="index" type="info">
            {{ tag }}
          </NTag>
        </div>
      </template>
      <h2>Words</h2>
      <div class="scrollable-descript">
        <NDynamicInput preset="pair" key-placeholder="Word" value-placeholder="Meaning" v-on:update-value="onWord" />
      </div>
      <h2>Sentences</h2>
      <div class="scrollable-descript">
        <NDynamicInput preset="pair" key-placeholder="Foreign Sentence" value-placeholder="Native Sentence" v-on:update-value="onSentence" />
      </div>
      <template #footer>
        <div class="__footer">
          <NButton type="primary" secondary @click="onSave(words, sentences)">
            <b>Save</b>
          </NButton>
          <NButton type="error" secondary @click="$router.push('/')">
            <b>Cancel</b>
          </NButton>
        </div>
      </template>
    </NThing>
  </div>
</template>

<script lang="ts">
import { computed, defineAsyncComponent, defineComponent } from "vue";
import { CardPairArray } from "../../composables/Card";
import { useCardStore } from "../../stores/CardStore";
import { useAuthStore } from "../../stores/AuthStore";
export default defineComponent({
  name: "CreateCards",
  setup(props: any) {
    let cardStore = useCardStore();
    return {
      cards: computed(() => cardStore.cards),
      currentCard: computed(() => cardStore.cards.get(props.title as string)),
    };
  },
  components: {
    NThing: defineAsyncComponent(() => import("naive-ui/lib/thing/src/Thing")),
    NDynamicInput: defineAsyncComponent(() => import("naive-ui/lib/dynamic-input/src/DynamicInput")),
    NButton: defineAsyncComponent(() => import("naive-ui/lib/button/src/Button")),
    NTag: defineAsyncComponent(() => import("naive-ui/lib/tag/src/Tag")),
  },
  props: {
    title: String,
  },

  methods: {
    onWord(arr: any) {
      this.words = arr;
    },
    onSentence(arr: any) {
      this.sentences = arr;
    },

    onSave(words: CardPairArray[], sentences: CardPairArray[]) {
      words.forEach((word: CardPairArray) => {
        this.currentCard?.words.set(word.key, word.value);
      });
      sentences.forEach((sentence: CardPairArray) => {
        this.currentCard?.sentences.set(sentence.key, sentence.value);
      });
      this.currentCard?.save(useAuthStore().user?.auth!!, null);
      this.$router.push("/card/{ID}");
    },
  },
  data() {
    return {
      words: [],
      sentences: [],
    };
  },
});
</script>

<style lang="scss" scoped>
.card {
  display: flex;
  flex-direction: column;
  gap: 8px;
  background-color: var(--headerbg);
  padding: 16px;
  border-radius: 16px;
  max-width: 75vw;
  width: 55vw;
  min-width: 120px;
  overflow-y: auto;
}
.scrollable-descript {
  overflow: scroll;
  max-height: 25vh;
  display: flex;
  flex-direction: column;
  padding-right: 25px;
  padding-left: 25px;
}
.__footer {
  display: flex;
  flex-direction: row;
  gap: 16px;
  justify-content: space-between;
}
.tags {
  display: flex;
  flex-direction: row;
  gap: 8px;
}
@media screen and (max-width: 1022px) {
  .card {
    width: 100%;
    height: 100%;
    padding: 4px;
    border-radius: 0;
    max-width: 100vw;
    background-color: transparent;
    .card-inner {
      padding: 8px;
    }
    .__footer {
      flex-direction: column;
      margin-top: 20%;
    }
  }
  .scrollable-descript {
    padding: 0;
    max-height: 19vh;
  }
}
</style>
