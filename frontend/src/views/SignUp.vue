<template>
    <div class="w-4/5 p-4 m-auto text-center rounded-md bg-gray-50">
        <p class="font-bold text-xl">회원가입</p>
        <div class="grid gap-y-4 mt-2">
            <input v-model="signupData.id" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="아이디 / ID">
            <input v-model="signupData.email" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="이메일 / E-mail">
            <input v-model="signupData.password" type="password" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="비밀번호 / PW">
            <input v-model="signupData.repeat_password" type="password" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="비밀번호 확인 / PW Repeat">
            <input v-model="signupData.name" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="이름">
            <input v-model="signupData.nickname" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="닉네임">
            <div class="relative w-full-w p-0 m-0">
                <input v-model="signupData.tel" @input="formatPhoneNumber" type="tel" class="w-full border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="전화번호" :disabled="isVerified">
                <button @click="verifyTel()" class="absolute h-full right-0 w-16 px-1 py-1 bg-green-400 text-white text-sm font-medium rounded-md">인증</button>
            </div>
            <span @click="toggleModal()" class="text-sm"><input v-model="isAgree" type="checkbox" @click="isModalOpen != isModalOpen"> [필수] 개인정보 수집 동의</span>
            <button @click="signup()" class="w-3/4 p-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">회원가입하기</button>
        </div>
        
        <div id="modal-bg" @click="toggleModal()" class="bg-gray-500 opacity-25 left-0 top-0 h-full w-full z-0" :class="{hidden: !isModalOpen, fixed: isModalOpen}"> 
        </div>
        <div id="modal" class="inset-0 m-auto w-11/12 h-1/2 bg-white z-1" :class="{hidden: !isModalOpen, fixed: isModalOpen}">
            <p class="font-bold m-4">개인정보 수집 및 이용 동의</p>
            <p class="">위시리스트는 서비스 회원가입, 서비스 품질 관리 등을 위해 아래와 같이 개인정보를 수집, 이용합니다.</p>
            <table class="table-auto my-4 border-2">
                <tbody>
                    <tr class="font-semibold">
                        <td>수집 목적</td>
                        <td>수집 항목</td>
                        <td>수집 근거</td>
                    </tr>
                    <tr>
                        <td>회원 식별 및 회원제 서비스 제공</td>
                        <td>아이디, 비밀번호, 전화번호, 이름</td>
                        <td>개인정보 보호법 제15조 제1항</td>
                    </tr>
                </tbody>
            </table>
            <p>귀하는 위시리스트의 서비스 이용에 필요한 최소한의 개인정보 수집, 이용에 동의하지 않을 수 있으나, 동의를 거부할 경우 회원제 서비스 이용이 불가합니다.</p>
            <p class="text-gray-400 my-4">창 바깥 영역 터치 시, 창이 닫힙니다.</p>
        </div>
    </div>
</template>

<script lang="ts">
import '../index.css'
import { signupData } from '../datatype';
import { defineComponent } from 'vue';
import axios from 'axios';

export default defineComponent({
    name: '',
    components: {
        
    },
    data() {
        return {
            isModalOpen: false,
            isVerified: false,
            isAgree: false,
            signupData: {} as signupData
        }
    },
    methods: {
        toggleModal() {
            setTimeout(() => {
                this.isModalOpen = !this.isModalOpen;
            }, 100);
        },
        verifyTel() {
            if (this.signupData.tel === undefined) alert("전화번호를 입력하세요!")
            else if (/^[0]{1}[1]{1}[0]{1}-[0-9]{4}-[0-9]{4}$/.test(this.signupData.tel.toString())) {
                alert("확인되었습니다!");
                this.isVerified = true;
            } else { alert("올바른 전화번호를 입력하세요!"); }
        },
        formatPhoneNumber() {
            const phoneNumber = this.signupData.tel.replace(/\D/g, "");
            if (phoneNumber.length >= 4) {
                this.signupData.tel = `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3, 7)}-${phoneNumber.slice(7,11)}`;
            } else if (phoneNumber.length >= 3) {
                this.signupData.tel = `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3)}`;
            } else {
                this.signupData.tel = phoneNumber;
            }
        },
        signup() {
            const data = this.signupData;
            if (data.password != data.repeat_password) {
                alert("입력된 비밀번호가 서로 다릅니다");
            } else if (data.id &&
            data.email &&
            data.password &&
            data.repeat_password &&
            data.name &&
            data.nickname &&
            data.tel) { // 입력 데이터 무결성 검사
                axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth", {
                    "id": data.id,
                    "email": data.email,
                    "password": data.password,
                    "name": data.name,
                    "nickname": data.nickname,
                    "tel": data.tel
                })
                .then(res => {
                    alert("회원가입 성공");
                    console.log(res);
                    this.$router.push("/")
                })
                .catch(res => {
                    alert("에러 발생");
                    console.log(res);
                })
            }
        }
    }
});
</script>