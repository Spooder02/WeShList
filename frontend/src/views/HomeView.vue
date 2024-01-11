<template>
  <div class="text-center py-10">
    <h1 class="text-2xl font-bold text-gray-900">직접 만들어 나가는 슈링크플레이션 목록!</h1>
    <p class="mt-2 text-lg">다가온 인플레이션, 위시리스트와 함께 극복해나가요!</p>
    <div class="mt-3 flex items-center justify-center gap-x-4">
      <a href="#" class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">시작하기</a>
      <a href="#" class="text-sm font-semibold text-gray-900">자세히 알아보기 <span aria-hidden="true">→</span></a>
    </div>
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
