<template>
    <div class="w-4/5 bg-gray-50 rounded-md text-center m-auto py-4">
        <p class="font-bold">비밀번호 찾기</p>
        <p class="text-md mt-2">가입시 아이디를 입력해주세요.</p>
        <input v-model="id" type="text" class="w-4/5 mt-1 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="아이디 / ID">
        <button @click="checkId()" class="w-3/4 p-1 mt-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">
            비밀번호 찾기
        </button>
        <div class="bg-gray-500 w-full h-full left-0 top-0 opacity-25 z-0" :class="{fixed : isModalOpen, hidden : !isModalOpen}">
        </div>
        <div class="rounded-md bg-white w-4/5 z-1 inset-0 m-auto" :class="{fixed : isModalOpen, hidden : !isModalOpen, 'h-2/5': !onSuccess || isFinished, 'h-1/4': onSuccess && !isFinished}">
            <div v-if="onSuccess && !isFinished" class="">
                <p class="text-xl font-bold my-4">비밀번호 재설정</p>
                <input v-model="password" type="password" class="w-4/5 mt-1 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="비밀번호">
                <input v-model="repeatPassword" type="password" class="w-4/5 mt-4 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="비밀번호 확인">
                <button @click="resetPassword" class="py-1 px-2 mt-4 bg-green-400 text-white rounded-md shadow-md">
                    비밀번호 재설정
                </button>
            </div>
            <div v-if="isFinished">
                <img class="px-16 py-5" src="@/assets/verified(1).png">
                <p class="text-xl font-bold">비밀번호 재설정이 완료되었습니다!</p>
                <p class="text-md mt-0.5">
                    이제 이동하여 로그인을 완료하세요
                </p>
                <button @click="$router.push('/')" class="p-1 mx-auto my-2 bg-blue-500 text-white rounded-md shadow-md">홈으로 이동</button>
                <button @click="$router.push('/login')" class="ml-2 p-1 mx-auto my-2 bg-green-500 text-white rounded-md shadow-md">로그인으로 이동</button>
            </div>
            <div v-if="!onSuccess && !isFinished" class="">
                <img v-if="!onSuccess" class="px-16 py-5" src="@/assets/cancel(1).png">
                <p class="text-xl font-bold">아이디 찾기에 실패하였습니다!</p>
                <p class="text-md mt-0.5">
                    회원가입이 되지 않은 아이디입니다.
                </p>
                <button @click="$router.push('/')" class="p-1 mx-auto my-2 bg-blue-500 text-white rounded-md shadow-md">홈으로 이동</button>
                <button @click="isModalOpen = false" class="ml-2 p-1 mx-auto my-2 bg-green-500 text-white rounded-md shadow-md">다시 시도</button>
            </div>
        </div>
    </div>
</template>

<script lang="ts"> 
import axios from 'axios';
import '../index.css'
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'LostPw',
    components: {
        
    },
    data() {
        return {
            isModalOpen: false,
            onSuccess: false,
            id: "",
            password: "",
            repeatPassword: "",
            isFinished: false
        }
    },
    methods: {
        checkId() {
            axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth/checkId", {
                id: this.id
            })
            .then((res) => {
                this.onSuccess = true;
                this.isModalOpen = true;
                this.id = res.data;
            })
            .catch((e) => {
                this.onSuccess = false;
                this.isModalOpen = true;
            })
        },
        resetPassword() {
            if ((this.password === this.repeatPassword) &&
                this.password.length >= 8) {
                axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth/resetPassword", {
                    id: this.id,
                    password: this.password
                })
                .then((res) => {
                    console.log(res);
                    this.isFinished = true;
                })
                .catch((e) => {
                    console.log(e);
                })
            } else {
                alert("[에러] 비밀번호를 최소 8자리로 설정하세요!")
            }
        }
    }
});
</script>