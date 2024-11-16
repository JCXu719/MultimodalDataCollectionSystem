<template>
    <section style="height: auto">
        <h1 class="text-center mt-3 mb-3" style="color: red; font-weight: 900;">采集前须知</h1>
        <p style="font-weight: 900; color: red;">
            务必仔细阅读下列内容，否则采集过程中出现问题影响数据采集质量的后果由您本人承担
        </p>
        <p style="font-weight: 900;">
            （1）采集地点：线上采集，地点由您自行决定，但要求设备放在稳定的平面上，环境安静，不要有太大的噪音（推荐寝室、自习室等环境）
        </p>

        <p style="font-weight: 900;">
            （2）采集网络环境：务必连接校园网并在网络环境畅通的地点进行采集<span style="color: red;">宣城校区的同学如果在寝室进行采集务必插上校园网网线，其他地点可连接无线校园网采集</span>
        </p>

        <p style="font-weight: 900;">
            （3）采集设备检查：使用含摄像头的笔记本电脑，注意要求<span style="color: red; font-weight: 900;">摄像头必须可以录制720p
                30fps的视频（后续的设备检查环节会进行检测，如果不符合要求请更换设备）；</span>还需要含麦克风的耳机，要求麦克风和耳机正常；不建议在手机或平板上进行（未经测试），出现问题则由您本人承担
        </p>
        <p style="font-weight: 900;">
            （4）采集姿势：采集前请仔细矫正您的坐姿，要求正对摄像头，头和肩膀都需要出现在摄像头内
        </p>
        <p style="font-weight: 900;color: red;">
            （5）<span
                style="color: red;">采集备份文件处理：</span>由于服务器带宽限制，您在提交文件的过程中可能会遇到传输速率慢、提交失败等情况，为了避免文件丢失问题，采集过程中会下载以local为开头名称的文件，这些文件是实验采集数据的本地备份，采集结束提交时，如果出现提交时间长或提交失败且重试没用的情况，请您将这些文件发到QQ群管理员处，发送成功后不会影响志愿时长奖励获取
        </p>
        <p style="font-weight: 900;">
            （6）允许采集权限：采集过程中浏览器会请求三种权限（摄像机权限、麦克风权限、同时下载多个文件权限），务必均同意请求，否则无法采集正常数据的后果由您本人承担。
        </p>
        <p style="font-weight: 900;">
            （7）采集过程中请尽量保持放松，按照真实的想法反应和回答问题，您的数据均会严格保密，无需担心泄露
        </p>
        <p style="font-weight: 900;">
            （8）采集结束后，务必对采集流程保密，发现泄露实验内容的行为将取消志愿时长奖励
        </p>

        <div class="text-center mt-3 mb-3">
            <div class="mt-3 mb-3">
                <base-button type="primary" disabled="true" @click="start" id="button">确定({{ notetimes }})</base-button>
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
            times: 90,
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
