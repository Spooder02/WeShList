<template>
    <header>
        <div class="my-3 text-center">
            <p class="text-2xl font-semibold">Find Product</p>
        </div>
    </header>
    <div class="bg-slate-100 py-2">
        <ItemCard
        v-for="data in itemInfo"
        :ref="data.product_id"
        :id="data.product_id"
        :productData="data"
        />
    </div>
    <div v-if="itemInfo[0] == null" class="p-4 mx-16 rounded-md shadow-lg m-auto text-center">
        <img src="@/assets/search.png" class="w-32 m-auto my-5">
        <div class="text-xl font-semibold">Cannot find the product</div>
    </div>
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
        axios.get(process.env.VUE_APP_BACKEND_ADDRESS+'/product')
        .then((res) => {
            this.itemInfo = res.data;
            
        })
    },
});
</script>