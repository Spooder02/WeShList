<template>
    <header>
        <div class="mt-4 text-center">
            <p class="text-2xl font-semibold">상품 찾기</p>
        </div>
    </header>
    <ItemCard/>
</template>

<script lang="ts">
import '../index.css';
import ItemCard from '@/components/ItemCard.vue';
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        ItemCard,
    },
});
</script>