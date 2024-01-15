<template>
  <div class="text-center p-6">
    <div id="main-title">
      <h1 class="text-2xl font-bold text-gray-900">직접 만들어 나가는 슈링크플레이션 목록!</h1>
      <p class="mt-2 text-lg">다가온 인플레이션, 위시리스트와 함께 극복해나가요!</p>
      <div class="mt-3 flex items-center justify-center gap-x-4">
        <a @click="$router.push('/additem')" class="rounded-md bg-green-500 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">+ 상품 제보하기</a>
        <a @click="$router.push('/finditem')" class="text-sm font-semibold text-gray-900">상품 알아보기 <span aria-hidden="true">→</span></a>
      </div>
    </div>
  </div>
  <div id="footer" class="bg-gray-300 absolute inset-x-0 bottom-0 text-center">
    <p class="">Developed by. spooder02</p>
    <p class="">Check out the code at 
    <a class="underline" href="https://github.com/Spooder02/WeShList">GitHub!</a></p>
  </div>
</template>

<script lang="ts">
import '../index.css';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'HomeView',
  components: {

  },
});
</script>
