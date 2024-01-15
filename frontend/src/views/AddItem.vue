<template>
    <div class="w-4/5 bg-gray-50 m-auto shadow-lg text-center grid place-content-center">
        <p class="font-semibold">상품 제보하기</p>
        <label for="file" class="p-8 m-auto border font-medium rounded-lg mt-4 mb-4">
        <span class="font-black">+</span>
            이미지 등록
        </label>
        <input id="file" type="file" class="hidden" accept="image/*"/>
        <input type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="상품명">
        <input type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="브랜드">
        <input type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="가격">
        <div v-for="i in changes" class="border p-1 rounded-lg mb-2" :key="i">
            <p class="text-base text-gray-700 font-semibold flex justify-between mb-2">
                상품 변화
            <button @click="addChanges(i)" class="p-0.5 pl-1 pr-1 rounded-lg bg-green-400 text-white font-medium shadow-xl"><span class="font-black">+</span> 변화 추가</button>
            </p>
            <input @change="setValue(i, 1, $event.target.value)" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5 mb-3" placeholder="변화 항목">
            <input @change="setValue(i, 2, $event.target.value)"type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 text-center p-0.5" placeholder="기존 용량(숫자 단위)">
            <select @input="setValue(i, 3, $event.target.value)" v-model="unit[i-1]" class="border rounded-lg border-gray-300 focus:border-blue-300 text-center p-0.5" :key="i">
                <option v-for="j in default_unit.length" :value="default_unit[j-1]">{{ default_unit[j-1] }}</option>
            </select>
            <p>↓</p>
            <input @change="setValue(i, 4, $event.target.value)" type="text" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" placeholder="변화 용량(숫자 단위)">
            <select v-model="unit[i-1]" class="border rounded-lg border-gray-300 focus:border-blue-300 mb-2 text-center p-0.5" name="unit" disabled>
                <option v-for="j in default_unit.length" :value="default_unit[j-1]">{{ default_unit[j-1] }}</option>
            </select>
        </div>
        <button class="p-2 mt-2 mb-2 bg-blue-400 w-1/2 m-auto rounded-lg text-white font-semibold shadow-xl">+ 상품 등록하기</button>
    </div>
</template>

<script lang="ts">
import '../index.css'
import { changed_value } from '../datatype'
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        
    },
    data() {
        return {
            changes: 1,
            default_unit: ['µg', 'mg', 'g', 'kg', 'mL', 'L', 'cc', '%'],
            unit: ['g'],
            input: [] as Array<changed_value>
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
                        alert("숫자만 입력해주세요!");
                    else {
                        this.input[n-1].before_value = Number(value);
                    }
                    break;
                case 3:
                    this.input[n-1].unit = value;
                    break;
                case 4:
                    if (isNaN(Number(value)))
                        alert("숫자만 입력해주세요!");
                    else
                        this.input[n-1].changed_value = Number(value);
            }
        },
        addChanges(i:number) {
            if (this.input[i-1].changed_point != '' &&
                this.input[i-1].before_value != -1 &&
                this.input[i-1].changed_value != -1) { // 값 무결성 체크
            this.changes+=1;
            this.unit[i]='g';
            console.log(this.input[i-1]);
            this.input.push({changed_point: '', before_value: -1, unit: 'g', changed_value: -1});
            } else {
                alert("값을 모두 입력 후 추가해주세요!")
            }
        }
    },
    mounted() {
        this.input.push({changed_point: '', before_value: -1, unit: 'g', changed_value: -1}) // 최초 변화 Array 생성
    }
});
</script>