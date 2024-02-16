<template>
    <div class="m-4 p-2 rounded-xl shadow-lg">
        <div class="">
            <img v-if="productData?.image_name" class="max-w-full" :src="'http://localhost:8081/image/'+productData?.image_name">
            <img v-if="!productData?.image_name" class="w-full" src="@/assets/unavailable_image.png">
            <div class="ml-4">
                <div id="inline-block">
                <p class="inline mt-2 text-lg">{{ productData?.name }}</p>
                <p class="inline ml-1 text-sm text-gray-500">{{ productData?.category }}</p>
                </div>
                <div class="m-0 p-1 rounded-xl shadow-lg">
                    <p class="text-md text-black-500 text-center">변화:</p>
                    <p v-for="detail in productData?.detail" class="text-lg text-black-500 text-center">
                        {{ detail.changed_point }}({{ detail.before_detail }}{{ detail.unit }} -> {{ detail.after_detail }}{{ detail.unit }})
                    </p>
                </div>
                <div class="text-center mt-4">
                    <p class="font-medium">올바른 정보를 위해 제보해주세요!</p>
                    <button class="rounded-md bg-green-500 p-1 text-white mr-1 shadow-md hover:bg-green-600">
                        <img class="inline w-5" src="@/assets/verified.png">
                        <p class="inline ml-1.5">올바른 정보</p>
                        <p class="font-bold">{{ productData?.positive_point }}</p>
                    </button>
                    <button class="rounded-md bg-red-500 p-1 text-white m-1 shadow-md hover:bg-red-600">
                        <img class="inline w-5" src="@/assets/cancel.png">
                        <p class="inline ml-1.5">잘못된 정보</p>
                        <p class="font-bold">{{ productData?.negative_point }}</p>
                    </button>
                </div>
                
            </div>
            <p class="mr-4 mt-11 text-right text-sm text-gray-300">
                {{productData?.uploader}} 님의 제보로 등록되었습니다.<br>
                등록일자: {{productData?.uploaded_date}}
            </p>
        </div>
    </div>
</template>

<script lang="ts">
import axios from 'axios';
import { product } from '../datatype';
import '../index.css'
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

export default defineComponent({
    setup() {
        const productData = ref<product| null>(null);
        
        onMounted(async ()=> {
            const response = await axios.get<product>('http://localhost:8081/product/'+useRoute().query.id);
            productData.value = response.data;
            console.log(productData.value)
        })
        return { productData };
    },
});
</script>