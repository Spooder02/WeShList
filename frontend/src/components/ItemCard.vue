<template>
    <div @click="$router.push('/detail')" class="m-4 rounded-xl shadow-lg">
        <div class="h-32">
            <img class="w-32 absolute" src="@/assets/unavailable_image.png">
            <div id="inline-block">
                <p class="inline ml-32 mt-2 text-lg">상품명</p>
                <p class="inline ml-1 text-sm text-gray-500">카테고리</p>
            </div>
            <p class="ml-32 text-md text-gray-500">상품 설명</p>
            <p class="mr-4 mt-11 text-right text-sm text-gray-300">등록 날짜: YY-MM-DD | HH:MM</p>
        </div>
    </div>
</template>

<script lang="ts">
import '../index.css'
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        
    },
});
</script>