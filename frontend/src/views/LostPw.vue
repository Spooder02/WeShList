<template>
    <div class="w-4/5 bg-gray-50 rounded-md text-center m-auto py-4">
        <p class="font-bold">Find Password</p>
        <p class="text-md mt-2">Enter your ID.</p>
        <input v-model="id" type="text" class="w-4/5 mt-1 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="ID">
        <button @click="checkId()" class="w-3/4 p-1 mt-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">
            Find Password
        </button>
        <div class="bg-gray-500 w-full h-full left-0 top-0 opacity-25 z-0" :class="{fixed : isModalOpen, hidden : !isModalOpen}">
        </div>
        <div class="rounded-md bg-white w-4/5 z-1 inset-0 m-auto" :class="{fixed : isModalOpen, hidden : !isModalOpen, 'h-2/5': !onSuccess || isFinished, 'h-1/4': onSuccess && !isFinished}">
            <div v-if="onSuccess && !isFinished" class="">
                <p class="text-xl font-bold my-4">Reset password</p>
                <input v-model="password" type="password" class="w-4/5 mt-1 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="비밀번호">
                <input v-model="repeatPassword" type="password" class="w-4/5 mt-4 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="비밀번호 확인">
                <button @click="resetPassword" class="py-1 px-2 mt-4 bg-green-400 text-white rounded-md shadow-md">
                    Reset
                </button>
            </div>
            <div v-if="isFinished">
                <img class="px-16 py-5" src="@/assets/verified(1).png">
                <p class="text-xl font-bold">Your password successfully changed!</p>
                <p class="text-md mt-0.5">
                    Now ready to login.
                </p>
                <button @click="$router.push('/')" class="p-1 mx-auto my-2 bg-blue-500 text-white rounded-md shadow-md">Go to home</button>
                <button @click="$router.push('/login')" class="ml-2 p-1 mx-auto my-2 bg-green-500 text-white rounded-md shadow-md">Go back to the login page</button>
            </div>
            <div v-if="!onSuccess && !isFinished" class="">
                <img v-if="!onSuccess" class="px-16 py-5" src="@/assets/cancel(1).png">
                <p class="text-xl font-bold">Failed to find your ID!</p>
                <p class="text-md mt-0.5">
                    ID that you've entered is not found on server.
                </p>
                <button @click="$router.push('/')" class="p-1 mx-auto my-2 bg-blue-500 text-white rounded-md shadow-md">Go to home</button>
                <button @click="isModalOpen = false" class="ml-2 p-1 mx-auto my-2 bg-green-500 text-white rounded-md shadow-md">Retry</button>
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