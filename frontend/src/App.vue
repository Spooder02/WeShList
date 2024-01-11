<template>
  <nav class="bg-gray-800">
    <div class="relative flex items-center space-around pt-2 pb-2">
      <p class="text-white px-3 py-2">위시리스트</p>
      <p class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium">메인</p>
      <p class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium">소개</p>
      <p class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium">상품 찾기</p>
    </div>
  </nav>
  <router-view/>
</template>

<script lang="ts">
import './index.css';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'HomeView',
  components: {

  },
});
</script>
