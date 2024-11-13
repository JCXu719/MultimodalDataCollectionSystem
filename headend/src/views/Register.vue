<template>
    <section class="section section-shaped section-lg my-0">
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
                <div class="col-lg-6">
                    <card type="secondary" shadow header-classes="bg-white pb-5" body-classes="px-lg-5 py-lg-5"
                        class="border-0">
                        <div class="text-center text-muted mb-4">
                            <h1 class="display-4">注册</h1>
                        </div>
                        <form role="form" @submit.prevent="registerUser()">
                            <base-input placeholder="用户名（20字以内）" addon-left-icon="fa fa-user" v-model="username"
                                id="username" alternative :valid="checkname" @blur="Setnamesafe">
                            </base-input>
                            <base-input placeholder="姓名" addon-left-icon="fa fa-user-circle-o" v-model="realname"
                                id="realname" alternative :valid="checkrealname" @blur="Setrealnamesafe"></base-input>
                            <base-input placeholder="学号（限合肥工业大学学生）" addon-left-icon="fa fa-id-card" v-model="sid"
                                id="number" alternative :valid="checknumber" @blur="Setnumbersafe">
                            </base-input>
                            <base-input placeholder="班级（eg.计算机22-1班）" addon-left-icon="fa fa-bandcamp"
                                v-model="classcode" id="class" alternative :valid="checkclass" @blur="Setclasssafe">
                            </base-input>
                            <base-input type="password" placeholder="密码（8-20字，必须包含大小写字母和数字）"
                                addon-left-icon="fa fa-lock" v-model="password" id="pass" alternative :valid="checkpass"
                                @blur="Setpasssafe">
                            </base-input>
                            <base-input type="password" placeholder="确认密码" addon-left-icon="fa fa-lock"
                                v-model="confirmPassword" id="confir" alternative :valid="checkconfir"
                                @blur="Setconfirsafe">
                            </base-input>

                            <base-input placeholder="手机号码" addon-left-icon="fa fa-phone" v-model="phone" id="phone"
                                alternative :valid="checkphone" @blur="Setphonesafe">
                            </base-input>

                            <base-input placeholder="QQ号（5-12位数字）" addon-left-icon="fa fa-handshake-o" v-model="qq"
                                id="qq" alternative :valid="checkqq" @blur="Setqqsafe">
                            </base-input>

                            <base-input addon-left-icon="fa fa-calendar">
                                <flat-picker :config="{ allowInput: true }" class="form-control datepicker"
                                    placeholder="出生日期" v-model="birthday">
                                </flat-picker>
                            </base-input>
                            <div class="row justify-content-center mt-3 mb-3">
                                <span class="col-lg-4">性别</span>
                                <base-radio name="1" class="col-lg-4" v-model="gender">
                                    <span>男</span>
                                </base-radio>
                                <base-radio name="0" class="col-lg-4" v-model="gender">
                                    <span>女</span>
                                </base-radio>
                            </div>
                            <div class="row justify-content-center mt-3 mb-3">
                                <span class="col-lg-4">教育程度</span>
                                <base-radio name="0" class="col-lg-4" v-model="educationLevel">
                                    <span>小学及以下</span>
                                </base-radio>
                                <base-radio name="1" class="col-lg-4" v-model="educationLevel">
                                    <span>初高中</span>
                                </base-radio>
                            </div>
                            <div class="row justify-content-center mt-3 mb-3">
                                <span class="col-lg-4"></span>
                                <base-radio name="2" class="col-lg-4" v-model="educationLevel">
                                    <span>本科</span>
                                </base-radio>
                                <base-radio name="3" class="col-lg-4" v-model="educationLevel">
                                    <span>硕博研究生</span>
                                </base-radio>
                            </div>
                            <!-- <base-checkbox v-model="agreeToPrivacyPolicy">
                  <span style="padding-left: 8px"
                    >我同意 <a href="#">隐私政策</a></span
                  >
                </base-checkbox> -->
                            <div style="margin-top: 36px">
                                <span>职业</span>
                                <span style="margin-left: 18px; font-weight: bold; font-size: 15px"
                                    @click="; (textOpenFlag0 = !textOpenFlag0), showAll0()">{{ textOpenFlag0 ? '收起' :
                                        '展开'
                                    }}<i class="ni ni-bold-down"></i></span>
                                <div class="listBox0">
                                    <div class="list0" v-for="(c, index) of labelList0" :key="index"
                                        :class="{ checked: arr0.includes(index) }" @click="checkedBox0(index)">
                                        {{ c }}
                                    </div>
                                </div>
                            </div>
                            <div style="margin-top: 26px">
                                <span>兴趣爱好</span>
                                <span style="margin-left: 18px; font-weight: bold; font-size: 15px"
                                    @click="; (textOpenFlag = !textOpenFlag), showAll()">{{ textOpenFlag ? '收起' : '展开'
                                    }}<i class="ni ni-bold-down"></i></span>
                                <div class="listBox">
                                    <div class="list" v-for="(c, index) of labelList" :key="index"
                                        :class="{ checked: arr.includes(index) }" @click="checkedBox(index)">
                                        {{ c }}
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <base-button type="info" class="my-4" @click="registerUser()" style="width: 120px">
                                    注册账号
                                </base-button>
                            </div>
                        </form>
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
import flatPicker from 'vue-flatpickr-component'
import 'flatpickr/dist/flatpickr.css'

