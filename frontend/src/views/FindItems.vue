<template>
    <header>
        <div class="mt-4 text-center">
            <p class="text-2xl font-semibold">상품 찾기</p>
        </div>
    </header>
    <ItemCard
    v-for="data in itemInfo"
    :ref="data.id"
    :name="data.name"
    :image="data.image_name"
    :category="data.category"
    :description="data.description"
    :update_time="data.uploaded_date"
    />
</template>

<script lang="ts">
import axios from 'axios';
import '../index.css';
import ItemCard from '@/components/ItemCard.vue';
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        ItemCard,
    },
    data() {
        return {
            itemInfo: Object
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