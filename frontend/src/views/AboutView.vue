<template>
  <div class="about">
    <p class="text-center font-bold text-2xl">Service Introduction</p>
    <hr class="w-64 h-1 my-2 mx-auto bg-gray-200 border-0 rounded">
    <div class="w-4/5 mx-auto">
      <p class="text-lg font-semibold">- What is WeShList?</p>
      <p>
        To overcome shrinkflation, We made a web service which can share Shrinkflation information.
        It can let you know how it changed and helps you smart comsumption based on this service.
      </p>
    </div>
    <p class="text-center font-bold text-2xl mt-8">How to use?</p>
    <hr class="w-48 h-1 my-2 mx-auto bg-gray-200 border-0 rounded">
    <div class="w-4/5 mx-auto my-6">
      <div class="block">
        <p class="font-semibold text-md">1. Mainpage</p>
        <img src="@/assets/WeShList_MainPage_Introduction.png">
        <div class="block text-sm">
          <p class="text-sm">There's two way to approach another page.</p>
          <p class="font-semibold pt-1">First, Click Hamburger menu to Open Menupage.</p>
          <li>Home - Back to this mainpage</li>
          <li>Introduction - You are here. Can see how to use WeShList</li>
          <li>Report Product - You can report a Shrinkflation product. People can see your report.</li>
          <li>Find Product - You can see Shrinkflation products in this page.</li>
        </div>
        <p class="font-semibold text-md pt-4">2. Report Product</p>
        <img src="@/assets/WeShList_ReportProduct_Introduction.png">
        <p class="text-sm">You can post a shrinkflation production with this form. Fill out the form to share shrinkflation information to people!</p>
        <p class="text-sm font-semibold p-1 bg-slate-100 rounded-md m-1">
          ! Details must be filled more than one <br>
          ! Image is additional option.
        </p>
        <p class="text-sm">* Modifying Information uses same UI.</p>
        <p class="font-semibold text-md pt-4">3. Find Product</p>
        <img src="@/assets/WeShList_FindProduct_Introduction.png">
        <p class="text-sm">
          You can see Shrinkflation products on this page, You can click on the product card to see more information.<br>
          When the infromation is wrong, you can rate this information with clicking a rating button or modify the information!
        </p>
      </div>
      
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'AboutView'
  });
</script>
