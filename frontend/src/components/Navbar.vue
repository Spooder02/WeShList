<template>
<nav class="bg-gray-800 block shadow-lg mb-4">
    <!--  <div class="w-full block flex-grow flex items-center justify-between">
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
    </div> --> <!-- PC VIEW -->
    <!-- MOBILE VIEW --> 
    <div class="w-full block flex-grow flex items-center justify-between p-2">
      <img @click="$router.push('/')" src="@/assets/WeShList_MainLogo_cropped.png" class="apect-w-5 aspect-h-1 h-8 ml-1 mr-1">
        <button @click="toggleMenu()" class="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white" aria-controls="mobile-menu" aria-expanded="false">
        <svg v-if="!isOpen" class="block h-8 w-8" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="white" aria-hidden="true">
          <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
        </svg>
        <svg v-if="isOpen" class="block h-8 w-8" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
          <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
    </div>
    <div v-if="isOpen" class="w-full bg-gray-500 p-4 text-center text-white">
      <div v-if="isLoggedin" class="mb-2">
        <span class="font-semibold">반갑습니다, {{ username }}님!</span>
        <a class="ml-4" @click="logout()">로그아웃</a>
      </div>
      <a @click="$router.push('/')" class="block">메인</a>
      <a @click="$router.push('/introduct')" class="block mt-2">소개</a>
      <a @click="$router.push('/finditem')" class="block mt-2">상품 찾기</a>
      <a @click="$router.push('/additem')" class="block mt-2">상품 제보</a>
      <div v-if="!isLoggedin" class="mt-3">
        <a @click="$router.push('/login')" class="font-medium">로그인</a>
        <span class="ml-1 mr-1">|</span>
        <a @click="$router.push('/signup')" class="font-medium">회원가입</a>
      </div>
    </div>
</nav>
</template>

<script lang="ts">
import axios from 'axios';
import '../index.css';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'Navbar',
  components: {

  },
  mounted() {
    this.checkLogin()
  },
  updated() {
    this.checkLogin()
  },
  data() {
    return {
      isOpen: false,
      isLoggedin: false,
      username: ''
    }
  },
  methods: {
    toggleMenu() {
      setTimeout(() => {
        this.isOpen = !this.isOpen;
      }, 100);
    },
    logout() {
      this.isLoggedin = false;
      this.username = '';
      sessionStorage.removeItem("Authorization")
    },
    refresh() {
      this.$forceUpdate();
    },
    checkLogin() {
      const token = sessionStorage.getItem("Authorization");
      if (token) {
        let base64Url = token.split('.')[1];
        let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const decodedJWT = JSON.parse(
        decodeURIComponent(
          window
            .atob(base64)
            .split('')
            .map(function (c) {
              return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join('')
          )
        );
        this.username = decodedJWT.sub
        this.isLoggedin = true;
      }
      else this.isLoggedin = false;
    }
  },
  

});
</script>