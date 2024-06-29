<template>
    <div class="w-4/5 bg-gray-50 rounded-md text-center m-auto py-4">
        <p class="font-bold">Find account</p>
        <p class="text-md mt-2">Please enter your phone number.</p>
        <input @input="formatPhoneNumber" v-model="tel" type="tel" class="w-4/5 mt-1 border rounded-lg border-gray-300 focus:border-blue-300 pl-2 p-0.5" placeholder="Phone Number">
        <button @click="findId()" class="w-3/4 p-1 mt-2 bg-blue-500 text-white font-semibold rounded-md content-center justify-self-center">Find ID</button>
        <div class="bg-gray-500 w-full h-full left-0 top-0 opacity-25 z-0" :class="{fixed : isModalOpen, hidden : !isModalOpen}">
        </div>
        <div class="rounded-md bg-white w-4/5 h-2/5 z-1 inset-0 m-auto" :class="{fixed : isModalOpen, hidden : !isModalOpen}">
            <div v-if="onSuccess" class="">
                <img class="px-16 py-5" src="@/assets/verified(1).png">
                <p class="text-xl font-bold">Your account is found!</p>
                <p class="text-md mt-0.5">
                    Your ID is <span class="font-semibold">{{ id }}</span>
                </p>
                <button @click="$router.push('/')" class="p-1 mx-auto my-2 bg-blue-500 text-white rounded-md shadow-md">Go to home</button>
                <button @click="$router.push('/login')" class="ml-2 p-1 mx-auto my-2 bg-green-500 text-white rounded-md shadow-md">Go back to login page</button>
            </div>
            <div v-if="!onSuccess" class="">
                <img v-if="!onSuccess" class="px-16 py-5" src="@/assets/cancel(1).png">
                <p class="text-xl font-bold">Failed to find account!</p>
                <p class="text-md mt-0.5">
                    There's no account which has your phone number.
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