<template>
<nav class="bg-gray-800 flex shadow-2xl mb-4">
    <div class="w-full block flex-grow flex items-center justify-between">
    <div id="right" class="flex flex-wrap items-center justify-between mt-2 mb-2">
      <img @click="$router.push('/')" src="@/assets/WeShList_MainLogo_cropped.png" class="apect-w-5 aspect-h-1 h-8 ml-1 mr-1">
      <a @click="$router.push('/')" class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium">메인</a>
      <a @click="$router.push('/introduce')" class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium">소개</a>
      <a @click="$router.push('/finditem')" class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium">상품 찾기</a>
    </div>
    <div id="left" class="flex items-center mr-4">
      <a class="text-white bg-green-500 rounded-md pt-1 pb-1 pl-2 pr-2 hover:bg-green-600 text-red">+ 상품 추가</a>
      <a class="text-white hover:bg-gray-700 hover:text-white rounded-md ml-2.5 px-2 py-2">로그인</a>
      <p class="text-gray-500">|</p>
      <a class="text-white hover:bg-gray-700 hover:text-white rounded-md px-2 py-2">회원가입</a>
    </div>
    </div>
</nav>
</template>

<script lang="ts">
import '../index.css';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'Navbar',
  components: {

  },
  
});
</script>