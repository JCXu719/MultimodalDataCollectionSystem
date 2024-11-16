<template>
  <section>
    <h1 class="text-center mt-3 mb-3">BFI-2 量表</h1>
    <div
      id="zero"
      style="display: none; padding-left: 160px; padding-top: 40px"
    >
      <h3 style="font-weight: 900;">数据提交中，请等待。</h3>
    </div>
    <p id="one" style="font-weight: 900;">
      下面是一些关于个人特征的描述，有些可能适用于你，有些可能不适用于你。
    </p>
    <p id="two" style="font-weight: 900;">比如，你是否同意“我是一个喜欢与他人待在一起的人”?</p>
    <p id="three" style="font-weight: 900;">
      用 1 至 5 描述你对于这些特征的认同程度，1 表示非常不认同，5 表示非常认同。
    </p>
    <div
      id="four"
      style="
        display: none;
        padding-top: 80px;
        padding-bottom: 40px;
        padding-left: 133px;
        font-weight: 900;
      "
    >
      <p style="font-weight: 900;">【预计需要1-3分钟左右,请勿点击任何按钮】</p>
    </div>
    <form @submit.prevent="submitForm" class="text-center">
      <div id="all">
        <div
          v-for="(question, index) in paginatedQuestions"
          :key="index"
          class="mt-3 mb-3"
        >
          <div>
            <label
              :for="'question-' + (currentPage * questionsPerPage + index)"
              class="form-label"
            >
              {{ question.text }}
            </label>
          </div>
          <label v-for="option in [1, 2, 3, 4, 5]" :key="option" class="mr-3">
            <base-radio
              :name="option"
              v-model="answers[currentPage * questionsPerPage + index]"
            >
              {{ option }}
            </base-radio>
          </label>
        </div>
        <div class="text-center mt-3 mb-3">
          <base-button
            v-if="currentPage != totalPages - 1"
            id="laststep"
            type="secondary"
            @click="prevPage"
            :disabled="currentPage === 0 || currentPage === totalPages - 1"
          >
            上一步
          </base-button>
          <base-button
            v-if="currentPage != totalPages - 1"
            type="primary"
            @click="nextPage"
            :disabled="currentPage >= totalPages - 2"
          >
            下一步
          </base-button>
          <base-button
            v-if="currentPage === totalPages - 2"
            type="success"
            native-type="submit"
          >
            提交
          </base-button>
          <!-- <base-button
            v-if="currentPage === totalPages - 1"
            type="info"
            native-type="submit"
          >
          </base-button> -->
          <slot v-if="currentPage === totalPages - 1"></slot>
        </div>
      </div>
    </form>
  </section>
</template>

