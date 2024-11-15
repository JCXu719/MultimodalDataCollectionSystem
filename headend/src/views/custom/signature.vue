<template>
    <section style="height: auto;">
        <h1 class="text-center mt-3 mb-3" style="color: red; font-weight: 900;">实验知情同意书</h1>
        <p style="font-weight: 900;">
            您好，我们是合肥工业大学计算机与信息学院媒体计算实验室的研究团队。我们正邀请您参加一项心理学研究。请<span class="font-weight-bold"
                style="color: red">仔细阅读本知情同意书并决定是否参与我们的研究</span>
        </p>
        <h4 style="font-weight: 900;">1. 研究目的</h4>
        <p style="font-weight: 900;">
            本研究借助多种生理刺激、问答和行为检测方法，以期研究自动的大五人格评测方法。当下流行的MBTI人格测试虽然一定程度上能反映真实人格，但其测试结果的离散性以及无负面性导致学界对其科学性持质疑态度，因此，本项目的研究应用了心理学界广泛认可的大五人格模型，并致力于研究自动的大五人格评测方法。快速准确的自动人格识别和分析方法将极大地便利对个体心理的准确分析和把控，并助推情感呵护、心理健康分析等领域的发展
        </p>
        <h4 style="font-weight: 900;">2. 研究步骤</h4>
        <p style="font-weight: 900;">
            本研究目前需要招募一批数据采集的志愿者，如果您符合入组标准，在您签署本知情同意书后，我们开始记录您的人口学信息等，并进行数据采集工作，全流程预计时间为30分钟~1小时。<br />
            <span class="font-weight-bold">a.入组标准：</span>1) 不符合DSM-IV
            诊断标准中所列出的任何一种精神疾病；2）
            无精神疾病家族史；3）无较严重的心脑血管疾病，神经系统疾病；4）精神正常，意识清醒，身体健康，具备基本的自理能力；5）年龄18岁及以上；6)
            签署知情同意书；7）承诺不会泄露实验过程的具体细节<br />
            <span class="font-weight-bold">b. 人口学信息：</span>性别、年龄、受教育程度、职业、兴趣爱好等<br />
            <span
                class="font-weight-bold">c.采集流程：</span>采集全程会对您进行录音和录像。采集流程分为如下4步：1）视频刺激：观看一系列视频片段；2）基本问答：回答几个开放式问题；3）墨迹测试：观看墨迹图并陈述感受；4）量表填写：填写一份人格量表。
        </p>
        <h4 style="font-weight: 900;">3. 本研究的风险</h4>
        <p style="font-weight: 900;">
            本研究不采用任何侵入性实验手段，全程无害，数据采集全过程您可以保持放松的状态，按照真实情况进行反应和作答即可。在研究进行中的任何时刻，您都有权利选择退出本研究。
        </p>
        <h4 style="font-weight: 900;">4. 信息保密情况</h4>
        <p style="font-weight: 900;">
            本研究中收集的您所有的个人信息和观察记录将会以电子及书面形式保存并保密，仅用于研究和科学分析。签署了这份知情同意书，就表明您同意使用您的信息。<br /><br />
            在科学会议或者科学杂志上发表本研究获得的研究信息和数据时，您的身份不会被公开，伦理委员会、国家主管部门和研究者有权查阅研究内容。要求您进行的所有监测及实验室检查均为研究需要。研究结果根据国家规定至少保存5年。
        </p >
        <h4 style="font-weight: 900;">5. 参与本研究获得的奖励</h4>
        <p style="font-weight: 900;">
            完成初筛阶段数据采集，在核实视听数据和电子量表的可用性及可靠性之后，您可以获得如下的奖励。<br><br>
