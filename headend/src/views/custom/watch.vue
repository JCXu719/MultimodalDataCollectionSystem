<template>
  <section>
    <h1 class="text-center mt-3 mb-3">步骤一 观看视频</h1>
    <p style="font-weight: 900;">您好，请观看一组视频片段，时间在9分钟左右。</p>
    <p style="font-weight: 900;">提示：注意左侧的摄像头，如果未打开，请点击左下角的“摄像头权限检测”开启，并允许网页访问摄像头</p>
    <video
    controls
    controlslist="nodownload noplaybackrate"
      disablePictureInPicture="true"
      ref="video"
      width="100%"
      @pause="updatePlayState(0)"
      @play="updatePlayState(1)"
      @ended="updatePlayState(2)"
      class="text-center mt-3 mb-3"
      id = "video"
    >
      <source src="videos/Stimulus.mp4" type="video/mp4" />
    </video >
    <div class="text-center mt-3 mb-3">
      <base-button
        type="primary"
        @click="updateVideo"
        v-if="this.playState === 0"
      >
        播放
      </base-button>
      <base-button
        type="primary"
        @click="updateVideo"
        v-else-if="this.playState === 1"
      >
        暂停
      </base-button>
      <base-button
        type="primary"
        @click="updateVideo"
        v-else-if="this.playState === 2"
      >
        下一步
      </base-button>
    </div>
  </section>
</template>
<script>
export default {
  name: 'Watch',
  data() {
    return {
      playState: 0,
    }
  },
  methods: {
    updatePlayState(state) {
      this.playState = state
      console.log(`Bearer ${this.$store.state.accessToken}`)
    },
    updateVideo() {
      const video = this.$refs.video
      if (this.playState === 0) {
        video.play()
      } else if (this.playState === 1) {
        video.pause()
      } else if (this.playState === 2) {
        this.$emit('finish')
      }
    },
  },
}

</script>
<style>
video::-webkit-media-controls-timeline {
        display: none;
    }
</style>
