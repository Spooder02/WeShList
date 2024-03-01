<template>
    <div class="w-4/5 bg-gray-50 rounded-md text-center m-auto py-4">
        <p class="font-bold">아이디 찾기</p>
        <p class="text-md mt-2">가입할 때 인증했던 핸드폰 번호를 입력해주세요.</p>
        <input @input="formatPhoneNumber" v-model="tel" type="tel" class="w-4/5 mt-1 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="전화번호">
        <button @click="findId()" class="w-3/4 p-1 mt-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">아이디 찾기</button>
        <div class="bg-gray-500 w-full h-full left-0 top-0 opacity-25 z-0" :class="{fixed : isModalOpen, hidden : !isModalOpen}">
        </div>
        <div class="rounded-md bg-white w-4/5 h-2/5 z-1 inset-0 m-auto" :class="{fixed : isModalOpen, hidden : !isModalOpen}">
            <div v-if="onSuccess" class="">
                <img class="px-16 py-5" src="@/assets/verified(1).png">
                <p class="text-xl font-bold">아이디 찾기에 성공하였습니다!</p>
                <p class="text-md mt-0.5">
                    회원님의 아이디는 <span class="font-semibold">{{ id }}</span> 입니다.
                </p>
                <button @click="$router.push('/')" class="p-1 mx-auto my-2 bg-blue-500 text-white rounded-md shadow-md">홈으로 이동</button>
                <button @click="$router.push('/login')" class="ml-2 p-1 mx-auto my-2 bg-green-500 text-white rounded-md shadow-md">로그인으로 이동</button>
            </div>
            <div v-if="!onSuccess" class="">
                <img v-if="!onSuccess" class="px-16 py-5" src="@/assets/cancel(1).png">
                <p class="text-xl font-bold">아이디 찾기에 실패하였습니다!</p>
                <p class="text-md mt-0.5">
                    회원가입 이력이 없는 번호입니다.
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
    name: 'LostId',
    components: {
        
    },
    data() {
        return {
            tel: '',
            isModalOpen: false,
            onSuccess: false,
            id: "testid"
        }
    },
    methods: {
        formatPhoneNumber() {
            const phoneNumber = this.tel.replace(/\D/g, "");
            if (phoneNumber.length >= 4) {
                this.tel = `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3, 7)}-${phoneNumber.slice(7,11)}`;
            } else if (phoneNumber.length >= 3) {
                this.tel = `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3)}`;
            } else {
                this.tel = phoneNumber;
            }
        },
        findId() {
            axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth/findId", {
                tel: this.tel
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
        }
    }
});
</script>