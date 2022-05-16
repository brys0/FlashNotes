<template>
    <div class="card currentPage">
        
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
    name: 'CardPage',
    beforeCreate() {
        if (!this.$router.currentRoute.value.params.id) {
            return this.$router.push('/');
        }
    }
})
</script>