<template>
    <section class="section section-shaped section-lg my-0 text-center">
        <div>
            <h1 style="margin-bottom: 50px">媒体设备检测</h1>
            <div>
                <h3>音频检测</h3>
                <div style="
            margin-top: 20px;
            margin-left: 90px;
            text-align: left;
            font-weight: 500;
          ">
                    (1)点击左侧【开始录制】按钮，任意录制一段音频；<br />
                    (2)点击右侧【音频回放】按钮，检测是否可听到自己录制的音频。
                </div>
                <select :disabled="audiorecording" style="width: 350px; height: 36px; margin-top: 30px"
                    v-model="main_form.chooseMicDeviceId" placeholder="请选择设备">
                    <option v-for="item in Mic" :key="item.deviceId" :label="item.label" :value="item.deviceId">
                    </option>
                </select>
                <div style="margin-top: 40px; margin-left: 20px">
                    <base-button type="info" @click="voiceInput" id="button">
                        {{ audiorecording ? '结束录制' : '开始录制' }}
                    </base-button>
                    <base-button @click="audioPlayRecording()" :disabled="!audiorecorded" type="success"
                        style="margin-left: 90px" id="audioplay">音频回放</base-button>
                </div>
                <!--
          <base-button @click="startRecording()" :disabled="recording" type="info"
            >开始录制</base-button
          >
          <base-button
            @click="stopRecording()"
            :disabled="!recording"
            type="danger"
            >停止录制</base-button
          >-->
            </div>
            <hr />
            <div>
                <h3 style="margin-top: 50px">视频检测</h3>
                <div style="
            margin-top: 20px;
            margin-left: 90px;
            text-align: left;
            font-weight: 500;
          ">
                    (1)点击左侧【开始录制】按钮，任意录制一段视频；<br />
                    (2)点击右侧【视频回放】按钮，检测是否可看到自己录制的视频。
                </div>
                <div style="margin-top: 40px; margin-left: 20px">
                    <base-button type="info" @click="videoInput" id="button0">
                        {{ videorecording ? '结束录制' : '开始录制' }}
                    </base-button>
                    <base-button @click="videoPlayRecording()" :disabled="!audiorecorded" type="success"
                        style="margin-left: 90px" id="videoplay">视频回放</base-button>
                </div>
                <div>
                    <video id="player" controls controlslist="nodownload noplaybackrate" disablePictureInPicture="true"
                        style="margin-top: 50px; margin-left: 30px" class="text-center mt-3 mb-3" ref="video"
                        width="80%" autoplay muted></video>
                    <img src="img/people.png" alt="人像" class="img-fluid rounded shadow" width="55%"
                        style="position: absolute; left: 145px; top: 765px" />
                </div>

                <div id="videoInfo" style="
            text-align: left;
            margin-left: 160px;
            margin-top: 20px;
            font-weight: 900;
            font-size: 16px;
          "></div>
            </div>
        </div>
        <div style="margin-top: 60px; margin-left: 30px">
            <base-button type="primary" id="nextstep" @click="start()" disabled>下一步</base-button>
        </div>
    </section>
</template>

