<template>
  <div class="latest-commit-card" @click="openTag()">
    <GitBranch color="var(--shadow-color)" class="git-icon" />
    <span class="__gitname">v1.4.4</span>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { GitBranch } from "@vicons/ionicons5";
export default defineComponent({
  name: "LatestCommitCard",
  async setup() {
    let gitRes = await fetch("https://api.github.com/repos/brys0/FlashNotes/tags");
    let errored;
    if (gitRes.status != 200) {
      errored = true;
    }
    let latestRelease = ((await gitRes.json()) as Array<any>)[0];
    if (latestRelease == undefined) return;
    let releaseData = {
      url: `https://github.com/brys0/FlashNotes/releases/tag/${latestRelease.name}`,
      version: latestRelease.name,
    } as any;
    return {
      commitData: releaseData as any,
      errored: errored,
    };
  },
  methods: {
    openTag() {
      // @ts-ignore
      let url = this.commitData.url;
      window.open(url, "_blank");
    },
  },
  components: { GitBranch },
});
</script>

<style lang="scss" scoped>
.latest-commit-card {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 2px;
  transition: all 250ms ease;
  cursor: pointer;
  &:hover {
    filter: brightness(120%);
  }
  .git-icon {
    height: 22px;
    width: 22px;
  }
  .__gitname {
    font-size: 25px;
    color: var(--green);
    font-family: "JetBrains Mono";
  }
}
</style>