var ConfirmName = /^[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$/ //1-20位登陆用户名的校验(英文可包含数字)
var ConfirmRealname = /^[a-zA-Z0-9_\u4e00-\u9fa5]{2,10}$/ //2-10位用户真实姓名
var ConfirmClass = /[\s\S]{3,}$/ // 用户班级
var ConfirmPhone = /^(?:\+?86)?1\d{10}$/ //11位手机号的校验
var ConfirmPwd = /^[a-zA-Z0-9_\u4e00-\u9fa5]{8,20}$/ //8-20位登录密码，包含数字特殊字符，大小写的校验
var ConfirmID = /^[0-9]{10}$/ //10位学号的校验
var ConfirmQQ = /^[0-9]{5,12}$/

export default {
    components: {
        Modal,
        flatPicker,
    },
    methods: {
        async registerUser() {
            if (
                !this.username ||
                !this.password ||
                !this.confirmPassword ||
                !this.sid ||
                !this.phone ||
                !this.birthday ||
                !this.educationLevel ||
                !this.career ||
                !this.hobbies
            ) {
                this.showModal = true
                this.message = '请填写所有信息'
            } else if (!ConfirmName.exec(this.username)) {
                this.showModal = true
                this.message = '请输入符合规范的用户名'
            } else if (!ConfirmRealname.exec(this.realname)) {
                this.showModal = true
                this.message = '请输入符合规范的姓名'
            } else if (!ConfirmID.exec(this.sid)) {
                this.showModal = true
                console.log(this.sid)
                this.message = '请输入符合规范的学号'
            } else if (!ConfirmClass.exec(this.classcode)) {
                this.showModal = true
                this.message = '请输入符合规范的班级'
            } else if (!ConfirmPwd.exec(this.password)) {
                this.showModal = true
                this.message = '请输入符合规范的登录密码'
            } else if (this.password !== this.confirmPassword) {
                this.showModal = true
                this.message = '请注意两次密码输入一致'
            } else if (!ConfirmPhone.exec(this.phone)) {
                this.showModal = true
                this.message = '请输入符合规范的手机号码'
            } else if (!ConfirmQQ.exec(this.qq)) {
                this.showModal = true
                this.message = '请输入符合规范的QQ号'
            } else if (!this.arr.length) {
                this.showModal = true
                console.log(this.arr)
                this.message = '请选择个人兴趣爱好'
            } else if (!this.arr0.length) {
                this.showModal = true
                console.log(this.arr0)
                this.message = '请选择个人职业'
            }
            else {

                // 查看人数是否达到上限
                try {
                    await axios
                        .get(
                            '/user/num',
                            {
                                headers: {
                                    'Content-Type': 'application/json',
                                    'token': this.$store.state.accessToken,
                                },
                            }
                        )
                        .then((response) => {
                            const { code, data } = response.data
                            if (code === 1) {
                                if (data >= 100) {
                                    this.showModal = true
                                    this.message = '注册人数已满'
                                }else{
                                    console.log("未满")
                                }
                            } else {
                                this.showModal = true
                                this.message = '注册失败，无法读取注册人数'
                            }
                        })
                } catch (e) {
                    this.showModal = true
                    this.message = "注册失败，未连接到服务器或服务器未启动"
                }

                // 未达到上限，正常采集
                try {
                    await axios
                        .post(
                            '/user/register',
                            {
                                studentId: this.sid,
                                username: this.username,
                                password: this.password,
                                classCode: this.classcode,
                                role: 1,
                                qq: this.qq,
                                gender: this.gender,
                                educationLevel: this.educationLevel,
                                birthDate: this.birthday,
                                phone: this.phone,
                                avatarUrl: 'nothing',
                                fullname: this.realname,
                                career: this.career,
                                hobbies: this.str_hobbies
                            },
                            {
                                headers: {
                                    'Content-Type': 'application/json',
                                },
                            }
                        )
                        .then((response) => {
                            const { code, data } = response.data
                            if (code === 1) {
                                this.showModal = true
                                this.message = '注册成功，三秒后跳转'
                                setTimeout(() => {
                                    this.$router.push('/login')
                                }, 3000)
                            } else {
                                this.showModal = true
                                this.message = '注册失败，用户名/学号/手机号可能存在重复，有疑问请咨询管理员'
                            }
                        })
                } catch (e) {
                    this.showModal = true
                    this.message = "注册失败，未连接到服务器或服务器未启动"
                }
            }
        },
        showAll() {
            if (!this.textOpenFlag) {
                //document.querySelector('.listBox').style.visibility = 'hidden'
                document.querySelector('.listBox').style.display = 'none'
            } else {
                //document.querySelector('.listBox').style.visibility = 'visible'
                document.querySelector('.listBox').style.display = 'flex'
            }
        },
        // 点击多选标签
        checkedBox(i) {
            if (this.arr.includes(i)) {
                console.log("yesinclude")
                //includes()方法判断是否包含某一元素,返回true或false表示是否包含元素，对NaN一样有效
                //filter()方法用于把Array的某些元素过滤掉，filter()把传入的函数依次作用于每个元素，然后根据返回值是true还是false决定保留还是丢弃该元素：生成新的数组
                this.arr = this.arr.filter(function (ele) {
                    return ele != i
                })
            } else {
                this.arr.push(i)
                this.hobbies.push(this.labelList[i])
                this.str_hobbies = this.hobbies.join()
            }
        },
        showAll0() {
            if (!this.textOpenFlag0) {
                //document.querySelector('.listBox').style.visibility = 'hidden'
                document.querySelector('.listBox0').style.display = 'none'
            } else {
                //document.querySelector('.listBox').style.visibility = 'visible'
                document.querySelector('.listBox0').style.display = 'flex'
            }
        },
        // 点击单选标签
        checkedBox0(i) {
            if (this.arr0.includes(i)) {
                //includes()方法判断是否包含某一元素,返回true或false表示是否包含元素，对NaN一样有效
                //filter()方法用于把Array的某些元素过滤掉，filter()把传入的函数依次作用于每个元素，然后根据返回值是true还是false决定保留还是丢弃该元素：生成新的数组
                this.arr0 = this.arr0.filter(function (ele) {
                    return ele != i
                })
            } else {
                this.arr0 = []
                this.arr0.push(i)
                console.log(this.arr0)
                this.career = this.labelList0[this.arr0[0]]
            }
        },
        Setnamesafe() {
            const name = document.querySelector('#username')
            if (ConfirmName.exec(name.value)) {
                this.checkname = true
            } else {
                this.checkname = false
            }
        },
        Setrealnamesafe() {
            const realname = document.querySelector('#realname')
            if (ConfirmRealname.exec(realname.value)) {
                this.checkrealname = true
            } else {
                this.checkrealname = false
            }
        },
        Setnumbersafe() {
            const number = document.querySelector('#number')
            if (ConfirmID.exec(number.value)) {
                this.checknumber = true
            } else {
                this.checknumber = false
            }
        },
        Setclasssafe() {
            const classes = document.querySelector('#class')
            if (ConfirmClass.exec(classes.value)) {
                this.checkclass = true
            } else {
                this.checkclass = false
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
        Setconfirsafe() {
            const confir = document.querySelector('#confir')
            if (
                ConfirmPwd.exec(confir.value) &&
                this.password == this.confirmPassword
            ) {
                this.checkconfir = true
            } else {
                this.checkconfir = false
            }
        },
        Setphonesafe() {
            const phone = document.querySelector('#phone')
            if (ConfirmPhone.exec(phone.value)) {
                this.checkphone = true
            } else {
                this.checkphone = false
            }
        },
        Setqqsafe() {
            const qq = document.querySelector('#qq')
            if (ConfirmQQ.exec(qq.value)) {
                this.checkqq = true
            } else {
                this.checkqq = false
            }
        },
    },
    data() {
        return {
            username: '',
            realname: '',
            password: '',
            confirmPassword: '',
            sid: '',
            phone: '',
            birthday: '',
            gender: '',
            educationLevel: '',
            agreeToPrivacyPolicy: true,
            showModal: false,
            message: '',
            classcode: '',
            qq: '',
            checkname: ' ',
            checknumber: ' ',
            checkclass: ' ',
            checkpass: ' ',
            checkconfir: ' ',
            checkphone: ' ',
            checkqq: ' ',
            checkrealname: ' ',
            //兴趣爱好，选取常见的35个，参照维基百科的爱好列表
            labelList: [
                '看动漫', '艺术', '烹饪', '跳舞', '追剧',
                '玩游戏', '书法', '下棋', '收藏物品', '编程',
                '唱歌', '写作', 'DIY', '绘画', '跑步',
                '时尚', '学外语', '武术', '听音乐', '按摩',
                '冥想', '表演', '摄影', '乐器演奏', '养宠物',
                '演讲', '猜谜', '阅读', '登山', '购物',
                '球类运动', '游泳', '旅行', '钓鱼', '健身'
            ],
            arr: [],
            hobbies: [],
            hobbies_str: null,
            textOpenFlag: true, // 爱好展开/收起 判定
            // 职业
            labelList0: [
                '专业人士（如教师/医生/律师等）',
                '服务业人员（餐饮服务员/司机/售货员等）',
                '自由职业者（如作家/艺术家/摄影师/导游等）',
                '工人（如工厂工人/建筑工人/城市环卫工人等）',
                '公司职员',
                '商人/雇主',
                '小商贩/个体户',
                '事业单位/公务员/政府工作人员',
                '学生',
                '家庭主妇',
                '农民/牧民/渔夫',
                '无业/失业',
                '其他'
            ],
            arr0: [],
            textOpenFlag0: true, // 职业展开/收起 判定
            career: null,
        }
    },
}
</script>
<style>
.listBox {
    margin-top: 26px;
    margin-left: -12px;
    flex-wrap: wrap;
    width: auto;
    display: flex;
}

.list {
    border: 1px solid #bec3cb;
    padding: 11px 27px;
    border-radius: 30px;
    margin-right: 18px;
    margin-bottom: 20px;
    cursor: pointer;
    height: 28px;
    width: auto;
    font-size: 12px;
    font-family: '微软雅黑';
    line-height: 5px;
    white-space: nowrap;
    font-weight: bold;
}

.listBox0 {
    margin-top: 26px;
    margin-left: -12px;
    flex-wrap: wrap;
    width: auto;
    display: flex;
}

.list0 {
    border: 1px solid #bec3cb;
    padding: 11px 27px;
    border-radius: 30px;
    margin-right: 18px;
    margin-bottom: 20px;
    cursor: pointer;
    height: 28px;
    width: auto;
    font-size: 12px;
    font-family: '微软雅黑';
    line-height: 5px;
    white-space: nowrap;
    font-weight: bold;
}

.checked {
    color: rgb(0, 49, 82);
    background: rgb(233, 235, 254);
    border: 1px solid rgb(233, 235, 254);
}
</style>
