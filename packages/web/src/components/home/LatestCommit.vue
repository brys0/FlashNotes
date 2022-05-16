<template>
  <div class="latest-commit-card">
      <h3>v1.4.4</h3>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

export default defineComponent({
  name: "LatestCommitCard",
  async setup() {
    let gitRes = await fetch("https://api.github.com/repos/brys0/FlashNotes/commits");
    let errored;
    let commitData;
    if (gitRes.status != 200) {
      errored = true;
    }
    let latestCommit = ((await gitRes.json()) as Array<any>)[0].commit;
    console.log(latestCommit);
    commitData = {
      message: latestCommit.message,
      sha: latestCommit.tree.sha,
      author: latestCommit.author.name,
    };
    return {
      commitData: commitData,
      errored: errored,
    };
  },
});
</script>

<style lang="scss" scoped>
.latest-commit-card {
  .__commit {
    color: var(--green);
  }
  .__title {
    color: gray;
  }
  .body {
    .__section {
      display: flex;
      flex-direction: row;
      gap: 8px;
      .__key {
        color: var(--shadow-color);
      }
      .__value {
        color: var(--green);
      }
    }
  }
}
</style>
