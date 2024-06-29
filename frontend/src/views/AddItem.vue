<template>
    <div class="w-4/5 bg-gray-50 m-auto p-2 shadow-lg text-center grid place-content-center">
        <p class="font-semibold">Report Product</p>
        <label v-if="!image_url" for="file" class="p-8 m-auto border font-medium rounded-lg mt-4 mb-4">
        <span class="font-black">+</span>
            Add Image
        </label>
        <img v-if="image_url" class="w-64 p-8 m-auto border rounded-lg mt-4 mb-4" :src="image_url">
        <button v-if="image_url" @click="resetImage()" class="w-1/2 mb-2 p-0.5 m-auto rounded-lg bg-red-400 text-white font-medium shadow-xl">Reset Image</button>
        <input @change="uploadImage($event)" id="file" type="file" class="hidden" accept="image/*"/>
        <input v-model="name" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="Product Name">
        <input v-model="brand" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="Brand">
        <input v-model="price" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="Price">
        <select v-model="category" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" name="unit">
            <option :value="''" selected disabled>-- Category --</option>
            <option v-for="j in categories.length" :value="categories[j-1]">{{ categories[j-1] }}</option>
        </select>
        <div v-for="i in input.length" class="border p-1 rounded-lg mb-2" :key="i">
            <p class="text-base text-gray-700 font-semibold flex justify-between mb-2 p-1">
                Details
            <div>
                <button @click="removeChanges(i)" class="p-0.5 pl-1 pr-1 mr-2 rounded-lg bg-red-400 text-white font-medium shadow-xl"><span class="font-black ml-0.5">-</span> Change</button>
                <button @click="addChanges(i)" class="p-0.5 pl-1 pr-1 rounded-lg bg-green-400 text-white font-medium shadow-xl"><span class="font-black ml-0.5">+</span> Change</button>
            </div>
            </p>
            <input v-model="input[i-1].changed_point" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5 mb-3" placeholder="Changed Thing">
            <input v-model="input[i-1].before_value" type="number" class="border rounded-lg border-gray-300 focus:border-blue-300 text-center p-0.5" placeholder="Previous Capacity(Number)" :disabled="unknown[i-1]">
            <select @input="setValue(i, 3, $event.target!.value)" v-model="unit[i-1]" class="border rounded-lg border-gray-300 focus:border-blue-300 text-center p-0.5" :key="i" :disabled="unknown[i-1]">
                <option v-for="j in default_unit.length" :value="default_unit[j-1]">{{ default_unit[j-1] }}</option>
            </select>
            <p>↓</p>
            <input v-model="input[i-1].after_value" type="number" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="Current Capacity(Number)" :disabled="unknown[i-1]">
            <select v-model="unit[i-1]" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" name="unit" disabled>
                <option v-for="j in default_unit.length" :value="default_unit[j-1]">{{ default_unit[j-1] }}</option>
            </select>
            <label class="block text-right mr-2 mb-0.5"><input type="checkbox" class="mr-1" v-model="unknown[i-1]">I don't know the details</label>
        </div>
        <button @click="addItem();" class="p-2 mt-2 mb-2 bg-blue-400 w-1/2 m-auto rounded-lg text-white font-semibold shadow-xl">+ Add Item</button>
    </div>
</template>

<script lang="ts">
import axios from 'axios';
import '../index.css'
import { changed_value } from '../datatype'
import { defineComponent, reactive } from 'vue';
import appendOrReplaceFormData from '@/function';
import { getNameFromToken } from '@/auth';
import { ErrorTypes } from 'vue-router';

