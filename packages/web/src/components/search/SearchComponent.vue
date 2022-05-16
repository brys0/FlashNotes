<template>
  <div class="search-inner">
    <div class="options-switch">
      <div class="__options">
        <span class="__options-title">Search By:</span>
        <NButton type="error" secondary size="large" :disabled="searchType == 'Tag'" @click="() => { searchType = 'Tag'; debounceEmit(searchInput, searchType)}">
          <b>Tag</b>
        </NButton>
        <NButton type="info" secondary size="large" :disabled="searchType == 'Title'" @click="() => { searchType = 'Title'; debounceEmit(searchInput, searchType) }">
          <b>Title</b>
        </NButton>
      </div>
    </div>
    <NInput size="large" placeholder="Input your search term" v-on:update-value="(e) => { debounceEmit(e, searchType) }" v-model="searchInput"/>
    <NSelect filterable placeholder="Search Tags"/>
  </div>
</template>

<script lang="ts">
import { computed, defineAsyncComponent, defineComponent } from "vue";
import { CardGroupSearchType } from "../../composables/Card";
import {debounce} from '../../helper';
import { useCardStore } from "../../stores/CardStore";
export default defineComponent({
  name: "SearchComp",
  components: {
    NInput: defineAsyncComponent(() => import("naive-ui/lib/input/src/Input")),
    NSelect: defineAsyncComponent(() => import("naive-ui/lib/select/src/Select")),
    NButton: defineAsyncComponent(() => import("naive-ui/lib/button/src/Button")),
  },
  setup(_, { emit }) {
      return {
          tags: useCardStore().tags,
          debounceEmit: debounce((e: string, t: CardGroupSearchType) => {emit('searched', {value: e, type: t})}, 200)
      }  
  },
  mounted() {
      this.convertToKeyValuePair(this.tags)
  },
  data() {
    return {
      searchType: "Tag" as CardGroupSearchType,
      searchInput: ''
    };
  },
  methods: {
      convertToKeyValuePair(arr: any) {
          let keyValuePair = [];
          console.log(arr[1]);
          for (let i = 0; arr.length > i; i++) {
              let item = arr[i];
              console.log(item);
              keyValuePair.push({
                  label: item,
                  value: item
              })
          }
          console.log('Prev', arr, 'New', keyValuePair);
          return keyValuePair;
      }
  }
});
</script>

<style lang="scss" scoped>
.options-switch {
  margin-top: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  color: white;

  .__options-title {
    font-family: "Jetbrains Mono", sans-serif;
    font-size: 20px;
  }
  .__options {
    display: flex;
    flex-direction: row;
    gap: 16px;
    align-items: center;
  }
}
</style>
