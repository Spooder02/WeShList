<template>
    <div class="m-4 p-2 rounded-xl shadow-lg">
        <div class="">
            <img class="max-w-full" src="@/assets/unavailable_image.png">
            <div class="ml-4">
                <div id="inline-block">
                <p class="inline mt-2 text-lg">상품명</p>
                <p class="inline ml-1 text-sm text-gray-500">카테고리</p>
                </div>
                <p class="text-md text-gray-500">상품 설명</p>
                <div class="text-center mt-4">
                    <p class="font-medium">올바른 정보를 위해 제보해주세요!</p>
                    <button class="rounded-md bg-green-500 p-1 text-white mr-1 shadow-md hover:bg-green-600">
                        <img class="inline w-5" src="@/assets/verified.png">
                        <p class="inline ml-1.5">올바른 정보</p>
                        <p class="font-bold">0</p>
                    </button>
                    <button class="rounded-md bg-red-500 p-1 text-white m-1 shadow-md hover:bg-red-600">
                        <img class="inline w-5" src="@/assets/cancel.png">
                        <p class="inline ml-1.5">잘못된 정보</p>
                        <p class="font-bold">0</p>
                    </button>
                </div>
                
            </div>
            <p class="mr-4 mt-11 text-right text-sm text-gray-300">
                익명님의 제보로 등록되었습니다.<br>
                등록일자: YY-MM-DD | HH:MM
            </p>
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