export default defineComponent({
    name: 'AddItem  ',
    components: {
        
    },
    data() {
        return {
            changes: 1,
            default_unit: ['µg', 'mg', 'g', 'kg', 'mL', 'L', 'cc', '%'],
            categories: ['Fruits', 'Nuts', 'Vegetables', 'Grains', 'Meats', 'Fish', 'Still Water/Drinks',
        'Crackers/Cereals', 'Chocolates', 'Canned Food', 'Oils/Seasoning', 'Sauces', 'Milk/Ice-cream',
    'Frozen Foods', 'Health Foods', 'Kids'],
            unit: ['g'],
            input: reactive([]) as Array<changed_value>,
            formData: new FormData(),
            name: '',
            brand: '',
            price: '',
            category: '',
            image_url: '',
            unknown: [ false ]
        }
    },
    methods: {
        setValue(n:number, i:number, value:string) {
            switch (i) {
                case 1:
                    this.input[n-1].changed_point = value;
                    break;
                case 2:
                    if (isNaN(Number(value)))
                        alert("Please enter the number.");
                    else {
                        this.input[n-1].before_value = Number(value);
                    }
                    break;
                case 3:
                    this.input[n-1].unit = value;
                    break;
                case 4:
                    if (isNaN(Number(value)))
                        alert("Please enter the number.");
                    else
                        this.input[n-1].after_value = Number(value);
            }
        },
        addChanges(i:number) {
            if ((this.input[i-1].changed_point != '' &&
                this.input[i-1].before_value != 0 &&
                this.input[i-1].after_value != 0) || 
                (this.input[i-1].changed_point != '' && this.unknown[i])) { // 값 무결성 체크
            this.changes++;
            this.unit[i]='g';
            this.input.push({changed_point: '', before_value: null, unit: 'g', after_value: null, unknown: false});
            } else {
                alert("[Error] Add more after fill out these information!")
            }
        },
        removeChanges(i:number) {
            if (this.input.length <= 1) {
                alert("[Error] You must enter the changes more than one!");
            } else {
                this.input.splice(i-1, 1);
                this.changes--;
                this.unit.splice(i-1, 1);
            }
        },
        uploadImage(event: { target: HTMLInputElement }) {
            if (this.formData.has("imageFile")) this.formData.set("imageFile", event.target.files![0]);
            else this.formData.append("imageFile", event.target.files![0]); // 있다면 교체, 없다면 추가
            this.image_url = URL.createObjectURL(event.target.files![0]);
        },
        addItem() {
            if (this.name !== '' &&
            this.brand !== '' &&
            this.price !== '' &&
            this.category !== '') {
                let i;
                
                for (i = 0; i < this.changes; i++) { // 디테일값 무결성 체크
                    if (this.input[i].changed_point != '' &&
                        this.input[i].before_value != 0 &&
                        this.input[i].after_value != 0) continue;
                    else if (this.input[i].changed_point != '' && this.unknown[i]) // 모르는 경우도 OK
                        continue;
                    else break;
                }
                if (i == this.changes) { // 체크 이후 폼 데이터 작성
                    for (i = 0; i < this.changes; i++) {
                        if (this.unknown[i]) {
                            appendOrReplaceFormData(this.formData, `detail[${i}].changed_point`, this.input[i].changed_point);
                            appendOrReplaceFormData(this.formData, `detail[${i}].unknown`, JSON.stringify(true))
                        } else {
                            appendOrReplaceFormData(this.formData, `detail[${i}].changed_point`, this.input[i].changed_point);
                            appendOrReplaceFormData(this.formData, `detail[${i}].before_value`, this.input[i].before_value?.toString() ?? '')
                            appendOrReplaceFormData(this.formData, `detail[${i}].after_value`, this.input[i].after_value?.toString() ?? '')
                            appendOrReplaceFormData(this.formData, `detail[${i}].unit`, this.input[i].unit ?? '')
                        }
                    }
                    const token = this.$cookies.get("Token");
                    axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth/verifyUser", {}, {
                        headers: { Authorization: token }
                    })
                    .then((res) => { // 유저 토큰 검증 후 업로더 등록
                        if (res.data === true) {
                            appendOrReplaceFormData(this.formData, "uploader", getNameFromToken(token));
                            appendOrReplaceFormData(this.formData, 'name', this.name);
                            appendOrReplaceFormData(this.formData, 'price', this.price);
                            appendOrReplaceFormData(this.formData, 'brand', this.brand);
                            appendOrReplaceFormData(this.formData, 'category', this.category);
                            axios.post(process.env.VUE_APP_BACKEND_ADDRESS+'/product', this.formData,
                            { headers: { 'Content-Type': 'multipart/form-data', 'Access-Control-Allow-Origin': '*'}})
                            .then(() => {
                                alert("[Success] Your report has uploaded!");
                                this.$router.push('/')
                            });
                        } else {
                            alert("[Error] There's problem in your login information. Retry after re-signin.");
                        }
                    })
                    .catch(() => { alert("[Error] Please sign in to report a product!"); })
                    
                } else {
                    alert("[Error] Please fill out all the changes!");
                }
            } else {
                alert("[Error] Please fill out all the information about product!");
            }
        },
        resetImage() {
            this.image_url = '';
        }
    },
    mounted() {
        this.input.push({changed_point: '', before_value: null, unit: 'g', after_value: null, unknown: false}); // 최초 변화 Array 생성
    }
});
</script>