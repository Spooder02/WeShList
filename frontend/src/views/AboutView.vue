<template>
  <div class="about">
    <p class="text-center font-bold text-2xl">서비스 소개</p>
    <hr class="w-48 h-1 my-2 mx-auto bg-gray-200 border-0 rounded">
    <div class="w-4/5 mx-auto">
      <p class="text-lg font-semibold">- 위시리스트(WeShList)는 어떤 서비스인가요?</p>
      <p>
        슈링크플레이션 시대를 극복하기 위한 소비자 정보 공유 플랫폼으로,
        어떠한 상품에 대해 정확한 변화를 파악하고 이를 근거로 현명한 소비를 돕기 위해 제작하였습니다.
      </p>
    </div>
    <p class="text-center font-bold text-2xl mt-8">사용 방법</p>
    <hr class="w-48 h-1 my-2 mx-auto bg-gray-200 border-0 rounded">
    <div class="w-4/5 mx-auto my-6">
      <div class="block">
        <img src="@/assets/weshlist_howtouse(1).png">
        <div class="block">
          <p class="font-semibold text-md">1. 메인 페이지 - 햄버거 박스를 이용해 다른 영역으로 이동이 가능합니다.</p>
          <p class="text-sm">로그인 영역 - 회원가입 및 로그인을 할 수 있습니다.</p>
          <p class="text-sm">메인 - 메인 페이지로 이동할 수 있습니다</p>
          <p class="text-sm">소개 - 서비스 소개 및 사용 방법을 확인할 수 있습니다.</p>
          <p class="text-sm">상품 찾기 - 사용자들이 등록한 상품들을 확인할 수 있습니다.</p>
          <p class="text-sm">상품 제보 - 직접 사용자가 상품에 대한 정보를 게시할 수 있습니다.</p>
        </div>
        <img class="mt-6" src="@/assets/weshlist_howtouse(2).png">
        <p class="font-semibold text-md">2. 상품 등록 페이지 - 첨부된 사진을 참고하여 상품을 등록합니다.</p>
        <p class="text-sm">상품 수정 페이지도 동일한 UI를 사용하며, 상품 등록과 마찬가지로 수정할 수 있습니다.</p>
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
