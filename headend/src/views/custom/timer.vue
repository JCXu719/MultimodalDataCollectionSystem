<template>
  <section>
    <h1>{{ minutes }}:{{ seconds }}</h1>
  </section>
</template>

<script>
export default {
  data() {
    return {
      localTotalSeconds: this.totalSeconds,
      localMiddleSeconds: this.middleSeconds,
      interval: null,
      isRunning: false,
    }
  },
  mounted() {
    this.startTimer()
  },
  computed: {
    minutes() {
      return Math.floor(this.localTotalSeconds / 60)
    },
    seconds() {
      return this.localTotalSeconds % 60
    },
  },
  methods: {
    startTimer() {
      if (!this.isRunning) {
        this.isRunning = true
        this.interval = setInterval(() => {
          if (this.localTotalSeconds <= 0) {
            clearInterval(this.interval)
            this.isRunning = false
            this.localTotalSeconds = 0
            this.$emit('finish')
            return
          }

          if (this.localTotalSeconds === this.localMiddleSeconds) {
            this.$emit('middle')
          }

          this.localTotalSeconds--
        }, 1000)
      }
    },
    resetTimer(newTotalSeconds, newMiddleSeconds) {
      clearInterval(this.interval)
      this.isRunning = false
      // 更新总秒数
      this.localTotalSeconds = newTotalSeconds
      // this.localMiddleSeconds = newTotalSeconds - 1
      this.localMiddleSeconds = newMiddleSeconds
      this.startTimer()
    },
  },
  props: {
    totalSeconds: {
      type: Number,
      default: 60,
    },
    middleSeconds: {
      type: Number,
      default: 30,
    },
  },
}
</script>
<style></style>
