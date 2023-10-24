<template>
  <section class="learning">
    <div class="breadcrumb-wrap exercise">
      <el-button v-if="enableCancel" class="cancel-practice" type="primary" @click="handleCancelPractice">取消练习</el-button>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item to="/purchased/coruses">我的课程</el-breadcrumb-item>
        <el-breadcrumb-item>学习详情</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <named-header class="header-wrap">
      <label>学科名称：</label>
      <span>{{statistics.subjectName || subjectName}}</span>
      <template v-if="false">
      <label>学习次数:</label>
      <span></span>
      </template>
      <template v-if="view">
        <label>总共耗时:</label>
        <span>{{statistics.useTime}}</span>
      </template>
    </named-header>
    <div class="list-wrap">
      <practice-question :view="view" :key="topic.id" :item="topic" :index="viewIndex" :user-answer="topic.userAnswer">
        <template slot-scope="scope">
          <answer-dashboard
            v-if="topic.userAnswer"
            :user-answer="scope.userAnswerText"
            :right-answer="scope.rightAnswerText"
            :status="topic.pstatus"
            :topic-type="scope.topicType">
          </answer-dashboard>
          <div class="answer-button-group">
            <el-button v-if="scope.index == 1" type="info" disabled>上一题</el-button>
            <el-button v-else type="primary" @click="jumQuestion(scope.index - 1)">上一题</el-button>
            <el-button v-if="!(topic.userAnswer || scope.view)" type="primary" @click="handleSubmit(scope.userAnswer)">提交</el-button>
            <el-button v-else-if="scope.index === statistics.total" type="info" disabled>下一题</el-button>
            <el-button v-else type="primary" @click="jumQuestion(scope.index + 1)">下一题</el-button>
          </div>
        </template>
      </practice-question>
    </div>
    <statistic-dashboard :statistic="statistics" :subject-name="subjectName"/>
    <answer-status-sheet :list="answerStatusList" @on-selected="jumQuestion"/>
  </section>
</template>

<script>
import StatisticDashboard from './components/Statistics.vue'
import AnswerStatusSheet from './components/AnswerStatusSheet'

export default {
  name: 'course-learning',
  props: {
    subject: {
      type: String,
      required: true
    },
    view: Boolean,
    trial: Boolean
  },
  filters: {
    textClasses (status) {
      return status === 2 ? 'text-success' : 'text-danger'
    },
    statusText (status) {
      return status === 2 ? '正确' : '错误'
    }
  },
  components: { StatisticDashboard, AnswerStatusSheet },
  computed: {
    state () {
      return this.trial ? this.$store.state.trial : this.$store.state.exams
    },
    finished () {
      return this.$store.state.exams.finished
    },
    topic () {
      return this.state.topic
    },
    viewIndex () {
      return this.state.viewIndex
    },
    statistics () {
      return this.state.statistics
    },
    answerStatusList () {
      return this.state.answerStatusList
    },
    namespace () {
      return this.trial ? 'trial' : 'exams'
    },
    enableCancel () {
      return !this.view && !this.finished
    }
  },
  data () {
    const { id, units, name } = this.$decode(this.subject)
    return {
      id: id,
      units: units,
      subjectName: name
    }
  },
  async mounted () {
    if (this.view) {
      await this.getCompletedPractice()
    } else {
      await this.getPractice()
    }
  },
  methods: {
    jumQuestion (page) {
      return this.$store.dispatch(`${this.namespace}/jumQuestion`, page)
    },
    submitAnswer (answer) {
      return this.$store.dispatch(`${this.namespace}/submitAnswer`, answer)
    },
    getPractice () {
      return this.$store.dispatch(`${this.namespace}/getPractice`, { id: this.id, units: this.units })
    },
    getCompletedPractice () {
      return this.$store.dispatch('exams/getCompletedPractice', this.id)
    },
    clearPractice () {
      this.$store.dispatch(`${this.namespace}/clearPractice`)
    },
    handleSubmit (answer) {
      if (!answer) {
        this.$notify.error({ message: '请答题后再提交！' })
      } else {
        this.submitAnswer({ answer, id: this.id, unit: this.units })
      }
    },
    handleCancelPractice () {
      this.$confirm('如取消本次学习，需开启全新学习，是否确认取消？', '确认', {
        center: true,
        customClass: 'confirm-dialog',
        dangerouslyUseHTMLString: true
      }).then(() => {
        if (this.$store.state.user.isAuthenticated) {
          this.$store.dispatch('exams/cancelPractice', { practiceId: this.statistics.practiceId }).then(() => {
            this.$router.push('/learned/courses')
          })
        } else {
          this.$router.push('/courses')
        }
      })
    }
  },
  beforeDestroy () {
    this.clearPractice()
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.header-wrap {
  label {
    margin: 0 10px 0 30px;

    &:first-child { margin-left: 0;}
  }
}

.cancel-practice {
  float: right;
}

.list-wrap {
  background-color: $--color-white;

  .practice-question { border-top: none; }
}

.statistic-dashboard,
.answer-status-sheet {
  margin-top: 20px;
}

.answer-dashboard {
  margin: 20px 0 40px;
}

.answer-button-group {
  text-align: center;
  .el-button {
    min-width: 80px;
    margin-left: 150px;

    &:first-child {
      margin-left: 0;
    }
  }
}
</style>
<style lang="scss" scoped>
@media screen and (max-width: 430px ) {
  .list-wrap {
    padding: 0;
  }
  .answer-button-group .el-button {
    margin-left: 30px;
  }
}
</style>
