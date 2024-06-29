<template>
    <div class="m-4 p-2 rounded-xl shadow-lg">
        <div class="">
            <p class="text-right text-gray-400"><a @click="modify()">Modify</a> | <a @click="remove()">Delete</a></p>
            <img v-if="productData?.image_name" class="max-w-full mx-auto" :src="static_address+productData?.image_name">
            <img v-if="!productData?.image_name" class="w-full" src="@/assets/unavailable_image.png">
            <div class="ml-4">
                <div id="inline-block">
                <p class="inline mt-2 text-lg">{{ productData?.name }}</p>
                <p class="inline ml-1 text-sm text-gray-500">{{ productData?.category }}</p>
                </div>
                <div class="m-0 p-1 rounded-xl shadow-lg">
                    <p class="text-md text-black-500 text-center">Changes:</p>
                    <p v-for="desc in description" class="text-lg text-black-500 text-center">
                        {{ desc }}
                    </p>
                </div>
                <div class="text-center mt-4">
                    <p class="font-medium">Rate this information for the right information!</p>
                    <button @click="setPoint(true)" class="rounded-md bg-green-500 p-1 text-white mr-1 shadow-md hover:bg-green-600">
                        <img class="inline w-5" src="@/assets/verified.png">
                        <p class="inline ml-1.5">Correct Info</p>
                        <p class="font-bold">{{ productData?.positive_point }}</p>
                    </button>
                    <button @click="setPoint(false)" class="rounded-md bg-red-500 p-1 text-white m-1 shadow-md hover:bg-red-600">
                        <img class="inline w-5" src="@/assets/cancel.png">
                        <p class="inline ml-1.5">Wrong Info</p>
                        <p class="font-bold">{{ productData?.negative_point }}</p>
                    </button>
                </div>
                
            </div>
            <p class="mr-4 mt-11 text-right text-sm text-gray-300">
                Uploader : {{productData?.uploader}}<br>
                Uploaded date: {{ uploaded_date }}
            </p>
        </div>
    </div>
</template>

<script lang="ts">
import axios from 'axios';
import { product } from '../datatype';
import '../index.css'
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { changeDateFormat, validUserCase } from '@/function';
import { getNameFromToken, getUserIdFromToken, verifyUser } from '@/auth';

export default defineComponent({
    setup() {
        let productData = ref<product| null>(null);
        let description = ref<string[]>([]);
        let static_address = process.env.VUE_APP_STATIC_ADDRESS;
        let uploaded_date = ref('');

        onMounted(async ()=> {
            const response = await axios.get<product>(process.env.VUE_APP_BACKEND_ADDRESS+'/product/'+useRoute().query.id);
            productData.value = response.data;
            uploaded_date.value = changeDateFormat(productData.value.uploaded_date);
            for (let i = 0; i < productData.value.detail.length; i++) {
                if (productData.value.detail[i].before_value) { // 설명 배치
                    description.value.push(`${productData.value.detail[i].changed_point}
            ${productData.value.detail[i].before_value}${productData.value.detail[i].unit} 
            -> ${productData.value.detail[i].after_value}${productData.value.detail[i].unit}`);
                } else {
                    description.value.push(`${productData.value.detail[i].changed_point} is decreased.`)
                }
            }
        }) 

        const addPosValue = () => {
            productData.value!.positive_point++
        }

        const subPosValue = () => {
            productData.value!.positive_point--;
        }

        const addNegValue = () => {
            productData.value!.negative_point++;
        }

        const subNegValue = () => {
            productData.value!.negative_point--;
        }

        return { productData, description, static_address, uploaded_date, addPosValue, subPosValue, addNegValue, subNegValue };
    },
    methods: {
        async remove() {
            const token = this.$cookies.get('Token');
            let userStatus = await (async () => {
                const result = await verifyUser(token);
                return result;
            })();
            validUserCase(userStatus,
                () => { // 정상 유저
                    axios.get(process.env.VUE_APP_BACKEND_ADDRESS+'/product/'+this.productData?.product_id)
                        .then((res) => {
                            if (res.data.uploader == getNameFromToken(token)) { // 업로더가 맞다면 삭제 처리
                                axios.delete(process.env.VUE_APP_BACKEND_ADDRESS+'/product/'+this.productData?.product_id)
                                .then(() => { 
                                    alert("[Success] The product has successfully deleted!");
                                    this.$router.push('/finditem');
                                })
                                .catch(() => { alert("[Error] The error caused during deleting the product.") })
                            } else {
                                alert("[Error] Only uploader can delete this product.");
                            }
                        })
                        .catch(() => { alert("[Error] Failed to get data from server.") })
                },
                () => { //  만료 유저
                    alert("[Error] Login has expired. Please re-try after sign in.");
                }, 
                () => { // 비정상 유저
                    alert("[Error] Please try after sign in.");
                }
            )
        },
        async setPoint(rating: boolean) {
            const token = this.$cookies.get('Token');
            let userId = await getUserIdFromToken(token);
            let userStatus = await (async () => {
                const result = await verifyUser(token);
                return result;
            })();
            console.log(userId)
            validUserCase(userStatus,
            () => {
                axios.post(process.env.VUE_APP_BACKEND_ADDRESS+`/product/${this.productData?.product_id}/ratings`, {
                userId: userId,
                isPositive: rating})
                    .then((res) => {
                        alert("[Success] Your rate is submitted!");
                        switch (res.data) { // UX를 위한 프론트단에서 사전 업데이트 처리
                            case "pos to neg":
                                this.subPosValue();
                                this.addNegValue();
                                break;
                            case "neg to pos":
                                this.subNegValue();
                                this.addPosValue();
                                break;
                            case "neg":
                                this.addNegValue();
                                break;
                            case "pos":
                                this.addPosValue();
                                break;
                        }
                    })
                    .catch((e) => { alert("[Error] Error on submitting your rate."); console.log(e); })
            },
            () => { //  만료 유저
                alert("[Error] Login has expired. Please re-try after sign in.");
            }, 
            () => { // 비정상 유저
                    alert("[Error] Please try after sign in.");
            })
        },
        modify() {
            this.$router.push({path: '/fixitem', query: { id: this.$route.query.id }})
        },
    },
});
</script>