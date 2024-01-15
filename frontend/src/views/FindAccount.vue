<template>
    <div class="w-4/5 bg-gray-50 rounded-md text-center m-auto py-4">
        <p class="font-bold">어느 부분을 도와드릴까요?</p>
        <button class="p-2 bg-white shadow-lg rounded-lg m-2 font-medium">
            <img src="@/assets/user(1).png" class="w-16 m-auto">
            아이디 분실
        </button>
        <button class="p-2 bg-white shadow-lg rounded-lg font-medium">
            <img src="@/assets/key(1).png" class="w-16 m-auto">
            비밀번호 분실
        </button>
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