数据存在严重问题或错误，无法使用则无法获得志愿时长；完成采集且数据质量合格即可获得1小时志愿时长；数据质量较高（完全符合要求）则可追加额外的1小时志愿时长奖励，即总共2小时。<br><br>
在采集过程中，如果发现任何系统的bug，可以向QQ群管理员提出，从而获得额外的志愿时长奖励。<br><br>
测试结束后，您还可以查看经量表和AI分析的您的大五人格维度分析结果，从而便于您更好地了解自身的人格和心理。
        </p>
        <h4 style="font-weight: 900;">6. 自愿参加的原则</h4>
        <p style="font-weight: 900;">
            您可以选择不参与本研究；在同意参与本研究后，您可以随时退出，无须理由。此外，研究者有权在研究过程中任何时间终止您的研究，不管是否得到您的同意
        </p>
        <h4 style="font-weight: 900;">7. 知情同意声明</h4>
        <p style="font-weight: 900;">
            “我已告知该受试者的研究背景、目的、步骤、风险及获益情况，给予他/她足够的时间阅读知情同意书、与他人讨论，并解答了其有关研究的问题；我已告知该受试者他/她随时可以退出本研究；我已告知该受试者他/她将得到这份知情同意书的副本，上面包含我和他/她的签名。”
        </p>
        <p style="font-weight: 900;color: red;">
            如果您已充分了解本数据采集的相关信息并愿意进行数据采集工作，请在下方区域内签名。（提示：可以在下方文本框中输入姓名，也可以在画板中签名）
        </p>

        <div class="text-center mt-3 mb-3">
            <base-input v-model="username" placeholder="请输入您的姓名"></base-input>
            <canvas ref="signatureCanvas" style="border: 2px solid #000000" width="500" height="200"></canvas>
            <div class="mt-3 mb-3">
                <base-button type="secondary" @click="clearSignature">清除</base-button>
                <base-button type="primary" disabled="true" @click="saveSignature" id="button">确定({{ notetimes
                    }})</base-button>
                <!--disabled="true"-->
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
        setupCanvas() {
            this.context.strokeStyle = '#000000'
            this.context.lineWidth = 2
            this.context.lineCap = 'round'

            this.$refs.signatureCanvas.addEventListener(
                'mousedown',
                this.startDrawing
            )
            this.$refs.signatureCanvas.addEventListener('mousemove', this.draw)
            this.$refs.signatureCanvas.addEventListener('mouseup', this.stopDrawing)
            this.$refs.signatureCanvas.addEventListener(
                'mouseleave',
                this.stopDrawing
            )

            this.drawName()
        },
        startDrawing(event) {
            this.isDrawing = true
            this.context.beginPath()
            this.context.moveTo(event.offsetX, event.offsetY)
        },
        draw(event) {
            if (!this.isDrawing) {
                return
            }
            this.context.lineTo(event.offsetX, event.offsetY)
            this.context.stroke()
        },
        stopDrawing() {
            if (!this.isDrawing) {
                return
            }
            this.isDrawing = false
            this.context.closePath()
        },
        clearSignature() {
            this.context.clearRect(
                0,
                0,
                this.$refs.signatureCanvas.width,
                this.$refs.signatureCanvas.height
            )
            this.drawName()
        },
        saveSignature() {
            // debug
            //this.$emit('finish')
            // 优先判断是否已经采集过，若已经采集过则直接返回
            axios({
                url: '/user/checkIfCollected',
                method: 'get',
                headers: { token: this.$store.state.accessToken },
            })
                .then((response) => {
                    const { code, data } = response.data
                    console.log(code)
                    // 后续需要加上这个判断逻辑
                    if (code === 0) {
                        alert("您已经参与过本次数据采集任务，请退出")
                        return
                    } else {
                        const canvas = this.$refs.signatureCanvas
                        const isEmpty = !this.context
                            .getImageData(0, 0, canvas.width, canvas.height)
                            .data.some((value) => value !== 0)

                        if (isEmpty) {
                            this.$emit('empty')
                        } else {
                            const dataURL = canvas.toDataURL('image/png')
                            const byteString = atob(dataURL.split(',')[1])
                            const mimeString = dataURL.split(',')[0].split(':')[1].split(';')[0]
                            const arrayBuffer = new Uint8Array(byteString.length)
                            for (let i = 0; i < byteString.length; i++) {
                                arrayBuffer[i] = byteString.charCodeAt(i)
                            }
                            const blob = new Blob([arrayBuffer], { type: mimeString })
                            const file = new File([blob], 'signature.png', {
                                type: 'image/png',
                            })
                            this.$emit('finish', file)
                        }
                    }
                })
                .catch((err) => {
                    alert("登录失效，请重新登录")
                    this.$router.push('/login')
                })
        },
        drawName() {
            if (this.username) {
                const canvasWidth = this.$refs.signatureCanvas.width
                const canvasHeight = this.$refs.signatureCanvas.height
                this.context.clearRect(0, 0, canvasWidth, canvasHeight)

                this.context.textAlign = 'center'
                this.context.textBaseline = 'middle'

                let fontSize = 50
                this.context.font = `${fontSize}px Arial`

                while (
                    this.context.measureText(this.username).width >
                    canvasWidth - 20
                ) {
                    fontSize--
                    this.context.font = `${fontSize}px Arial`
                }

                this.context.fillStyle = '#000000'
                this.context.fillText(this.username, canvasWidth / 2, canvasHeight / 2)
            }
        },
    },
    watch: {
        username() {
            this.clearSignature()
        },
    },
    data() {
        return {
            isDrawing: false,
            context: null,
            showModal: false,
            message: null,
            username: '',
            // 测试时间暂时设置为1
            times: 1,
        }
    },
    mounted() {
        const canvas = this.$refs.signatureCanvas
        this.context = canvas.getContext('2d')
        this.setupCanvas()
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
