<template>
    <div class="w-4/5 bg-gray-50 m-auto p-2 shadow-lg text-center grid place-content-center">
        <p class="font-semibold">상품 수정하기</p>
        <label v-if="image_url === ''" for="file" class="p-8 m-auto border font-medium rounded-lg mt-4 mb-4">
        <span class="font-black">+</span>
            이미지 등록
        </label>
        <img v-if="image_url != ''" class="w-64 p-8 m-auto border rounded-lg mt-4 mb-4" :src="image_url">
        <button v-if="image_url" @click="resetImage()" class="w-1/2 mb-2 p-0.5 m-auto rounded-lg bg-red-400 text-white font-medium shadow-xl">이미지 초기화</button>
        <input @change="uploadImage($event)" id="file" type="file" class="hidden" accept="image/*"/>
        <input v-model="name" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="상품명">
        <input v-model="brand" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="브랜드">
        <input v-model="price" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="가격">
        <select v-model="category" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" name="unit">
            <option :value="null" selected disabled>-- 카테고리 --</option>
            <option v-for="j in categories.length" :value="categories[j-1]">{{ categories[j-1] }}</option>
        </select>
        <div v-for="i in changes" class="border p-1 rounded-lg mb-2" :key="i">
            <p class="text-base text-gray-700 font-semibold flex justify-between mb-2">
                상품 변화
            <button @click="addChanges(i)" class="p-0.5 pl-1 pr-1 rounded-lg bg-green-400 text-white font-medium shadow-xl"><span class="font-black">+</span> 변화 추가</button>
            </p>
            <input @change="setValue(i, 1, $event.target!.value)" :value="detail[i-1]?.changed_point" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5 mb-3" placeholder="변화 항목">
            <input @change="setValue(i, 2, $event.target!.value)" :value="detail[i-1]?.before_value" type="number" class="border rounded-lg border-gray-300 focus:border-blue-300 text-center p-0.5" placeholder="기존 용량(숫자 단위)" :disabled="unknown[i-1]">
            <select @input="setValue(i, 3, $event.target!.value)" v-model="unit[i-1]" class="border rounded-lg border-gray-300 focus:border-blue-300 text-center p-0.5" :key="i" :disabled="unknown[i-1]">
                <option v-for="j in default_unit.length" :value="default_unit[j-1]">{{ default_unit[j-1] }}</option>
            </select>
            <p>↓</p>
            <input @change="setValue(i, 4, $event.target!.value)" :value="detail[i-1]?.after_value" type="number" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="변화 용량(숫자 단위)" :disabled="unknown[i-1]">
            <select v-model="unit[i-1]" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" name="unit" disabled>
                <option v-for="j in default_unit.length" :value="default_unit[j-1]">{{ default_unit[j-1] }}</option>
            </select>
            <label :for="'checkbox' + (i - 1)" class="block text-right mr-2 mb-0.5"><input type="checkbox" :id="'checkbox' + (i - 1)" class="mr-1" v-model="unknown[i-1]">변경 양을 몰라요</label>
        </div>
        <button @click="addItem();" class="p-2 mt-2 mb-2 bg-blue-400 w-1/2 m-auto rounded-lg text-white font-semibold shadow-xl">+ 상품 수정하기</button>
    </div>
</template>

<script lang="ts">
import axios from 'axios';
import '../index.css'
import { changed_value, product, productDetail } from '../datatype'
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import appendOrReplaceFormData from '@/function';