<script>
import axios from 'axios'
export default {
    name: 'Test',
    //定义的变量
    data() {
        return {
            mediaRecorder: null,
            mediaRecorder0: null,
            audiorecording: false,
            audiorecorded: false,
            audioBlob: null,
            videorecording: false,
            videorecorded: false,
            videoBlob: null,
            main_form: {
                chooseMicDeviceId: '', // 选择的麦克风id
            },
            Mic: [], // 可选择的麦克风
            times_audio: 0,
            times_video: 0,
            formData_video: new FormData()
        }
    },
    mounted() {
        this.getMic()
    },
    methods: {
        //开始录音
        audioStartRecording() {
            this.audiorecording = false
            navigator.mediaDevices
                .getUserMedia({ audio: true })
                .then((stream) => {
                    this.mediaRecorder = new MediaRecorder(stream)
                    const audioChunks = []
                    this.mediaRecorder.addEventListener('dataavailable', (event) => {
                        if (event.data.size > 0) {
                            audioChunks.push(event.data)
                        }
                    })
                    this.mediaRecorder.start()
                    this.audiorecording = true
                    this.mediaRecorder.addEventListener('stop', () => {
                        stream.getTracks().forEach((track) => track.stop())
                        this.audioBlob = new Blob(audioChunks, { type: 'audio/mp3' })
                        this.audiorecording = false
                        this.audiorecorded = true
                    })
                })
                .catch((error) => {
                    console.error('获取音频流失败：', error)
                })
        },
        //停止录音
        audioStopRecording() {
            document.querySelector('#audioplay').disabled = false
            this.mediaRecorder.stop()
            console.log(this.mediaRecorder, '停止录制')
        },
        //播放录音
        audioPlayRecording() {
            const audio = new Audio(URL.createObjectURL(this.audioBlob))
            console.log(audio)
            audio.play()
            this.times_audio += 1
            if (this.times_audio && this.times_video) {
                this.times_audio = 0
                this.times_video = 0
                const nextstep = document.querySelector('#nextstep')
                nextstep.disabled = false
            }
        },
        // 获取当前页面可以选择的麦克风
        getMic() {
            let that = this
            if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
                // 弹框获取麦克风
                navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
                    navigator.mediaDevices.enumerateDevices().then(function (devices) {
                        devices.forEach(function (device) {
                            console.log(devices)
                            if (device.kind === 'audioinput') {
                                // 麦克风
                                if (
                                    device.deviceId != 'default' &&
                                    device.deviceId != 'communications'
                                ) {
                                    that.Mic.push(device)
                                }
                            }
                        })
                    })
                    stream.getTracks().forEach((track) => track.stop())
                })
            }
        },
        // 语音输入
        voiceInput() {
            // 正在语音输入
            if (this.audiorecording) {
                this.audioStopRecording() // 停止输入
                document.querySelector('#button').style.backgroundColor = '#11CDEF'
            } else {
                // 开启语音输入
                this.audioStartRecording()
                document.querySelector('#button').style.backgroundColor = '#F87C95'
            }
        },
        start() {
            this.$emit('finish')
        },
        videoStartRecording() {
            // 设置视频约束条件 1280x720 30fps
            const constraints = {
                video: {
                    width: { ideal: 1280, max: 1280, min: 640 }, // 理想宽度为1280，允许的最小宽度为640
                    height: { ideal: 720, max: 720, min: 480 }, // 理想高度为720，允许的最小高度为480
                    frameRate: { ideal: 30, max: 30, min: 30 }, // 理想帧率为30fps，最大帧率也为30fps
                },
                audio: true, // 包含音频
            }

            this.audiorecording = false
            navigator.mediaDevices
                .getUserMedia(constraints)
                .then((stream) => {
                    console.log(stream.getVideoTracks()[0].getSettings().frameRate)
                    //document.querySelector('#player').removeAttribute('src')
                    document.querySelector('#player').setAttribute('autoplay', 'true')
                    this.$refs.video.srcObject = stream
                    this.mediaRecorder0 = new MediaRecorder(stream)
                    const videoChunks = []
                    this.mediaRecorder0.addEventListener('dataavailable', (event) => {
                        if (event.data.size > 0) {
                            videoChunks.push(event.data)
                        }
                    })
                    this.mediaRecorder0.start()
                    this.videorecording = true
                    this.mediaRecorder0.addEventListener('stop', () => {
                        stream.getTracks().forEach((track) => track.stop())
                        this.videoBlob = new Blob(videoChunks, { type: '"video/mp4"' })
                        const file = new File([this.videoBlob], 'recording.mp4', { type: 'video/mp4' })
                        // 将文件添加到formData_video中
                        this.formData_video.append('video', file)

                        alert("已经添加到formData_video中")
                        this.videorecording = false
                        this.videorecorded = true

                        // 上传视频到后端
                        try {
                            axios
                                .post('/video/upload/test', this.formData_video, {
                                    headers: {
                                        'Content-Type': 'multipart/form-data',
                                        token: this.$store.state.accessToken,
                                    },
                                })
                                .then((response) => {
                                    const { code, data } = response.data
                                    console.log('code' + code)
                                    if (code === 1) {
                                        this.showModal = true
                                        this.message = this.messages.messageScaleFinish
                                    } else {
                                        this.showModal = true
                                        this.message = this.messages.messageScaleError
                                    }
                                })
                        } catch (e) {
                            alert(e)
                            alert("测试视频上传失败，请检查与服务器的连接")
                        }
                    })


                })
                .catch((error) => {
                    console.error('获取视频流失败：', error)
                })
        },
        //停止视频
        videoStopRecording() {

            document.querySelector('#videoplay').disabled = false
            this.mediaRecorder0.stop()
            console.log(this.mediaRecorder, '停止录制')


        },
        //播放视频
        videoPlayRecording() {
            // const video = new Video(URL.createObjectURL(this.videoBlob))
            // console.log(video)
            // video.play()
            const player = document.getElementById('player')
            const url = URL.createObjectURL(this.videoBlob)
            player.removeAttribute('autoplay')
            this.$refs.video.srcObject = null
            player.src = url
            player.play()
            console.log(player)
            //this.formatePlayerDuration()
            this.getVideoData().then((videoInfo) => {
                console.log('videoInfo对象：', videoInfo)
                document.querySelector('#videoInfo').innerHTML =
                    'VideoInfo:<br>' +
                    'resolution:' +
                    videoInfo.height +
                    '*' +
                    videoInfo.width +
                    '<br>' +
                    'duration:' +
                    '3s'
            })
            this.times_video += 1
            if (this.times_audio && this.times_video) {
                this.times_audio = 0
                this.times_video = 0
                const nextstep = document.querySelector('#nextstep')
                nextstep.disabled = false
            }
        },
        videoInput() {
            // 正在视频输入
            if (this.videorecording) {
                this.videoStopRecording() // 停止输入
                document.querySelector('#button0').style.backgroundColor = '#11CDEF'
            } else {
                // 开启视频输入
                this.videoStartRecording()
                document.querySelector('#button0').style.backgroundColor = '#F87C95'
                document.querySelector('#button0').disabled = true
                setTimeout(() => {
                    this.videoStopRecording()
                    document.querySelector('#button0').style.backgroundColor = '#11CDEF'
                    document.querySelector('#button0').disabled = false
                }, 3000)
            }
        },
        getVideoData() {
            return new Promise((resolve, reject) => {
                let videoElement = document.querySelector('#player')
                videoElement.addEventListener('loadedmetadata', function () {
                    resolve({
                        width: this.videoWidth,
                        height: this.videoHeight,
                        duration: this.duration,
                        frameRate: this.webkitDecodedFrameCount / this.duration,
                    })
                })
            })
        },
        formatePlayerDuration() {
            const player = document.querySelector('#player')
            player.onloadedmetadata = (e) => {
                const video = e.target
                const videoDuration = video.duration
                if (videoDuration === Infinity) {
                    video.currentTime = 1e101
                    video.ontimeupdate = function () {
                        this.ontimeupdate = () => {
                            return
                        }
                        // 不重新设置currtTime,会直接触发audio的ended事件，因为之前将currentTime设置成了一个比音频时长还大的值。
                        // 注: 这里有一个问题，直接设置为0 是不起作用的。需要重新设置一下audio.currentTime = 1e101;然后再设置为0
                        video.currentTime = 1e101
                        video.currentTime = 0
                    }
                }
            }
        },
    },
}
</script>

<style>
.overlay-image {
    position: absolute;

    top: 10px;
    /* 根据需要调整位置 */

    left: 10px;
    /* 根据需要调整位置 */

    z-index: 10;
    /* 确保图片覆盖在视频上 */

    width: 100px;
    /* 根据需要调整图片大小 */

    height: auto;
}
</style>
