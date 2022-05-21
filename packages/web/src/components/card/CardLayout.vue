<template>
    <div class="card-layout" @click="$router.push(`/card/${card?.id}`)">
        <NThing class="card-layout-inner" clickable>
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
            <template #default>
                <div class="header">
                    <NElement tag="h2" style="color: var(--info-color);">
                        Words
                    </NElement>
                </div>
                <NElement tag="div" class="word-row" v-for="(word, index) in card?.words" v-bind:key="index">
                    <NTooltip trigger="hover">
                        <template #trigger>
                            <NElement class="__key" tag="span" style="color: var(--success-color); font-weight: bold;">
                                {{ word[0] }}
                            </NElement>
                        </template>
                        Foreign Word
                    </NTooltip>
                    <NTooltip trigger="hover">
                        <template #trigger>
                            <NIcon>
                                <ArrowRight />
                            </NIcon>
                        </template>
                        To
                    </NTooltip>
                    <NTooltip trigger="hover">
                        <template #trigger>
                            <NElement class="__value" tag="span" style="color: var(--error-color); font-weight: bold;">
                                {{ word[1] }}
                            </NElement>
                        </template>
                        Native Word
                    </NTooltip>
                </NElement>
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
import { NIcon } from "naive-ui";
export default defineComponent({
    name: "CardLayout",
    props: {
        card: Object as PropType<Card>
    },
    components: {
        NThing: defineAsyncComponent(() => import("naive-ui/lib/thing/src/Thing")),
        NTag: defineAsyncComponent(() => import("naive-ui/lib/tag/src/Tag")),
        NElement: defineAsyncComponent(() => import("naive-ui/lib/element/src/Element")),
        NTooltip: defineAsyncComponent(() => import("naive-ui/lib/tooltip/src/Tooltip")),
        NIcon,
        ArrowRight: defineAsyncComponent(() => import('@vicons/ionicons5/ArrowForward'))
    },
});
</script>

<style lang="scss" scoped>
.card-layout {
    transition: all 250ms ease;
    margin-top: 10px;
    margin-bottom: 10px;
    padding: 5px;
    height: 150px;
}

.word-row {
    display: flex;
    flex-direction: row;
    gap: 8px;
    flex-wrap: wrap;
    background-color: var(--popover-color);
    width: fit-content;
    padding: 4px;
    font-family: Inter;
    border-radius: 8px;
    justify-content: space-between;
    align-items: center;
    margin: 5px;
}


.tags {
    display: flex;
    flex-direction: row;
    gap: 8px;
}
</style>
