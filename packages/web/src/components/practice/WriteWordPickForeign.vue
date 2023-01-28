<template>
  <div class="word-pick">
    <div class="header">
      <NElement tag="h1" class="title-pick"> Write this in the foreign language </NElement>
    </div>
    <NElement tag="div" class="foreign-sentence sentence bubble">
      <NElement tag="span" class="foreign-word word" v-for="(word, index) in sentence?.split(' ')" v-bind:key="index">
        {{ word }}
      </NElement>
    </NElement>
    <div class="input-sentence">
      <div class="line">
        <div class="line-word">
          <NButton type="default" class="word-button" secondary v-for="(word, index) in formedWords" v-bind:key="index" @click="formedWords = formedWords.filter((i) => i != word)">
            {{ word }}
          </NButton>
        </div>
        <div class="sep" />
      </div>
      <div class="sep" />
      <div class="sep" />
    </div>
    <div class="actions">
      <NButton type="default" quaternary v-for="(word, index) in wordsRandom" v-bind:key="index" @click="formedWords.push(word)" :disabled="formedWords.includes(word)">
        {{ word }}
      </NButton>
    </div>
    <div class="potential-answer submit" v-if="!correct">
      <NButton type="success" secondary size="large" :disabled="formedWords.length <= 0" @click="onSubmit()"> Submit </NButton>
    </div>
    <div class="correct-answer submit" v-else>
       <NButton type="success" quaternary size="large" @click="$emit('onWordPickCorrect')"> Continue </NButton>
    </div>
  </div>
</template>

<script lang="ts">
import { useDialog, useMessage } from "naive-ui";
import { defineComponent, defineAsyncComponent, PropType } from "vue";
import shuffleArray from "../../composables/shuffleArray";
export default defineComponent({
  name: "WriteWordPickForeignCard",
  components: {
    NElement: defineAsyncComponent(() => import("naive-ui/lib/element/src/Element")),
    NButton: defineAsyncComponent(() => import("naive-ui/lib/button/src/Button")),
  },
  props: {
    words: {
      type: Map as PropType<Map<string, string>>,
      required: false,
      default: () => {
        let map = new Map<string, string>();
        map.set("De", "A");
        map.set("Krant", "Paper");
        return map;
      },
    },
    randomWords: {
      type: Map as PropType<Map<string, string>>,
      required: false,
      default: () => {
        let map = new Map<string, string>();
        map.set("fhghffh", "Aghfhfh");
        map.set("Krangfhgfhft", "hhgfhfghfhPaper");
        return map;
      },
    },
    sentence: String
  },
  mounted() {
    this.wordsRandom.push(...Array.from(this.words.keys()));
    this.wordsRandom.push(...Array.from(this.randomWords.keys()));
    this.wordsRandom = shuffleArray(this.wordsRandom);
  },
  data() {
    return {
      formedWords: [] as Array<string>,
      wordsRandom: [] as string[],
      correct: false
    };
  },
  setup() {
    const message = useMessage();
    const dialog = useDialog();
    return {
      message: message,
      dialog: dialog,
    };
  },
  methods: {
    onSubmit() {
      let wordValueArray = Array.from(this.words.keys());
      const equals = (a: any, b: any) => JSON.stringify(a) === JSON.stringify(b);
      const filterByDifferent = (a: any[], b: any[]) => {
        let incorrectArray = [] as any[];
        a.forEach((aItem) => {
          if (!b.includes(aItem)) {
            incorrectArray.push(aItem);
          }
        });
        return incorrectArray;
      };
      if (equals(this.formedWords, wordValueArray)) {
        this.message.success("Nice, you got it right!", {
          closable: true,
        });
        this.correct = true;
      } else {
        this.dialog.error({
          title: "Not Quite!",
          content: `The correct answer was: "${wordValueArray.join(" ")}"`,
          positiveText: "Next Question ➤",
          positiveButtonProps: {
            type: "success",
            secondary: true,
          },
        });
        this.$emit("onWordPickWrong", {
          incorrectWords: filterByDifferent(wordValueArray, this.formedWords),
        });
      }
      return;
    },
  },
  emits: ["onWordPickWrong", "onWordPickCorrect"],
});
</script>

<style lang="scss" scoped>
.word-pick {
  max-width: 75vw;
  .header {
    .title-pick {
      color: var(--info-color);
    }
  }
  .sentence {
    width: fit-content;
    &.bubble {
      background-color: var(--close-color-pressed);
      padding: 8px;
      border-radius: 8px;
    }
    font-size: 1rem;
    .word {
      color: var(--text-color-1);
      margin-left: 5px;
    }
  }
  .input-sentence {
    margin-top: 50px;
    line-height: 2.3rem;
    .line {
      margin-top: 2rem;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      .sep {
        background-color: gray;
        height: 2px;
        border-radius: 8px;
        width: 100%;
      }
      .foreign-word {
        color: var(--text-color-1);
        font-size: 20px;
        line-height: 2.3rem;
        margin-left: 4px;
      }
    }
  }
  .actions {
    margin-top: 20px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 16px;
  }
  .submit {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
.word-button {
  margin-right: 5px;
}
</style>
