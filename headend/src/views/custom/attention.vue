<template>
  <section style="height: auto">
    <h1 class="text-center mt-3 mb-3">采集前须知</h1>
    <p style="font-weight: 900">
      务必仔细阅读下列内容，否则采集过程中出现问题影响数据采集的后果由您本人承担
    </p>
    <p>
      （1）采集开始前，务必检查设备是否正常（摄像头可录制720p
      30fps视频，耳机可听到声音且麦克风正常）
    </p>
    <p>
      （2）采集前将手机暂时存放在管理员处，采集结束后归还，采集过程中请专注在电脑屏幕上，尽量不要分神
    </p>
    <p>
      （3）采集过程中请仔细阅读各板块的指导语，充分理解后再开始各板块的采集任务。
    </p>
    <p>
      （4）采集过程中浏览器会请求三种权限（摄像机权限、麦克风权限、同时下载多个文件权限），务必均同意请求，否则无法采集正常数据的后果由您本人承担。
    </p>
    <p>
      （5）采集过程中会下载以local为开头名称的文件，这些文件是实验采集数据的本地备份，采集结束后，请坐在座位上等待管理员将这些文件拷贝到移动硬盘里，拷贝完成之后您方可离场。
    </p>
    <p>
      （6）采集过程涉及部分问答环节，说话声音不要太大，正常音量即可，避免影响到其他同学
    </p>
    <p>
      （7）志愿过程中遇到任何问题，请暂停流程并坐在座位上等待，待其他同学完成采集后找管理员解决，不要做举手示意或大叫等会影响其他同学的行为
    </p>
    <p>（8）采集结束，带着设备走到管理员处，管理员检查完成后方可离开</p>

    <div class="text-center mt-3 mb-3">
      <div class="mt-3 mb-3">
        <base-button type="primary" @click="start" id="button"
          >确定({{ notetimes }})</base-button
        >
        <!-- disabled="true"-->
      </div>
    </div>
  </section>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Signature',
  methods: {
    Timereduce() {
      this.interval = setInterval(() => {
        this.times--
        console.log(this.times)
        if (this.times === 0) {
          clearInterval(this.interval)
          document.querySelector('#button').disabled = false
        }
      }, 1000)
    },
    start() {
      this.$emit('finish')
    },
  },

  data() {
    return {
      isDrawing: false,
      context: null,
      username: '',
      times: 60,
    }
  },
  mounted() {
    this.Timereduce()
  },
  computed: {
    notetimes() {
      return this.times
    },
  },
}
</script>

<style></style>
