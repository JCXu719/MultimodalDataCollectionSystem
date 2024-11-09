<template>
  <section
    class="section section-shaped section-lg my-0"
    style="height: 1000px"
  >
    <div class="shape shape-style-1 bg-gradient-info">
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </div>
    <div class="container pt-lg-md">
      <div class="row justify-content-center">
        <div class="col-lg-5">
          <card
            type="secondary"
            shadow
            header-classes="bg-white pb-5"
            body-classes="px-lg-5 py-lg-5"
            class="border-0"
          >
            <template>
              <div class="text-center text-muted mb-4" style="margin-top: 50px">
                <h1 class="display-4">登录</h1>
              </div>
              <form role="form" @submit.prevent="loginUser()">
                <base-input
                  class="mb-3"
                  placeholder="用户名/学号/手机号"
                  addon-left-icon="ni ni-hat-3"
                  v-model="username"
                  id="name"
                  alternative
                  :valid="checkname"
                >
                </base-input>
                <base-input
                  type="password"
                  placeholder="密码"
                  addon-left-icon="ni ni-lock-circle-open"
                  v-model="password"
                  id="pass"
                  alternative
                  :valid="checkpass"
                >
                </base-input>

                <div class="text-center">
                  <base-button
                    type="info"
                    class="my-4"
                    @click="loginUser()"
                    style="width: 120px"
                    >登录</base-button
                  >
                </div>
              </form>
            </template>
          </card>
        </div>
      </div>
    </div>
    <modal :show.sync="showModal">
      <h6 slot="header" class="modal-title" id="modal-title-default">提示</h6>
      <p>{{ message }}</p>
      <base-button type="info" @click="showModal = false">关闭</base-button>
    </modal>
  </section>
</template>
<script>
import Modal from '@/components/Modal.vue'
import axios from 'axios'
var ConfirmName = /^[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$/ //1-20位登陆用户名的校验(英文可包含数字)
var ConfirmPwd = /^[a-zA-Z0-9_\u4e00-\u9fa5]{8,20}$/ //8-20位登录密码，包含数字特殊字符，大小写的校验

export default {
  components: { Modal },
  methods: {
    async loginUser() {
      if (!this.username || !this.password) {
        this.showModal = true
        this.message = '请填写所有信息'
      } else {
        try {
          await axios
            .post(
              '/user/login?RememberMe=false',
              {
                username: this.username,
                password: this.password,
              },
              {
                headers: {
                  'Content-Type': 'application/json',
                },
              }
            )
            .then((response) => {
              const { code, data } = response.data
              console.log(code)
              if (code === 1) {
                this.$store.commit('SET_ACCESS_TOKEN', data)
                console.log(this.$store.state.accessToken)
                this.showModal = true
                this.message = '登录成功，三秒后跳转'
                setTimeout(() => {
                  this.$router.push('/')
                }, 3000)
              } else {
                this.showModal = true
                this.message = '登录失败，请检查个人信息或密码是否正确'
              }
            })
        } catch (e) {
          this.showModal = true
          this.message = '登录失败，未连接到服务器或服务器未启动'
        }
      }
    },
    Setnamesafe() {
      const name = document.querySelector('#name')
      if (ConfirmName.exec(name.value)) {
        this.checkname = true
      } else {
        this.checkname = false
      }
    },
    Setpasssafe() {
      const pass = document.querySelector('#pass')
      if (ConfirmPwd.exec(pass.value)) {
        this.checkpass = true
      } else {
        this.checkpass = false
      }
    },
  },
  data() {
    return {
      username: '',
      password: '',
      remember: false,
      showModal: false,
      message: '',
      checkname: ' ',
      checkpass: ' ',
    }
  },
}
</script>
<style></style>
