<template>
    <div class="card currentPage">
        <CardLayout :card="card!!"/>
    </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue";
import { useRoute } from "vue-router";
import { useCardStore } from "../stores/CardStore";
import CardLayout from "../components/card/CardLayout.vue";
export default defineComponent({
    name: 'CardPage',
    async setup() {
        const route = useRoute();
        const cardStore = useCardStore();
        let cardID = route.params.id as string;
        if (!cardID) location.href = "/";
        cardStore.getCard(cardID);
        return {
            card: computed(() => cardStore.currentCard),
        }
    },
    components: {CardLayout}
})
</script>

<style lang="scss" scoped>
.card {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
</style>