import Vue from 'vue'
import Router from 'vue-router'
import AppHeader from './layout/AppHeader'
import AppFooter from './layout/AppFooter'
import Components from './views/Components.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import Profile from './views/Result.vue'
import Collection from './views/Collection.vue'

Vue.use(Router)

export default new Router({
  linkExactActiveClass: 'active',
  routes: [
    {
      path: '/',
      name: 'components',
      components: {
        header: AppHeader,
        default: Components,
        footer: AppFooter,
      },
      meta: { requiresAuth: false },
    },
    {
      path: '/login',
      name: 'login',
      components: {
        header: AppHeader,
        default: Login,
        footer: AppFooter,
      },
      meta: { requiresAuth: false },
    },
    {
      path: '/register',
      name: 'register',
      components: {
        header: AppHeader,
        default: Register,
        footer: AppFooter,
      },
      meta: { requiresAuth: false },
    },
    {
      path: '/profile',
      name: 'profile',
      components: {
        header: AppHeader,
        default: Profile,
        footer: AppFooter,
      },
      meta: { requiresAuth: false },
    },
    {
      path: '/collection',
      name: 'collection',
      components: {
        header: AppHeader,
        default: Collection,
        footer: AppFooter,
      },
      meta: { requiresAuth: false },
    },
  ],
  scrollBehavior: (to) => {
    if (to.hash) {
      return { selector: to.hash }
    } else {
      return { x: 0, y: 0 }
    }
  },
})