<script>
export default {
  name: 'Scale',
  computed: {
    totalPages() {
      return Math.ceil(this.questions.length / this.questionsPerPage) + 1
    },
    paginatedQuestions() {
      const start = this.currentPage * this.questionsPerPage
      const end = start + this.questionsPerPage
      return this.questions.slice(start, end)
    },
  },
  methods: {
    async Setdata() {
      await axios({
        url: 'http://localhost:8080/user/getAll',
        method: 'get',
      })
        .then((response) => {
          console.log(response.data)
          const numbers = response.data
          this.Agreeableness = numbers[0] * 100
          this.Openness = numbers[1] * 100
          this.Extraversion = numbers[2] * 100
          this.Conscientiousness = numbers[3] * 100
          this.Neuroticism = numbers[4] * 100
        })
        .catch((err) => {
          this.showModal = true
          this.message = err.response.data['detail']
        })
    },
    prevPage() {
      if (this.currentPage > 0) this.currentPage--
    },
    nextPage() {
      if (this.currentPage < this.totalPages - 1) this.currentPage++
    },
    submitForm() {
    //   // 测试部分代码，可删，随机生成了60个回答
    //   if (this.testFlag == true) {
    //     // 下一行关键测试代码！著时候将不会随机产生60个问答
    //     // this.answers = this.generateRandomArray(60, 1, 5)
    //     // 判断选项是否为空
    //     console.log(this.answers)
    //     const csv = this.questions
    //       .map((question, index) => {
    //         return `${index + 1}, ${question.text},${this.answers[index]}`
    //       })
    //       .join('\n')
    //     const file = new File([csv], 'answer.csv', { type: 'text/mp4' })
    //     this.nextPage()
    //     // 临时测试下载文件到本地
    //     const url = URL.createObjectURL(file)
    //     const a = document.createElement('a')
    //     document.body.appendChild(a)
    //     a.style.display = 'none'
    //     a.href = url
    //     // 设置下载文件名
    //     a.download = 'localAnswer.csv'
    //     // 下载文件
    //     a.click()
    //     // 释放内存
    //     window.URL.revokeObjectURL(url)
    //     // 信息处理
    //     document.querySelector('#one').hidden = true

    //     console.log(document.querySelector('#one'))
    //     document.querySelector('#zero').style.display = 'block'
    //     document.querySelector('#one').style.display = 'none'
    //     document.querySelector('#two').style.display = 'none'
    //     document.querySelector('#three').style.display = 'none'
    //     document.querySelector('#four').style.display = 'block'
    //     setTimeout(() => {
    //       this.$emit('finish', file)
    //     }, 3000)
    //   }

      // 原代码
      if (
        this.answers.length < this.questions.length ||
        this.answers.includes(undefined)
      ) {
        this.$emit('empty')
      } else {
        const csv = this.questions
          .map((question, index) => {
            return `${index + 1}, ${question.text},${this.answers[index]}`
          })
          .join('\n')
        const file = new File([csv], 'answer.csv', { type: 'text/mp4' })
        this.nextPage()
        // 临时测试下载文件到本地
        const url = URL.createObjectURL(file)
        const a = document.createElement('a')
        document.body.appendChild(a)
        a.style.display = 'none'
        a.href = url
        // 设置下载文件名
        a.download = 'localAnswer.csv'
        // 下载文件
        a.click()
        // 释放内存
        window.URL.revokeObjectURL(url)
        // 信息处理
        document.querySelector('#one').hidden = true

        console.log(document.querySelector('#one'))
        document.querySelector('#zero').style.display = 'block'
        document.querySelector('#one').style.display = 'none'
        document.querySelector('#two').style.display = 'none'
        document.querySelector('#three').style.display = 'none'
        document.querySelector('#four').style.display = 'block'
        setTimeout(() => {
          this.$emit('finish', file)
        }, 3000)
      }
    },
    generateRandomArray(length, min, max) {
      const arr = []
      for (let i = 0; i < length; i++) {
        const randomNum = Math.floor(Math.random() * (max - min + 1)) + min
        arr.push(randomNum)
      }
      this.randomArray = arr
      return arr
    },
  },
  data() {
    return {
      questions: [
        { text: '性格外向，喜欢交际。' },
        { text: '心肠柔软，有同情心。' },
        { text: '缺乏条理' },
        { text: '从容，善于处理压力。' },
        { text: '对艺术没有什么兴趣。' },
        { text: '性格坚定自信，敢于表达自己的观点。' },
        { text: '为人恭谦，尊重他人。' },
        { text: '比较懒。' },
        { text: '经历挫折后仍能保持积极心态。' },
        { text: '对许多不同的事物都感兴趣。' },
        { text: '很少觉得兴奋或者特别想要（做）什么。' },
        { text: '常常挑别人的毛病。' },
        { text: '可信赖的，可靠的。' },
        { text: '喜怒无常，情绪起伏较多。' },
        { text: '善于创造，能找到聪明的方法来做事。' },
        { text: '比较安静。' },
        { text: '对他人没有什么同情心。' },
        { text: '做事有计划有条理。' },
        { text: '容易紧张。' },
        { text: '着迷于艺术、音乐或文学。' },
        { text: '常常处于主导地位，像个领导一样。' },
        { text: '常与他人意见不和。' },
        { text: '很难开始行动起来去完成一项任务。' },
        { text: '觉得有安全感，对自己满意。' },
        { text: '不喜欢知识性或者哲学性强的讨论。' },
        { text: '不如别人有活力。' },
        { text: '宽宏大量。' },
        { text: '有时比较没有责任心。' },
        { text: '情绪稳定，不易生气。' },
        { text: '几乎没有什么创造性。' },
        { text: '有时会害羞，比较内向。' },
        { text: '乐于助人，待人无私。' },
        { text: '习惯让事物保持整洁有序。' },
        { text: '时常忧心忡忡，担心很多事情。' },
        { text: '重视艺术与审美。' },
        { text: '感觉自己很难对他人产生影响。' },
        { text: '有时对人比较粗鲁。' },
        { text: '有效率，做事有始有终。' },
        { text: '时常觉得悲伤。' },
        { text: '思想深刻。' },
        { text: '精力充沛。' },
        { text: '不相信别人，怀疑别人的意图。' },
        { text: '可靠的，总是值得他人信赖。' },
        { text: '能够控制自己的情绪。' },
        { text: '缺乏想象力。' },
        { text: '爱说话，健谈。' },
        { text: '有时对人冷淡，漠不关心。' },
        { text: '乱糟糟的，不爱收拾。' },
        { text: '很少觉得焦虑或者害怕。' },
        { text: '觉得诗歌、戏剧很无聊。' },
        { text: '更喜欢让别人来领头负责。' },
        { text: '待人谦逊礼让。' },
        { text: '有恒心，能坚持把事情做完。' },
        { text: '时常觉得郁郁寡欢。' },
        { text: '对抽象的概念和想法没什么兴趣。' },
        { text: '充满热情。' },
        { text: '把人往最好的方面想。' },
        { text: '有时候会做出一些不负责任的行为。' },
        { text: '情绪多变，容易愤怒。' },
        { text: '有创意，能想出新点子。' },
      ],
      answers: [],
      currentPage: 0,
      questionsPerPage: 5,
      testFlag: true,
      message: '',
    }
  },
}
</script>
<style scoped></style>
