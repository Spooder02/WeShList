<template>
  <div class="">
    <img src="@/assets/main-banner.png" class="absolute z-0 blur-[1px]">
    <div id="main-title" class="absolute text-center p-6 z-10">
      <h1 class="text-2xl font-bold text-white">We share shrinkflation</h1>
      <p class="mt-2 text-lg text-white">Let's share information about Shrinkflation!</p>
      <div class="mt-3 flex items-center justify-center gap-x-4">
        <a @click="$router.push('/additem')" class="rounded-md brightness-110 shadow-2xl bg-green-500 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">+ Add List</a>
        <a @click="$router.push('/finditem')" class="text-sm font-semibold text-white">See more <span aria-hidden="true">→</span></a>
      </div>
    </div>
  </div>
  <div id="footer" class="bg-gray-100 absolute inset-x-0 bottom-0 text-center p-4">
    <p class="">Developed by. Geonhui Jo (SCH Univ.)</p>
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
