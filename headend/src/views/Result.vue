<template>
    <div class="profile-page" style="margin-top: -150px">
        <section class="section-profile-cover section-shaped my-0">
            <div class="shape shape-style-1 shape-primary shape-skew alpha-4 bg-gradient-info">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>
        </section>
        <section class="section section-skew">
            <div class="container">
                <card shadow class="card-profile mt--300" no-body>
                    <div class="px-4">
                        <div class="row justify-content-center"></div>
                        <div class="text-center mt-5">
                            <h2 style="color: rgb(17, 116, 239)">
                                祝贺您完成本次数据采集任务！
                            </h2>
                            <h2 style="color: rgb(17, 116, 239)">感谢您的参与配合！</h2>
                            <br />
                            <div class="h6 font-weight-300" style="color: rgb(17, 56, 200)">
                                <i class="ni location_pin mr-2"></i>具体奖励将于数据审核通过后发放，请耐心等待！
                            </div>
                        </div>
                        <div class="mt-5 py-5 border-top text-center">
                            <base-button type="info" style="margin-top: -20px; margin-bottom: 50px"
                                @click="Setdata()">查看结果</base-button>
                            <div style="display: none" id="result">
                                <h3 style="color: rgb(17, 143, 239)">
                                    根据AI分析，您的大五人格特质如下:
                                </h3>
                                <base-progress type="success" :value="Agreeableness"
                                    label="宜人性（Agreeableness）"></base-progress>
                                <base-progress type="warning" :value="Openness" label="开放性（Openness）"></base-progress>
                                <base-progress type="danger" :value="Extraversion"
                                    label="外倾性（Extraversion）"></base-progress>
                                <base-progress type="primary" :value="Conscientiousness"
                                    label="尽责性（Conscientiousness）"></base-progress>
                                <base-progress type="default" :value="Neuroticism" label="神经质（Neuroticism）"
                                    style=""></base-progress>
                                <div class="mt-5 py-5 border-top text-left">
                                    <h5 style="color:dodgerblue; font-weight: 900;">Q&A 上述的评测结果有什么含义？</h5>
                                    <p style="font-weight: 600;">大五人格模型(Big
                                        Five)将人格特质视为不同的维度。这个模型将人格分为开放性、尽责性、外倾性、宜人性和神经质这五个不同的维度。每个人在这五个维度上都有不同程度的得分
                                        （连续的值，0-100），这些得分构成了个体在人格特质上的基本特征，当前维度的得分越高说明该特质越强</p>
                                    <p style="font-weight: 600;">对五个维度的具体含义解释如下</p>
                                    <p style="font-weight: 600;"><span style="color: rgb(43,209,136);">宜人性（Agreeableness）： </span>代表个体的合作性、亲和力和对他人的信任和理解。亲和性高的人通常友善、慷慨、合作，并能与他人和睦相处。 </p>
                                    <p style="font-weight: 600;"> <span style="color: rgb(251,98,63);">开放性（Openness）：</span> 意味着个体对新鲜事物和新体验的接受程度。开放性高的人喜欢探索新观念、艺术、文化和思想，富有想象力和创造力。</p>
                                    <p style="font-weight: 600;"><span style="color: rgb(242,54,94);">外倾性（Extraversion）：</span> 表示个体在社交和情感上的倾向。外倾性高的人喜欢社交互动，乐观、活跃、爱交朋友，并能从社交中获得能量。 </p>
                                    <p style="font-weight: 600;"><span style="color: rgb(91,116,226);">尽责性（Conscientiousness）：</span> 描述个体的自律性、目标导向性和有条理性。尽责性强的人通常组织有序、自律，更倾向于完成任务和遵守规则。 </p>
                                    <p style="font-weight: 600;"> <span style="color: rgb(23,43,76);">神经质（Neuroticism）：</span> 描述个体情绪稳定性和对压力的应对能力。神经质高的人可能更容易焦虑、情绪波动，并对压力更敏感。</p>

                                </div>
                                <img src="../../BigFive.png"><br>
                                <router-link to="/">
                                    <base-button type="info" style="margin-top: 40px" @click="logOut()">完成，退出系统</base-button>
                                </router-link>
                            </div>


                        </div>


                    </div>
                </card>
            </div>
        </section>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    methods: {
        // 点击按钮时将store的数据获取显示
        async Setdata() {
            const score_array = this.$store.state.score_array
            this.Openness = Math.floor(score_array[0] * 100 * 100) / 100
            this.Conscientiousness = Math.floor(score_array[1] * 100 * 100) / 100
            this.Extraversion = Math.floor(score_array[2] * 100 * 100) / 100
            this.Agreeableness = Math.floor(score_array[3] * 100 * 100) / 100
            this.Neuroticism = Math.floor(score_array[4] * 100 * 100) / 100
            
            setTimeout(() => {
                document.querySelector('#result').style.display = '' //显示
            }, 50)
        },
        logOut(){
            this.$store.commit('CLEAR_ACCESS_TOKEN')
            console.log(this.$store.accessToken)
        }
    },
    data() {
        return {
            Agreeableness: '',
            Openness: '',
            Extraversion: '',
            Conscientiousness: '',
            Neuroticism: '',
        }
    },
}
</script>
<style></style>
