<template>
    <div @click="$router.push({path: '/finditem/detail', query: { id: id } })" class="m-4 rounded-xl shadow-lg">
        <div class="h-24 p-auto">
            <img v-if="productData.image_name" class="w-24 h-24 absolute" :src="backend_address+'/image/'+productData.image_name">
            <img v-if="!productData.image_name" class="w-24 h-24 absolute" src="@/assets/unavailable_image.png">
            <div id="inline-block">
                <p class="inline ml-24 pl-2 mt-2 text-lg">{{ productData.name }}</p>
                <p class="inline ml-1 text-sm text-gray-500">{{ productData.category }}</p>
            </div>
            <p class="ml-24 m-0 pl-2 text-sm text-gray-500">{{ preview_description }}<label class="block mt-1">... 더보기</label></p>
            <p class="mr-3 mt-0 text-right text-xs text-gray-300">등록 날짜: {{ productData.uploaded_date }}</p>
        </div>
    </div>
</template>

<script lang="ts">
import { changed_value, product } from '@/datatype';
import '../index.css'
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        
    },
    props: {
        id: Number,
        productData: { type: Object as () => product, required: true }
    },
    data(){
        return {
            preview_description: '',
            backend_address: ''
        }
    },
    beforeMount() {
        this.backend_address = process.env.VUE_APP_BACKEND_ADDRESS;
        if (this.$props.productData.detail[0].before_value !== null) {
            this.preview_description = `${this.$props.productData.detail[0].changed_point}
            ${this.$props.productData.detail[0].before_value}${this.$props.productData.detail[0].unit} 
            -> ${this.$props.productData.detail[0].after_value}${this.$props.productData.detail[0].unit} `
        } else {
            this.preview_description = `${this.$props.productData.detail[0].changed_point}의 양이 줄음.`
        }
    }
});
</script>