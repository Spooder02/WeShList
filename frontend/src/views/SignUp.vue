<template>
    <div class="w-4/5 p-4 m-auto text-center rounded-md bg-gray-50">
        <p class="font-bold text-xl">Sign up</p>
        <div class="grid gap-y-4 mt-2">
            <input v-model="signupData.id" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="ID">
            <input v-model="signupData.email" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="E-mail">
            <input v-model="signupData.password" type="password" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="PW">
            <input v-model="signupData.repeat_password" type="password" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="PW Repeat">
            <input v-model="signupData.name" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="Name">
            <input v-model="signupData.nickname" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="Nickname">
            <div class="relative w-full-w p-0 m-0">
                <input v-model="signupData.tel" @input="formatPhoneNumber" type="tel" class="w-full border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="Phone Number" :disabled="isVerified">
                <button @click="verifyTel()" class="absolute h-full right-0 w-16 px-1 py-1 bg-green-400 text-white text-sm font-medium rounded-md">Verify</button>
            </div>
            <span @click="toggleModal()" class="text-sm"><input v-model="isAgree" type="checkbox" @click="isModalOpen != isModalOpen"> [Essential] Agree on your Sign up</span>
            <button @click="signup()" class="w-3/4 p-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">Sign up</button>
        </div>
        
        <div id="modal-bg" @click="toggleModal()" class="bg-gray-500 opacity-25 left-0 top-0 h-full w-full z-0" :class="{hidden: !isModalOpen, fixed: isModalOpen}"> 
        </div>
        <div id="modal" class="inset-0 m-auto w-11/12 h-[60%] bg-white z-1" :class="{hidden: !isModalOpen, fixed: isModalOpen}">
            <p class="font-bold m-4">Consent to collect and use personal information</p>
            <p class="">We collect and use personal information as follows for service membership registration and service quality management.</p>
            <table class="table-auto my-4 border-2">
                <tbody>
                    <tr class="font-semibold">
                        <td>Purpose</td>
                        <td>Item</td>
                        <td>Basis</td>
                    </tr>
                    <tr>
                        <td>Identifying members and providing membership services</td>
                        <td>ID, password, phone number, name</td>
                        <td>Article 15 (1) of the Personal Information Protection Act in South Korea</td>
                    </tr>
                </tbody>
            </table>
            <p>You may disagree with the minimum collection and use of personal information required to use the service of the wish list, but if you refuse to consent, you cannot use the membership service.</p>
            <p class="text-gray-400 my-4">Click outside to close this modal.</p>
        </div>
    </div>
</template>

<script lang="ts"> // TODO: 회원가입 휴대폰 인증 구현, 프론트/백 단에서 회원가입 최소 가입 조건문, 에러 메시지 백엔드 -> 프론트 출력 구현
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
            const regex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');
            if (data.id &&
            data.email &&
            data.password &&
            data.repeat_password &&
            data.name &&
            data.nickname &&
            data.tel) { // 입력 데이터 무결성 검사
                let i = 1;
                switch (i) {
                    case 1:
                        if (data.id.length >= 8) i++;
                        else {
                            alert("ID must be longer than 8 letters!");
                            break;
                        }
                        if (regex.test(data.email)) i++;
                        else {
                            alert("Invalid Email Expression.");
                            break;
                        }
                        if (data.password === data.repeat_password) i++;
                        else {
                            alert("Repeat Password doesn't match. Try again.");
                            break;
                        }
                        if (data.password.length >= 8) i++;
                        else {
                            alert("Password must be longer than 8 letters!");
                            break;
                        }
                        case 5:
                            axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth", {
                                "id": data.id,
                                "email": data.email,
                                "password": data.password,
                                "name": data.name,
                                "nickname": data.nickname,
                                "tel": data.tel
                            })
                            .then(res => {
                                alert("Sign up has successfully completed!");
                                this.$router.push("/")
                            })
                            .catch(res => {
                                alert("Error on signing up. Check your form!");
                    })
                        
                }
                
            }
        }
    }
});
</script>