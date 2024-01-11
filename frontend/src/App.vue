<template>
  <Navbar/>
  <router-view/>
</template>

<script lang="ts">
import './index.css';
import { defineComponent } from 'vue';
import Navbar from "@/components/Navbar.vue";

export default defineComponent({
  name: 'HomeView',
  components: {
    Navbar
  },
});
</script>
