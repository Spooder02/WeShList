<template>
    <div class="w-4/5 p-4 m-auto text-center rounded-md bg-gray-50">
        <p class="font-bold text-xl">로그인</p>
        <div class="grid gap-y-4 mt-2">
            <input v-model="loginData.id" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="아이디 / ID">
            <input v-model="loginData.password" type="password" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="비밀번호 / PW">
            <button @click="login()" class="w-3/4 p-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">로그인하기</button>
            <a @click="$router.push('/signup')" class="underline text-sm">아직 계정이 없으시다면? 회원가입</a>
            <a @click="$router.push('/findaccount')" class="text-sm text-gray-400 text-right">계정을 잊어버렸어요</a>
        </div>
    </div>
</template>

<script lang="ts">
import '../index.css'
import { loginData } from '@/datatype';
import axios from 'axios';
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        
    },
    data() {
        return {
            loginData: {} as loginData
        }
    },
    methods: {
        login() {
            if (this.loginData.id != undefined && this.loginData.password != undefined) {
                axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth/login", {
                    "id": this.loginData.id,
                    "password": this.loginData.password
                })
                .then(res => {
                    this.$cookies.set("Token", res.headers.authorization);
                    this.$router.push('/');
                    this.$nextTick(async () => {
                        await new Promise((resolve) => setTimeout(resolve, 0));
                        window.location.reload();
                    });
                })
                .catch(e => { alert("로그인에 실패했습니다!") })
            }
        }
    }
});
</script>