import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/introduct',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/finditem',
    name: 'FindItem',
    component: () => import('@/views/FindItems.vue')
  },
  {
    path: '/finditem/detail',
    name: 'DetailView',
    component: () => import('@/views/ItemDetail.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('@/views/SignUp.vue')
  },
  {
    path: '/findaccount',
    name: 'FindAccount',
    component: () => import('@/views/FindAccount.vue')
  },
  {
    path: '/findaccount/lostid',
    name: 'LostId',
    component: () => import('@/views/LostId.vue')
  },
  {
    path: '/findaccount/lostpw',
    name: 'LostPw',
    component: () => import('@/views/LostPw.vue')
  },
  {
    path: '/additem',
    name: 'AddItem',
    component: () => import('@/views/AddItem.vue')
  },
  {
    path: '/fixitem',
    name: 'FixItem',
    component: () => import('@/views/FixItem.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
