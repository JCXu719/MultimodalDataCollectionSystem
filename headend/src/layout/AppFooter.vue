<template>
  <footer class="footer has-cards">
    <div class="container">
      <div class="row row-grid align-items-center justify-content-center my-md">
        <div class="text-center text-muted mb-4">
          <h3 style="margin-top: -50px">感谢您对本项目研究的参与和支持!</h3>
        </div>
      </div>
      <hr />
      <div class="row align-items-center justify-content-md-between">
        <div class="col-md-6">
          <img src="../../logo.jpg" alt="加载失败" width="70%" />
        </div>
        <div class="col-md-6">
          <ul class="nav nav-footer justify-content-end">
            <li class="nav-item">
              <p>HFUT LMC 2024</p>
              <!-- <a href="" class="nav-link" target="_blank" rel="noopener"
                >About us</a
              > -->
            </li>
          </ul>
        </div>
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  name: 'app-footer',
  data() {
    return {
      year: new Date().getFullYear(),
    }
  },
}
</script>
<style></style>
