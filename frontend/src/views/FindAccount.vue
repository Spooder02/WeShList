<template>
    <div class="w-4/5 bg-gray-50 rounded-md text-center m-auto py-4">
        <p class="font-bold">What can I help you?</p>
        <button @click="$router.push('/findaccount/lostid')" class="p-2 bg-white shadow-lg rounded-lg m-2 font-medium">
            <img src="@/assets/user(1).png" class="w-16 m-auto">
            Lost ID
        </button>
        <button class="p-2 bg-white shadow-lg rounded-lg font-medium">
            <img @click="$router.push('/findaccount/lostpw')" src="@/assets/key(1).png" class="w-16 m-auto">
            Lost PW
        </button>
    </div>
</template>

<script lang="ts">
import '../index.css'
import { defineComponent } from 'vue';

export default defineComponent({
    name: '',
    components: {
        
    },
});
</script>