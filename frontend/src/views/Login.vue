<template>
    <div class="w-4/5 p-4 m-auto text-center rounded-md bg-gray-50">
        <p class="font-bold text-xl">로그인</p>
        <div class="grid gap-y-4 mt-2">
            <input type="text" class="border-b border-gray focus:border-blue-400" placeholder="아이디 / ID">
            <input type="password" class="border-b border-gray focus:border-blue-400" placeholder="비밀번호 / PW">
            <button class="w-3/4 p-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">로그인하기</button>
            <a @click="$router.push('/signin')" class="underline text-sm">아직 계정이 없으시다면? 회원가입</a>
            <a @click="$router.push('/findaccount')" class="text-sm text-gray-400 text-right">계정을 잊어버렸어요</a>
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