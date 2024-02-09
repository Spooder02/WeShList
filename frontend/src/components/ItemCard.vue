<template>
    <div @click="$router.push('/detail')" class="m-4 rounded-xl shadow-lg">
        <div class="h-32">
            <img class="w-32 absolute" :src="'http://localhost:8081/image/'+image">
            <div id="inline-block">
                <p class="inline ml-32 mt-2 text-lg">{{ name }}</p>
                <p class="inline ml-1 text-sm text-gray-500">{{ category }}</p>
            </div>
            <p class="ml-32 text-md text-gray-500">{{ description }}</p>
            <p class="mr-4 mt-11 text-right text-sm text-gray-300">등록 날짜: {{ update_time }}</p>
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
        name: String,
        category: String,
        description: String,
        update_time: String,
        image: String
    }
});
</script>