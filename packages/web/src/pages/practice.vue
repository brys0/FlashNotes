<template>
  <div class="practice">
    <WriteWordPickForeign @on-word-pick-wrong="(e) => wordPickWrong(e.incorrectWords)" :words="wordPickForeignWords" :sentence="'E eo  DJKLkwkef wfk l'" />
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from "vue";
import WriteWordPickForeign from "../components/practice/WriteWordPickForeign.vue";
import { useRoute } from "vue-router";
import { useCardStore } from "../stores/CardStore";
import { Card, ISentenceWithWords } from "../composables/Card";
import { words } from "lodash";
export default defineComponent({
  name: "PracticeCard",
  components: { WriteWordPickForeign },
  async setup() {
    const route = useRoute();
    const cardStore = useCardStore();
    let cardID = route.params.id as string;
    let wordPickForeignWords  = new Map<string, string>();
    if (!cardID) location.href = "/";
    await cardStore.getCard(cardID);
    wordPickForeignWords = cardStore.currentCard?.words!!;
    cardStore.currentCard?.sentences.forEach((value: string, key: string) => {
       let wordsFromSentence = value.split(' ');
       let sentenceWordPair = {struct: {key: value, value: key}} as ISentenceWithWords
       console.log(value, key);
       wordsFromSentence.forEach(word => {
           console.log('Word', word);
           let foreignWordValues = Array.from(wordPickForeignWords.values());
           console.log(foreignWordValues);
           if (foreignWordValues.includes(word)) {
              console.log('Includes', word);
           }
       }) 
    });
    return {
      card: computed(() => cardStore.currentCard),
      wordPickForeignWords: wordPickForeignWords
    };
  },
  mounted() {
      this.wordPickForeignWords = this.card?.words!!;

  },
  methods: {
    wordPickWrong(incorrectWords: string[]) {
      
    },
  },
  data() {
    return {
      incorrectWords: [] as string[],

      wordPickForeignSentences: new Map<string, ISentenceWithWords>()
    };
  },
});
</script>

<style lang="scss" scoped>
.practice {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>
