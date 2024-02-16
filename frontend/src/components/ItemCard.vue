<template>
    <div @click="$router.push({path: '/finditem/detail', query: { id: id } })" class="m-4 rounded-xl shadow-lg">
        <div class="h-24 p-auto">
            <img v-if="image" class="w-24 h-24 absolute" :src="'http://localhost:8081/image/'+image">
            <img v-if="!image" class="w-24 h-24 absolute" src="@/assets/unavailable_image.png">
            <div id="inline-block">
                <p class="inline ml-24 pl-2 mt-2 text-lg">{{ name }}</p>
                <p class="inline ml-1 text-sm text-gray-500">{{ category }}</p>
            </div>
            <p class="ml-24 m-0 pl-2 text-sm text-gray-500">{{ description[0].changed_point }} {{ description[0].before_detail}}{{ description[0].unit }} -> {{ description[0].after_detail }}{{ description[0].unit }}<br class="m-0">...</p>
            <p class="mr-3 mt-1 text-right text-xs text-gray-300">등록 날짜: {{ update_time }}</p>
        </div>
    </div>
</template>

<script lang="ts">
import '../index.css'
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        
    },
    props: {
        id: Number,
        name: String,
        category: String,
        description: Object,
        update_time: String,
        image: String,
    }
});
</script>