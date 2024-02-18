<template>
    <header>
        <div class="mt-4 text-center">
            <p class="text-2xl font-semibold">상품 찾기</p>
        </div>
    </header>
    <ItemCard
    v-for="data in itemInfo"
    :ref="data.product_id"
    :id="data.product_id"
    :productData="data"
    />
</template>

<script lang="ts">
import axios from 'axios';
import '../index.css';
import ItemCard from '@/components/ItemCard.vue';
import { defineComponent } from 'vue';
import { product } from '@/datatype';

export default defineComponent({
    name: '',
    components: {
        ItemCard,
    },
    data() {
        return {
            itemInfo: {} as product
        }
    },
    mounted() {
        axios.get('http://localhost:8081/product')
        .then((res) => {
            this.itemInfo = res.data;
        })
    },
});
</script>