export default defineComponent({
    name: '',
    components: {
        
    },
    data() {
        return {
            changes: 1,
            default_unit: ['µg', 'mg', 'g', 'kg', 'mL', 'L', 'cc', '%'],
            categories: ['과일', '견과/건과', '채소', '쌀/잡곡', '축산/계란', '수산물/건어물', '생수/음료',
        '커피/원두/차', '과자/초콜릿/시리얼', '면/통조림/가공식품', '가루/조미료/오일', '장/소스/드레싱', '유제품/아이스크림',
    '냉장/냉동/간편식품', '건강식품', '분유/어린이식품'],
            unit: ['g'],
            detail: [] as Array<changed_value>,
            formData: new FormData(),
            name: '',
            brand: '',
            price: 0,
            category: '',
            image_url: '',
            unknown: [false,]
        }
    },
    methods: {
        setValue(n:number, i:number, value:string) {
            switch (i) {
                case 1:
                    this.detail[n-1].changed_point = value;
                    break;
                case 2:
                    if (isNaN(Number(value)))
                        alert("숫자만 입력해주세요!");
                    else {
                        this.detail[n-1].before_value = Number(value);
                    }
                    break;
                case 3:
                    this.detail[n-1].unit = value;
                    break;
                case 4:
                    if (isNaN(Number(value)))
                        alert("숫자만 입력해주세요!");
                    else
                        this.detail[n-1].after_value = Number(value);
            }
        },
        addChanges(i:number) {
            if ((this.detail[i-1].changed_point != '' &&
                this.detail[i-1]?.before_value &&
                this.detail[i-1]?.after_value) || 
                (this.detail[i-1].changed_point != '' && this.detail[i-1].unknown)) { // 값 무결성 체크
            this.changes+=1;
            this.unit[i]='g';
            this.detail.push({changed_point: '', before_value: null, unit: 'g', after_value: null, unknown: false});
            } else {
                alert("값을 모두 입력 후 추가해주세요!")
            }
        },
        uploadImage(event: { target: HTMLInputElement }) {
            if (this.formData.has("imageFile")) this.formData.set("imageFile", event.target.files![0]);
            else this.formData.append("imageFile", event.target.files![0]); // 있다면 교체, 없다면 추가
            this.image_url = URL.createObjectURL(event.target.files![0]);
        },
        addItem() {
            if (this.name != null &&
            this.brand != null &&
            this.price != null &&
            this.category != null) {
                let i;
                
                for (i = 0; i < this.changes; i++) { // 디테일값 무결성 체크 + TODO: changed_point 프론트 차원 중복 방지 로직 작성
                    if (this.detail[i].changed_point != '' &&
                        this.detail[i].before_value != 0 &&
                        this.detail[i].after_value != 0) continue;
                    else if (this.detail[i].changed_point != '' && this.detail[i].unknown) // 모르는 경우도 OK
                        continue;
                    else break;
                }
                if (i == this.changes) { // 체크 이후 폼 데이터 작성
                    for (i = 0; i < this.changes; i++) {
                        if (this.unknown[i]) {
                            appendOrReplaceFormData(this.formData, `detail[${i}].changed_point`, this.detail[i].changed_point);
                            appendOrReplaceFormData(this.formData, `detail[${i}].unknown`, JSON.stringify(true))
                        } else {
                            appendOrReplaceFormData(this.formData, `detail[${i}].changed_point`, this.detail[i].changed_point);
                            appendOrReplaceFormData(this.formData, `detail[${i}].before_value`, this.detail[i].before_value?.toString() ?? '')
                            appendOrReplaceFormData(this.formData, `detail[${i}].after_value`, this.detail[i].after_value?.toString() ?? '')
                            appendOrReplaceFormData(this.formData, `detail[${i}].unit`, this.detail[i].unit ?? '')
                        }
                    }
                    appendOrReplaceFormData(this.formData, 'name', this.name);
                    appendOrReplaceFormData(this.formData, 'price', this.price.toString());
                    appendOrReplaceFormData(this.formData, 'brand', this.brand);
                    appendOrReplaceFormData(this.formData, 'category', this.category);
                    if (this.formData.has("imageFile")) {
                        axios.put(process.env.VUE_APP_BACKEND_ADDRESS+"/product/"+this.$route.query.id, this.formData, { headers: {
                            'Content-Type': 'multipart/form-data'
                        }})
                        .then(() => {
                            alert("상품 수정이 완료되었습니다!");
                            this.$router.push("/finditem");
                        })
                        .catch(() => {
                            alert("[에러] 상품 수정에 실패했습니다!");
                        })
                    } else { // 기존 이미지 사용
                        this.formData.append("image_name", this.image_url.split('/')[5])
                        axios.put(process.env.VUE_APP_BACKEND_ADDRESS+"/product/"+this.$route.query.id, this.formData, { headers: {
                            'Content-Type': 'multipart/form-data'
                        }})
                        .then(() => {
                            alert("상품 수정이 완료되었습니다!");
                            this.$router.push("/finditem");
                        })
                        .catch(() => {
                            alert("[에러] 상품 수정에 실패했습니다!");
                        })
                    }
                        
                } else {
                    alert("[에러] 상품 변화 값을 모두 입력해주세요!");
                }
            } else {
                alert("[에러] 상품 정보를 모두 입력해주세요!");
            }
        },
        resetImage() {
            this.image_url = '';
        }
    },
    async created() {
        let response: product = (await axios.get<product>('http://localhost:8081/product/'+useRoute().query.id)).data;
        this.image_url = response.image_name? process.env.VUE_APP_BACKEND_ADDRESS+"/static/image/"+response.image_name: '';
        this.name = response.name;
        this.brand = response.brand;
        this.price = Number(response.price);
        this.category = response.category;
        this.changes = response.detail.length;
        for (let i = 0; i < response.detail.length; i++) {
            this.detail.push({
                changed_point: response.detail[i].changed_point, 
                before_value: response.detail[i].before_value,
                unit: response.detail[i].unit,
                after_value: response.detail[i].after_value,
                unknown: response.detail[i].unknown
            })
            this.unknown[i] = response.detail[i].unknown;
            this.unit[i] = response.detail[i].unit ?? '';
        }
    }
});
</script>