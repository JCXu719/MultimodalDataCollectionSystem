<template>
  <header class="header-global">
    <base-nav class="navbar-main" transparent type="" effect="light" expand>
        <span
          class="navbar-brand mr-lg-5"
          style="
            height: 45px;
            width: 280px;
            text-align: center;
            line-height: 40px;
            font-size: 18px;
            margin-left: -80px;
          "
          >多模态数据采集系统</span
        >

      <ul class="navbar-nav align-items-lg-center ml-lg-auto">
        <template v-if="isAuthenticated()">
          <!-- <li class="nav-item">
            <router-link to="/profile" class="nav-link">
              <i class="fa fa-user-circle"></i>
              <span class="nav-link-inner--text">个人中心</span>
            </router-link>
          </li> -->
          <li class="nav-item">
            <router-link to="/collection" class="nav-link">
              <i class="fa fa-database"></i>
              <span class="nav-link-inner--text">采集数据</span>
            </router-link>
          </li>
        </template>
        <template v-else>
          <li class="nav-item">
            <router-link to="/register" class="nav-link">
              <i class="fa fa-user-plus"></i>
              <span class="nav-link-inner--text">注册</span>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/login" class="nav-link">
              <i class="fa fa-user"></i>
              <span class="nav-link-inner--text">登录</span>
            </router-link>
          </li>

          <!-- <li class="nav-item">
            <router-link to="/collection" class="nav-link">
              <i class="fa fa-database"></i>
              <span class="nav-link-inner--text">采集数据</span>
            </router-link>
          </li>
          
          <li class="nav-item">
            <router-link to="/profile" class="nav-link">
              <i class="fa fa-user-circle"></i>
              <span class="nav-link-inner--text">个人中心</span>
            </router-link>
          </li> -->
        </template>
      </ul>
    </base-nav>
  </header>
</template>
<script>
import BaseNav from '@/components/BaseNav'
import BaseDropdown from '@/components/BaseDropdown'
import CloseButton from '@/components/CloseButton'
import { mapGetters } from 'vuex'

export default {
  components: {
    BaseNav,
    CloseButton,
    BaseDropdown,
  },
  methods: {
    ...mapGetters(['isAuthenticated']),
  },
}
</script>
<style></style>
