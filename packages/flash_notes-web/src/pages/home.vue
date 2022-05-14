<template>
    <div class="home">
        <div class="__center">
            <AppSlogan/>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AppSlogan from "../components/home/AppSlogan.vue";

export default defineComponent({
    name: 'FlashNotesHome',
    components: {
       AppSlogan 
    }
})
</script>

<style lang="scss" scoped>
.__center {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: calc(100vh - 54px);
}
@media screen and (max-width: 1022px) {
   .__center {
       justify-content: flex-start;
   }
}
</style>