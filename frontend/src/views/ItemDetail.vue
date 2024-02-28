<template>
    <div class="m-4 p-2 rounded-xl shadow-lg">
        <div class="">
            <p class="text-right text-gray-400"><a @click="modify()">수정하기</a> | <a @click="remove()">삭제하기</a></p>
            <img v-if="productData?.image_name" class="max-w-full" :src="backend_address+'/static/image/'+productData?.image_name">
            <img v-if="!productData?.image_name" class="w-full" src="@/assets/unavailable_image.png">
            <div class="ml-4">
                <div id="inline-block">
                <p class="inline mt-2 text-lg">{{ productData?.name }}</p>
                <p class="inline ml-1 text-sm text-gray-500">{{ productData?.category }}</p>
                </div>
                <div class="m-0 p-1 rounded-xl shadow-lg">
                    <p class="text-md text-black-500 text-center">변화:</p>
                    <p v-for="desc in description" class="text-lg text-black-500 text-center">
                        {{ desc }}
                    </p>
                </div>
                <div class="text-center mt-4">
                    <p class="font-medium">올바른 정보를 위해 평가해주세요!</p>
                    <button @click="setPoint(true)" class="rounded-md bg-green-500 p-1 text-white mr-1 shadow-md hover:bg-green-600">
                        <img class="inline w-5" src="@/assets/verified.png">
                        <p class="inline ml-1.5">올바른 정보</p>
                        <p class="font-bold">{{ productData?.positive_point }}</p>
                    </button>
                    <button @click="setPoint(false)" class="rounded-md bg-red-500 p-1 text-white m-1 shadow-md hover:bg-red-600">
                        <img class="inline w-5" src="@/assets/cancel.png">
                        <p class="inline ml-1.5">잘못된 정보</p>
                        <p class="font-bold">{{ productData?.negative_point }}</p>
                    </button>
                </div>
                
            </div>
            <p class="mr-4 mt-11 text-right text-sm text-gray-300">
                {{productData?.uploader}} 님의 제보로 등록되었습니다.<br>
                등록일자: {{ uploaded_date }}
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
        let backend_address = process.env.VUE_APP_BACKEND_ADDRESS;
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
                    description.value.push(`${productData.value.detail[i].changed_point}의 양이 감소.`)
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

        return { productData, description, backend_address, uploaded_date, addPosValue, subPosValue, addNegValue, subNegValue };
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
                                    alert("정상적으로 삭제되었습니다!");
                                    this.$router.push('/finditem');
                                })
                                .catch(() => { alert("[에러] 삭제하는 도중 문제가 발생했습니다.") })
                            } else {
                                alert("[에러] 본인이 게시한 상품만 삭제할 수 있습니다.");
                            }
                        })
                        .catch(() => { alert("[에러] 상품 정보를 가져오는 데 실패했습니다.") })
                },
                () => { //  만료 유저
                    alert("[에러] 로그인이 만료되었습니다. 다시 로그인 후 시도해주세요.");
                }, 
                () => { // 비정상 유저
                    alert("[에러] 로그인 후 이용해주세요.");
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
                        alert("평점이 반영됐습니다!");
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
                    .catch((e) => { alert("[에러] 평점 설정에 문제가 있습니다"); console.log(e); })
            },
            () => { //  만료 유저
                alert("[에러] 로그인이 만료되었습니다. 다시 로그인 후 시도해주세요.");
            }, 
            () => { // 비정상 유저
                    alert("[에러] 로그인 후 이용해주세요.");
            })
        },
        modify() {
            this.$router.push({path: '/fixitem', query: { id: this.$route.query.id }})
        },
    },
});
</script>